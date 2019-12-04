package com.example.deblift.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deblift.R;

public class TestSlidingUpPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_item_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
