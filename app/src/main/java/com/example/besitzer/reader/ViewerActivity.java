package com.example.besitzer.reader;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

public class ViewerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView page;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private int currentPage = 0;
    private int maxPage;
    private SeekBar pageBar;
    int[] images={R.drawable.resource, R.drawable.directory, R.mipmap.ic_launcher, R.drawable.resource, R.mipmap.ic_launcher_round};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewer);

        page = (ImageView) findViewById(R.id.image_view) ;
        page.setImageResource(images[currentPage]);
        maxPage = images.length-1;

        btnPrevious = (ImageButton) findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);

        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

        pageBar = (SeekBar) findViewById(R.id.seek_bar);
        pageBar.setMax(maxPage);

        /**
         * SeekBar to display the current state of the image number.
         */
        pageBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentPage, boolean fromUser) {
                page.setImageResource(images[currentPage]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

            case R.id.btn_previous:
                if(!(currentPage == 0))
                    currentPage--;
                    currentPage = currentPage % images.length;
                    page.setImageResource(images[currentPage]);
                break;

            case R.id.btn_next:
                if(!(currentPage >= maxPage)){
                    currentPage++;
                    currentPage = currentPage % images.length;
                    page.setImageResource(images[currentPage]);
                }
                break;

            default:
                break;
        }
    }
}
