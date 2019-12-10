package com.deblift.database;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.ui.exercises.Exercise;
import com.deblift.ui.exercises.ExerciseDao;
import com.deblift.ui.workout.WorkoutEntity;
import com.deblift.ui.workout.WorkoutTemplateDao;


@Database(entities = {Exercise.class, WorkoutEntity.class}, exportSchema = false, version = 8)
public abstract class AppRoomDatabase extends RoomDatabase {
    private static final String DB_NAME = MainActivity.resoruces.getString(R.string.database_name);
    private static AppRoomDatabase instance;

    public static synchronized AppRoomDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppRoomDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract ExerciseDao exercisesDao();

    public abstract WorkoutTemplateDao workoutTemplateDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
