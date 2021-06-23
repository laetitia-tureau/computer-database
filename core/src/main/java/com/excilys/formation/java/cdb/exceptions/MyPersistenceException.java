package com.excilys.formation.java.cdb.exceptions;

/**
 * Exception for computer's date.
 * @author Laetitia Tureau
 */
@SuppressWarnings("serial")
public class MyPersistenceException extends RuntimeException {

    public static final String ER_NOT_FIND = "This element does not exist in database";

    /** Constructor. */
    public MyPersistenceException() {
    }

    /** Constructor with specific message.
     * @param message A String to provides information about exception
     */
    public MyPersistenceException(String message) {
        super(message);
    }

    /** Constructor with specific cause.
     * @param cause A Throwable that describe the cause of the exception
     */
    public MyPersistenceException(Throwable cause) {
        super(cause);
    }

    /** Constructor with message and cause.
     * @param message A String to provides information about exception
     * @param cause A Throwable that describe the cause of the exception
     */
    public MyPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
