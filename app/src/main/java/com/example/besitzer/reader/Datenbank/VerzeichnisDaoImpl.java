package com.example.besitzer.reader.Datenbank;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.apache.commons.io.FilenameUtils;

/**
 * Created by robin on 22.03.18.
 */

public class VerzeichnisDaoImpl implements VerzeichnisDao {
    @Override
    public void setHasLeaves(int directoryId, boolean hasLeaves) throws SQLException {
        Verzeichnis directory;
        directory = verzeichnisDao.queryForId(directoryId);
        directory.setHasLeaves(hasLeaves);
        verzeichnisDao.update(directory);
    }

    @Override
    public void setHasLeaves(String path, boolean hasLeaves) throws SQLException {
        if(!findByPath(path)){
            throw new SQLException("SQLException on call: getByPath("+path+"): " + "The Directory with the path\""+ path + "\" does not exist in the database. Can't set hasLeaves property.");
        }
        Verzeichnis directory = getByPath(path);
        directory.setHasLeaves(hasLeaves);
        verzeichnisDao.update(directory);
    }

    private Context context;

    public Dao<Opened, Integer> openedDao = null;
    OrmDbHelper helper; //= OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);
    public Dao<Verzeichnis, Integer> verzeichnisDao;
    public VerzeichnisDaoImpl(Context context)

    {

        helper = OpenHelperManager.getHelper(context.getApplicationContext(), OrmDbHelper.class);

        try {
            verzeichnisDao = helper.getVerzeichnisDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * add a Directory to Database
     * @param path     the path of the Directory
     * @param parentId the Id of the Parent_Directory
     * @param name     the name of the Directory
     * @param type     the type of the Directory
     * @param hasLeaves true if the Directory has children, else false
     */

    public void addDirectory(String path, int parentId, String name, int type, boolean hasLeaves)
    {
        Verzeichnis directory = new Verzeichnis();
        directory.setFilepath(path);
        directory.setParentId(parentId);
        directory.setFilename(name);
        directory.setFiletype(type);
        directory.setHasLeaves(hasLeaves);

        try
        {
            verzeichnisDao = helper.getVerzeichnisDao();
            verzeichnisDao.createOrUpdate(directory);
        }catch (SQLException e)
        {
            Log.e(
                    "VerzeichnisDaoImpl",
                    "in addDirectory("
                            +path
                            +","
                            +parentId
                            +","
                            +name
                            +","
                            +type
                            +","
                            +hasLeaves
                            +");"
                            +" there was an SQLException:"
                            + e.toString()
            );
        }
    }


    /**
     * return true, if the given path is in the Database
     * else return false
     * @param path
     * @return
     * @throws SQLException
     */

    public boolean findByPath(String path) throws SQLException
    {
        List<Verzeichnis> inlist;
        List<Verzeichnis> outlist=new ArrayList<Verzeichnis>();
        Verzeichnis verzeichnis;
        //list = verzeichnisDao.queryForEq("Dateipfad", path);
        inlist = verzeichnisDao.queryForAll();
        if(inlist != null && inlist.size()>0){
            for (Verzeichnis v : inlist){
                if(new File(path).equals( new File(v.getFilepath()))){
                    outlist.add(v);
                }else{
                    //Log.w("verzeichnisdao", path+" doesn't equal "+v.getFilepath());
                }
            }
        }else{
            if(inlist==null){
                Log.w("verzeichnisdao", "on call getByPath("+path+") inlist is null");
            }else if(inlist.size()<=0){
                Log.w("verzeichnisdao", "on call getByPath("+path+") inlist length is 0");
            }
        }
        if(outlist==null||outlist.size()<=0){
            return false;
        }else{
            return true;
        }


    }


    /**
     * return a directory from Database by a given path
     * @param path
     * @return
     * @throws SQLException
     */

    public Verzeichnis getByPath(String path)throws SQLException
    {
        List<Verzeichnis> inlist;
        List<Verzeichnis> outlist=new ArrayList<Verzeichnis>();
        Verzeichnis verzeichnis;
        //list = verzeichnisDao.queryForEq("Dateipfad", path);
        inlist = verzeichnisDao.queryForAll();
        if(inlist != null && inlist.size()>0){
            for (Verzeichnis v : inlist){
                if(new File(path).equals( new File(v.getFilepath()))){
                    outlist.add(v);
                }else{
                    //Log.w("verzeichnisdao", path+" doesn't equal "+v.getFilepath());
                }
            }
        }else{
            if(inlist==null){
                Log.w("verzeichnisdao", "on call getByPath("+path+") inlist is null");
            }else if(inlist.size()<=0){
                Log.w("verzeichnisdao", "on call getByPath("+path+") inlist length is 0");
            }
        }

        if(outlist.size()<=0){
            throw new SQLException("SQLException on call: getByPath("+path+"):" + " The directory \"" +path+ "\" doesn't exist in the DB.");
        }
        verzeichnis = outlist.get(0);
        return verzeichnis;
    }




    /**
     * Return a List of Child_Directorys for a given Directory
     * @param verzeichnis
     * @return
     * @throws SQLException
     */
    public List<Verzeichnis> getChildren(Verzeichnis verzeichnis) throws SQLException
    {
        List<Verzeichnis> list;
       // QueryBuilder<Verzeichnis, Integer> queryBuilder = verzeichnisDao.queryBuilder();

        //queryBuilder.where().eq("ElterID", verzeichnis.getId());
        //list = queryBuilder.query();
        list = verzeichnisDao.queryForEq("ElterID", verzeichnis.getId());
        //queryBuilder.reset();


        return list;
    }

    /**
     * logs the whole directory table into the android debug log.
     */
    public void debugLogTable(){
        List<Verzeichnis> list=null;
        try {
            list = verzeichnisDao.queryForAll();
        } catch (SQLException e) {
            Log.d("debuglogtable", "sqlexception during directory db dump: "+e.toString());
            return;
        }
        Log.d("debuglogtable", "starting directory db dump");
        for(Verzeichnis v : list){
            Log.d("debuglogtable", v.toString());
        }
    }


}
