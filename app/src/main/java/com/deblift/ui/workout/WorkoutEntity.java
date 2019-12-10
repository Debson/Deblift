package com.deblift.ui.workout;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.deblift.ui.history.WorkoutExercise;
import com.deblift.ui.history.WorkoutTypeConverter;


import java.util.List;

@Entity(tableName = "workouts")
public class WorkoutEntity {

    public static final int WORKOUT_TEMPLATE = 1;
    public static final int WORKOUT_HISTORY = 2;

    @NonNull
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "workout_id")
    private int workoutId;

    @ColumnInfo(name = "workout_name")
    private String workoutName;

    @ColumnInfo (name = "workout_type")
    private int workoutType;

    @ColumnInfo (name = "workout_date")
    private long workoutDate;           // in milliseconds

    @ColumnInfo (name = "workout_duration")
    private long workoutDuration;       // in milliseconds

    @ColumnInfo (name = "workout_exercises")
    private int workoutExercise;

    @ColumnInfo (name = "workout_exercises_list")
    @TypeConverters(WorkoutTypeConverter.class)
    public List<WorkoutExercise> workoutExercisesList;



    public WorkoutEntity(String workoutName,
                         int workoutType,
                         long workoutDate,
                         long workoutDuration,
                         int workoutExercise) {
        this.workoutName = workoutName;
        this.workoutType = workoutType;
        this.workoutDate = workoutDate;
        this.workoutDuration = workoutDuration;
        this.workoutExercise = workoutExercise;
    }

    @Ignore
    public WorkoutEntity(String workoutName,
                         int workoutType,
                         long workoutDate,
                         int workoutExercise) {
        this.workoutName = workoutName;
        this.workoutType = workoutType;
        this.workoutDate = workoutDate;
        this.workoutExercise = workoutExercise;
    }

    @Ignore
    public WorkoutEntity(String workoutName,
                         int workoutType,
                         int workoutExercise) {
        this.workoutName = workoutName;
        this.workoutType = workoutType;
        this.workoutExercise = workoutExercise;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public long getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(long workoutDate) {
        this.workoutDate = workoutDate;
    }

    public int getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(int workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public List<WorkoutExercise> getWorkoutExercisesList() {
        return workoutExercisesList;
    }

    public void setWorkoutExercises(List<WorkoutExercise> workoutExercisesList) {
        this.workoutExercisesList = workoutExercisesList;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(int workoutType) {
        this.workoutType = workoutType;
    }

    public long getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(long workoutDuration) {
        this.workoutDuration = workoutDuration;
    }
}
