package com.example.besitzer.reader;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.besitzer.logik.BrowserListAdapter;

import com.example.besitzer.logik.ComicDirectoryScannerService;
import com.example.besitzer.reader.Datenbank.OpenedDao;
import com.example.besitzer.reader.Datenbank.OpenedDaoImpl;
import com.example.besitzer.reader.Datenbank.Verzeichnis;
import com.example.besitzer.reader.Datenbank.VerzeichnisDao;
import com.example.besitzer.reader.Datenbank.VerzeichnisDaoImpl;
import com.example.besitzer.reader.R;
import com.example.besitzer.util.ComicBookDirectoryFinder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivity thisActivity = this;
    private VerzeichnisDao daodir;
    private OpenedDao daoopen;

    private FileBrowserDataService fileBrowserDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1
        );

        Log.v("MainActivityCreation", "before Dao Creation");
        daoopen=new OpenedDaoImpl(getApplicationContext());
        daodir=new VerzeichnisDaoImpl(getApplicationContext());

        Log.v("MainActivityCreation", "before super.onCreate()");
        super.onCreate(savedInstanceState);

        Log.v("MainActivityCreation", "before service Launch");
        if(! isServiceRunning(ComicDirectoryScannerService.class)){
            getApplicationContext().startService(new Intent(this, ComicDirectoryScannerService.class));
        }
        bindService(
                new Intent(MainActivity.this, FileBrowserDataService.class),
                mConnection,
                Context.BIND_AUTO_CREATE
        );


        /*
        String [] itemname = {
                "Adidas - Kurs: 73,45 €",
                "Allianz - Kurs: 145,12 €"
        };

        Integer [] imgid = {
                R.drawable.directory,
                R.drawable.resource
        };

        ListView view;
        view =(ListView)findViewById(R.id.browser_list);
        BrowserListAdapter adapter = new BrowserListAdapter(this, itemname, imgid);
        view.setAdapter(adapter);
        */
        //---------------------------------------------------------------------------------------
    }


    public void openDirectory(String path){
        Log.v("openDirectory", "start of openDirectory()");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView view;
        view = (ListView) findViewById(R.id.browser_list);
        //old: String [] array = new String[0];
        List werte=null;
        Log.v("openDirectory", "before dao Call");
        try {
             werte = daodir.getChildren(daodir.getByPath(ComicBookDirectoryFinder.getComicBookDirectoryPath()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.v("openDirectory", "after dao call, before adapter call()");
        //old: ArrayList<String> werte = new ArrayList(Arrays.asList(array));
        if( (werte != null) ){
            if(werte.size() >=0){
                //ArrayAdapter adapter = new ArrayAdapter(this, R.layout.browser_list_item, R.id.browser_list_item_text, werte);
                BrowserListAdapter adapter = new BrowserListAdapter(getApplicationContext(), werte, this.thisActivity);
                view.setAdapter(adapter);
            }
        }

        Log.v("openDirectory", "after adapter call");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            Intent intent = new Intent(thisActivity, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * checks wether a given service is running
     */
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void recreateOnDirectory(Verzeichnis directory){
        if(!directory.getHasLeaves()) {//if it has directories inside
            try {
                fileBrowserDataService.setData(daodir.getChildren(directory), directory);
            } catch (SQLException e) {
                Log.e("MainActivity", "in recreateOnDirectory(" + directory.getFilepath() + ") there was an SQLException:" + e.toString());
            }
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }else{//if it doesn't have directories inside
            startActivity(new Intent(MainActivity.this, ViewerActivity.class));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            fileBrowserDataService = ((FileBrowserDataService.LocalBinder)service).getService();
            Log.v("MainActivityCreation", "before openDirectory()");
            openDirectory(fileBrowserDataService.getPosition().getFilepath());
            Log.v("MainActivity", "FileBrowserDataService connected");
        }
        public void onServiceDisconnected(ComponentName className) {
            fileBrowserDataService = null;
            Log.v("MainActivity", "FileBrowserDataService disconnected");
        }
    };

}
