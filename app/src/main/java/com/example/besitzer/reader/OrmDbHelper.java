package com.example.besitzer.reader;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import android.content.*;


import java.sql.SQLException;

/**
 * Created by robin on 20.03.18.
 */

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    public static final String LOG = OrmDbHelper.class.getName();
    public static final String DB_NAME = "MangaReader.db";
    public static final int DB_VERSION = 1;



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

            onCreate(sqLiteDatabase, source);
        } catch (SQLException e)
          {
              Log.e(OrmDbHelper.class.getName(), "Can´t drop databases", e);
              throw new RuntimeException(e);
          }
    }



 private void handleVerzeichnisse() throws SQLException
 {

     // create Data Acces Object for Verzeichnisse
     final Dao<Verzeichnis, Integer> VerzeichnisDAO = this.createVerzeichnisDAO();

     //querry all Directorys from db
      final List<Verzeichnis> allDirectorys = VerzeichnisDAO.
 }


    @Override
    public void close ()
    {
        super.close();

    }
}
