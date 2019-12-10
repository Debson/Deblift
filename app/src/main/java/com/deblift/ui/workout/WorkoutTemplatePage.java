package com.deblift.ui.workout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.deblift.MainActivity;
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
    private boolean editWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_template_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.template_exercise_list);

        recyclerView.setClipToPadding(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        WorkoutEntity workoutEntity = null;

        Intent intent = getIntent();

        editWorkout = intent.getBooleanExtra("edit_workout", false);
        if(editWorkout) {
            int workoutId = intent.getIntExtra("workout_id", 0);
            AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);
            workoutEntity = appDb.workoutDao().loadWorkout(workoutId);

        }else {

            workoutEntity = new WorkoutEntity(
                    MainActivity.resoruces.getString(R.string.empty_workout),
                    WorkoutEntity.WORKOUT_HISTORY);

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
        }

        setTitle(workoutEntity.getWorkoutName());

        templateExerciseAdapter = new TemplateExerciseAdapter(workoutEntity);
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

                        WorkoutExercise we = new WorkoutExercise(ex.getExerciseName());

                        templateExerciseAdapter.addItem(we);
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

                if(templateExerciseAdapter.getWorkoutEntity().workoutExercisesList.isEmpty())
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

                AppRoomDatabase appDb = AppRoomDatabase.getInstance(this);

                templateExerciseAdapter.getWorkoutEntity().setWorkoutType(WorkoutEntity.WORKOUT_TEMPLATE);

                if(editWorkout)
                    appDb.workoutDao().updateWorkout(templateExerciseAdapter.getWorkoutEntity());
                else
                    appDb.workoutDao().insertWorkout(templateExerciseAdapter.getWorkoutEntity());

                templateExerciseAdapter.notifyDataSetChanged();

                setResult(Activity.RESULT_OK);
                finish();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
