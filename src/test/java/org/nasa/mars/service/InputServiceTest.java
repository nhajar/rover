package org.nasa.mars.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.nasa.mars.exception.InvalidInputException;
import org.nasa.mars.model.Plateau;
import org.nasa.mars.model.Rover;
import org.nasa.mars.model.enums.Direction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InputServiceTest {
    private final InputService service = new InputService();
    @TempDir Path tempDir;

    @Test void readInputValidFileTest() throws Exception {
        Path file = tempDir.resolve("input.txt");
        List<String> lines = List.of("5 5", "1 2 N", "LMLMLMLMM");
        Files.write(file, lines);
        assertEquals(lines, service.readInput(file.toString()));
    }

    @Test void readInputTooShortTest() throws Exception {
        Path file = tempDir.resolve("input.txt");
        Files.write(file, List.of("5 5", "1 2 N"));
        assertThrows(InvalidInputException.class, () -> service.readInput(file.toString()));
    }

    @Test void readInputMissingFileTest() {
        assertThrows(IOException.class, () -> service.readInput(tempDir.resolve("input.txt").toString()));
    }

    @Test void initValidPlateauTest() throws Exception {
        Plateau plateau = service.initPlateau(new String[]{"5", "6"});
        assertAll(() -> assertEquals(5, plateau.getMaxX()), () -> assertEquals(6, plateau.getMaxY()));
    }

    @Test void initInvalidPlateauTest() {
        assertThrows(InvalidInputException.class, () -> service.initPlateau(new String[]{"0", "5"}));
    }

    @Test void initValidRoverTest() throws Exception {
        Rover rover = service.initRover(new String[]{"1", "2", "N"}, new Plateau(5, 5));
        assertAll(() -> assertEquals(1, rover.getX()), () -> assertEquals(2, rover.getY()),
                () -> assertEquals(Direction.N, rover.getDirection()));
    }

    @Test void initInvalidRoverOutsidePlateauTest() {
        assertThrows(InvalidInputException.class,
                () -> service.initRover(new String[]{"6", "2", "N"}, new Plateau(5, 5)));
    }

    @Test void initInvalidRoverDirection() {
        assertThrows(InvalidInputException.class,
                () -> service.initRover(new String[]{"1", "2", "X"}, new Plateau(5, 5)));
    }
}
