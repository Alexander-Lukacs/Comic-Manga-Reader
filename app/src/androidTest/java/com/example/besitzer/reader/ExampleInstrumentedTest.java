package com.example.besitzer.reader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private OrmDbHelper helper = null;
    Context context;

    Dao<Verzeichnis, Integer> verzeichnisDao;
    Verzeichnis directory = new Verzeichnis();
    Verzeichnis directorychild_one = new Verzeichnis();
    Verzeichnis directorychild_two = new Verzeichnis();
    VerzeichnisDaoImpl dao;


    @Before
    public void setUp() throws Exception {

        context = InstrumentationRegistry.getTargetContext();
        helper = new OrmDbHelper(this.context);

        helper = OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);
        verzeichnisDao = helper.createVerzeichnisDao();
       // directory.setId(1);
        directory.setFilename("Psychopath");
        directory.setFilepath("ComicBooc/Psychopath");
        directory.setFiletype(1);
        directory.setHasLeaves(true);
        directory.setParentId(0);

        //   directorychild_one.setId(2);
        directorychild_one.setFilename("Psychopath");
        directorychild_one.setFilepath("ComicBooc/Psychopath/Chapter1");
        directorychild_one.setFiletype(1);
        directorychild_one.setHasLeaves(false);
        directorychild_one.setParentId(1);

        // directorychild_two.setId(3);
        directorychild_two.setFilename("Psychopath");
        directorychild_two.setFilepath("ComicBooc/Psychopath/Chapter2");
        directorychild_two.setFiletype(1);
        directorychild_two.setHasLeaves(false);
        directorychild_two.setParentId(1);

        dao = new VerzeichnisDaoImpl(context);
    }

    @Test
    public void addDirectory() throws Exception {
        dao.addDirectory(directory.getFilepath(), directory.getParentId(), directory.getFilename(), directory.getFiletype(), directory.getHasLeaves());


         Verzeichnis v2 = verzeichnisDao.queryForId(1);


         assertEquals("ComicBooc/Psychopath", v2.getFilepath());




    }

    @Test
    public void findByPath() throws Exception {
        int anzahl;
        boolean found;
        verzeichnisDao.createOrUpdate(directory);
        found = dao.findByPath(directory.getFilepath());

        assertEquals(true, found);
    }

    @Test
    public void testfindByPath() throws Exception{
        boolean found;
        verzeichnisDao.createOrUpdate(directory);
        found = dao.findByPath("ComicBooc/MOB");


        assertEquals(false, found);

    }
    @Test
    public void getByPath() throws Exception {
        verzeichnisDao.createOrUpdate(directory);

        Verzeichnis verzeichnis;
        verzeichnis = dao.getByPath("ComicBooc/Psychopath");


        assertEquals("ComicBooc/Psychopath", verzeichnis.getFilepath());
    }

  // liefert bei fest gesetzer Id für directory bei jedem neuen Durchlauf +2 --> nach 10 durchläufen also 20 Kinder
  // liegt vermutlich daran, dass das System bei jedem neu erstellten objekt eine neue ID vergibt.
    //Da die ID immer unterschiedlich ist, erzeugt create or Update jedes mal einen neuen Eintrag, anstatt ihn upzudaten
    @Test
    public void getChildren() throws Exception {
        int anzahl_children;
        verzeichnisDao.createOrUpdate(directory);
        verzeichnisDao.createOrUpdate(directorychild_one);
        verzeichnisDao.createOrUpdate(directorychild_two);

        List<Verzeichnis> list;
        list = dao.getChildren(directory);
        anzahl_children = list.size();
        assertEquals(2, anzahl_children);
    }


    @Test
    public void testegetByPath() throws Exception
    {

        verzeichnisDao.createOrUpdate(directory);
        Verzeichnis verzeichnis;
        verzeichnis = dao.getByPath(directory.getFilepath());
        assertEquals("ComicBooc/Psychopath", verzeichnis.getFilepath());

    }
}
