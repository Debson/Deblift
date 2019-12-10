package com.deblift.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.ui.workout.Set;
import com.deblift.ui.workout.WorkoutEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

        public ImageView workoutTimeImg;
        public ImageView workoutVolumeImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.history_workout_nameText);
            workoutDate = itemView.findViewById(R.id.history_workout_date);
            workoutTime = itemView.findViewById(R.id.history_time_text);
            workoutVolume = itemView.findViewById(R.id.history_volume_text);
            workoutExercises = itemView.findViewById(R.id.history_workout_exercises);
            workoutBestSets = itemView.findViewById(R.id.history_workout_sets_text);
            workoutTimeImg = itemView.findViewById(R.id.history_time_icon);
            workoutVolumeImg = itemView.findViewById(R.id.history_volume_icon);
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

        String bestSets = prepareBestSetsString(position);
        holder.workoutBestSets.setText(bestSets);

        holder.workoutTimeImg.setImageResource(R.drawable.ic_deblift_small);
        holder.workoutVolumeImg.setImageResource(R.drawable.ic_deblift_small);


        holder.itemView.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {

                // HAD NO TIME TO IMPLEMENT THAT
                //historyFragment.goToHistoryItemPage();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.length;
    }


    private String prepareWorkoutLengthString(int position) {
        String str = "";
        long workoutTimeMillis = workouts[position].getWorkoutDuration();

        long hours = TimeUnit.MILLISECONDS.toHours(workoutTimeMillis);
        workoutTimeMillis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(workoutTimeMillis);
        workoutTimeMillis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(workoutTimeMillis);

        str = hours > 0 ? (hours + "h ") : "";
        str += minutes > 0 ? (minutes + "m ") : "";
        str += seconds + "s";

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
            if(w.getSets().size() > 0) {
                str += w.getSets().size() + " x ";
                str += w.getExercise();
                str += '\n';
            }
        }

        return str;
    }

    private String prepareBestSetsString(int position) {
        String str = "";
        for(WorkoutExercise w : workouts[position].workoutExercisesList) {
            float max = 0;
            String exMax = "";
            for(Set s : w.getSets()) {
                float vol = (float)s.getReps() * s.getWeight();
                if(vol >= max) {
                    max = vol;
                    exMax = s.getWeight() + MainActivity.resoruces.getString(R.string.weight_unit) + " x " + s.getReps() + '\n';
                }
            }
            str += exMax;
        }

        return str;

    }

    private String prepareWorkoutDateString(int position) {
        String str = "";

        Date date = new Date();
        date.setTime(workouts[position].getWorkoutDate());
        str = new SimpleDateFormat("d MMM yyyy  HH:MM").format(date);

        return str;
    }
}
