package org.nasa.mars;

import org.nasa.mars.controller.RoverController;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please make sure to add the input file." +
                    " Expected command : java -jar rover.jar input.txt");
            return;
        }
        RoverController controller = new RoverController();
        controller.run(args[0]);


    }
}