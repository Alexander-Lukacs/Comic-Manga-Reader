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
import android.widget.ListView;

import com.example.besitzer.logik.BrowserListAdapter;

import com.example.besitzer.logik.ComicDirectoryScannerService;
import com.example.besitzer.reader.R;

public class MainActivity extends AppCompatActivity {

    private Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(! isServiceRunning(ComicDirectoryScannerService.class)){
            getApplicationContext().startService(new Intent(this, ComicDirectoryScannerService.class));
        }
        setSupportActionBar(toolbar);

        //---------------------------------------------------------------------------------------
        // Test(Mockdaten) für die ListView -> Alex bitte für die Logik entfernen
        /*ListView view;
        view = (ListView) findViewById(R.id.browser_list);
        String [] array = {
                "Adidas - Kurs: 73,45 €",
                "Allianz - Kurs: 145,12 €",
                "BASF - Kurs: 84,27 €",
                "Bayer - Kurs: 128,60 €",
                "Beiersdorf - Kurs: 80,55 €",
                "BMW St. - Kurs: 104,11 €",
                "Commerzbank - Kurs: 12,47 €",
                "Continental - Kurs: 209,94 €",
                "Daimler - Kurs: 84,33 €"
        };
        ArrayList<String> werte = new ArrayList(Arrays.asList(array));
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.browser_list_item, R.id.browser_list_item_text, werte);
        view.setAdapter(adapter);*/

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

        //---------------------------------------------------------------------------------------
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
