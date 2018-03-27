package com.example.besitzer.reader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.besitzer.reader.Datenbank.Verzeichnis;
import com.example.besitzer.util.Directory;

import java.util.List;

public class FileBrowserDataService extends Service {

    private List<Verzeichnis> children;
    private Verzeichnis position;

    public FileBrowserDataService() {
    }

    public void setData(List<Verzeichnis> children, Verzeichnis position){
        this.children=children;
        this.position=position;
    }

    public List<Verzeichnis> getChildren(){
        return children;
    }
    public Verzeichnis getPosition(){
        return position;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
