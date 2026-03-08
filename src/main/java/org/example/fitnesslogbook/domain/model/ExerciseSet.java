package org.example.fitnesslogbook.domain.model;

import java.time.LocalDate;

public class ExerciseSet {
    private final int reps;
    private final double weight;
    private final LocalDate date;

    private ExerciseSet(int reps, double weight, LocalDate date) {
        this. reps = reps;
        this.weight = weight;
        this.date = date;
    }

    public static ExerciseSet of(int reps, double weight) {
        if (reps <= 0) {
            throw new IllegalArgumentException("Reps must be greater than zero");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        return new ExerciseSet(reps, weight, LocalDate.now());
    }

    public static ExerciseSet of(int reps, double weight, LocalDate date) {
        if (reps <= 0) {
            throw new IllegalArgumentException("Reps must be greater than zero");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return new ExerciseSet(reps, weight, date);
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }
}
