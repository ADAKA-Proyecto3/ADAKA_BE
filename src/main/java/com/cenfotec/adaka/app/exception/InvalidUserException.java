package com.cenfotec.adaka.app.exception;

/**
 * This class represents the customized Exception that would be thrown in the case an invalid user request was made during the User's CRUD process
 */
public class InvalidUserException extends RuntimeException{
    /**
     * Construct a InvalidUserException with the required information, such as the message and the cause
     * @param message
     * @param cause
     */
    public InvalidUserException(String message,Exception cause){ super(message,cause);}

    /**
     * contruct InvalidUserException given a message
     * @param message
     */
    public InvalidUserException(String message){super(message);}

}
