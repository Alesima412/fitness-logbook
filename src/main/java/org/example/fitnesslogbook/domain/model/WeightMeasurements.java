package org.example.fitnesslogbook.domain.model;

import java.time.LocalDate;

public class WeightMeasurements {
    private final double weight;
    private final LocalDate date;

    private WeightMeasurements(double weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }

    public static WeightMeasurements of(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        return new WeightMeasurements(weight, LocalDate.now());
    }

    public static WeightMeasurements of(double weight, LocalDate date) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return new WeightMeasurements(weight, date);
    }

    public double getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }
}
