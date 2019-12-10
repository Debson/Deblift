package com.deblift.ui.history;


import com.deblift.ui.workout.Set;

import java.util.List;


public class WorkoutExercise {

    private String exercise;
    private int setCount;

    private List<Set> sets;

    public WorkoutExercise(String exercise, int setCount) {
        this.exercise = exercise;
        this.setCount = setCount;
        this.sets = null;
    }

    public WorkoutExercise(String exercise, int setCount, List<Set> sets) {
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

    public List<Set> getSets() {
        return sets;
    }
}
