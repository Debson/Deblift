package com.deblift.ui.workout;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.SlidingPanelManager;
import com.deblift.database.AppRoomDatabase;
import com.deblift.ui.exercises.Exercise;

import java.util.ArrayList;

public class WorkoutFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkoutAdapter workoutAdapter;
    private TextView workoutNameTV;

    private AppRoomDatabase appDb;

    public final int SUBACTIVITY_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workouts, container, false);

        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.workouts_list);

        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);


        appDb = AppRoomDatabase.getInstance(getActivity());

        workoutAdapter = new WorkoutAdapter(this, appDb.workoutTemplateDao().loadAllWorkouts(WorkoutEntity.WORKOUT_TEMPLATE));
        recyclerView.setAdapter(workoutAdapter);

        final LayoutInflater factory = getLayoutInflater();
        final View workoutItemLayout = factory.inflate(R.layout.activity_workouts_listview, null);
        workoutNameTV = (TextView) workoutItemLayout.findViewById(R.id.workout_nameText);

        setupAddTemplateButton(root);

        setupStartEmptyWorkoutButton(root);



        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SUBACTIVITY_CODE: {

                if(resultCode == Activity.RESULT_OK) {
                    workoutAdapter.updateWorkoutTemplates(appDb.workoutTemplateDao().loadAllWorkouts(WorkoutEntity.WORKOUT_TEMPLATE));
                    Log.d("WorkoutFragment: ", "WorkoutExercise Templates updated!");
                }

                break;
            }
        }
    }


    public void goToWorkoutPage(int workoutId) {
        Intent intent = new Intent(getContext(), WorkoutsItemPage.class);

        intent.putExtra("workout_id", workoutId);
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
                //startActivity(intent);

                startActivityForResult(intent, SUBACTIVITY_CODE);
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