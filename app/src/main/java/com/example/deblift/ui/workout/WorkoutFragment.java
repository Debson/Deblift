package com.example.deblift.ui.workout;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.MainActivity;
import com.example.deblift.R;
import com.example.deblift.SlidingPanelManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class WorkoutFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkoutAdapter workoutAdapter;
    private TextView workoutNameTV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workouts, container, false);

        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.workouts_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        workoutAdapter = new WorkoutAdapter(this);
        recyclerView.setAdapter(workoutAdapter);

        final LayoutInflater factory = getLayoutInflater();
        final View workoutItemLayout = factory.inflate(R.layout.activity_workouts_listview, null);
        workoutNameTV = (TextView) workoutItemLayout.findViewById(R.id.workout_nameText);

        setupAddTemplateButton(root);

        setupStartEmptyWorkoutButton(root);



        return root;
    }


    public void goToWorkoutPage() {
        Intent intent = new Intent(getContext(), WorkoutsItemPage.class);

        intent.putExtra("workout_name", workoutNameTV.getText());
        //intent.putExtra("exercise_name", adapter.getExerciseName(position));
        startActivity(intent);
    }

    private void setupAddTemplateButton(View root)
    {
        Button addTemplateButton = root.findViewById(R.id.workout_add_button);

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

    private void setupStartEmptyWorkoutButton(View root)
    {
        if(!SlidingPanelManager.IsSlidingPanelActive()) {

            Button startEmptyWorkoutButton = root.findViewById(R.id.workouts_start_empty_workout_button);

            startEmptyWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("panel_enabled", true);
                    intent.putExtra("workout_template", true);
                    //intent.putExtra("exercise_name", adapter.getExerciseName(position));
                    startActivity(intent);
                }
            });
        }
        else {
            // Display dialog to discard current workout and star new
        }
    }
}