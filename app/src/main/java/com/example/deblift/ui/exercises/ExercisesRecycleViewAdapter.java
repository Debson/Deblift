package com.example.deblift.ui.exercises;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;

import java.util.ArrayList;
import java.util.Locale;

public class ExercisesRecycleViewAdapter extends RecyclerView.Adapter {

    ArrayList<ExerciseItem> exerciseItems = new ArrayList<>();
    ArrayList<ExerciseItem> displayedExerciseItems;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);
        }
    }

    public ExercisesRecycleViewAdapter(ArrayList<String> exercisesList, ArrayList<String> muscleGroupList, ArrayList<Integer> exerciseIcon) {
        for(int i = 0; i < exercisesList.size(); i++)
        {
            exerciseItems.add(new ExerciseItem(exercisesList.get(i), muscleGroupList.get(i), exerciseIcon.get(i)));
        }
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView exerciseText = holder.itemView.findViewById(R.id.exerciseNameText);
        TextView muscleGroupText = holder.itemView.findViewById(R.id.bodyPartText);
        ImageView exerciseIcon = holder.itemView.findViewById(R.id.icon);

        exerciseText.setText(displayedExerciseItems.get(position).exercise);
        muscleGroupText.setText(displayedExerciseItems.get(position).muscleGroup);
        exerciseIcon.setImageResource(displayedExerciseItems.get(position).exerciseIcon);
    }

    @Override
    public int getItemCount() {
        return displayedExerciseItems.size();
    }

    public void filter(String characterText) {
        characterText = characterText.toLowerCase(Locale.getDefault());

        displayedExerciseItems.clear();

        for(ExerciseItem ex : exerciseItems)
        {
            if(ex.exercise.contains(characterText))
                displayedExerciseItems.add(ex);
        }

        Log.d("Msg: ", characterText);
        notifyDataSetChanged();
    }

    public void resetFilter()
    {
        displayedExerciseItems.clear();
        displayedExerciseItems = new ArrayList<>(exerciseItems);

        notifyDataSetChanged();
    }
}
