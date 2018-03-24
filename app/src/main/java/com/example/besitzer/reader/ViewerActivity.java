package com.example.besitzer.reader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class ViewerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        ImageButton btnPrevious = (ImageButton) findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);

        ImageButton btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

        //SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        //seekBar.setOnSeekBarChangeListener(this);
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
     *
     * @param view the previous image
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_previous:
                // do your code
                break;

            case R.id.btn_next:
                // do your code
                break;

            default:
                break;
        }

        /**
         * SeekBar to display the current state of the image number.
         *
         * @param "Number" from the current image
         * @return current state of the image number
         */
        /*public void onProgressChanged (){

        }

        public void onStartTrackingTouch (){

        }

        public void onStopTrackingTouch (){

        }*/
    }
}
