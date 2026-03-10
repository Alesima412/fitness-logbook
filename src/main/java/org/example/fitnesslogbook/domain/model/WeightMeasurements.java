package org.example.fitnesslogbook.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class WeightMeasurements {
    private final UUID id;
    private double weight;
    private LocalDate date;

    private WeightMeasurements(UUID id, double weight, LocalDate date) {
        this.id = id;
        this.weight = weight;
        this.date = date;
    }

    public static WeightMeasurements of(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        return new WeightMeasurements(UUID.randomUUID(), weight, LocalDate.now());
    }

    public static WeightMeasurements of(double weight, LocalDate date) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return new WeightMeasurements(UUID.randomUUID(), weight, date);
    }

    public UUID getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }
}
