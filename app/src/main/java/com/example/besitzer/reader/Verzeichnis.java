package com.example.besitzer.reader;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="Verzeichnis")
public class Verzeichnis {
    @DatabaseField(generatedId = true, columnName = "ID")
    private int id;
    @DatabaseField(columnName = "Dateipfad")
    private String filepath;
    @DatabaseField(columnName = "ElterID")
    private int parentId;
    @DatabaseField(columnName = "Dateiname")
    private String filename;
    @DatabaseField(columnName = "Dateityp")
    private int filetype;
    @DatabaseField(columnName = "hat-Blätter")
    private boolean hasLeaves;


    public Verzeichnis() {


    }

    public Verzeichnis(int id, String filepath, int parentId, String filename, int filetype, boolean hasLeaves)
    {
        this.id = id;
        this.filepath = filepath;
        this.parentId = parentId;
        this.filename = filename;
        this.filetype = filetype;
        this.hasLeaves = hasLeaves;

    }

    public int getId(){return id;}

    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public int getParentId()
    {
        return parentId;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFiletype(int filetype)
    {
        this.filetype = filetype;
    }

    public int getFiletype ()
    {
        return filetype;
    }

    public void setHasLeaves(boolean hasLeaves)
    {
        this.hasLeaves = hasLeaves;
    }

    public boolean getHasLeaves()
    {
        return hasLeaves;
    }
}