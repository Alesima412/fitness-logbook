package org.example.fitnesslogbook.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private final String username;
    private final String name;
    private int age;
    private double height;
    private final List<WeightMeasurements> measurements;
    private final Set<Exercise> exercises;

    private User(String username, String name, int age, double height) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.height = height;
        this.measurements = new ArrayList<>();
        this.exercises = new HashSet<>();
    }

    public static User create(String username, String name, int age, double height) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if(height < 0){
            throw new IllegalArgumentException("height can not be negative");
        }
        return new User(username, name, age, height);
    }

    // Business Logic Methods

    public void recordWeight(double weight) {
        this.measurements.add(WeightMeasurements.of(weight));
    }

    public void logExerciseSet(String exerciseName, int reps, double weight) {
        Exercise exercise = findOrCreateExercise(exerciseName);
        exercise.logSet(reps, weight);
    }

    private Exercise findOrCreateExercise(String exerciseName) {
        for (Exercise ex : exercises) {
            if (ex.getName().equalsIgnoreCase(exerciseName)) {
                return ex;
            }
        }
        Exercise newExercise = Exercise.of(exerciseName);
        exercises.add(newExercise);
        return newExercise;
    }

    public void updateAge(int newAge) {
        if (newAge < this.age) {
            throw new IllegalArgumentException("New age cannot be less than current age");
        }
        this.age = newAge;
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<WeightMeasurements> getMeasurements() {
        return Collections.unmodifiableList(measurements);
    }

    public Set<Exercise> getExercises() {
        return Collections.unmodifiableSet(exercises);
    }

    public double getHeight() {
        return height;
    }

    public void changeHeight(double newHeight){
        if(newHeight <= 0 ){
            throw new IllegalArgumentException("height can not be negative");
        } else {
            this.height = newHeight;
        }
    }
}
