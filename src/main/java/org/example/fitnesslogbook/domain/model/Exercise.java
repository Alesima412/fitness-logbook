package org.example.fitnesslogbook.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise {
    private final String name;
    private final List<ExerciseSet> exerciseSets;

    private Exercise(String name) {
        this.name = name;
        this.exerciseSets = new ArrayList<>();
    }

    public static Exercise of(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be empty");
        }
        return new Exercise(name);
    }

    public void logSet(int reps, double weight) {
        this.exerciseSets.add(ExerciseSet.of(reps, weight));
    }

    public String getName() {
        return name;
    }

    public List<ExerciseSet> getExerciseSets() {
        return Collections.unmodifiableList(exerciseSets);
    }
}
