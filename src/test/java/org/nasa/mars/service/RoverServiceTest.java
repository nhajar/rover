package org.nasa.mars.service;

import org.junit.jupiter.api.Test;
import org.nasa.mars.exception.InvalidInputException;
import org.nasa.mars.model.Plateau;
import org.nasa.mars.model.Rover;
import org.nasa.mars.model.enums.Direction;
import static org.junit.jupiter.api.Assertions.*;

class RoverServiceTest {
    private final RoverService service = new RoverService();
    private final Plateau plateau = new Plateau(5, 5);


    @Test void processRover() throws Exception {
        Rover rover = new Rover(1, 2, Direction.N);
        service.process(rover, "LMLMLMLMM", plateau);
        assertEquals("1 3 N", rover.toString());
    }


    @Test void invalidInstructionTest() {
        Rover rover = new Rover(1, 1, Direction.N);
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> service.process(rover, "X", plateau));
        assertEquals("invalid instruction", exception.getMessage());
    }

    @Test void cannotMoveOutsidePlateauTest() {
        Rover rover = new Rover(0, 0, Direction.W);
        assertThrows(InvalidInputException.class, () -> service.process(rover, "M", plateau));
        assertEquals("0 0 W", rover.toString());
    }

    @Test void canReachPlateauBoundaryTest() throws Exception {
        Rover rover = new Rover(4, 5, Direction.E);
        service.process(rover, "M", plateau);
        assertEquals("5 5 E", rover.toString());
    }

    @Test void emptyInstructionsTest() throws Exception {
        Rover rover = new Rover(1, 2, Direction.N);
        service.process(rover, "", plateau);
        assertEquals("1 2 N", rover.toString());
    }
}
