package com.example.deblift.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryAdapter historyAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.history_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        historyAdapter = new HistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);


        return root;
    }

    public void goToHistoryItemPage()
    {
        Intent intent = new Intent(getContext(), HistoryItemPage.class);
        //intent.putExtra("position", position);
        //intent.putExtra("exercise_name", adapter.getExerciseName(position));
        startActivity(intent);
    }
}