package org.example.fitnesslogbook.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Create User successfully")
    void test01() {
        User user = User.create("John", 30, 180.0);
        assertEquals("John", user.getName());
        assertEquals(30, user.getAge());
        assertEquals(180.0, user.getHeight());
        assertTrue(user.getMeasurements().isEmpty());
        assertTrue(user.getExercises().isEmpty());
        assertNull(user.getPinnedExercise());
    }

    @Test
    @DisplayName("Fail to create User with invalid parameters")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> User.create(null, 30, 180.0));
        assertThrows(IllegalArgumentException.class, () -> User.create("   ", 30, 180.0));
        assertThrows(IllegalArgumentException.class, () -> User.create("John", -1, 180.0));
        assertThrows(IllegalArgumentException.class, () -> User.create("John", 30, -5.0));
    }

    @Test
    @DisplayName("Change height successfully")
    void test03() {
        User user = User.create("John", 30, 180.0);
        user.changeHeight(185.0);
        assertEquals(185.0, user.getHeight());
    }

    @Test
    @DisplayName("Fail to change height to invalid value")
    void test04() {
        User user = User.create("John", 30, 180.0);
        assertThrows(IllegalArgumentException.class, () -> user.changeHeight(0.0));
        assertThrows(IllegalArgumentException.class, () -> user.changeHeight(-10.0));
    }

    @Test
    @DisplayName("Update age successfully")
    void test05() {
        User user = User.create("John", 30, 180.0);
        user.updateAge(31);
        assertEquals(31, user.getAge());
    }

    @Test
    @DisplayName("Fail to update age to a lower value")
    void test06() {
        User user = User.create("John", 30, 180.0);
        assertThrows(IllegalArgumentException.class, () -> user.updateAge(29));
    }

    @Test
    @DisplayName("Record weight and maintain chronological order")
    void test07() {
        User user = User.create("John", 30, 180.0);
        LocalDate date1 = LocalDate.of(2023, 1, 5);
        LocalDate date2 = LocalDate.of(2023, 1, 1);
        LocalDate date3 = LocalDate.of(2023, 1, 10);
        
        user.recordWeight(80.0, date1);
        user.recordWeight(81.0, date2);
        user.recordWeight(79.0, date3);
        
        assertEquals(3, user.getMeasurements().size());
        assertEquals(date2, user.getMeasurements().get(0).getDate());
        assertEquals(date1, user.getMeasurements().get(1).getDate());
        assertEquals(date3, user.getMeasurements().get(2).getDate());
    }

    @Test
    @DisplayName("Record weight with current date")
    void test08() {
        User user = User.create("John", 30, 180.0);
        user.recordWeight(80.0);
        assertEquals(1, user.getMeasurements().size());
        assertEquals(80.0, user.getMeasurements().get(0).getWeight());
    }

    @Test
    @DisplayName("Update weight measurement successfully")
    void test09() {
        User user = User.create("John", 30, 180.0);
        LocalDate initialDate = LocalDate.of(2023, 1, 1);
        user.recordWeight(80.0, initialDate);
        
        UUID id = user.getMeasurements().get(0).getId();
        LocalDate newDate = LocalDate.of(2023, 1, 2);
        user.updateWeight(id, 78.0, newDate);
        
        assertEquals(78.0, user.getMeasurements().get(0).getWeight());
        assertEquals(newDate, user.getMeasurements().get(0).getDate());
    }

    @Test
    @DisplayName("Delete weight measurement successfully")
    void test10() {
        User user = User.create("John", 30, 180.0);
        user.recordWeight(80.0);
        UUID id = user.getMeasurements().get(0).getId();
        
        user.deleteWeight(id);
        assertTrue(user.getMeasurements().isEmpty());
    }

    @Test
    @DisplayName("Add and retrieve exercise")
    void test11() {
        User user = User.create("John", 30, 180.0);
        user.addExercise("Deadlift");
        
        assertEquals(1, user.getExercises().size());
        assertNotNull(user.getExercise("Deadlift"));
        assertNull(user.getExercise("Squat"));
        
        // Adding the same exercise again should not create a duplicate
        user.addExercise("deadlift");
        assertEquals(1, user.getExercises().size());
    }

    @Test
    @DisplayName("Log exercise set successfully")
    void test12() {
        User user = User.create("John", 30, 180.0);
        user.logExerciseSet("Squat", 5, 120.0);
        
        Exercise squat = user.getExercise("Squat");
        assertNotNull(squat);
        assertEquals(1, squat.getExerciseSets().size());
        assertEquals(5, squat.getExerciseSets().get(0).getReps());
        assertEquals(120.0, squat.getExerciseSets().get(0).getWeight());
    }

    @Test
    @DisplayName("Pin and unpin exercise successfully")
    void test13() {
        User user = User.create("John", 30, 180.0);
        user.addExercise("Bench Press");
        
        user.pinExercise("Bench Press");
        assertNotNull(user.getPinnedExercise());
        assertEquals("Bench Press", user.getPinnedExercise().getName());
        
        user.unpinExercise();
        assertNull(user.getPinnedExercise());
    }

    @Test
    @DisplayName("Fail to pin non-existent exercise")
    void test14() {
        User user = User.create("John", 30, 180.0);
        assertThrows(IllegalArgumentException.class, () -> user.pinExercise("NonExistent"));
    }
}
