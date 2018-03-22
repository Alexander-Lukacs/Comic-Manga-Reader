import org.junit.Before;
import org.junit.Test;
import android.content.Context;
import com.example.besitzer.reader.OrmDbHelper;
import com.example.besitzer.reader.Verzeichnis;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;


import static org.junit.Assert.*;

/**
 * Created by robin on 22.03.18.
 */
public class VerzeichnisDaoImplTest {

    private OrmDbHelper helper = null;
    Context context;

    Dao<Verzeichnis, Integer> verzeichnisDao;
    Verzeichnis directory = new Verzeichnis();


    @Before
    public void setUp() throws Exception {


        helper = new OrmDbHelper(this.context);

        helper = OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);
        verzeichnisDao = helper.createVerzeichnisDao();

        directory.setFilename("Psychopath");
        directory.setFilepath("ComicBooc/Psychopath");
        directory.setFiletype(1);
        directory.setHasLeaves(true);
        directory.setParentId(0);
    }

    @Test
    public void addDirectory() throws Exception {
        verzeichnisDao.createOrUpdate(directory);
        Verzeichnis v2 = verzeichnisDao.queryForId(1);
        assertEquals("ComicBooc/Psychopath", v2.getFilepath());
    }

    @Test
    public void findByPath() throws Exception {
    }

    @Test
    public void getByPath() throws Exception {
    }

    @Test
    public void getChildren() throws Exception {
    }

}