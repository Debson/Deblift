package com.deblift.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.ui.workout.Set;
import com.deblift.ui.workout.WorkoutEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private HistoryFragment historyFragment;
    private WorkoutEntity[] workouts;


    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView workoutName;
        public TextView workoutDate;
        public TextView workoutTime;
        public TextView workoutVolume;
        public TextView workoutExercises;
        public TextView workoutBestSets;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.history_workout_nameText);
            workoutDate = itemView.findViewById(R.id.history_workout_date);
            workoutTime = itemView.findViewById(R.id.history_time_text);
            workoutVolume = itemView.findViewById(R.id.history_volume_text);
            workoutExercises = itemView.findViewById(R.id.history_workout_exercises);
            workoutBestSets = itemView.findViewById(R.id.history_workout_sets_text);
        }
    }

    public HistoryAdapter(HistoryFragment historyFragment, WorkoutEntity[] workouts) {
        this.historyFragment = historyFragment;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_history_listview, parent, false);

        return new MyViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.workoutName.setText(workouts[position].getWorkoutName());

        String dateStr = prepareWorkoutDateString(position);
        holder.workoutDate.setText(dateStr);

        String lengthStr = prepareWorkoutLengthString(position);
        holder.workoutTime.setText(lengthStr);

        String volumeStr = prepareWorkoutVolumeString(position) + " " + MainActivity.resoruces.getString(R.string.weight_unit);
        holder.workoutVolume.setText(volumeStr);

        String exercisesStr = prepareWorkoutExercisesString(position);
        holder.workoutExercises.setText(exercisesStr);



        holder.itemView.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyFragment.goToHistoryItemPage();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.length;
    }


    private String prepareWorkoutLengthString(int position) {
        String str = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(workouts[position].getWorkoutDate());

        str += new SimpleDateFormat("H").format(calendar.getTime()) + "h ";
        str += new SimpleDateFormat("M").format(calendar.getTime()) + "m";

        return str;
    }

    private String prepareWorkoutVolumeString(int position) {
        String str = "";
        float volume = 0;
        for(WorkoutExercise w : workouts[position].workoutExercisesList) {
            for(Set s : w.getSets()) {
                volume += (float)s.getReps() * s.getWeight();
            }
        }

        str = Float.toString(volume);
        return str;
    }

    private String prepareWorkoutExercisesString(int position) {
        String str = "";
        for(WorkoutExercise w : workouts[position].workoutExercisesList) {
            str += w.getSetCount() + " x ";
            str += w.getExercise();
            str += '\n';
        }

        return str;
    }

    private String prepareWorkoutDateString(int position) {
        String str = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(workouts[position].getWorkoutDate());

        str = new SimpleDateFormat("d MMM yyyy  HH:MM").format(calendar.getTime());

        return str;
    }
}
