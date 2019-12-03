package com.example.deblift.ui.exercises;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deblift.R;

import java.util.ArrayList;
import java.util.Locale;


public class ExercisesCustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ExerciseItem> exerciseItems = new ArrayList<>();
    ArrayList<ExerciseItem> displayedExerciseItems;
    LayoutInflater inflater;

    public ExercisesCustomAdapter(Context applicationContext, ArrayList<String> exercisesList, ArrayList<String> muscleGroupList, ArrayList<Integer> exerciseIcon) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
        for(int i = 0; i < exercisesList.size(); i++)
        {
            exerciseItems.add(new ExerciseItem(exercisesList.get(i), muscleGroupList.get(i), exerciseIcon.get(i)));
        }
        displayedExerciseItems = new ArrayList<>(exerciseItems);

    }


    public String getExerciseName(int pos) {
        return displayedExerciseItems.get(pos).exercise;
    }

    @Override
    public int getCount() {
        return displayedExerciseItems.size();
    }

    @Override
    public Object getItem(int i) {
        return displayedExerciseItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View root, ViewGroup viewGroup) {

        root = inflater.inflate(R.layout.activity_exercises_listview, null);
        TextView exerciseText = root.findViewById(R.id.exerciseNameText);
        TextView muscleGroupText = root.findViewById(R.id.bodyPartText);
        ImageView exerciseIcon = root.findViewById(R.id.icon);

        exerciseText.setText(displayedExerciseItems.get(i).exercise);
        muscleGroupText.setText(displayedExerciseItems.get(i).muscleGroup);
        exerciseIcon.setImageResource(displayedExerciseItems.get(i).exerciseIcon);

        return root;
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
