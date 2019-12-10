package com.deblift.ui.workout;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;

import java.util.ArrayList;

public class TemplateSetAdapter extends RecyclerView.Adapter<TemplateSetAdapter.MyViewHolder> {

    private ArrayList<Set> sets = new ArrayList<>();
    private int parentIndex = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {

        public int position = 0;
        public TextView setCountText;
        public CheckBox setFinishedCheckBox;
        public EditText weightInput;
        public EditText repsInput;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);


            setCountText = itemView.findViewById(R.id.set_number);
            setFinishedCheckBox = itemView.findViewById(R.id.set_finished_checkbox);
            weightInput = itemView.findViewById(R.id.weight_input);
            repsInput = itemView.findViewById(R.id.reps_input);

            String weightStr = weightInput.getText().toString();
            String repsStr = repsInput.getText().toString();

            // ALLOW User can only check mark sets with both filled edit fields. Currently disabled
            // There is no text in text fields so disable check boxes on start.
            //setFinishedCheckBox.setEnabled(false);

        }
    }





    public TemplateSetAdapter() {

        sets.add(new Set(1, 0, 0, false));
    }

    public TemplateSetAdapter(ArrayList<Set> sets) {
        this.sets = sets;
        if(sets.isEmpty())
            sets.add(new Set(1, 0, 0, false));
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workout_template_exercise_set_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.setCountText.setText(Integer.toString(sets.get(position).getPosition()));

        holder.repsInput.setText(Integer.toString(sets.get(position).getReps()));
        holder.weightInput.setText(Float.toString(sets.get(position).getWeight()));


        holder.weightInput.setTag(position);
        holder.weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() > 0)
                    sets.get(holder.getAdapterPosition()).setWeight(Float.parseFloat(s.toString()));
                else
                    sets.get(holder.getAdapterPosition()).setWeight(0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.repsInput.setTag(position);
        holder.repsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() > 0)
                    sets.get(holder.getAdapterPosition()).setReps(Integer.parseInt(s.toString()));
                else
                    sets.get(holder.getAdapterPosition()).setReps(0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return sets.size();
    }
    public void addItem() {
        sets.add(new Set(sets.size() + 1, 0, 0, false));

        notifyDataSetChanged();
    }

    public void removeItem(int pos) {

        sets.remove(pos);

        for(int i = pos; i < sets.size(); i++) {
            sets.get(i).setPosition(i + 1);
        }

        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getParentIndex() {
        return this.parentIndex;
    }


    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
        if(sets.isEmpty())
            sets.add(new Set(1, 0, 0, false));
        notifyDataSetChanged();
    }

}
