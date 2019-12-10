package com.deblift.ui.history;


import com.deblift.ui.workout.Set;

import java.util.ArrayList;
import java.util.List;


public class WorkoutExercise {

    private String exercise = "";
    private int setCount = 1;

    private ArrayList<Set> sets = new ArrayList<>();

    public WorkoutExercise(String exercise) {
        this.exercise = exercise;
        setCount = 1;
        sets = new ArrayList<>();
    }

    public WorkoutExercise(String exercise, int setCount) {
        this.exercise = exercise;
        this.setCount = setCount;
        this.sets = null;
    }

    public WorkoutExercise(String exercise, int setCount, ArrayList<Set> sets) {
        this.exercise = exercise;
        this.setCount = setCount;
        this.sets = sets;
    }

    public String getExercise() {
        return exercise;
    }

    public int getSetCount() {
        return setCount;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }
}
