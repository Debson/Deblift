package com.deblift.ui.exercises;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "exercises")
public class Exercise
{
    @NonNull
    @PrimaryKey @ColumnInfo (name = "exercise_name")
    private String exerciseName;

    @NonNull
    @ColumnInfo (name = "muscle_group")
    private String muscleGroup;

    @NonNull
    @ColumnInfo (name = "exercise_description")
    private String exerciseDescription;

    @ColumnInfo (name = "exercise_icon")
    private int exerciseIcon;


    public Exercise(String exerciseName, String muscleGroup, String exerciseDescription, int exerciseIcon) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.exerciseDescription = "exercise description1;exercise description 2";
        this.exerciseIcon = exerciseIcon;

    }

    @Ignore
    public Exercise(String exerciseName, String muscleGroup) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.exerciseIcon = 0;

    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public int getExerciseIcon() {
        return exerciseIcon;
    }

    public void setExerciseIcon(int exerciseIcon) {
        this.exerciseIcon = exerciseIcon;
    }
}
