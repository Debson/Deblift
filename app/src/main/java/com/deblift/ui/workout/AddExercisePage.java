package com.deblift.ui.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.database.AppRoomDatabase;
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

        getSupportActionBar().setTitle(MainActivity.resoruces.getString(R.string.add_exercise));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);

        recyclerView = findViewById(R.id.exercises_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);

        workoutAddExerciseAdapter = new WorkoutAddExerciseAdapter(this, appDb.exercisesDao().loadAllExercises());
        recyclerView.setAdapter(workoutAddExerciseAdapter);


        submitExercisesFAB = findViewById(R.id.submit_exercises_fab);
        submitExercisesFAB.hide();

        submitExercisesFAB.setBackgroundResource(R.drawable.ic_check_black_24dp);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                workoutAddExerciseAdapter.filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void updateSelectedItems(ArrayList<Integer> selectedItems) {

        this.selectedItems = selectedItems;

        if(selectedItems.size() > 0)
            submitExercisesFAB.show();
        else
            submitExercisesFAB.hide();
    }

}
