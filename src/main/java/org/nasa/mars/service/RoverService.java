package org.nasa.mars.service;

import org.nasa.mars.exception.InvalidInputException;
import org.nasa.mars.model.Plateau;
import org.nasa.mars.model.Rover;
import org.nasa.mars.model.enums.Direction;
import org.nasa.mars.model.enums.Instruction;

import java.util.HashMap;
import java.util.Map;

public class RoverService {

    private final static Map<Direction, Direction> turnLeft = new HashMap<>();
    private final static Map<Direction, Direction> turnRight = new HashMap<>();

    static{
        turnLeft.put(Direction.N, Direction.W);
        turnLeft.put(Direction.W, Direction.S);
        turnLeft.put(Direction.S, Direction.E);
        turnLeft.put(Direction.E, Direction.N);

        turnRight.put(Direction.N, Direction.E);
        turnRight.put(Direction.E, Direction.S);
        turnRight.put(Direction.S, Direction.W);
        turnRight.put(Direction.W, Direction.N);
    }
    public void process(Rover rover, String instructions, Plateau plateau) throws InvalidInputException {
        for (char instruction : instructions.toCharArray()) {
            if (instruction  == Instruction.Left.getValue()) {
                rover.setDirection(turnLeft.get(rover.getDirection()));
            } else if (instruction  == Instruction.Right.getValue()) {
                rover.setDirection(turnRight.get(rover.getDirection()));
            } else if (instruction  == Instruction.Move.getValue()) {
                move(rover, plateau);
            } else
                throw new InvalidInputException("invalid instruction");
        }
        System.out.println(rover.toString());
    }

    private void move(Rover rover, Plateau plateau) throws InvalidInputException{
        int x = rover.getX();
        int y = rover.getY();

        switch (rover.getDirection()) {
            case N -> y++;
            case E -> x++;
            case S -> y--;
            case W -> x--;
        }
        if (x < 0 || x > plateau.getMaxX() || y < 0 || y > plateau.getMaxY()) {
            throw new InvalidInputException("The rover cannot move off the plateau");
        }

        rover.setX(x);
        rover.setY(y);
    }
}
