package com.example.besitzer.logik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.besitzer.reader.Datenbank.Opened;
import com.example.besitzer.reader.Datenbank.OpenedDao;
import com.example.besitzer.reader.Datenbank.OpenedDaoImpl;
import com.example.besitzer.reader.Datenbank.Verzeichnis;
import com.example.besitzer.reader.MainActivity;
import com.example.besitzer.reader.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.Inflater;


public class BrowserListAdapter extends BaseAdapter{

    private Context context;
    private List<Verzeichnis> directories;
    private List<Opened> openedStats;
    private LayoutInflater aufpuster;
    private MainActivity mainActivity;

    public BrowserListAdapter(Context context, List<Verzeichnis> directories, MainActivity activity){
        this(context, directories, findOpenedStates(directories, context), activity);
    }

    private static List<Opened> findOpenedStates(List<Verzeichnis> directories, Context context) {
        List<Opened> list=new ArrayList<Opened>();
        OpenedDao opdao = new OpenedDaoImpl(context);

        for(Verzeichnis v : directories){
            try {
                list.add(opdao.getOrCreateOpenedStateById(v.getId()));
            } catch (SQLException e) {
                Log.e("BrowserListAdapter", "in findOpenedStates: "+e.toString());
            }
        }


        return list;
    }

    public BrowserListAdapter(Context context, List<Verzeichnis> directories, List<Opened> openedStats, MainActivity activity) {
        this.context = context;
        this.directories=directories;
        this.openedStats=openedStats;
        this.mainActivity=activity;
        aufpuster = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View view, ViewGroup parent) {
        Log.v("BrowserListAdapter", "BrowserListAdapter.getView() was called!");
        //component declarations
        LayoutInflater inflater=aufpuster;
        View rowView=inflater.inflate(R.layout.browser_list_item, parent,false);
        final Verzeichnis dir = directories.get(position);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.browser_list_item_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.browser_list_item_image);

        //component assignments
        switch(openedStats.get(position).getState()){
            case com.example.besitzer.util.Opened.UNREAD: rowView.setBackgroundColor(R.color.unread);
            case com.example.besitzer.util.Opened.PARTIALLY_READ: rowView.setBackgroundColor(R.color.partiallyRead);
            case com.example.besitzer.util.Opened.READ: rowView.setBackgroundColor(R.color.read);
            case com.example.besitzer.util.Opened.UNREADABLE: rowView.setBackgroundColor(R.color.unreadable);
        }
        txtTitle.setText(dir.getFilename());
        if(dir.getHasLeaves()){
            imageView.setImageResource(R.drawable.resource);
        }else{
            imageView.setImageResource(R.drawable.directory);
        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("BrowserListAdapter", "opening: "+dir.getFilepath());
                mainActivity.recreateOnDirectory(dir);
            }
        });
        return rowView;
    }


    @Override
    public int getCount() {
        return directories.size();
    }

    @Override
    public Object getItem(int position) {
        return directories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
