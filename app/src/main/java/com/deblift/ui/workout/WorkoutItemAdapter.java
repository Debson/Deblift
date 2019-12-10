package com.deblift.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deblift.R;
import com.deblift.database.AppRoomDatabase;

public class WorkoutItemAdapter extends RecyclerView.Adapter<WorkoutItemAdapter.MyViewHolder>
{
    AppRoomDatabase appDb;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseName;
        private TextView muscleGroup;
        private ImageView exerciseImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exercise_name_text);
            muscleGroup = itemView.findViewById(R.id.muscle_group_text);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
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
        holder.exerciseImage.setImageResource(R.drawable.ic_deblift_small);

    }

    @Override
    public int getItemCount() {
        return workoutEntity.workoutExercisesList.size();
    }

}
