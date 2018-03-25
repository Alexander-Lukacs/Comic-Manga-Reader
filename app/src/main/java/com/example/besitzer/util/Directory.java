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


    /**
     *
     */
    public Directory newFromPath(String path){
        //search for that path in the database
        //if it exists, get that directory from the database
        //if it doesn't exist:
        // -create an entry in the database for that directory
        // -get that directory from the database
        //return the retrieved directory.
        return null;
        //TODO: DAO method
    }

    /**
     * Full constructor.
     * Private because object creation should only occur through the database,
     * to ensure that ids and paths are unique
     */
    private Directory
    (
            int id,
            String path,
            int parentId,
            String name,
            int fileType,
            boolean hasLeaves
    ){
        this.id=id;
        this.path=path;
        this.parentId=parentId;
        this.name=name;
        this.fileType=fileType;
        this.hasLeaves=hasLeaves;
    }

    /**
     * retrieves a Directory from the Database with the Directory path as key.
     * @param path the path of the directory to retrieve
     * @return the directory as object of this class
     */
    public static Directory getByPath(String path){
        //TODO: DAO method
        return null;
    }

    /**
     * writes the Contents of the Directory Object back to the Database.
     */
    public void writeDown(){
        //TODO: DAO method
    }


}
