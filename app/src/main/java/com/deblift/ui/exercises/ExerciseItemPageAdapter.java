/*
 * Date: 10/12/2019
 * Name: Michal Debski
 * Class: DT211C
 * Description:
 *
 */

package com.deblift.ui.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.MainActivity;
import com.deblift.R;

public class ExerciseItemPageAdapter extends RecyclerView.Adapter<ExerciseItemPageAdapter.MyViewHolder> {

    private Exercise exercise;
    private String[] exerciseDescriptionList;


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stepCountText;
        public TextView stepDescriptionText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stepCountText = itemView.findViewById(R.id.step_count_text);
            stepDescriptionText = itemView.findViewById(R.id.step_description_text);;
        }

    }

    public ExerciseItemPageAdapter(Exercise exercise) {

        this.exercise = exercise;
        this.exerciseDescriptionList = exercise.getExerciseDescription().split(MainActivity.resoruces.getString(R.string.exercise_description_delimiter));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_exercises_item_page_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(position < exerciseDescriptionList.length) {
            holder.stepCountText.setText(Integer.toString(position + 1));
            holder.stepDescriptionText.setText(exerciseDescriptionList[position]);
        }

    }

    @Override
    public int getItemCount() {
        return exerciseDescriptionList.length;
    }
}
