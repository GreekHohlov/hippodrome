import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    Horse horseTwoParameters;
    Horse horseThreeParameters;
    double horseDistance = 10;
    double horseSpeed = 2.4;
    String horseName = "Bucephalus";

    @BeforeEach
    void setUp() {
        horseTwoParameters = new Horse(horseName, horseSpeed);
        horseThreeParameters = new Horse("Bucephalus", horseSpeed, horseDistance);
    }

    @Test
    void nullHorseNameThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, horseSpeed, horseDistance));
    }
    @Test
    void nullHorseNameReturnMessage() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, horseSpeed, horseDistance)).getMessage();
        assertEquals("Name cannot be null.", message);
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void emptyHorseNameThrow(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(value, horseSpeed, horseDistance));
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void emptyHorseNameReturnMessage(String value) {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse(value, horseSpeed, horseDistance)).getMessage();
        assertEquals("Name cannot be blank.", message);
    }

    @Test
    void negativeHorseSecondParameterThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(horseName, -2, horseDistance));
    }

    @Test
    void negativeHorseSecondParameterMessage() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse(horseName, -2, horseDistance)).getMessage();
        assertEquals("Speed cannot be negative.", message);
    }

    @Test
    void negativeHorseThirdParameterThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(horseName, horseSpeed, -10));
    }

    @Test
    void negativeHorseThirdParameterMessage() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse(horseName, horseSpeed, -10)).getMessage();
        assertEquals("Distance cannot be negative.", message);
    }

    @Test
    void getName() {
        assertEquals(horseName, horseTwoParameters.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(horseSpeed, horseTwoParameters.getSpeed());
    }

    @Test
    void getDistanceHorseThreeParameters() {
        assertEquals(horseDistance, horseThreeParameters.getDistance());
    }

    @Test
    void getDistanceHorseTwoParameters() {
        assertEquals(0, horseTwoParameters.getDistance());
    }

    @Test
    void moveUseRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            horseThreeParameters.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0.4", "0.6", "0.8"
    })
    void moveCalculateDistanceToFormula(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            Horse horse = horseThreeParameters;
            horse.move();
            assertEquals(horseDistance + horseSpeed * randomValue, horse.getDistance());
        }
    }
}