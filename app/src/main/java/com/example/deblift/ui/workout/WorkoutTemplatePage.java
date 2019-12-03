package com.example.deblift.ui.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.deblift.R;
import com.example.deblift.ui.exercises.ExercisesRecycleViewAdapter;

public class WorkoutTemplatePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TemplateExerciseRecycleAdapter templateExerciseRecycleAdapter;
    private Button addExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_template_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_black_24dp);

        setTitle("New workout template");

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.template_exercise_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        templateExerciseRecycleAdapter = new TemplateExerciseRecycleAdapter();
        recyclerView.setAdapter(templateExerciseRecycleAdapter);


        addExerciseButton = findViewById(R.id.add_exercise_button);
        setupAddExerciseButton(addExerciseButton);
    }

    private void setupListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Exercise item clicked", Long.toString(position));

            }
        });
    }


    private void setupAddExerciseButton(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Add exercise: ", " button pressed");
                templateExerciseRecycleAdapter.addItem();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_save_button, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.menu_save: {
                Log.d("Workout ", "saved");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
