package com.cenfotec.adaka.app.exception;

public class InvalidMedicalCenterException extends RuntimeException{

    /**
     * Construct a InvalidUserException with the required information, such as the message and the cause
     * @param message
     * @param cause
     */
    public InvalidMedicalCenterException(String message,Exception cause){ super(message,cause);}

    /**
     * contruct InvalidUserException given a message
     * @param message
     */
    public InvalidMedicalCenterException(String message){super(message);}



}
