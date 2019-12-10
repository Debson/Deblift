package com.deblift.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deblift.R;
import com.deblift.database.AppRoomDatabase;

import java.util.ArrayList;


public class ExerciseFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExerciseAdapter exercisesAdapter;

    public ExerciseFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercises, container, false);
        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.exercises_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        AppRoomDatabase appDb = AppRoomDatabase.getInstance(this.getActivity());

        // Pass all exercises data from database
        exercisesAdapter = new ExerciseAdapter(this, appDb.exercisesDao().loadAllExercises());
        recyclerView.setAdapter(exercisesAdapter);


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_nav_search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                exercisesAdapter.filter(newText);
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

    public void goToExerciseItemPage(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ExerciseItemPage.class);
        TextView exName = (TextView)view.findViewById(R.id.exerciseNameText);
        if(exName != null)
            intent.putExtra("exercise_name", exName.getText());
        startActivity(intent);
    }
}