package com.deblift.ui.workout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.SlidingPanelManager;
import com.deblift.database.AppRoomDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WorkoutsItemPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkoutItemAdapter workoutItemAdapter;
    private Button workoutStartButton;
    private MainActivity mainActivity;

    private String workoutName;
    private int workoutId;
    private WorkoutEntity workoutEntity;


    SlidingUpPanelLayout slidingPanelBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_item_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();

        workoutId = intent.getIntExtra("workout_id", 0);

        AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);
        workoutEntity = appDb.workoutDao().loadWorkout(workoutId);
        workoutName = workoutEntity.getWorkoutName();


        setTitle(workoutName);

        TextView workoutNameText = findViewById(R.id.workout_name_item_text);
        workoutNameText.setText(workoutName);

        String dateStr = prepareWorkoutDateString();
        TextView workoutDateText = findViewById(R.id.workout_date_item_text);
        workoutDateText.setText(dateStr);

        recyclerView = findViewById(R.id.workouts_item_page_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        workoutItemAdapter = new WorkoutItemAdapter(getApplicationContext(), workoutEntity);
        recyclerView.setAdapter(workoutItemAdapter);

        workoutStartButton = findViewById(R.id.workout_start_button);
        workoutStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Start workout clicked. Bring Sliding Up Panel, open it, start the timer
                // Send workout name to the next page
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("panel_enabled", true);
                intent.putExtra("workout_id", workoutEntity.getWorkoutId());

                if(!SlidingPanelManager.IsSlidingPanelActive()) {

                    //intent.putExtra("exercise_name", adapter.getExerciseName(position));
                    startActivity(intent);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                    builder.setTitle(MainActivity.resoruces.getString(R.string.workout_in_progress));
                    builder.setMessage(MainActivity.resoruces.getString(R.string.workout_in_progress_msg));

                    builder.setPositiveButton(MainActivity.resoruces.getString(R.string.discard), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(intent);

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton(MainActivity.resoruces.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
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

    private String prepareWorkoutDateString() {
        String str = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(workoutEntity.getWorkoutDate());

        str = new SimpleDateFormat("d MMM yyyy  HH:MM").format(calendar.getTime());

        return str;
    }

}
