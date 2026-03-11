package org.example.fitnesslogbook.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseSetTest {

    @Test
    @DisplayName("Create ExerciseSet with reps and weight")
    void test01() {
        ExerciseSet set = ExerciseSet.of(10, 50.0);
        assertEquals(10, set.getReps());
        assertEquals(50.0, set.getWeight());
        assertNotNull(set.getDate());
    }

    @Test
    @DisplayName("Create ExerciseSet with reps, weight, and date")
    void test02() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        ExerciseSet set = ExerciseSet.of(8, 60.5, date);
        assertEquals(8, set.getReps());
        assertEquals(60.5, set.getWeight());
        assertEquals(date, set.getDate());
    }

    @Test
    @DisplayName("Fail to create ExerciseSet with zero or negative reps")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> ExerciseSet.of(0, 50.0));
        assertThrows(IllegalArgumentException.class, () -> ExerciseSet.of(-5, 50.0, LocalDate.now()));
    }

    @Test
    @DisplayName("Fail to create ExerciseSet with negative weight")
    void test04() {
        assertThrows(IllegalArgumentException.class, () -> ExerciseSet.of(10, -10.0));
        assertThrows(IllegalArgumentException.class, () -> ExerciseSet.of(10, -1.0, LocalDate.now()));
    }

    @Test
    @DisplayName("Fail to create ExerciseSet with null date")
    void test05() {
        assertThrows(IllegalArgumentException.class, () -> ExerciseSet.of(10, 50.0, null));
    }
}
