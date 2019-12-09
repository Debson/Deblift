package com.example.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.deblift.R;
import com.example.deblift.utils.MyViewHolder;

import java.lang.ref.WeakReference;

public class WorkoutAdapter extends RecyclerView.Adapter {

    Button button;
    PopupMenu popup;
    Context context;

    WorkoutFragment workoutFragment;

    private int workoutCount = 5;


    public WorkoutAdapter(WorkoutFragment workoutFragment) {
        this.workoutFragment = workoutFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workouts_listview, parent, false);

        context = root.getContext();

        button = root.findViewById(R.id.workout_moreButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.workout_menu_button, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.workout_menu_delete: {

                            }

                        }


                        return false;
                    }
                });

                popup.show();
            }
        });


        return new MyViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        button = holder.itemView.findViewById(R.id.workout_moreButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.workout_menu_button, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.workout_menu_delete: {
                                // get workout name from textfield
                                // delete workout from database, update

                                workoutCount--;
                                notifyDataSetChanged();
                                break;
                            }
                            case R.id.workout_menu_duplicate: {

                                notifyDataSetChanged();
                                break;
                            }
                            case R.id.workout_menu_edit: {

                                notifyDataSetChanged();
                                break;
                            }
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutFragment.goToWorkoutPage();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutCount;
    }


}
