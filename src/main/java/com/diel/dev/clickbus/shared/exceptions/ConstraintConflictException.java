package com.diel.dev.clickbus.shared.exceptions;

public class ConstraintConflictException extends RuntimeException {
    public ConstraintConflictException(String message) {
        super(message);
    }
}
