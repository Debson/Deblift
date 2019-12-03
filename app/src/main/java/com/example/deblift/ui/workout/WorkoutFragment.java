package com.example.deblift.ui.workout;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;

import java.util.ArrayList;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel workoutViewModel;
    private WorkoutsCustomAdapter adapter;

    private Button addTemplateButton;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkoutRecycleAdapter workoutRecycleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workoutViewModel =
                ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_workouts, container, false);

        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.workouts_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        workoutRecycleAdapter = new WorkoutRecycleAdapter();
        recyclerView.setAdapter(workoutRecycleAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                goToWorkoutPage();
                Log.d("Position ", Integer.toString(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        setupAddTemplateButton(root);

        return root;
    }


    public void goToWorkoutPage() {
        Intent intent = new Intent(getContext(), WorkoutsItemPage.class);
        //intent.putExtra("position", position);
        //intent.putExtra("exercise_name", adapter.getExerciseName(position));
        startActivity(intent);
    }

    private void setupAddTemplateButton(View root)
    {
        addTemplateButton = root.findViewById(R.id.workout_add_button);

        addTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WorkoutTemplatePage.class);
                //intent.putExtra("position", position);
                //intent.putExtra("exercise_name", adapter.getExerciseName(position));
                startActivity(intent);
            }
        });

    }
}