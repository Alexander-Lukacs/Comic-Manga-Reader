package com.example.besitzer.reader;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.besitzer.reader.Datenbank.Verzeichnis;
import com.example.besitzer.util.Directory;

import java.util.List;

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