package com.cenfotec.adaka.app.exception;

public class InvalidRoomException extends RuntimeException{

    /**
     * Construct a InvalidRoomException with the required information, such as the message and the cause
     * @param message
     * @param cause
     */
    public InvalidRoomException(String message,Exception cause){ super(message,cause);}

    /**
     * contruct InvalidRoomException given a message
     * @param message
     */
    public InvalidRoomException(String message){super(message);}

}
