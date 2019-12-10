package com.deblift.database;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.deblift.MainActivity;
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

            while((nextLine = reader.readNext()) != null) {

                Exercise exercise = new Exercise(
                        nextLine[1],
                        nextLine[0],
                        "sample exercise sjhyjuy htywt;descriptionasdasd asdasda asd asdasd ;textasd asdaawe erwqgjjjjg ghjghj ghdhdffgd dfgdfgdf",
                        0);

                appDb.exercisesDao().insertExercise(exercise);

            }

            Exercise[] exercises = appDb.exercisesDao().loadAllExercises();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
