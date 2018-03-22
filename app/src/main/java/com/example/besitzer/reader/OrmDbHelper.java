package com.example.besitzer.reader;

import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.List;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;



import java.sql.SQLException;

/**
 * Created by robin on 20.03.18.
 */

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    public static final String LOG = OrmDbHelper.class.getName();
    public static final String DB_NAME = "MangaReader.db";
    public static final int DB_VERSION = 1;

    //the DAO object which is using to access the table Verzeichnis
    public Dao<Verzeichnis, Integer> verzeichnisDao = null;
    private RuntimeExceptionDao<Verzeichnis, Integer> verzeichnisRuntimeDao = null;

    // the DAO object which is using to access the table Opened
    private Dao<Opened, Integer> openedDao = null;
    private RuntimeExceptionDao<Opened, Integer> openedRuntimeDao = null;




     //private OrmDbHelper dbHelper = null;





    public OrmDbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource source)
    {
        try {
            Log.i(OrmDbHelper.class.getSimpleName(), "onCreate()");
            TableUtils.createTable(source, Verzeichnis.class);
            TableUtils.createTable(source, Opened.class);

        }catch (SQLException ex)
        {
            Log.e(LOG, "error creating tables", ex);
            throw new RuntimeException(ex);
        }
    }






   /** try {
        Dao<Opened, Integer> meineDao = DaoManager.createDao(connectionSource, Opened.class);
        return meineDao;
    } catch (SQLException ex)
    {
        Log.e(LOG, "error creating DAO for Opened class", ex);
    }
     return null;

    **/




  // Create Data Access Object for Opened
    public Dao<Opened, Integer> createOpenedDAO() {
        try {
            return DaoManager.createDao(connectionSource, Opened.class);
        } catch (SQLException ex) {
            Log.e(LOG, "error creating DAO for Opened class", ex);
        }
        return null;


    }

    public Dao<Verzeichnis, Integer> createVerzeichnisDao()
    {
        try {
            return DaoManager.createDao(connectionSource, Verzeichnis.class);
        } catch (SQLException ex) {
            Log.e(LOG, "error creating DAO for Verzeichnis class", ex);
        }
        return null;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource source, int oldVersion, int newVersion)
    {
        try {
            Log.i(OrmDbHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(source, Verzeichnis.class, true);
            TableUtils.dropTable(source, Opened.class, true);
            onCreate(sqLiteDatabase, source);
        } catch (SQLException e)
          {
              Log.e(OrmDbHelper.class.getName(), "Can´t drop databases", e);
              throw new RuntimeException(e);
          }
    }

    /**
    // Create Data Access Object for Verzeichnis
    public Dao<Verzeichnis, Integer> createVerzeichnisDAO()
    {
        try{
            return DaoManager.createDao(connectionSource, Verzeichnis.class);
        }catch (SQLException ex)
        {
            Log.e(LOG, " error creating DAO for Verzeichnis class", ex);
        }
        return null;
    }

     **/

    public Dao<Verzeichnis, Integer> getVerzeichnisDao() throws SQLException
    {
        if (verzeichnisDao == null)
        {
            verzeichnisDao = getDao(Verzeichnis.class);
        }
        return verzeichnisDao;
    }

    public RuntimeExceptionDao<Verzeichnis, Integer> getVerzeichnisRuntimeDao()
    {
        if(verzeichnisRuntimeDao == null) verzeichnisRuntimeDao = getRuntimeExceptionDao(Verzeichnis.class);
        return verzeichnisRuntimeDao;
    }




    public Dao<Opened, Integer> getOpenedDao() throws SQLException
    {
        if (openedDao == null)
        {
            openedDao = getDao(Opened.class);
        }
        return openedDao;
    }

    public RuntimeExceptionDao<Opened, Integer> getOpenedRuntimeDao()
    {
        if(openedRuntimeDao == null) openedRuntimeDao = getRuntimeExceptionDao(Opened.class);
        return openedRuntimeDao;
    }

/**
 private void handleVerzeichnisse() throws SQLException
 {    //Beispiel id
       int id;
       id = 5;
     // create Data Acces Object for Verzeichnisse
     final Dao<Verzeichnis, Integer> VerzeichnisDAO = this.createVerzeichnisDAO();

     //querry all Directorys from db
      final List<Verzeichnis> allDirectorys = VerzeichnisDAO.queryForAll();

      //create new verzeichnis
      Verzeichnis verzeichnis = new Verzeichnis();
      VerzeichnisDAO.create(verzeichnis);

      //delete verzeichnis element with id = 5
     VerzeichnisDAO.deleteById(5);

     //upadate Verzeichnis element
     VerzeichnisDAO.update(verzeichnis);

    // VerzeichnisDAO.deleteIds(Collection<Integer> collection)

    // Verzeichnis eintrag = VerzeichnisDAO.queryForId(id);

   //  VerzeichnisDAO.query()
            // VerzeichnisDAO.query()

   // Verzeichnis zahl = VerzeichnisDAO.queryForId(4);

 }

**/
    @Override
    public void close ()
    {
        super.close();
        verzeichnisDao = null;
        verzeichnisRuntimeDao = null;
        openedDao = null;
    }

    /*

     Die folgenden Funktionen ist eine Beispielfunktionen

    public void addDirectory()
    {
        Verzeichnis verzeichnis = new Verzeichnis();
        verzeichnis.setFilename("");
        verzeichnis.setFilepath("String/String");
        verzeichnis.setFiletype(1);
        verzeichnis.setHasLeaves(true);
        verzeichnis.setParentId(2);

        try
        {
            verzeichnisDao.createOrUpdate(verzeichnis);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateDirectory(Verzeichnis verzeichnis)
    {
        try
        {
            verzeichnisDao.createOrUpdate(verzeichnis);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteDirectory(Verzeichnis verzeichnis)
    {
        try
        {
            verzeichnisDao.deleteById(verzeichnis.getId());


        }catch (SQLException e)
         {
            e.printStackTrace();
         }
    }
    private OrmDbHelper getDbHelper()
    {
        if (dbHelper == null)
        {
            dbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return dbHelper;
    }

    public void updateDirectory(Verzeichnis verzeichnis)
    {
     try
      {
        verzeichnisDao.update(verzeichnis);
        } catch (SQLException e)
         {
           e.printStackTrace();
         }
 **/
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

    /**
     * Return a List of Child_Directorys for a given Directory
     * @param verzeichnis
     * @return
     * @throws SQLException
     */
    public List<Verzeichnis> getChildren(Verzeichnis verzeichnis) throws SQLException
         {
             List<Verzeichnis> list;
             QueryBuilder<Verzeichnis, Integer> queryBuilder = verzeichnisDao.queryBuilder();

          //   list= verzeichnisDao.queryforEq("ElterID", verzeichnis.getParentId());
            queryBuilder.where().eq("ElterID", verzeichnis.getParentId());
            list = queryBuilder.query();
            //queryBuilder.reset();


             return list;
         }

    /**
     * return a directory from Database by a given path
     * @param path
     * @return
     * @throws SQLException
     */
         public Verzeichnis getByPath(String path) throws SQLException
         {
             List<Verzeichnis> list;
             Verzeichnis verzeichnis;
             list = verzeichnisDao.queryForEq("Dateipfad", path);
           //  QueryBuilder<Verzeichnis, Integer> queryBuilder = verzeichnisDao.queryBuilder();
            // queryBuilder.where().eq("Dateipfad", path);
            // list = queryBuilder.query();

            verzeichnis = list.get(0);
            return verzeichnis;
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
                 verzeichnisDao.createOrUpdate(directory);
             } catch (SQLException e)
               {
                 e.printStackTrace();
               }
         }
}
