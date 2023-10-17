package com.cenfotec.adaka.app.exception;

/**
 * This class represents the customized Exception that would be thrown in the case where a request was made during to obtain an exiting user bt the requested Id
 * does not find any matching user
 */
public class UserNotFoundException extends  RuntimeException {

    /**
     * Construct a UserNotFoundException with the required information, such as the message and the cause
     * @param message
     * @param cause
     */
    public UserNotFoundException(String message,Exception cause){ super(message,cause);}

    /**
     * contruct UserNotFoundException given a message
     * @param message
     */
    public UserNotFoundException(String message){super(message);}
}
