import android.support.test.InstrumentationRegistry;
import android.content.Context;
import org.junit.Before;
import org.junit.Test;

import com.example.besitzer.reader.OrmDbHelper;
import com.example.besitzer.reader.Verzeichnis;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import static org.junit.Assert.*;

/**
 * Created by robin on 23.03.18.
 */
public class VerzeichnisDaoTest {
    Context context = InstrumentationRegistry.getTargetContext();
    VerzeichnisDaoImpl dao = new VerzeichnisDaoImpl(context);
    Verzeichnis directory = new Verzeichnis();
    Dao<Verzeichnis, Integer> verzeichnisDao;
    OrmDbHelper helper = null;


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
    }

    @Test
    public void findByPath() throws Exception {
    }

    @Test
    public void getByPath() throws Exception {
        Verzeichnis verzeichnis;
        verzeichnisDao.createOrUpdate(directory);
        verzeichnis = dao.getByPath(directory.getFilepath());
        assertEquals("ComicBooc/Psychopath", verzeichnis.getFilepath());
    }

    @Test
    public void getChildren() throws Exception {
    }

}