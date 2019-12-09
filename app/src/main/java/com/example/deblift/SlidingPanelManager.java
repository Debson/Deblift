package com.example.deblift;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.ui.workout.AddExercisePage;
import com.example.deblift.ui.workout.SlidingExerciseAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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

    private PopupMenu popup;

    private Timer timer;

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

            boolean workoutTemplate = intent.getBooleanExtra("workout_template", false);
            if(workoutTemplate)
            {
                slidingExerciseAdapter.setTemplateWorkout(true);
            }
            else
            {
                String workoutName = intent.getStringExtra("workout_name");

                // Set workout name in adapter
                slidingExerciseAdapter.setWorkoutName(workoutName);
            }

            slidingPanelActive = true;

            // Start the timer;

            startTimer();

            workoutTimer =  mainActivity.findViewById(R.id.workout_timer);
            workoutTimerSlidingPage =  mainActivity.findViewById(R.id.workout_timer_sliding_page);


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

                            slidingPanelActive = false;
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                            navController.navigate(R.id.navigation_history);

                            // Ask if you sure to finish
                            // Display congratulation page for a workout or something
                            // Save new workout to a finished workouts database



                        }
                    });

                    Button addExercise = mainActivity.findViewById(R.id.add_exercise_button);
                    addExercise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            slidingExerciseAdapter.addItem();

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
