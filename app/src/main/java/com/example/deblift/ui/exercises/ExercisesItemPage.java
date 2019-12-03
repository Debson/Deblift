package com.example.deblift.ui.exercises;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.deblift.R;


public class ExercisesItemPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_item_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra("exercise_name");

        Log.d("ListViewUtem_Page name", exerciseName);

        setTitle(exerciseName);

        WebView exercise_description = findViewById(R.id.exercise_descriptionText);

        String htmlString = "Instructions" +
                            "<ol style='padding-left: 20px;'>" +
                            "<li>First item</li>" +
                            "<li>Second item extension extension extension extension extension extension extension extension extension extension extension extension</li>"+
                            "<li>Third item</li></ol>";
        exercise_description.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
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
