package com.example.besitzer.logik;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.util.List;

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
    public static final int LAZY_FULL_RATIO = 5;//how many lazy scans are made before another pedantic one
    public static final int NOW = 0;//yes, I'm *that guy*

    public ComicDirectoryScannerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO: ComicDirectory Wert zuweisen!!!
        LaunchDaemon();
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
                ComicDirectoryScanner.FullScan(comicbookDirectory);
                for(int i = 0; i< LAZY_FULL_RATIO; i++){
                    try{ Thread.sleep((long)TIME_BETWEEN_SCANS); }catch(Throwable t){}
                    ComicDirectoryScanner.QuickScan(comicbookDirectory);
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
                ComicDirectoryScanner.FullScan(subDirectory);
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
