package com.example.besitzer.logik;

import java.util.List;

/**
 * This class provides functions that are used when scanning the designated comic directory
 * for changes (new files? old files deleted?)
 *
 * At several occasions the "last proper scan" is mentioned.
 * A proper scan consists of searching the comic directory for changes
 * and copying these changes to the database.
 *
 * These scans are implemented in the Methods "FullScan" and "QuickScan" of this Class.
 * The differences between a QuickScan and a FullScan are explained in the method documentations.
 *
 */

public class ComicDirectoryScanner {

    /**
     * For a given directory Path and a list of its known(saved in the database) direct children,
     * this function returns all direct children of that directory, which aren't known.
     *
     * @param directory the directory to scan for unknown children
     * @param knownChildren the list of known direct children of a directory
     * @return list of children that were newly created since the last proper scan
     */
    private static List<String> findUnknownChildren(String directory, List<String> knownChildren){
        List<String> unknownChildren = findChildren(directory); // 1. get list of children of the directory
        for(String child : unknownChildren){    // 2. remove all the known children from that list
            if(knownChildren.contains(child)){
                unknownChildren.remove(child);
            }
        }
        return unknownChildren; // 3. return the list
    }

    /**
     * For a given directory Path and a list of its known(saved in the database) direct children,
     * this function returns all known children which are no longer present.
     *
     * @param directory the directory to scan for unknown children
     * @param knownChildren the list of known direct children of a directory
     * @return list of children that were deleted since the last proper scan
     */
    private static List<String> findRemovedChildren(String directory, List<String> knownChildren){
        List<String> RemovedChildren = knownChildren; // rename the parameter
        List<String> currentChildren = findChildren(directory); // 1. get the current list of children
        for (String child : RemovedChildren) { // 2. remove all the children that haven't been deleted
            if(currentChildren.contains(child)){
                RemovedChildren.remove(child);
            }
        }
        return RemovedChildren; // 3. return the list
    }

    /**
     * For a given directory Path and a list of its known(saved in the database) direct and indirect
     * children, this function returns all direct children of that directory, which aren't known.
     *
     * @param directory the directory to scan for unknown children
     * @param knownChildren the list of known direct or indirect children of a directory
     * @return list of children that were newly created since the last proper scan
     */
    private static List<String> findUnknownChildrenRecursive(String directory, List<String> knownChildren){
        return null;
        //TODO: implement
    }

    /**
     * returns all direct child directories of a given directory
     * @param directory the given directory path
     * @return list of current child directories
     */
    private static List<String> findChildren(String directory){
        return null;
        //TODO: implement
    }

    /**
     * For a given directory (usually the comic reader directory), recursively scan for added or
     * removed files and carry over those changes into the database
     *
     * WARNING: this method is expensive. It consumes large amounts of time and memory for large
     *          directory structures. Do not call more often than necessary.
     *
     * @param directory the directory to scan and carry over into the database
     */
    public static void FullScan(String directory){
        //TODO: implement
    }

    /**
     * For a given directory (usually the comic reader directory), recursively scan for added or
     * removed files, and if any are found, call Fullscan for the relevant directory.
     * WARNING: this scan is lazy and might miss changes
     * If any changes are detected, Fullscan is invoked for the given directory.
     * This Method is NOT meant to replace Fullscan completely.
     * QuickScan's scan accuracy is limited for the sake of performance.
     *
     * To keep the Database accurate, one must sometimes manually call FullScan.
     * This is designed to be done from the DatabaseService Class.
     * @param directory
     */
    public static void QuickScan(String directory){
        //TODO: implement
    }


}
