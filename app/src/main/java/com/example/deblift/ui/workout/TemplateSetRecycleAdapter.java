package com.example.deblift.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;

public class TemplateSetRecycleAdapter extends RecyclerView.Adapter {

    public int setCount = 3;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workout_template_exercise_set_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final TextView setNum = holder.itemView.findViewById(R.id.set_number);
        setNum.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return setCount;
    }
    public void addItem(int pos) {
        setCount++;

        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        setCount--;

        notifyDataSetChanged();
    }
}
