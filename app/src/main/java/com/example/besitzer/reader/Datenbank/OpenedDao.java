package com.example.besitzer.reader.Datenbank;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by robin on 22.03.18.
 */

public interface OpenedDao {
    /**
     * creates a new entry in the opened table
     */
    public void addOpened(int id, int state, int timestamp, Verzeichnis filepath);

    /**
     * checks wether a given directory (defined by ID) already has an opened entry
     */
    public boolean findById(int directoryId)throws SQLException;

    /**
     * returns the Opened entry for a given directory (defined by ID)
     */
    public Opened getById(int directoryId)throws SQLException;

    /**
     * returns a list of the opened states of the children of a directory
     * @param verzeichnis
     */
    public List<Opened> getChildren(Verzeichnis verzeichnis)throws SQLException;

    /**
     * sets the opened state of a given directory to "read"
     */
    public void setRead(int directoryId)throws SQLException;

    /**
     * sets the opened state of a given directory to "partially read"
     */
    public void setPartiallyRead(int directoryId)throws SQLException;

    /**
     * sets the opened state of a given directory to "unread"
     */
    public void setUnRead(int directoryId)throws SQLException;

    /**
     * sets the opened state of a given directory to "unreadable"
     */
    public void setUnreadable(int directoryId)throws SQLException;
}
