import com.example.besitzer.reader.Opened;
import com.example.besitzer.reader.Verzeichnis;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by robin on 22.03.18.
 */

public interface VerzeichnisDao {
    public void addDirectory(String path, int parentId, String name, int type, boolean hasLaeves);
    public boolean findByPath(String path)throws SQLException;
    public Verzeichnis getByPath(String path)throws SQLException;
    public List<Verzeichnis> getChildren(Verzeichnis verzeichnis)throws SQLException;
   // public void deleteOpened(Opened opened);
   // public void updateOpened(Opened opened);
}
