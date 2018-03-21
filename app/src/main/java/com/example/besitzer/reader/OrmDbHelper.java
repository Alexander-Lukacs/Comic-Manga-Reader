package com.example.besitzer.reader;

import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
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
 //   private Dao<Verzeichnis, Integer> verzeichnisDao = null;
  //  private RuntimeExceptionDao<Verzeichnis, Integer> verzeichnisRuntimeDao = null;

    // the DAO object which is using to access the table Opened
  //  private Dao<Opened, Integer> openedDao = null;
  //  private RuntimeExceptionDao<Opened, Integer> openedRuntimeDao = null;




     private OrmDbHelper dbHelper = null;





    public OrmDbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource source)
    {
        try {
            TableUtils.createTable(source, Verzeichnis.class);
            TableUtils.createTable(source, Opened.class);

        }catch (SQLException ex)
        {
            Log.e(LOG, "error creating tables", ex);
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
    public Dao<Opened, Integer> createOpenedDAO()
    {
     try {
         return DaoManager.createDao(connectionSource, Opened.class);
     } catch (SQLException ex)
     {
         Log.e(LOG, "error creating DAO for Opened class", ex);
     }
     return null;


    }



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


    public Dao<Verzeichnis, Integer> getVerzeichnisDao() throws SQLException
    {
        if (verzeichnisDao == null)
        {
            verzeichnisDao = getDao(Verzeichnis.class);
        }
        return verzeichnisDao;
    }



    public Dao<Opened, Integer> getOpenedDao() throws SQLException
    {
        if (openedDao == null)
        {
            openedDao = getDao(Opened.class);
        }
        return openedDao;
    }



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


    @Override
    public void close ()
    {
        super.close();
        verzeichnisDao = null;
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
 **/
}
