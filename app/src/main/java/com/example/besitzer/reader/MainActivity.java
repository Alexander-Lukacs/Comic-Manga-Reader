package com.example.besitzer.reader;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.besitzer.logik.BrowserListAdapter;

import com.example.besitzer.logik.ComicDirectoryScannerService;
import com.example.besitzer.reader.Datenbank.OpenedDao;
import com.example.besitzer.reader.Datenbank.OpenedDaoImpl;
import com.example.besitzer.reader.Datenbank.VerzeichnisDao;
import com.example.besitzer.reader.Datenbank.VerzeichnisDaoImpl;
import com.example.besitzer.reader.R;
import com.example.besitzer.util.ComicBookDirectoryFinder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Activity thisActivity = this;
    private VerzeichnisDao daodir;
    private OpenedDao daoopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1
        );
        daoopen=new OpenedDaoImpl(getApplicationContext());
        daodir=new VerzeichnisDaoImpl(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(! isServiceRunning(ComicDirectoryScannerService.class)){
            getApplicationContext().startService(new Intent(this, ComicDirectoryScannerService.class));
        }
        setSupportActionBar(toolbar);

        openDirectory(ComicBookDirectoryFinder.getComicBookDirectoryPath());

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
        ListView view;
        view = (ListView) findViewById(R.id.browser_list);
        //old: String [] array = new String[0];
        List werte=null;
        try {
             werte = daodir.getChildren(daodir.getByPath(ComicBookDirectoryFinder.getComicBookDirectoryPath()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //old: ArrayList<String> werte = new ArrayList(Arrays.asList(array));
        if( (werte != null) ){
            if(werte.size() >=0){
                ArrayAdapter adapter = new ArrayAdapter(this, R.layout.browser_list_item, R.id.browser_list_item_text, werte);

                view.setAdapter(adapter);
            }
        }

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

}
