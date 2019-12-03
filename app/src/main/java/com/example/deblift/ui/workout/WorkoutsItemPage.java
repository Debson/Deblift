package com.example.deblift.ui.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deblift.R;

public class WorkoutsItemPage extends AppCompatActivity {

    private WorkoutsItemCustomAdapter workoutsItemCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_item_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);

        Intent intent = getIntent();

        workoutsItemCustomAdapter = new WorkoutsItemCustomAdapter(this);

        final ListView itemPageListView = findViewById(R.id.workouts_item_page_list);
        setupListView(itemPageListView);
        itemPageListView.setAdapter(workoutsItemCustomAdapter);


    }

    @Override
    public void onBackPressed() {
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

    private void setupListView(ListView listView) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Workout item clicked", Long.toString(position));

            }
        });
    }
}
