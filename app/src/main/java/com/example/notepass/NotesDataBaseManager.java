package com.example.notepass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.core.database.CursorKt;

import java.sql.SQLDataException;

public class NotesDataBaseManager {
    private NotesDataBaseHelper dataBaseHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public NotesDataBaseManager(Context context){
        this.context = context;
    }

    public NotesDataBaseManager open() throws SQLDataException{
        dataBaseHelper = new NotesDataBaseHelper(context);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public void insert(String title, String note) {
        ContentValues noteValues = new ContentValues();
        noteValues.put("TITLE", title);
        noteValues.put("NOTE", note);
        sqLiteDatabase.insert("NOTES", null, noteValues);
    }

    public Cursor fetch(){
        Cursor cursor = sqLiteDatabase.query("NOTES", new String[]{"_id","TITLE","NOTE"},null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchWhere(String noteId){
        Cursor cursor = sqLiteDatabase.query("NOTES",new String[]{"_id","TITLE","NOTE"},"_id=?",new String[]{noteId},null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(String noteId, String title, String note){
        ContentValues noteValues = new ContentValues();
        noteValues.put("TITLE", title);
        noteValues.put("NOTE", note);
        sqLiteDatabase.update("NOTES",noteValues,"_id=?",new String[]{noteId});
    }

    public void delete(String noteId){
        sqLiteDatabase.delete("NOTES","_id=?",new String[]{noteId});
    }

    public void deleteAll(){
        sqLiteDatabase.delete("NOTES",null,null);
    }
}
