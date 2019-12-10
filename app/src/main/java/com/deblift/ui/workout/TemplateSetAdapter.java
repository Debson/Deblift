package com.deblift.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;

public class TemplateSetAdapter extends RecyclerView.Adapter<TemplateSetAdapter.MyViewHolder> {

    public int setCount = 1;


    class MyViewHolder extends RecyclerView.ViewHolder {

        private int position;
        public TextView setCountText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            setCountText = itemView.findViewById(R.id.set_number);
        }
        void setPosition(int position) {
            this.position = position;

            setCountText.setText(Integer.toString(position));
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workout_template_exercise_set_listview, parent, false);


        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Position starts from 1
        holder.setPosition(position + 1);
    }

    @Override
    public int getItemCount() {
        return setCount;
    }
    public void addItem(int pos) {
        setCount++;
        notifyDataSetChanged();
        //notifyItemInserted(setCounter - 1);
    }

    public void removeItem(int pos) {

        setCount--;
        notifyDataSetChanged();
        //notifyItemRemoved(setCounter +1);
    }

}
