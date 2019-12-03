package com.example.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deblift.R;

import java.util.ArrayList;

public class TemplateSetAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int itemCount = 30;

    public TemplateSetAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return itemCount;
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
        root = inflater.inflate(R.layout.activity_workout_template_exercise_set_listview, null);

        final TextView setNum = root.findViewById(R.id.set_number);
        setNum.setText(Integer.toString(position + 1));


        return root;
    }

    public void addItem(int pos) {
        itemCount++;

        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        itemCount--;

        notifyDataSetChanged();
    }

}
