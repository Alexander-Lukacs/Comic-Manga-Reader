package com.example.besitzer.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * This is a Class that contains every interesting information about a Directory/Archive
 *
 * The purpose of this class is to marshall between the Database, the Logic and the GUI,
 * But also to provide some helpful functions.
 */

public class Directory {
    private int id;
    private String path;
    private int parentId;
    private String name;
    private int fileType;
    private boolean hasLeaves;

    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_DIRECTORY = 1;
    public static final int TYPE_RAR = 2;
    public static final int TYPE_ZIP = 3;
    public static final int TYPE_7ZIP=4;
    public static final int TYPE_PNG=5;
    public static final int TYPE_JPG=6;
    public static final int TYPE_BMP=7;
    public static final int TYPE_JPEG=8;
    public static final int TYPE_CBR=9;
    public static final int TYPE_CBZ=10;
    public static final int TYPE_CB7=11;

    /**
     * turns a file path into the proper file extension integer
     */
    public static int extensionToType(String directory){
        File dirfile = new File(directory);
        if(dirfile.isDirectory()){
            return TYPE_DIRECTORY;
        }
        String extension = FilenameUtils.getExtension(directory);
        extension=extension.toLowerCase();
        int result = 0;
        switch (extension){
            case "rar":  return TYPE_RAR;
            case "zip":  return TYPE_ZIP;
            case "7z":   return TYPE_7ZIP;
            case "png":  return TYPE_PNG;
            case "jpg":  return TYPE_JPG;
            case "bmp":  return TYPE_BMP;
            case "jpeg": return TYPE_JPEG;
            case "cbr":  return TYPE_CBR;
            case "cbz":  return TYPE_CBZ;
            case "cb7":  return TYPE_CB7;
            default:     return TYPE_UNKNOWN;
        }
    }
    public static boolean isImage(int type){
        switch(type){
            case TYPE_BMP: return true;
            case TYPE_JPEG: return true;
            case TYPE_JPG: return true;
            case TYPE_PNG: return true;
            default: return false;
        }
    }





}
