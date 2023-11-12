package com.cenfotec.adaka.app.exception;

public class InvalidMetricException extends RuntimeException {

    /**
     * Construct a InvalidMetricException with the required information, such as the message and the cause
     * @param message The detailed message of the exception.
     * @param cause The cause of the exception.
     */
    public InvalidMetricException(String message, Exception cause) {
        super(message, cause);
    }

    /**
     * Construct InvalidMetricException given a message
     * @param message The detailed message of the exception.
     */
    public InvalidMetricException(String message) {
        super(message);
    }
}
