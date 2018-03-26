package com.example.besitzer.reader.Datenbank;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;
/**
 * Created by robin on 26.03.18.
 */
public class OpenedDaoImpl implements OpenedDao {
    private Context context;
    public Dao<Verzeichnis, Integer> verzeichnisDao;
    public Dao<Opened, Integer> openedDao = null;
    OrmDbHelper helper;
    public OpenedDaoImpl(Context context)
    {
        helper = OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);
        try {
            openedDao = helper.getOpenedDao();
            verzeichnisDao = helper.getVerzeichnisDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * creates a new entry in the opened table
     */
    public void addOpened(int id, int state, int timestamp, Verzeichnis filepath)
    {
        Opened opened = new Opened();
        opened.setId(id);
        opened.setState(state);
        opened.setTimestamp(timestamp);
        opened.setFilepath(filepath);
        try
        {
            openedDao = helper.getOpenedDao();
            openedDao.createOrUpdate(opened);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * checks wether a given directory (defined by ID) already has an opened entry
     */
    public boolean findById(int directoryId)throws SQLException
    {   Opened opened;
        boolean found;
        opened = openedDao.queryForId(directoryId);
        found = openedDao.idExists(directoryId);
        return found;
    }
    /**
     * returns the Opened entry for a given directory (defined by ID)
     */
    public Opened getById(int directoryId)throws SQLException
    {
        Opened opened;
        opened = openedDao.queryForId(directoryId);
        return opened;
    }
    /**
     * returns a list of the opened states of the children of a directory
     * @param verzeichnis
     */
    public List<Opened> getChildren(Verzeichnis verzeichnis)throws SQLException
    { Verzeichnis directory;
        Opened opened;
        List<Verzeichnis> list_children;
        List<Opened> opened_states = new ArrayList<Opened>();
        list_children = verzeichnisDao.queryForEq("ElterID", verzeichnis.getId());
        for(int i=0; i<list_children.size(); i++)
        {
            directory = list_children.get(i);
            opened = openedDao.queryForId(directory.getId());
            opened_states.add(opened);
        }
        return opened_states;
    }
    /**
     * sets the opened state of a given directory to "read"
     */
    public void setRead(int directoryId)throws SQLException
    {
        Verzeichnis directory;
        Opened opened;
        opened = openedDao.queryForId(directoryId);
        opened.setState(com.example.besitzer.util.Opened.READ);
        openedDao.update(opened);
    }
    /**
     * sets the opened state of a given directory to "partially read"
     */
    public void setPartiallyRead(int directoryId)throws SQLException
    {
        Verzeichnis directory;
        Opened opened;
        opened = openedDao.queryForId(directoryId);
        opened.setState(com.example.besitzer.util.Opened.PARTIALLY_READ);
        openedDao.update(opened);
    }
    /**
     * sets the opened state of a given directory to "unread"
     */
    public void setUnRead(int directoryId)throws SQLException
    {
        Verzeichnis directory;
        Opened opened;
        opened = openedDao.queryForId(directoryId);
        opened.setState(com.example.besitzer.util.Opened.UNREAD);
        openedDao.update(opened);
    }
    /**
     * sets the opened state of a given directory to "unreadable"
     */
    public void setUnreadable(int directoryId)throws SQLException
    {
        Verzeichnis directory;
        Opened opened;
        opened = openedDao.queryForId(directoryId);
        opened.setState(com.example.besitzer.util.Opened.UNREADABLE);
        openedDao.update(opened);
    }
}