package scripts.dao;

import scripts.beans.ScriptJson;
import scripts.beans.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emil Kalbaliyev
 */
@Repository
public class JsonDao implements IJsonDao {

    @Value("${path.to.json}")
    private String jsonPath;
  @Value("${path.to.admin.json}")
    private String adminJsonPath;
    @Value("${path.to.NPCjson}")
    private String NPCjsonPath;

    @Override
    public List list() throws FileNotFoundException, IOException {
        List<ScriptJson> scripts = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        scripts = mapper.readValue(new File(jsonPath), new TypeReference<List<ScriptJson>>() {
        });
        return scripts;
    }

    @Override
    public void write(List<ScriptJson> scriptList) throws JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String listToJson = objectMapper.writeValueAsString(scriptList);
        BufferedWriter writer = new BufferedWriter(new FileWriter(jsonPath));
        writer.write(listToJson);
        writer.close();

    }

    @Override
    public List listAdmin() throws FileNotFoundException, IOException {
        List<User> users = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        users = mapper.readValue(new File(adminJsonPath), new TypeReference<List<User>>() {
        });
        return users;
    }

    @Override
    public ScriptJson getNPC() throws FileNotFoundException, IOException {
        ScriptJson scripts = new ScriptJson();
        ObjectMapper mapper = new ObjectMapper();
        scripts = mapper.readValue(new File( NPCjsonPath), new TypeReference<ScriptJson>() {
        });
        return scripts;
    }

    @Override
    public void writeNPC(ScriptJson script) throws JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String listToJson = objectMapper.writeValueAsString(script);
        BufferedWriter writer = new BufferedWriter(new FileWriter( NPCjsonPath));
        writer.write(listToJson);
        writer.close();

    }
}
