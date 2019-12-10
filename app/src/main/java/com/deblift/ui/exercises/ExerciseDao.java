package com.deblift.ui.exercises;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ExerciseDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void insertExercise(Exercise exercise);

    @Update
    public void updateExercise(Exercise exercise);

    @Delete
    public void deleteExercise(Exercise exercise);

    @Query("SELECT * FROM exercises")
    public Exercise[] loadAllExercises();

    @Query("SELECT * FROM exercises where exercise_name = :exerciseName")
    public Exercise loadExercise(String exerciseName);

    @Query("DELETE FROM exercises")
    void deleteAll();

    @Query("SELECT exercise_description from exercises where exercise_name = :exerciseName")
    public String getExerciseDescription(String exerciseName);
}
