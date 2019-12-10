/*
 * Date: 10/12/2019
 * Name: Michal Debski
 * Class: DT211C
 * Description:
 *
 */
package com.deblift.database;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.deblift.MainActivity;
import com.deblift.R;
import com.deblift.ui.exercises.Exercise;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class DatabaseGenerator {

    public DatabaseGenerator() {

    }

    public static void GenerateExerciseData(MainActivity app) {
        try {
            InputStreamReader is = new InputStreamReader(app.getAssets().open("exercises.csv"));
            CSVReader reader = new CSVReader(is);
            String[] nextLine;

            AppRoomDatabase appDb = AppRoomDatabase.getInstance(app.getApplicationContext());

            appDb.exercisesDao().deleteAll();

            // First line contains column labels
            nextLine = reader.readNext();

            String desc = "Place the bar between the traps and the uppar back, with the hands shoulder width apart.;" +
                    "Place feet shoulder width apart and descend by breaking at the hips and stitting backwards.;" +
                    "Keep the head in a neutral position, back and spine in a straight and neutral position, the core flexed and knees pushed slightly outwards.;" +
                    "Descend to the bottom where gihts are parallel to the floor.;" +
                    "Push through the heel and middle foot to bring yourself back to starting position.;" +
                    "Repeat for reps.";

            while((nextLine = reader.readNext()) != null) {

                Exercise exercise = new Exercise(
                        nextLine[1],
                        nextLine[0],
                        desc,
                        R.drawable.ic_deblift_large);

                appDb.exercisesDao().insertExercise(exercise);

            }

            Exercise[] exercises = appDb.exercisesDao().loadAllExercises();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
