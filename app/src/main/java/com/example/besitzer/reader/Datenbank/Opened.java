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



    public Opened()
    {

    }
    public Opened(int id, int state, int timestamp)
    {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;

    }

    public void setId(int id) {this.id = id;}

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


}
