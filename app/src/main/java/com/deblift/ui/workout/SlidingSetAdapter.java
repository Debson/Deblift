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

public class SlidingSetAdapter extends RecyclerView.Adapter<SlidingSetAdapter.MyViewHolder> {

    private int setCounter = 1;
    private boolean initialized = false;
    private int parentIndex = 0;

    private CheckBox setFinishedCheckBox;
    private ArrayList<Boolean> checkedList = new ArrayList<>();
    private ArrayList<Set> sets = new ArrayList<>();
    private SlidingExerciseAdapter parentAdapter;


    class MyViewHolder extends RecyclerView.ViewHolder {

        private int position;
        public TextView setCountText;
        public CheckBox setFinishedCheckBox;
        public EditText weightInput;
        public EditText repsInput;
        private Set set;



        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            setCountText = itemView.findViewById(R.id.set_number);
            setFinishedCheckBox = itemView.findViewById(R.id.set_finished_checkbox);
            weightInput = itemView.findViewById(R.id.weight_input);
            repsInput = itemView.findViewById(R.id.reps_input);

            String weightStr = weightInput.getText().toString();
            String repsStr = repsInput.getText().toString();
            float weight = weightStr.isEmpty() ? 0.f : Float.parseFloat(weightStr);
            int reps = repsStr.isEmpty() ? 0 : Integer.parseInt(repsStr);

            set = new Set(position, weight, reps, setFinishedCheckBox.isChecked());
            sets.add(set);

            // ALLOW User can only check mark sets with both filled edit fields. Currently disabled
            // There is no text in text fields so disable check boxes on start.
            //setFinishedCheckBox.setEnabled(false);

            setFinishedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked) {
                        itemView.setBackgroundColor(Color.argb(40, 0, 255, 0));
                        weightInput.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        repsInput.setBackgroundColor(Color.argb(0, 0, 0, 0));

                    }
                    else {
                        itemView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        weightInput.setBackgroundResource(R.drawable.border_textedit);
                        repsInput.setBackgroundResource(R.drawable.border_textedit);
                    }

                    set.setChecked(isChecked);
                }
            });

            weightInput.addTextChangedListener(new TextWatcher() {
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
                    set.setWeight(Float.parseFloat(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            repsInput.addTextChangedListener(new TextWatcher() {
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

                    set.setReps(Integer.parseInt(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        void setPosition(int position) {
            this.position = position;

            set.setPosition(position);

            setCountText.setText(Integer.toString(position));
        }

    }




    public SlidingSetAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_sliding_workout_exercise_set_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.setPosition(position);


    }

    @Override
    public int getItemCount() {
        return setCounter;
    }
    public void addItem(int pos) {
        setCounter++;

        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        setCounter--;

        if(setCounter <= 0)
        {
            parentAdapter.removeItem(parentIndex);
        }

        notifyDataSetChanged();
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public void setParentAdapter(SlidingExerciseAdapter adapter) {
        this.parentAdapter = adapter;
    }

    public void setSetCounter(int setCounter) {
        this.setCounter = setCounter;
    }



    public void initializeSetCounter(int setCounter) {
        if(!initialized) {
            this.setCounter = setCounter;
            initialized = true;
        }
    }

    public boolean isSetCounterInitialized() {
        return initialized;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }
}
