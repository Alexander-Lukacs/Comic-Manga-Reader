package com.example.besitzer.reader.Datenbank;



import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Opened")
public class Opened {
    @DatabaseField(generatedId = true, columnName = "ID")
    private int id;
    @DatabaseField(columnName = "Status")
    private int state;
    @DatabaseField(columnName = "Zeitpunkt")
    private int timestamp;
    @DatabaseField(canBeNull=false, foreign=true, foreignAutoRefresh = true, columnName = "Dateipfad")
    private Verzeichnis filepath;


    public Opened()
    {

    }
    public Opened(int id, int state, int timestamp, Verzeichnis filepath)
    {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.filepath = filepath;
    }

    public int getId(){return id;}

    public void setState(int state)
    {
        this.state = state;
    }

    public int getState ()
    {
        return state;
    }

    public void setTimestamp(int timestamp)
    {
        this.timestamp = timestamp;
    }

    public int getTimestamp()
    {
        return timestamp;
    }

    public void setFilepath(Verzeichnis filepath)
    {
        this.filepath = filepath;
    }

    public Verzeichnis getFilepath()
    {
        return filepath;
    }
}
