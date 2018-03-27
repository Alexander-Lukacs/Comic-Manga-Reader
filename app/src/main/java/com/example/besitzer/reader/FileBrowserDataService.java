package com.example.besitzer.reader;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.besitzer.reader.Datenbank.Verzeichnis;
import com.example.besitzer.reader.Datenbank.VerzeichnisDao;
import com.example.besitzer.reader.Datenbank.VerzeichnisDaoImpl;
import com.example.besitzer.util.ComicBookDirectoryFinder;
import com.example.besitzer.util.Directory;

import java.sql.SQLException;
import java.util.List;

/**
 * dynamic singleton storage for data used by the mainactivity and the vieweractivity
 */
public class FileBrowserDataService extends Service {

    private List<Verzeichnis> children;
    private Verzeichnis position;
    private final IBinder mBinder = new LocalBinder();



    /**
     * binder to get an instance of the service in the activity
     */
    public class LocalBinder extends Binder {
        FileBrowserDataService getService() {
            return FileBrowserDataService.this;
        }
    }

    @Override
    public void onCreate() {
        VerzeichnisDao dirdao = new VerzeichnisDaoImpl(getApplicationContext());
        try {
            this.position = dirdao.getByPath(ComicBookDirectoryFinder.getComicBookDirectoryPath());
            this.children = dirdao.getChildren(position);

        } catch (SQLException e) {
            Log.e("DataService", "comicbook directory doesn't exist in the db!");
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public FileBrowserDataService() {
    }

    public void setData(List<Verzeichnis> children, Verzeichnis position) {
        this.children = children;
        this.position = position;
    }

    public List<Verzeichnis> getChildren() {
        return children;
    }

    public Verzeichnis getPosition() {
        return position;
    }
}