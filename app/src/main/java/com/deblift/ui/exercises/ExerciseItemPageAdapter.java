package com.deblift.ui.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;

public class ExerciseItemPageAdapter extends RecyclerView.Adapter {

    private String[] exerciseDescriptionList;

    public ExerciseItemPageAdapter(String[] exerciseDescriptionList) {

        this.exerciseDescriptionList = exerciseDescriptionList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_exercises_item_page_listview, parent, false);

        return new ExerciseAdapter.MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView stepCountText = holder.itemView.findViewById(R.id.step_count_text);
        TextView stepDescriptionText = holder.itemView.findViewById(R.id.step_description_text);
        if(position < exerciseDescriptionList.length) {
            stepCountText.setText(Integer.toString(position + 1));
            stepDescriptionText.setText(exerciseDescriptionList[position]);
        }

    }

    @Override
    public int getItemCount() {
        return exerciseDescriptionList.length;
    }
}
