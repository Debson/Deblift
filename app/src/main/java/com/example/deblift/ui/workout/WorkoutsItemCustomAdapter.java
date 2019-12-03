package com.example.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deblift.R;

public class WorkoutsItemCustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public WorkoutsItemCustomAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 30;
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
        root = inflater.inflate(R.layout.activity_workouts_item_page_listview, null);

        return root;
    }
}
