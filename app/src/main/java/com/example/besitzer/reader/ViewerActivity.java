package com.example.besitzer.reader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besitzer.logik.ComicDirectoryScannerService;
import com.example.besitzer.reader.Datenbank.Verzeichnis;
import com.example.besitzer.reader.Datenbank.VerzeichnisDao;
import com.example.besitzer.util.Directory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewerActivity extends AppCompatActivity implements View.OnClickListener {

    private final Handler handler = new Handler();

    private ImageView page;
    private TextView headerPage;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private int currentPage = 0;
    private int maxPage;
    private FileBrowserDataService fileBrowserDataService;
    //int[] images={R.drawable.resource, R.drawable.directory, R.mipmap.ic_launcher, R.drawable.resource, R.mipmap.ic_launcher_round};
    List<Verzeichnis> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindService(
                new Intent(ViewerActivity.this, FileBrowserDataService.class),
                mConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            fileBrowserDataService = ((FileBrowserDataService.LocalBinder)service).getService();
            images = new ArrayList<Verzeichnis>();
            List <Verzeichnis> files = fileBrowserDataService.getChildren();
            for(Verzeichnis v :files){
                if(!Directory.isImage(v.getFiletype())){
                    Log.e("ViewerActivity", v.getFilepath()+" can not be converted to Bitmap");
                }else{
                    images.add(v);
                }
            }
            if(images.size()<=0){//no images in here. whoops!
                Toast.makeText(getApplicationContext(), "no images found :( wait some time and try again.", Toast.LENGTH_LONG).show();
                finish();
            }else {
                Log.v("ViewerActivity", "images.size:" + images.size() + " currentpage:" + currentPage);
                setContentView(R.layout.viewer);

                page = (ImageView) findViewById(R.id.image_view);
                page.setImageBitmap(BitmapFactory.decodeFile(images.get(currentPage).getFilepath()));
                maxPage = images.size() - 1;

                headerPage = (TextView) findViewById(R.id.page_number);
                headerPage.setText("[ " + (currentPage + 1) + " / " + images.size() + " ]");

                btnPrevious = (ImageButton) findViewById(R.id.btn_previous);
                btnPrevious.setOnClickListener(ViewerActivity.this);

                btnNext = (ImageButton) findViewById(R.id.btn_next);
                btnNext.setOnClickListener(ViewerActivity.this);

                Log.v("ViewerActivity", "FileBrowserDataService connected");
            }
        }
        public void onServiceDisconnected(ComponentName className) {
            fileBrowserDataService = null;
            Log.v("ViewerActivity", "FileBrowserDataService disconnected");
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    /**
     * Function to switch to the next or previous image with a swipe.
     *
     * @param "ImageID" from the previous image
     * @param "ImageID" form the next image
     * @return and display the previous or next image
     */
    //TODO

    /**
     * Switch-Case to change the current image to the previous or next image.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //TODO: read/unread state!
            case R.id.btn_previous:
                if(!(currentPage == 0))
                    currentPage--;
                    currentPage = currentPage % images.size();
                    page.setImageBitmap(BitmapFactory.decodeFile(images.get(currentPage).getFilepath()));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            headerPage.setText("[ " + (currentPage+1) + " / " + images.size() + " ]");
                        }
                    });
                break;

            case R.id.btn_next:
                if(!(currentPage >= maxPage)){
                    currentPage++;
                    currentPage = currentPage % images.size();
                    page.setImageBitmap(BitmapFactory.decodeFile(images.get(currentPage).getFilepath()));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            headerPage.setText("[ " + (currentPage+1) + " / " + images.size() + " ]");
                        }
                    });
                }
                break;

            default:
                break;
        }
    }
}
