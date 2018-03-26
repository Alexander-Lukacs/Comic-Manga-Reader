package com.example.besitzer.util;

import android.os.Environment;

import java.io.File;

/**
 * this is nothing but a central spot to ask for the location of the comicbook directory
 * that path is used on multiple occasions, so it's a good idea to put the call in a single spot
 */
public class ComicBookDirectoryFinder {
    private static final String DIRECTORY_NAME = "/Comics/";
    public static String getComicBookDirectoryPath(){

        //entspricht aktuell "/Documents/Comics"
        File comicbookDirectoryLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return comicbookDirectoryLocation.getAbsolutePath() + DIRECTORY_NAME;
    }

}
