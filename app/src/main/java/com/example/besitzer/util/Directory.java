package com.example.besitzer.util;

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
