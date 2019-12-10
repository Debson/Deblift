package com.deblift.ui.exercises;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ExerciseAdapter extends RecyclerView.Adapter {

    private ArrayList<Exercise> exerciseItems;
    private ArrayList<Exercise> displayedExerciseItems;

    private ExerciseFragment exercisesFragment;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);
        }
    }

    public ExerciseAdapter(ExerciseFragment exercisesFragment, Exercise[] exercisesList) {
        this.exercisesFragment = exercisesFragment;
        exerciseItems = new ArrayList<>(Arrays.asList(exercisesList));
        displayedExerciseItems = new ArrayList<>(exerciseItems);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_exercises_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercisesFragment.goToExerciseItemPage(holder.itemView);
            }
        });

        TextView exerciseText = holder.itemView.findViewById(R.id.exerciseNameText);
        TextView muscleGroupText = holder.itemView.findViewById(R.id.bodyPartText);
        ImageView exerciseIcon = holder.itemView.findViewById(R.id.icon);

        exerciseText.setText(displayedExerciseItems.get(position).getExerciseName());
        muscleGroupText.setText(displayedExerciseItems.get(position).getMuscleGroup());
        exerciseIcon.setImageResource(displayedExerciseItems.get(position).getExerciseIcon());
    }

    @Override
    public int getItemCount() {
        return displayedExerciseItems.size();
    }

    public void filter(String characterText) {
        characterText = characterText.toLowerCase(Locale.getDefault());

        displayedExerciseItems.clear();

        for(Exercise ex : exerciseItems)
        {
            if(ex.getExerciseName().toLowerCase().contains(characterText))
                displayedExerciseItems.add(ex);
        }

        Log.d("Msg: ", characterText);
        notifyDataSetChanged();
    }

    public String getExerciseName(int pos) {
        return displayedExerciseItems.get(pos).getExerciseName();
    }

    public void resetFilter()
    {
        displayedExerciseItems.clear();
        displayedExerciseItems = new ArrayList<>(exerciseItems);

        notifyDataSetChanged();
    }
}
