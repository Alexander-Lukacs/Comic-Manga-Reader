package com.example.besitzer.logik;

import android.content.Context;
import android.util.Log;

import com.example.besitzer.reader.Datenbank.VerzeichnisDao;
import com.example.besitzer.reader.Datenbank.VerzeichnisDaoImpl;
import com.example.besitzer.util.ComicBookDirectoryFinder;
import com.example.besitzer.util.Directory;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * This class provides functions that are used when scanning the designated comic directory
 * for changes (new files? old files deleted?)
 * <p>
 * At several occasions the "last proper scan" is mentioned.
 * A proper scan consists of searching the comic directory for changes
 * and copying these changes to the database.
 * <p>
 * These scans are implemented in the Methods "FullScan" and "QuickScan" of this Class.
 * The differences between a QuickScan and a FullScan are explained in the method documentations.
 */

public class ComicDirectoryScanner {

    /**
     * For a given directory (usually the comic reader directory), recursively scan for added or
     * removed files and carry over those changes into the database
     * <p>
     * WARNING: this method is expensive. It consumes large amounts of time and memory for large
     * directory structures. Do not call more often than necessary.
     *
     * @param directory the !ABSOLUTE PATH! to scan and carry over into the database
     */
    public static void FullScan(String directory, Context context) {
        VerzeichnisDao dirdao = new VerzeichnisDaoImpl(context);

        File dirFile = new File(directory);

        boolean isInDB = false;
        try {
            isInDB = dirdao.findByPath(dirFile.getAbsolutePath());
        } catch (SQLException e) {
            Log.e("ComicDirectoryScanner", e.toString());
        }
        if (dirFile != null) {
            if (isInDB) {//we're good, keep scanning the children

                if (dirFile != null) {
                    if (dirFile.isDirectory()) {
                        File[] children = dirFile.listFiles();
                        if (children.length == 0) {//directory is empty. should we do something?
                            //nah...
                        } else {
                            for (File child : children) {
                                FullScan(child.getAbsolutePath(), context);
                            }
                        }
                    } else {
                        if (dirFile.isFile()) {//we're a file. get the extension and compare to DB

                        } else {//spooky error! we're neither file nor directory
                            Log.e("ComicDirectoryScanner",
                                    "File in path \""
                                            + directory
                                            + "\" is apparently neither file nor directory");
                        }
                    }
                }
            } else {//directory doesn't exist in DB, add it!
                try {
                    dirdao.addDirectory(
                            directory,
                            dirdao.getByPath(dirFile.getParent()).getId(),
                            FilenameUtils.getName(directory),
                            Directory.extensionToType(directory),
                            checkForLeaves(dirFile)
                    );
                    if (dirFile.isDirectory() && !directory.equals(ComicBookDirectoryFinder.getComicBookDirectoryPath())) {
                        dirdao.setHasLeaves(dirFile.getParent(), false);
                    }
                }catch (SQLException e) {
                    if(directory.equals(ComicBookDirectoryFinder.getComicBookDirectoryPath())){
                        dirdao.addDirectory(
                                directory,
                                0,
                                FilenameUtils.getName(directory),
                                Directory.extensionToType(directory),
                                checkForLeaves(dirFile));
                    }else {
                        Log.e("ComicDirectoryScanner", "this happened: " + e.toString());
                    }
                }
            }
        }
    }



    /**
     * checks wether the "hasleaves property for a given directory should be true.
     * that is defined to be true when the directory does not have any child directories, only files or nothing.
     *
     * @param directory
     * @return hasleaves
     */
    public static boolean checkForLeaves(File directory) {
        boolean hasleaves = true; //unless we find a directory in the children, we're good.
        if (directory.listFiles() != null) {
            for (File child : directory.listFiles()) {
                if (child.isDirectory()) {
                    hasleaves = false;
                }
            }
        }
        return hasleaves;
    }


    /**
     * For a given directory (usually the comic reader directory), recursively scan for added or
     * removed files, and if any are found, call Fullscan for the relevant directory.
     * WARNING: this scan is lazy and might miss changes
     * If any changes are detected, Fullscan is invoked for the given directory.
     * This Method is NOT meant to replace Fullscan completely.
     * QuickScan's scan accuracy is limited for the sake of performance.
     * <p>
     * To keep the Database accurate, one must sometimes manually call FullScan.
     * This is designed to be done from the DatabaseService Class.
     *
     * @param directory
     */

    public static void QuickScan(String directory, Context context) {
        VerzeichnisDao dirdao = new VerzeichnisDaoImpl(context);
        File dirFile = new File(directory);
        boolean isInDB = false;
        try {
            isInDB = dirdao.findByPath(dirFile.getAbsolutePath());
        } catch (SQLException e) {
            Log.e("ComicDirectoryScanner", e.toString());
        }
        if (dirFile != null) {
            if (isInDB) {//if entry is already in DB)

                return;
            } else { //if it's not in the DB we have to fullscan.
                FullScan(directory, context);
            }
            if (dirFile.isDirectory()) {
                for (File child : dirFile.listFiles()) {
                    QuickScan(child.getAbsolutePath(), context);
                }
            } else {
                return;
            }
        }

    }


}
