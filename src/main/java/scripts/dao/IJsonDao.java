package scripts.dao;

import scripts.beans.ScriptJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Emil Kalbaliyev
 */
public interface IJsonDao {

    public List list() throws FileNotFoundException, IOException;

    public void write(List<ScriptJson> scriptList) throws JsonProcessingException, IOException;

    public List listAdmin() throws FileNotFoundException, IOException;

    public ScriptJson getNPC() throws FileNotFoundException, IOException;

    public void writeNPC(ScriptJson script) throws JsonProcessingException, IOException;
}
