package org.example.fitnesslogbook.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    @Test
    @DisplayName("Create Exercise successfully")
    void test01() {
        Exercise exercise = Exercise.of("Bench Press");
        assertEquals("Bench Press", exercise.getName());
        assertTrue(exercise.getExerciseSets().isEmpty());
    }

    @Test
    @DisplayName("Fail to create Exercise with null name")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> Exercise.of(null));
    }

    @Test
    @DisplayName("Fail to create Exercise with empty name")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> Exercise.of("   "));
    }

    @Test
    @DisplayName("Log a set successfully")
    void test04() {
        Exercise exercise = Exercise.of("Squat");
        exercise.logSet(10, 100.0);
        assertEquals(1, exercise.getExerciseSets().size());
        assertEquals(10, exercise.getExerciseSets().get(0).getReps());
        assertEquals(100.0, exercise.getExerciseSets().get(0).getWeight());
    }
}
