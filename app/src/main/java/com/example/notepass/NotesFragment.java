package com.example.notepass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class NotesFragment extends Fragment {

    public static final String NOTE_ID = "noteId";
    NotesDataBaseManager notesDataBaseManager;

    int[] _idValues;
    String[] titleNames;
    String[] noteNames;

    RecyclerView notesRecycler;

    TextView emptyView;
    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_save_note);
        super.onCreate(savedInstanceState);
        notesDataBaseManager = new NotesDataBaseManager(this.getContext());
        try {
            notesDataBaseManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View)inflater.inflate(R.layout.fragment_notes,container,false);
        Cursor cursor = notesDataBaseManager.fetch();
        _idValues = new int[cursor.getCount()];
        titleNames = new String[cursor.getCount()];
        noteNames = new String[cursor.getCount()];
        notesRecycler = (RecyclerView) view.findViewById(R.id.NotesRecycler);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
        if(cursor.getCount()==0){
            notesRecycler.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    _idValues[i] = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    //String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    titleNames[i] = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
                    //System.out.println(titleNames[i]);
                    noteNames[i] = cursor.getString(cursor.getColumnIndexOrThrow("NOTE"));
                    //System.out.println(noteNames[i]);
                    i++;
                } while (cursor.moveToNext());
                notesRecycler.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                notesCardAdapter adapter = new notesCardAdapter(_idValues,titleNames,noteNames);
                notesRecycler.setAdapter(adapter);
                notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            }
        }
        return view;
    }
}