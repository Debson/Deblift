package com.example.deblift.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.deblift.R;
import com.example.deblift.ui.workout.WorkoutsCustomAdapter;
import com.example.deblift.ui.workout.WorkoutsItemPage;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private HistoryCustomAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        setHasOptionsMenu(true);


        adapter = new HistoryCustomAdapter(getContext());

        final ListView listView = root.findViewById(R.id.history_list);
        setupListView(listView);

        listView.setAdapter(adapter);

        return root;
    }

    private void setupListView(ListView listView) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item clicked", Long.toString(position));

                Intent intent = new Intent(getContext(), HistoryItemPage.class);
                //intent.putExtra("position", position);
                //intent.putExtra("exercise_name", adapter.getExerciseName(position));
                startActivity(intent);
            }
        });
    }
}