import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    List<Horse> emptyHorsesList = new ArrayList<>();
    List<Horse> horseList;

    private List<Horse> getHorseList(int horseNumber) {
            horseList = new ArrayList<>();
        for (int i = 0; i < horseNumber; i++) {
            horseList.add(new Horse("Number " + i, i + 1.0));
        }
        return horseList;
    }

    @Test
    void nullToConstructorThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void nullToConstructorMessage() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null)).getMessage();
        assertEquals("Horses cannot be null.", message);
    }

    @Test
    void emptyListToConstructorThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyHorsesList));
    }

    @Test
    void emptyListConstructorMessage() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(emptyHorsesList)).getMessage();
        assertEquals("Horses cannot be empty.", message);
    }

    @Test
    void getHorses() {
        horseList = getHorseList(30);
        Hippodrome hippodrome = new Hippodrome(horseList);
        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void moveForFiftyHorses() {
        horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horseList.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();
        for (Horse horse: hippodrome.getHorses()) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        int horses = 20;
        horseList = getHorseList(horses);
        Horse horseWinner = horseList.get(0);
        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();
        for (Horse horse: hippodrome.getHorses()) {
            if (horse.getDistance() > horseWinner.getDistance()) {
                horseWinner = horse;
            }
        }
        assertEquals(horseWinner, hippodrome.getWinner());
    }
}