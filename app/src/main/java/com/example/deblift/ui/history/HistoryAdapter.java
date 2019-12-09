package com.example.deblift.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;
import com.example.deblift.utils.MyViewHolder;

public class HistoryAdapter extends RecyclerView.Adapter {

    private HistoryFragment historyFragment;

    public HistoryAdapter(HistoryFragment historyFragment) {
        this.historyFragment = historyFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_history_listview, parent, false);

        return new MyViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyFragment.goToHistoryItemPage();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
