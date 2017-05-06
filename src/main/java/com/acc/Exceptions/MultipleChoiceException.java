package com.acc.Exceptions;

/**
 * Created by melsom.adrian on 06.05.2017.
 */
public class MultipleChoiceException extends Exception {

    private String message;

    public final int status = 300;

    public MultipleChoiceException(String message) {
        this.message = message;
    }

    public MultipleChoiceException() {}

    @Override
    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return status;
    }
}
