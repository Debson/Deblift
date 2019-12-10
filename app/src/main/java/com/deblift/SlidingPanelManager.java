package com.deblift;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.database.AppRoomDatabase;
import com.deblift.ui.exercises.Exercise;
import com.deblift.ui.history.WorkoutExercise;
import com.deblift.ui.workout.AddExercisePage;
import com.deblift.ui.workout.Set;
import com.deblift.ui.workout.SlidingExerciseAdapter;
import com.deblift.ui.workout.SlidingSetAdapter;
import com.deblift.ui.workout.WorkoutEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SlidingPanelManager {

    private MainActivity mainActivity;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SlidingExerciseAdapter slidingExerciseAdapter;
    public final int SUBACTIVITY_CODE = 2;

    private NavController navController;
    private BottomNavigationView navView;

    private TextView workoutTimer;
    private TextView workoutTimerSlidingPage;

    private EditText workoutNameEditText;

    private PopupMenu popup;

    private Timer timer;
    private Toast toast;

    private long workoutTimeSeconds = 0;


    private static boolean slidingPanelActive = false;

    public SlidingPanelManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        navController = mainActivity.getNavController();
        navView = mainActivity.getNavView();
    }


    public void setupSlidingPanel()
    {
        final SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) mainActivity.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        Intent intent = mainActivity.getIntent();

        boolean panel_enabled = intent.getBooleanExtra("panel_enabled",false);
        if(panel_enabled)
        {
            navController.navigate(R.id.navigation_workouts);

            setupRecycleView();
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);

            WorkoutEntity workoutEntity;
            boolean isWorkoutTemplate = intent.getBooleanExtra("workout_template", false);
            if(isWorkoutTemplate)
            {
                //slidingExerciseAdapter.setTemplateWorkout(true);
            }
            else
            {
                int workoutId = intent.getIntExtra("workout_id", 0);

                AppRoomDatabase appDb = AppRoomDatabase.getInstance(mainActivity);
                workoutEntity = appDb.workoutTemplateDao().loadWorkout(workoutId);

                slidingExerciseAdapter.setWorkoutEntity(workoutEntity);

                // Set workout name in adapter
                //slidingExerciseAdapter.setWorkoutName(workoutName);
            }

            slidingPanelActive = true;

            // Start the timer;

            startTimer();

            workoutTimer =  mainActivity.findViewById(R.id.workout_timer);
            workoutTimerSlidingPage =  mainActivity.findViewById(R.id.workout_timer_sliding_page);

            workoutNameEditText = mainActivity.findViewById(R.id.workout_name_edit_text);
            workoutNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            //slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }


        final View workoutInfoView = mainActivity.findViewById(R.id.workout_info_panel);
        final View workoutContentView = mainActivity.findViewById(R.id.workout_content_panel);

        final float workoutInfoStartAlpha = workoutInfoView.getAlpha();


        navView.setTag(navView.getVisibility());
        navView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int newVis = navView.getVisibility();
                if((int)navView.getTag() != newVis)
                {
                    navView.setTag(navView.getVisibility());

                    if(navView.getVisibility() != View.GONE && slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.DRAGGING)
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });


        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                // Add some fancy alpha when panel is beeing dragged
                workoutInfoView.setAlpha(workoutInfoStartAlpha - slideOffset * 8.f);
                workoutContentView.setAlpha(slideOffset * 5.f);

                // Set up limits for translation in Y axis
                float navViewTransY = navView.getHeight() * slideOffset;
                if(navViewTransY < 0.f)
                    navViewTransY = 0.f;
                else if(navViewTransY > navView.getHeight())
                    navViewTransY = navView.getHeight();

                navView.setTranslationY(navViewTransY);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if(newState == SlidingUpPanelLayout.PanelState.EXPANDED)
                {
                    // Setup a custom toolbar when EXPANDED
                    slidingUpPanelLayout.setTouchEnabled(false);
                    mainActivity.getSupportActionBar().setDisplayShowCustomEnabled(true);
                    mainActivity.getSupportActionBar().setCustomView(R.layout.current_workout_toolbar);

                    View view = mainActivity.getSupportActionBar().getCustomView();

                    Button hideWorkoutButton = view.findViewById(R.id.hide_workout_button);
                    hideWorkoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navView.setVisibility(View.VISIBLE);
                            slidingUpPanelLayout.setTouchEnabled(true);

                        }
                    });

                    Button finnishButton = view.findViewById(R.id.workout_finnish);
                    finnishButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int completedSets = 0;
                            int counter = 0;
                            for(Exercise e : slidingExerciseAdapter.getExerciseList()) {
                                Log.d("Exercise: ", e.getExerciseName());

                                for(Set s : slidingExerciseAdapter.getAdapterList().get(counter).getSets())
                                {
                                    Log.d("Pos: ", Integer.toString(s.getPosition()));
                                    Log.d("Weight: ", Float.toString(s.getWeight()));
                                    Log.d("Reps: ", Integer.toString(s.getReps()));
                                    Log.d("Checked:", Boolean.toString(s.isChecked()));
                                    Log.d("-----","-----");
                                    if(s.isChecked())
                                        completedSets++;
                                }
                                counter++;
                            }


                            if(completedSets == 0) {
                                if (toast != null)
                                    toast.cancel();
                                toast = Toast.makeText(mainActivity, "Please complete some sets before finishing", Toast.LENGTH_SHORT);

                                toast.show();


                            }
                            else {

                                // Display dialog if users is sure to finish


                                // Build History workout

                                long currentTimeMilis = System.currentTimeMillis();
                                long workoutDuration = workoutTimeSeconds * 1000;

                                WorkoutEntity workoutEntity = new WorkoutEntity(
                                        workoutNameEditText.getText().toString(),
                                        WorkoutEntity.WORKOUT_HISTORY,
                                        currentTimeMilis,
                                        workoutDuration,
                                        slidingExerciseAdapter.getExerciseList().size());

                                List<WorkoutExercise> workoutExerciseList = new ArrayList<>();
                                for (int i = 0; i < slidingExerciseAdapter.getExerciseList().size(); i++) {
                                    slidingExerciseAdapter.saveSets();

                                    for(int s = slidingExerciseAdapter.getAdapterList().get(i).getSets().size() - 1; s >= 0; s--)
                                    {
                                        if(!slidingExerciseAdapter.getAdapterList().get(i).getSets().get(s).isChecked())
                                        {
                                            slidingExerciseAdapter.getAdapterList().get(i).getSets().remove(s);
                                            s--;
                                        }

                                    }

                                    if(slidingExerciseAdapter.getAdapterList().get(i).getSets().isEmpty())
                                        continue;

                                    workoutExerciseList.add(
                                            new WorkoutExercise(
                                                    slidingExerciseAdapter.getExerciseList().get(i).getExerciseName(),
                                                    slidingExerciseAdapter.getSets().get(i),
                                                    slidingExerciseAdapter.getAdapterList().get(i).getSets()));
                                }

                                workoutEntity.setWorkoutExercises(workoutExerciseList);

                                AppRoomDatabase appDb = AppRoomDatabase.getInstance(mainActivity);
                                appDb.workoutTemplateDao().insertWorkout(workoutEntity);

                                slidingPanelActive = false;
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                                navController.navigate(R.id.navigation_history);
                            }

                        }
                    });

                    Button addExercise = mainActivity.findViewById(R.id.add_exercise_button);
                    addExercise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navView.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(mainActivity.getApplicationContext(), AddExercisePage.class);
                            //Intent intent = new Intent(getContext(), ExerciseItemPage.class);
                            //intent.putExtra("position", position);
                            //intent.putExtra("exercise_name", exercisesAdapter.getExerciseName(position));
                            mainActivity.startActivityForResult(intent, SUBACTIVITY_CODE);;
                        }
                    });

                    Button cancelWorkout = mainActivity.findViewById(R.id.cancel_workout_button);
                    cancelWorkout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            slidingPanelActive = false;
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                            navController.navigate(R.id.navigation_workouts);
                            // Ask if sure to cancel workout.
                            // then just exit panel view


                        }
                    });


                    Button editWorkoutName = mainActivity.findViewById(R.id.edit_workout_name_button);
                    editWorkoutName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup = new PopupMenu(mainActivity.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.edit_workout_name_popup, popup.getMenu());

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch(item.getItemId()) {
                                        case R.id.exercise_menu_remove: {

                                            break;
                                        }
                                    }

                                    return false;
                                }
                            });

                            popup.show();
                        }
                    });

                    EditText workoutNameEditText = mainActivity.findViewById(R.id.workout_name_edit_text);
                    workoutNameEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });


                    //workoutInfoView.setVisibility(View.GONE);
                    navView.setVisibility(View.GONE);
                }
                else if(previousState == SlidingUpPanelLayout.PanelState.EXPANDED && newState == SlidingUpPanelLayout.PanelState.DRAGGING)
                {
                    mainActivity.getSupportActionBar().setDisplayShowCustomEnabled(false);
                    navView.setVisibility(View.VISIBLE);
                    workoutInfoView.setVisibility(View.VISIBLE);
                    slidingUpPanelLayout.setTouchEnabled(true);
                }
                else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED)
                {
                    workoutInfoView.setVisibility(View.VISIBLE);
                }
                else if(previousState == SlidingUpPanelLayout.PanelState.COLLAPSED && newState == SlidingUpPanelLayout.PanelState.DRAGGING)
                {
                    workoutInfoView.setVisibility(View.GONE);

                }
            }
        });

    }


    private void setupRecycleView()
    {
        recyclerView = mainActivity.findViewById(R.id.current_workout_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mainActivity.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        slidingExerciseAdapter = new SlidingExerciseAdapter();
        recyclerView.setAdapter(slidingExerciseAdapter);
    }

    private void startTimer()
    {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int hours = (int)(workoutTimeSeconds / 3600);
                        int minutes = (int)(workoutTimeSeconds % 3600) / 60;
                        long seconds = workoutTimeSeconds % 60;
                        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);;
                        workoutTimer.setText(timeString);
                        workoutTimerSlidingPage.setText(timeString);
                        workoutTimeSeconds++;
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask,0, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    public SlidingExerciseAdapter getAdapter()
    {
        return this.slidingExerciseAdapter;
    }


    public static boolean IsSlidingPanelActive()
    {
        return slidingPanelActive;
    }
}
