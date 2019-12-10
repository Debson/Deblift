package com.deblift.ui.workout;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;
import com.deblift.ui.exercises.Exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class WorkoutAddExerciseAdapter extends RecyclerView.Adapter {

    private ArrayList<Exercise> exerciseItems;
    private ArrayList<Exercise> displayedExerciseItems;

    private ArrayList<Boolean> selectedItems = new ArrayList<>();
    private ArrayList<Integer> selectedInOrderItems = new ArrayList<>();

    private AddExercisePage addExercisePage;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);
        }
    }

    public WorkoutAddExerciseAdapter(AddExercisePage addExercisePage, Exercise[] exercisesArr) {
        this.addExercisePage = addExercisePage;

        exerciseItems = new ArrayList<>(Arrays.asList(exercisesArr));

        displayedExerciseItems = new ArrayList<>(exerciseItems);

        for(int i = 0; i < getItemCount(); i++)
        {
            selectedItems.add(false);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_exercises_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedItems.set(position, !selectedItems.get(position));

                if(selectedItems.get(position))
                {
                    selectedInOrderItems.add(position);
                }
                else
                {
                    selectedInOrderItems.remove(selectedInOrderItems.indexOf(position));
                }

                addExercisePage.updateSelectedItems(selectedInOrderItems);
                notifyDataSetChanged();
            }
        });


        TextView exerciseText = holder.itemView.findViewById(R.id.exercise_name_text);
        TextView muscleGroupText = holder.itemView.findViewById(R.id.muscle_group_text);
        ImageView exerciseIcon = holder.itemView.findViewById(R.id.exercise_image);

        exerciseText.setText(displayedExerciseItems.get(position).getExerciseName());
        muscleGroupText.setText(displayedExerciseItems.get(position).getMuscleGroup());
        exerciseIcon.setImageResource(displayedExerciseItems.get(position).getExerciseIcon());

        if(selectedItems.get(position) == true)
        {
            holder.itemView.setBackgroundColor(Color.argb(50, 0, 0, 255));
        }
        else
        {
            holder.itemView.setBackgroundColor(addExercisePage.getResources().getColor(android.R.color.transparent));
        }

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
