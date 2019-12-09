package com.example.deblift.ui.workout;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;
import com.example.deblift.utils.MyViewHolder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SlidingSetAdapter extends RecyclerView.Adapter {

    public int setCount = 3;

    private CheckBox setFinishedCheckBox;
    private ArrayList<Boolean> checkedList = new ArrayList<>();

    private EditText weightInput;
    private EditText repsInput;


    public SlidingSetAdapter() {
        for(int i = 0; i < setCount; i++)
            checkedList.add(false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_sliding_workout_exercise_set_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final TextView setNum = holder.itemView.findViewById(R.id.set_number);
        setNum.setText(Integer.toString(position + 1));


        setFinishedCheckBox = holder.itemView.findViewById(R.id.set_finished_checkbox);
        setFinishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                weightInput = holder.itemView.findViewById(R.id.weight_input);
                repsInput = holder.itemView.findViewById(R.id.reps_input);

                if(isChecked) {
                    holder.itemView.setBackgroundColor(Color.argb(40, 0, 255, 0));
                    weightInput.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    repsInput.setBackgroundColor(Color.argb(0, 0, 0, 0));

                }
                else {
                    holder.itemView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    weightInput.setBackgroundResource(R.drawable.border_textedit);
                    repsInput.setBackgroundResource(R.drawable.border_textedit);
                }

                notifyDataSetChanged();
            }
        });



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

    public void setSetCount(int setCount)
    {
        this.setCount = setCount;
    }
}
