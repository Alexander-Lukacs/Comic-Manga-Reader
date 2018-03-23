package com.example.besitzer.reader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.example.besitzer.reader.OrmDbHelper;
import com.example.besitzer.reader.Verzeichnis;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by robin on 23.03.18.
 */
public class VerzeichnisDaoImplTest {

    private OrmDbHelper helper = null;
    Context context;

    Dao<Verzeichnis, Integer> verzeichnisDao;
    Verzeichnis directory = new Verzeichnis();
    Verzeichnis directorychild_one = new Verzeichnis();
    Verzeichnis directorychild_two = new Verzeichnis();


    @Before
    public void setUp() throws Exception {

        context = InstrumentationRegistry.getTargetContext();
        helper = new OrmDbHelper(this.context);

        helper = OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);
        verzeichnisDao = helper.createVerzeichnisDao();
        //directory.setId(1);
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
    }

    @Test
    public void addDirectory() throws Exception {
        /**

        verzeichnisDao.createOrUpdate(directory);


        Verzeichnis v2 = verzeichnisDao.queryForId(1);


        assertEquals("ComicBooc/Psychopath", v2.getFilepath());
         **/
        VerzeichnisDao dao = new VerzeichnisDaoImpl(context.getApplicationContext());




    }

    @Test
    public void findByPath() throws Exception {
        int anzahl;
        boolean found;
        verzeichnisDao.createOrUpdate(directory);
        List<Verzeichnis> list;
        list = verzeichnisDao.queryForEq("Dateipfad", "ComicBooc/Psychopath");

        if (list.size() == 0)
        {
            found = false;
        }else
        {
            found = true;
        }
        //anzahl = list.size();
        assertEquals(true, found);
    }

    @Test
    public void testfindByPath() throws Exception{
        int anzahl;
        boolean found;
        verzeichnisDao.createOrUpdate(directory);
        List<Verzeichnis> list;
        list = verzeichnisDao.queryForEq("Dateipfad", "ComicBooc/Mob");

        if (list.size() == 0)
        {
            found = false;
        }else
        {
            found = true;
        }
        //anzahl = list.size();
        assertEquals(false, found);

    }
    @Test
    public void getByPath() throws Exception {
        verzeichnisDao.createOrUpdate(directory);
        List<Verzeichnis> list;
        Verzeichnis verzeichnis;
        list = verzeichnisDao.queryForEq("Dateipfad", "ComicBooc/Psychopath");
        verzeichnis = list.get(0);

        assertEquals("ComicBooc/Psychopath", verzeichnis.getFilepath());
    }

    @Test
    public void getChildren() throws Exception {
        int anzahl_children;
        verzeichnisDao.createOrUpdate(directory);
        verzeichnisDao.createOrUpdate(directorychild_one);
        verzeichnisDao.createOrUpdate(directorychild_two);

        List<Verzeichnis> list;
        QueryBuilder<Verzeichnis, Integer> queryBuilder = verzeichnisDao.queryBuilder();

        queryBuilder.where().eq("ElterID", directory.getParentId());
        list = queryBuilder.query();
       // list = verzeichnisDao.queryForEq("ElterID", directory.getId());
        anzahl_children = list.size();
        assertEquals(2, anzahl_children);
    }


    @Test
    public void testegetByPath() throws Exception
    {
        VerzeichnisDaoImpl dao = new VerzeichnisDaoImpl(context);
        verzeichnisDao.createOrUpdate(directory);
        Verzeichnis verzeichnis;
        verzeichnis = dao.getByPath(directory.getFilepath());
       // verzeichnis = helper.getByPath(directory.getFilepath());
        assertEquals("ComicBooc/Psychopath", verzeichnis.getFilepath());

    }


}