package org.example.fitnesslogbook.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OnboardingForm {
    @NotBlank(message = "Du musst einen Namen angeben!")
    private String name;
    @NotNull(message = "Bitte gib dein Alter an!")
    @Min(value = 18,message = "Du musst mindestens 18 Jahre alt seien um die App nutzen zu können!")
    private Integer age;
    @NotNull(message = "Bitte gib deine Größe an!")
    @Min(value = 0, message = "Deine Größe kann nicht negativ seien!")
    private Double height;
    @NotNull(message = "Bitte gib dein Gewicht an!")
    @Min(value = 0, message = "Deine Gewicht kann nicht negativ seien!")
    private Double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
