package com.example.besitzer.logik;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.besitzer.reader.Datenbank.VerzeichnisDao;
import com.example.besitzer.reader.Datenbank.VerzeichnisDaoImpl;
import com.example.besitzer.util.Directory;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * this class implements the service that regularly calls the ComicDirectoryScanner functions.
 *
 */
public class ComicDirectoryScannerService extends Service {
    private Handler handler;
    private Runnable CDScannerDaemon;
    private String comicbookDirectory;//root directory where the comics are placed


    //TODO: performance tuning on these constants
    public static final int TIME_BETWEEN_SCANS = 60_000;//ms
    public static final int LAZY_FULL_RATIO = 10;//how many lazy scans are made before another pedantic one
    public static final int NOW = 0;//yes, I'm *that guy*

    public ComicDirectoryScannerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("CDScannerService", "before onCreate");
        //weise dem directory pfad einen wert zu
        comicbookDirectory = com.example.besitzer.util.ComicBookDirectoryFinder.getComicBookDirectoryPath();
        File comicDir = new File(comicbookDirectory);

        //erstelle das Directory, falls nonexistent
        if(!comicDir.exists()){
            if( !comicDir.mkdir() ){//versuche zu erstellen, falls elterverzeichnis fehlt erstelle das auch und logge.
                Log.e("CDScannerService", "tried to create comicDirectory in "
                        + comicbookDirectory + ", but parent didn't exist. creating parents too now.");
                if(!comicDir.mkdirs()){
                    Log.e("CDScannerService", "tried to create comicDirectory and parents in "
                            + comicbookDirectory + ", but failed at mkdirs() call. Severe Error, abandon ship.");
                }
            }
        }
        VerzeichnisDao dirdao = new VerzeichnisDaoImpl(getApplicationContext());
        try {
            if (!dirdao.findByPath(comicbookDirectory)){ //if first DB entry is nonexistant, create it!
                dirdao.addDirectory(
                        comicbookDirectory,
                        0,
                        FilenameUtils.getName(comicbookDirectory),
                        Directory.TYPE_DIRECTORY,
                        ComicDirectoryScanner.checkForLeaves(new File(comicbookDirectory))
                );
            }
        }catch(Throwable t){}
        LaunchDaemon();
        Log.v("CDScannerService", "after onCreate");
    }


    /**
     * starts the actual Comic Directory Scanner Daemon.
     * That's a runnable scheduled to be periodically run in the background.
     */
    private void LaunchDaemon(){
        handler = new Handler();
        CDScannerDaemon = new Runnable() {
            @Override
            public void run() {
                //what we do is
                //run a full scan
                //do LAZY_FULL_RATIO lazy scans, sleeping TIME_BETWEEN_SCANS inbetween them
                //tell the handler to reschedule all this in TIME_BETWEEN_SCANS ms
                Log.v("CDScannerservice", "launching fullscan...");
                ComicDirectoryScanner.FullScan(comicbookDirectory, getApplicationContext());
                Log.v("CDScannerservice", "fullscan complete!");

                for(int i = 0; i< LAZY_FULL_RATIO; i++){
                    try{ Thread.sleep((long)TIME_BETWEEN_SCANS); }catch(Throwable t){}
                    Log.v("CDScannerservice", "launching quickscan...");
                    ComicDirectoryScanner.QuickScan(comicbookDirectory, getApplicationContext());
                    Log.v("CDScannerservice", "quickscan complete!");
                }
                handler.postDelayed(this, TIME_BETWEEN_SCANS);
            }
        };
        //then we schedule that thing once. it will reschedule itself.
        handler.postDelayed(CDScannerDaemon, NOW);
    }

    /**
     * relaunches the CDScanner Daemon.
     * This causes an immediate FullScan to occur.
     * This method can be used to invoke a forced refresh from the Activity.
     */
    public void LaunchFullScan(){
        handler.removeCallbacks(CDScannerDaemon);
        handler.postDelayed(CDScannerDaemon, NOW);
    }

    public void LaunchFullScanOnSubDirectory(final String subDirectory){
        handler.removeCallbacks(CDScannerDaemon);
        Runnable subdirScanner = new Runnable() {
            @Override
            public void run() {
                ComicDirectoryScanner.FullScan(subDirectory, getApplicationContext());
                handler.postDelayed(CDScannerDaemon, TIME_BETWEEN_SCANS);
            }
        };
        handler.postDelayed(subdirScanner, NOW);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
