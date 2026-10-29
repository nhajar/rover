package org.nasa.mars.controller;

import org.nasa.mars.exception.InvalidInputException;
import org.nasa.mars.model.Plateau;
import org.nasa.mars.model.Rover;
import org.nasa.mars.service.InputService;
import org.nasa.mars.service.RoverService;

import java.io.IOException;
import java.util.List;

public class RoverController {

    private final InputService inputService;
    private final RoverService roverService;

    public RoverController() {
        this.inputService = new InputService();
        this.roverService = new RoverService();
    }

    public void run(String filePath) {
        try {
            List<String> inputLines = inputService.readInput(filePath);
            Plateau plateau = inputService.initPlateau(inputLines.getFirst().trim().split("\\s+"));
            for (int i = 1; i < inputLines.size(); i += 2) {
                Rover rover = inputService.initRover(inputLines.get(i).split("\\s"), plateau);
                String instructions = inputLines.get(i + 1);
                try {
                    roverService.process(rover, instructions, plateau);
                } catch (InvalidInputException e) {
                    System.out.println("Error while executing instruction " + instructions + " for rover " + rover.toString() + " " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error while reading the input file: " + e.getMessage());

        } catch (InvalidInputException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
