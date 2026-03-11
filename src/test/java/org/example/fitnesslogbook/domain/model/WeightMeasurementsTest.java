package org.example.fitnesslogbook.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WeightMeasurementsTest {

    @Test
    @DisplayName("Create WeightMeasurements with weight")
    void test01() {
        WeightMeasurements wm = WeightMeasurements.of(80.5);
        assertEquals(80.5, wm.getWeight());
        assertNotNull(wm.getDate());
        assertNotNull(wm.getId());
    }

    @Test
    @DisplayName("Create WeightMeasurements with weight and date")
    void test02() {
        LocalDate date = LocalDate.of(2023, 5, 10);
        WeightMeasurements wm = WeightMeasurements.of(75.0, date);
        assertEquals(75.0, wm.getWeight());
        assertEquals(date, wm.getDate());
        assertNotNull(wm.getId());
    }

    @Test
    @DisplayName("Fail to create with invalid weight")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> WeightMeasurements.of(0.0));
        assertThrows(IllegalArgumentException.class, () -> WeightMeasurements.of(-5.0, LocalDate.now()));
    }

    @Test
    @DisplayName("Fail to create with null date")
    void test04() {
        assertThrows(IllegalArgumentException.class, () -> WeightMeasurements.of(70.0, null));
    }

    @Test
    @DisplayName("Set weight successfully")
    void test05() {
        WeightMeasurements wm = WeightMeasurements.of(80.0);
        wm.setWeight(78.5);
        assertEquals(78.5, wm.getWeight());
    }

    @Test
    @DisplayName("Fail to set invalid weight")
    void test06() {
        WeightMeasurements wm = WeightMeasurements.of(80.0);
        assertThrows(IllegalArgumentException.class, () -> wm.setWeight(0));
    }

    @Test
    @DisplayName("Set date successfully")
    void test07() {
        WeightMeasurements wm = WeightMeasurements.of(80.0);
        LocalDate newDate = LocalDate.of(2023, 6, 1);
        wm.setDate(newDate);
        assertEquals(newDate, wm.getDate());
    }

    @Test
    @DisplayName("Fail to set null date")
    void test08() {
        WeightMeasurements wm = WeightMeasurements.of(80.0);
        assertThrows(IllegalArgumentException.class, () -> wm.setDate(null));
    }
}
