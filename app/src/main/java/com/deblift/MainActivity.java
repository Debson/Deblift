package com.deblift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.deblift.database.AppRoomDatabase;
import com.deblift.database.DatabaseGenerator;
import com.deblift.ui.exercises.Exercise;
import com.deblift.ui.history.WorkoutExercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Resources resoruces;
    public final int SUBACTIVITY_CODE = 2;

    private SlidingPanelManager slidingPanelManager;

    private NavController navController;
    private BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resoruces = getResources();

        // **** Create exercises entries
        DatabaseGenerator.GenerateExerciseData(this);




        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_history, R.id.navigation_workouts, R.id.navigation_exercises)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        final SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);


        slidingPanelManager = new SlidingPanelManager(this);

        slidingPanelManager.setupSlidingPanel();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SUBACTIVITY_CODE: {
                ArrayList<String> exerciseNamesArray = null;
                if(resultCode == Activity.RESULT_OK) {
                    exerciseNamesArray = data.getStringArrayListExtra("selected");

                    AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);

                    for (String exName : exerciseNamesArray) {
                        Exercise ex = appDb.exercisesDao().loadExercise(exName);

                        WorkoutExercise we = new WorkoutExercise(ex.getExerciseName());


                        slidingPanelManager.getAdapter().addItem(we);
                    }

                    navController.navigate(R.id.navigation_workouts);
                }

                break;
            }
        }
    }

    // Override dispatch touch event so all the EditText will lose focus on outside click
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    v.clearFocus();

                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }



    public NavController getNavController() {
        return navController;
    }

    public BottomNavigationView getNavView() {
        return navView;
    }
}
