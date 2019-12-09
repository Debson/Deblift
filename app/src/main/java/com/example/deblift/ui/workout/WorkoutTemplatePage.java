package com.example.deblift.ui.workout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.deblift.R;

import java.util.ArrayList;

public class WorkoutTemplatePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TemplateExerciseAdapter templateExerciseAdapter;
    private Button addExerciseButton;
    private Button editWorkoutName;
    private PopupMenu popup;

    private EditText workoutNameEditText;

    public final int SUBACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_template_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_exit_black_24dp);

        setTitle("New workout template");

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.template_exercise_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        templateExerciseAdapter = new TemplateExerciseAdapter();
        recyclerView.setAdapter(templateExerciseAdapter);


        addExerciseButton = findViewById(R.id.add_exercise_button);
        setupAddExerciseButton(addExerciseButton);

        editWorkoutName = findViewById(R.id.edit_workout_name_button);
        editWorkoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(getApplicationContext(), v);
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

        workoutNameEditText = (EditText)findViewById(R.id.workout_name_edit_text);
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

    }

    private void setupListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Exercise item clicked", Long.toString(position));

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SUBACTIVITY_CODE: {
                ArrayList<String> exerciseNamesArray = null;
                if(resultCode == Activity.RESULT_OK) {
                    exerciseNamesArray = data.getStringArrayListExtra("selected");

                    for (String ex : exerciseNamesArray) {
                        templateExerciseAdapter.addItem();
                        Log.e("Exercise name: ", ex);
                    }
                }

                break;
            }

        }

    }

    private void setupAddExerciseButton(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Add exercise: ", " button pressed");
                //templateExerciseAdapter.addItem();

                Intent intent = new Intent(getApplicationContext(), AddExercisePage.class);
                //Intent intent = new Intent(getContext(), ExerciseItemPage.class);
                //intent.putExtra("position", position);
                //intent.putExtra("exercise_name", exercisesAdapter.getExerciseName(position));
                startActivityForResult(intent, SUBACTIVITY_CODE);;
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

                // Create workout entry into a workout table...

                finish();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
