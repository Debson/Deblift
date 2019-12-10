package com.deblift;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;


import com.deblift.database.AppRoomDatabase;
import com.deblift.database.DatabaseGenerator;
import com.deblift.ui.exercises.Exercise;
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


                        slidingPanelManager.getAdapter().addItem(ex);
                    }
                }

                break;
            }
        }
    }

    public NavController getNavController() {
        return navController;
    }

    public BottomNavigationView getNavView() {
        return navView;
    }
}
