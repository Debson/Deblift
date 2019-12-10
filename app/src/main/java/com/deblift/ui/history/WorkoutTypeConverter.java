package com.deblift.ui.history;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class WorkoutTypeConverter {

    private static Gson gson = new Gson();


    @TypeConverter
    public static List<WorkoutExercise> stringToWorkoutList(String data) {
        if(data == null)
            return Collections.emptyList();

        Type listType = new TypeToken<List<WorkoutExercise>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String workoutListToString(List<WorkoutExercise> workoutExercises) {
        return gson.toJson(workoutExercises);
    }

}
