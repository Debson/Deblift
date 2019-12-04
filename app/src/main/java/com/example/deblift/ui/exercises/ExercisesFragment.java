package com.example.deblift.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.deblift.R;
import com.example.deblift.ui.history.HistoryItemPage;
import com.example.deblift.ui.workout.RecyclerTouchListener;

import java.util.ArrayList;


public class ExercisesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ExercisesAdapter exercisesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_exercises, container, false);
        setHasOptionsMenu(true);


        ArrayList<String> exercises = new ArrayList<>();
        ArrayList<String> muscleGroups = new ArrayList<>();
        ArrayList<Integer> icons = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            exercises.add("Exercise " + i);
            muscleGroups.add("Legs");
            icons.add(R.drawable.ic_dumbbell_black_24dp);
        }

        recyclerView = root.findViewById(R.id.exercises_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        exercisesAdapter = new ExercisesAdapter(exercises, muscleGroups, icons);
        recyclerView.setAdapter(exercisesAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Item clicked", Long.toString(position));

                Intent intent = new Intent(getContext(), TestSlidingUpPanelPage.class);
                //Intent intent = new Intent(getContext(), ExercisesItemPage.class);
                //intent.putExtra("position", position);
                //intent.putExtra("exercise_name", exercisesAdapter.getExerciseName(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_nav_search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);

        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                exercisesAdapter.filter(newText);
                //Log.d("Msg: ", newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(queryTextListener);
    }

}