package org.nasa.mars.model.enums;

public enum Instruction {
    Left('L'),
    Right('R'),
    Move('M');

    private char value;

    private Instruction(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

}
