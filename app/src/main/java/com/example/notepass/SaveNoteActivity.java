package com.example.notepass;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.DayOfWeek;
import java.util.Objects;

public class SaveNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "noteId";
    TextInputEditText title;
    TextInputEditText note;
    NotesDataBaseManager notesDataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        notesDataBaseManager = new NotesDataBaseManager(this);
        try {
            notesDataBaseManager.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        int noteId;
        try {
            noteId = (Integer)getIntent().getExtras().get(EXTRA_NOTE_ID);
            Cursor cursor = notesDataBaseManager.fetchWhere(String.valueOf(noteId));
            String noteTitle = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String noteNote = cursor.getString(cursor.getColumnIndexOrThrow("NOTE"));
            title = findViewById(R.id.saveTitle);
            note = findViewById(R.id.saveNote);
            title.setText(noteTitle);
            note.setText(noteNote);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void saveNote(View view){
        title = findViewById(R.id.saveTitle);
        note = findViewById(R.id.saveNote);
        int noteId;
        String Message;
        try {
            noteId = (Integer)getIntent().getExtras().get(EXTRA_NOTE_ID);
            notesDataBaseManager.update(String.valueOf(noteId),title.getText().toString(),note.getText().toString());
            Message = "Note Updated Successfully!";
        }catch (NullPointerException e){
            notesDataBaseManager.insert(title.getText().toString(), note.getText().toString());
            Message = "Note Saved Successfully!";
        }
        Toast.makeText(SaveNoteActivity.this,Message,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}


//        Cursor cursor = notesDataBaseManager.fetch();
//        if(cursor.moveToFirst()){
//            do {
//                String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
//                String Title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
//                String Note = cursor.getString(cursor.getColumnIndexOrThrow("NOTE"));
//                System.out.println("I have Read ID " + id+" Title: "+Title+ " Note: "+Note);
//                Log.i("DATABASE TAG", "I have Read ID " + id+" Title: "+Title+ " Note: "+Note);
//            }while (cursor.moveToNext());
//        }