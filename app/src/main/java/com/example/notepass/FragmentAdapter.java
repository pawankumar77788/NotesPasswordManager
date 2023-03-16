package com.example.notepass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        //Fragment fragment = null;
        //Bundle args = new Bundle();
        if (position == 0){
            return new NotesFragment();
            //args.putString(NotesFragment.TITLE,"Notes");
        }
        if (position == 1){
            return new PasswordFragment();
            //args.putString(NotesFragment.TITLE,"Password");
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
