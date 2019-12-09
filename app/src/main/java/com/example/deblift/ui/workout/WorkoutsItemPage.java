package com.example.deblift.ui.workout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deblift.MainActivity;
import com.example.deblift.R;
import com.example.deblift.SlidingPanelManager;
import com.example.deblift.ui.history.HistoryItemPage;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class WorkoutsItemPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkoutItemAdapter workoutItemAdapter;
    private Button workoutStartButton;
    private MainActivity mainActivity;

    private String workoutName;


    SlidingUpPanelLayout slidingPanelBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_item_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();

        workoutName = intent.getStringExtra("workout_name");
        Log.d("Workout_Name: ", workoutName);
        setTitle(intent.getStringExtra("workout_name"));

        recyclerView = findViewById(R.id.workouts_item_page_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        workoutItemAdapter = new WorkoutItemAdapter();
        recyclerView.setAdapter(workoutItemAdapter);


        workoutStartButton = findViewById(R.id.workout_start_button);
        workoutStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Start workout clicked. Bring Sliding Up Panel, open it, start the timer
                // Send workout name to the next page

                if(!SlidingPanelManager.IsSlidingPanelActive()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("panel_enabled", true);
                    intent.putExtra("workout_name", workoutName);
                    //intent.putExtra("exercise_name", adapter.getExerciseName(position));
                    //slidingPanelBottom.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                    startActivity(intent);
                }
                else {
                    // Display dialog if user wants to discard current training
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
