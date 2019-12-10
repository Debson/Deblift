/*
 * Date: 10/12/2019
 * Name: Michal Debski
 * Class: DT211C
 * Description:
 *
 */

package com.deblift.ui.exercises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.deblift.R;
import com.deblift.database.AppRoomDatabase;


public class ExerciseItemPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExerciseItemPageAdapter exerciseItemPageAdapter;

    private ImageView exerciseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_item_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra("exercise_name");

        setTitle(exerciseName);


        recyclerView = findViewById(R.id.exercise_description_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);

        Exercise exercise = appDb.exercisesDao().loadExercise(exerciseName);

        exerciseImage = findViewById(R.id.exercise_image);
        exerciseImage.setImageResource(exercise.getExerciseIcon());


        String exDesc = exercise.getExerciseDescription();
        // Passlist of steps how to perform exercise to the adapter
        String[] exDescList = exDesc.split(getResources().getString(R.string.exercise_description_delimiter));

        exerciseItemPageAdapter = new ExerciseItemPageAdapter(exercise);
        recyclerView.setAdapter(exerciseItemPageAdapter);
    }


    @Override
    public void onBackPressed() {
        Log.d("exit", "exit");
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
