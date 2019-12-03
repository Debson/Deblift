package com.example.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.deblift.R;

public class WorkoutsCustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    Button button;
    PopupMenu popup;

    public WorkoutsCustomAdapter(Context applicationContext)
    {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 100;
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
        root = inflater.inflate(R.layout.activity_workouts_listview, null);

        button = root.findViewById(R.id.workout_moreButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.workout_menu_button, popup.getMenu());
                popup.show();
            }
        });



        return root;
    }
}
