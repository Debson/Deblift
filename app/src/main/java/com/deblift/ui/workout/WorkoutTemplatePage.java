package com.deblift.ui.workout;

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
import android.widget.Toast;

import com.deblift.R;
import com.deblift.database.AppRoomDatabase;
import com.deblift.ui.exercises.Exercise;
import com.deblift.ui.history.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

public class WorkoutTemplatePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TemplateExerciseAdapter templateExerciseAdapter;
    private Button addExerciseButton;
    private Button editWorkoutName;
    private PopupMenu popup;
    private Toast toast;

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

        recyclerView.setHasFixedSize(false);
        recyclerView.setClipToPadding(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        templateExerciseAdapter = new TemplateExerciseAdapter(this);
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
                ArrayList<String> exerciseNamesArray;
                if(resultCode == Activity.RESULT_OK) {
                    exerciseNamesArray = data.getStringArrayListExtra("selected");

                    AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);

                    for (String exName : exerciseNamesArray) {
                        Exercise ex = appDb.exercisesDao().loadExercise(exName);
                        templateExerciseAdapter.addItem(ex);
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
                Intent intent = new Intent(getApplicationContext(), AddExercisePage.class);

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
                // Save workout

                if(templateExerciseAdapter.getExerciseList().isEmpty())
                {
                    // Cancel toast if already displayed(prevent stacking Toast alerts when user spams a button)
                    if(toast != null)
                        toast.cancel();
                    toast = Toast.makeText(this, "Please add an exercise and some sets before proceeding", Toast.LENGTH_SHORT);

                    // TODO custom toast
                    /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout., null);
                    toast.setView(view);*/

                    toast.show();
                    return false;
                }

                templateExerciseAdapter.saveSets();

                long currentTimeMilis = System.currentTimeMillis();
                WorkoutEntity workoutEntity = new WorkoutEntity(
                        workoutNameEditText.getText().toString(),
                        WorkoutEntity.WORKOUT_TEMPLATE,
                        currentTimeMilis,
                        templateExerciseAdapter.getExerciseList().size());

                List<WorkoutExercise> workoutExerciseList = new ArrayList<>();
                for(int i = 0; i < templateExerciseAdapter.getExerciseList().size(); i++) {
                    workoutExerciseList.add(new WorkoutExercise(templateExerciseAdapter.getExerciseList().get(i).getExerciseName(), templateExerciseAdapter.getSets().get(i)));
                }

                workoutEntity.setWorkoutExercises(workoutExerciseList);

                AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);

                appDb.workoutTemplateDao().insertWorkout(workoutEntity);

                // Create workout entry into a workout table...

                setResult(Activity.RESULT_OK);
                finish();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
