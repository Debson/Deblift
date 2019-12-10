package com.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;
import com.deblift.database.AppRoomDatabase;

public class WorkoutItemAdapter extends RecyclerView.Adapter<WorkoutItemAdapter.MyViewHolder>
{
    AppRoomDatabase appDb;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView muscleGroup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseNameText);
            muscleGroup = itemView.findViewById(R.id.bodyPartText);
        }
    }

    private WorkoutEntity workoutEntity;

    public WorkoutItemAdapter(Context context, WorkoutEntity workoutEntity) {
        this.workoutEntity = workoutEntity;

        appDb = AppRoomDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workouts_item_page_listview, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String exerciseName = workoutEntity.workoutExercisesList.get(position).getExercise();
        holder.exerciseName.setText(exerciseName);
        String muscleGroup = appDb.exercisesDao().loadExercise(exerciseName).getMuscleGroup();
        holder.muscleGroup.setText(muscleGroup);

    }

    @Override
    public int getItemCount() {
        return workoutEntity.workoutExercisesList.size();
    }

}
