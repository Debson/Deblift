package com.deblift.ui.workout;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface WorkoutTemplateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWorkout(WorkoutEntity workout);

    @Update
    public void updateWorkout(WorkoutEntity workout);

    @Delete
    public void deleteWorkout(WorkoutEntity workout);

    @Query("SELECT * FROM workouts where workout_type = :workoutTYpe")
    public WorkoutEntity[] loadAllWorkouts(int workoutTYpe);

    @Query("SELECT * FROM workouts where workout_id = :workoutId")
    public WorkoutEntity loadWorkout(int workoutId);


    @Query("DELETE FROM workouts")
    void deleteAll();
}
