package com.excilys.formation.java.cdb.exceptions;

/**
 * Exception for computer's date.
 * @author Laetitia Tureau
 */
@SuppressWarnings("serial")
public class ComputerDateException extends Exception {

    /** Constructor. */
    public ComputerDateException() { }

    /** Constructor with specific message.
     * @param message A String to provides information about exception
     */
    public ComputerDateException(String message) {
        super(message);
    }

    /** Constructor with specific cause.
     * @param cause A Throwable that describe the cause of the exception
     */
    public ComputerDateException(Throwable cause) {
        super(cause);
    }

    /** Constructor with message and cause.
     * @param message A String to provides information about exception
     * @param cause A Throwable that describe the cause of the exception
     */
    public ComputerDateException(String message, Throwable cause) {
        super(message, cause);
    }

}
