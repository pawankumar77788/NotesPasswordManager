package com.example.notepass;

import android.app.AlertDialog;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class notesCardAdapter extends RecyclerView.Adapter<notesCardAdapter.ViewHolder> {
    private String[] Title;
    private String[] Note;

    private int[] _id;
    Toast deleteToast;
    AlertDialog.Builder builder;
    NotesDataBaseManager notesDataBaseManager;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    public notesCardAdapter(int[] _id, String[] Title, String[] Note){
        this._id = _id;
        this.Title = Title;
        this.Note = Note;
    }
    @Override
    public int getItemCount(){
        return Title.length;
    }

    @Override
    public notesCardAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notescard, parent, false);
        notesDataBaseManager = new NotesDataBaseManager(parent.getContext());
        deleteToast = Toast.makeText(parent.getContext(),"Deleted Successfully!",Toast.LENGTH_SHORT);
        builder = new AlertDialog.Builder(parent.getContext());
        try {
            notesDataBaseManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardView cardView = holder.cardView;
        TextView titleTextView = (TextView)cardView.findViewById(R.id.noteTitle);
        TextView noteTextView = (TextView)cardView.findViewById(R.id.noteInfo);
        titleTextView.setText(Title[position]);
        noteTextView.setText(Note[position]);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cardView.getContext(),SaveNoteActivity.class);
                intent.putExtra(SaveNoteActivity.EXTRA_NOTE_ID,_id[holder.getAdapterPosition()]);
                cardView.getContext().startActivity(intent);
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                builder.setMessage("Delete Note ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    notesDataBaseManager.delete(String.valueOf(_id[holder.getAdapterPosition()]));
                    if(getItemCount()!=0){
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),getItemCount());
                    }
                    deleteToast.show();
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

    }
}
