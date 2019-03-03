package scripts.controller;

import scripts.beans.Script;
import scripts.beans.ScriptJson;
import scripts.beans.User;
import scripts.service.MainService;
import scripts.protocols.TelnetPClient;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Emil Kalbaliyev
 */
@Controller
public class MainController {

    @Autowired
    MainService mainService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        String url = "";
        HttpSession session = request.getSession(true);

        if (null == session.getAttribute("telnet")) {
            url = "redirect:/login";
        } else {
            TelnetPClient tel = (TelnetPClient) session.getAttribute("telnet");
            if (tel.telnet.isConnected()) {
                url = "redirect:/index";
            } else {
                url = "redirect:/login";

            }
        }

        return url;
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String script(HttpServletRequest request, Map<String, Object> model) throws IOException, Exception {
        String url = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (null == session.getAttribute("telnet")) {
            url = "redirect:/login";
        } else {
            TelnetPClient telnet = (TelnetPClient) session.getAttribute("telnet");
            if (telnet.telnet.isConnected()) {
                mainService.refresh(telnet, user);
                List<Script> scripts = new ArrayList<>();
                scripts = mainService.listScripts();
                model.put("scripts", scripts);
                url = "scripts";
            } else {
                url = "redirect:/login";

            }
        }
        return url;
    }

    @PostMapping(value = "/start/{id}")
    public String start(HttpServletRequest request, @PathVariable int id, @RequestParam("addParam") String param) throws IOException, Exception {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        TelnetPClient telnet = new TelnetPClient("ip goes here", user.getUsername(), user.getPassword());
        mainService.start(id, param, telnet, user);
        return "redirect:/index";
    }

    

    @GetMapping(value = "/download/{id}")
    public void download2(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) throws IOException, Exception {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        File file = null;
        if (id != 1997) {
             file = mainService.download(id, user);
        } 
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/force-download";
            }
            response.setContentType(mimeType);
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");//fileName);
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        String url = "";
        HttpSession session = request.getSession();
        if (null == session.getAttribute("telnet")) {
            url = "login";
        } else {
            TelnetPClient telnet = (TelnetPClient) session.getAttribute("telnet");
            if (telnet.telnet.isConnected()) {
                url = "redirect:/index";
            } else {
                url = "login";
            }
        }
        return url;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        String url = "";
        HttpSession session = request.getSession();
        TelnetPClient telnet = (TelnetPClient) session.getAttribute("telnet");
        telnet.disconnect();
        session.removeAttribute("telnet");
        url = "redirect:/login";
        return url;
    }

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    public String loginCheck(Map<String, Object> model, HttpServletRequest request, @ModelAttribute("user") User user) {
        String url = "";
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 60);
        TelnetPClient telnet = new TelnetPClient("ip goes here", user.getUsername(), user.getPassword());
        if (telnet.loginSuccess == true) {
            session.setAttribute("telnet", telnet);
            session.setAttribute("user", user);
            url = "redirect:/index";
        } else {
            model.put("error", "Your username and password is invalid.");
            url = "login";
        }
        return url;
    }

    @RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
    public String adminLogin(HttpServletRequest request) {
        String url = "";
        HttpSession session = request.getSession();
        if (null == session.getAttribute("admin")) {
            url = "adminLogin";
        } else {
            url = "redirect:/admin";
        }
        return url;
    }

    @RequestMapping(value = "/adminlogincheck", method = RequestMethod.POST)
    public String adminLoginCheck(Map<String, Object> model, HttpServletRequest request, @ModelAttribute("user") User user) throws IOException {
        String url = "";
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 60);
        if (mainService.adminLogin(user) == false) {
            model.put("error", "Your username and password is invalid.");
            url = "adminLogin";
        } else {
            session.setAttribute("admin", user);
            url = "redirect:/admin";
        }
        return url;
    }

    @RequestMapping(value = "/adminlogout", method = RequestMethod.GET)
    public String adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        return "redirect:/adminLogin";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, Map<String, Object> model) throws IOException, Exception {
        String url = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (null == session.getAttribute("admin")) {
            url = "redirect:/adminLogin";
        } else {
            List<ScriptJson> scripts = new ArrayList<>();
            scripts = mainService.listScriptAdmin();
            model.put("scripts", scripts);
            url = "admin";
        }
        return url;
    }

    @GetMapping("/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable int id) throws IOException {
        String url = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (null == session.getAttribute("admin")) {
            url = "redirect:/adminLogin";
        } else {
            mainService.deleteFromJson(id);
            url = "redirect:/admin";
        }
        return url;
    }

    @RequestMapping(path = "edit/{id}", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @PathVariable int id, Map<String, Object> model) throws IOException {
        String url = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (null == session.getAttribute("admin")) {
            url = "redirect:/adminLogin";
        } else {
            ScriptJson scripts = mainService.find(id);
            model.put("script", scripts);
            url = "edit";
        }
        return url;
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute ScriptJson s, HttpServletRequest request) throws IOException {
        String url = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (null == session.getAttribute("admin")) {
            url = "redirect:/adminLogin";
        } else {
            mainService.save(s);
            url = "redirect:/admin";
        }
        return url;

    }

    @RequestMapping(path = "add", method = RequestMethod.GET)
    public String add(HttpServletRequest request, Map<String, Object> model) throws IOException {
        String url = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (null == session.getAttribute("telnet")) {
            url = "redirect:/adminLogin";
        } else {
            int id = mainService.findNextID();
            model.put("id", id);
            url = "addNew";
        }
        return url;
    }
}
