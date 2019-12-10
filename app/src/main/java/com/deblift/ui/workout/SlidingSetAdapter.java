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
import java.util.List;

public class SlidingSetAdapter extends RecyclerView.Adapter<SlidingSetAdapter.MyViewHolder> {

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




    public SlidingSetAdapter() {

        sets.add(new Set(1, 0, 0, false));
    }

    public SlidingSetAdapter(ArrayList<Set> sets) {
        this.sets = sets;
        if(sets.isEmpty())
            sets.add(new Set(1, 0, 0, false));

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_sliding_workout_exercise_set_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.setCountText.setText(Integer.toString(sets.get(position).getPosition()));

        holder.repsInput.setText(Integer.toString(sets.get(position).getReps()));
        holder.weightInput.setText(Float.toString(sets.get(position).getWeight()));

        holder.setFinishedCheckBox.setTag(position);
        holder.setFinishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    holder.itemView.setBackgroundColor(Color.argb(40, 0, 255, 0));
                    holder.weightInput.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    holder.repsInput.setBackgroundColor(Color.argb(0, 0, 0, 0));

                }
                else {
                    holder.itemView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    holder.weightInput.setBackgroundResource(R.drawable.border_textedit);
                    holder.repsInput.setBackgroundResource(R.drawable.border_textedit);
                }
                sets.get((Integer)buttonView.getTag()).setChecked(isChecked);
            }
        });


        holder.weightInput.setTag(position);
        holder.weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Only enable textbox when both editable fields are not empty
                    /*if(s.equals("") || repsInput.getText().toString().isEmpty())
                        setFinishedCheckBox.setEnabled(false);
                    else
                        setFinishedCheckBox.setEnabled(true);*/
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
                // Only enable textbox when both editable fields are not empty
                    /*if(s.equals("") || weightInput.getText().toString().isEmpty())
                        setFinishedCheckBox.setEnabled(false);
                    else
                        setFinishedCheckBox.setEnabled(true);*/
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


    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
        if(sets.isEmpty())
            sets.add(new Set(1, 0, 0, false));
        notifyDataSetChanged();
    }
}
