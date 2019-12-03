package com.example.deblift.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deblift.R;
import com.example.deblift.ui.exercises.ExerciseItem;

import java.util.ArrayList;

public class HistoryCustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public HistoryCustomAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View root, ViewGroup parent) {
        root = inflater.inflate(R.layout.activity_history_listview, null);


        return root;
    }
}
