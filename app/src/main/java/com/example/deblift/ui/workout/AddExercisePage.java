package com.example.deblift.ui.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.deblift.MainActivity;
import com.example.deblift.R;
import com.example.deblift.ui.exercises.WorkoutAddExerciseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddExercisePage extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    WorkoutAddExerciseAdapter workoutAddExerciseAdapter;

    ArrayList<Integer> selectedItems = null;

    FloatingActionButton submitExercisesFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_page);

        getSupportActionBar().setTitle("Add exercise");

        ArrayList<String> exercises = new ArrayList<>();
        ArrayList<String> muscleGroups = new ArrayList<>();
        ArrayList<Integer> icons = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            exercises.add("Exercise " + i);
            muscleGroups.add("Legs");
            icons.add(R.drawable.ic_dumbbell_black_24dp);
        }

        recyclerView = findViewById(R.id.exercises_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        workoutAddExerciseAdapter = new WorkoutAddExerciseAdapter(this, exercises, muscleGroups, icons);
        recyclerView.setAdapter(workoutAddExerciseAdapter);


        submitExercisesFAB = findViewById(R.id.submit_exercises_fab);
        submitExercisesFAB.hide();


        submitExercisesFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Selected items count: ", Integer.toString(selectedItems.size()));

                ArrayList<String> exerciseNames = new ArrayList<>();
                for (int i: selectedItems) {
                    exerciseNames.add(workoutAddExerciseAdapter.getExerciseName(i));
                }

                Intent intent = new Intent();
                intent.putExtra("selected", exerciseNames);
                setResult(Activity.RESULT_OK, intent);

                finish();

            }
        });

    }

    public void updateSelectedItems(ArrayList<Integer> selectedItems) {

        this.selectedItems = selectedItems;

        if(selectedItems.size() > 0)
            submitExercisesFAB.show();
        else
            submitExercisesFAB.hide();
    }

}
