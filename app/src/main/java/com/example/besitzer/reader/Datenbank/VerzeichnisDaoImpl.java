package com.example.besitzer.reader.Datenbank;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by robin on 22.03.18.
 */

public class VerzeichnisDaoImpl implements VerzeichnisDao {
    private Context context;

    public Dao<Opened, Integer> openedDao = null;
    OrmDbHelper helper; //= OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);
    public Dao<Verzeichnis, Integer> verzeichnisDao;
    public VerzeichnisDaoImpl(Context context)

    {

        helper = OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);

        try {
            verzeichnisDao = helper.getVerzeichnisDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
/**
 content://Verzeichnisse
 content://database/Verzeichnisse
 content://database/Opened
 content://database/Verzeichnisse/1
 content://database/Verzeichnisse/2
 android.permission.READ_
 **/

    }


    /**
     * add a Directory to Database
     * @param path     the path of the Directory
     * @param parentId the Id of the Parent_Directory
     * @param name     the name of the Directory
     * @param type     the type of the Directory
     * @param hasLaeves true if the Directory has children, else false
     */

    public void addDirectory(String path, int parentId, String name, int type, boolean hasLaeves)
    {
        Verzeichnis directory = new Verzeichnis();
        directory.setFilepath(path);
        directory.setParentId(parentId);
        directory.setFilename(name);
        directory.setFiletype(type);
        directory.setHasLeaves(hasLaeves);

        try
        {
            verzeichnisDao = helper.getVerzeichnisDao();
            verzeichnisDao.createOrUpdate(directory);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * return true, if the given path is in the Database
     * else return false
     * @param path
     * @return
     * @throws SQLException
     */

    public boolean findByPath(String path) throws SQLException
    {
        List<Verzeichnis> list;
        list = verzeichnisDao.queryForEq("Dateipfad", path);
        if (list.size() == 0)
        {
            return false;
        }else
        {
            return true;
        }

    }


    /**
     * return a directory from Database by a given path
     * @param path
     * @return
     * @throws SQLException
     */

    public Verzeichnis getByPath(String path)throws SQLException
    {
        List<Verzeichnis> list;
        Verzeichnis verzeichnis;
        list = verzeichnisDao.queryForEq("Dateipfad", path);
        verzeichnis = list.get(0);
        return verzeichnis;
    }


    /**
     * Return a List of Child_Directorys for a given Directory
     * @param verzeichnis
     * @return
     * @throws SQLException
     */
    public List<Verzeichnis> getChildren(Verzeichnis verzeichnis) throws SQLException
    {
        List<Verzeichnis> list;
       // QueryBuilder<Verzeichnis, Integer> queryBuilder = verzeichnisDao.queryBuilder();

        //queryBuilder.where().eq("ElterID", verzeichnis.getId());
        //list = queryBuilder.query();
        list = verzeichnisDao.queryForEq("ElterID", verzeichnis.getId());
        //queryBuilder.reset();


        return list;
    }

/**

    public void deleteOpened(Opened opened)
    {
        try
        {
            openedDao.deleteById(opened.getId());

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    public void updateOpened(Opened opened)
    {
        try
        {
            openedDao.createOrUpdate(opened);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

 **/
}
