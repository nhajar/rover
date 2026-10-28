package org.nasa.mars.service;

import org.nasa.mars.exception.InvalidInputException;
import org.nasa.mars.model.Plateau;
import org.nasa.mars.model.Rover;
import org.nasa.mars.model.enums.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputService {

    public List<String> readInput(String filePath) throws IOException, InvalidInputException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        if (lines.size()<3) {
            throw new InvalidInputException("the file content is invalid");
        }
        return lines;
    }

    public Plateau initPlateau(String[] plateauCoordinates) throws InvalidInputException{
        try {
            int maxX = Integer.parseInt(plateauCoordinates[0]);
            int maxY = Integer.parseInt(plateauCoordinates[1]);
            if (maxX == 0 || maxY == 0)
                throw new InvalidInputException("the plateau coordinates should be greater than 0");
            return new Plateau(maxX, maxY);
        } catch (InvalidInputException e) {
            throw new InvalidInputException("the plateau coordinates are invalid : " + e.getMessage());
        }
    }

    public Rover initRover(String[] roverPosition, Plateau plateau) throws InvalidInputException{
        try {
            int x = Integer.parseInt(roverPosition[0]);
            int y = Integer.parseInt(roverPosition[1]);
            String direction = roverPosition[2];

            if (x < 0 ||  x >plateau.getMaxX() || y >= plateau.getMaxY() || y < 0)
                throw new InvalidInputException("First rover position is invalid x=" + x + " and y=" + y + " is invalid");

            return  new Rover(x, y, Direction.valueOf(direction));

        } catch (InvalidInputException | IllegalArgumentException e ) {
            throw new InvalidInputException("the rover data are invalid");
        }
    }
 }
