package com.excilys.formation.java.cdb.validator;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.excilys.formation.java.cdb.exceptions.ComputerDateException;
import com.excilys.formation.java.cdb.models.Computer;

/**
 * Validator for computers.
 * @author Laetitia Tureau
 */
public class ComputerValidator {

    private static final String FORBIDDEN_DATE = "discontinued date must be greater than introduced date";
    private static final String NO_INTRO_DATE = "computer must have an introduced date";

    /**
     * Creates a validator for computers.
     */
    private ComputerValidator() {
    }

    /** Computer Validator. */
    private static ComputerValidator instance = null;

    /**
     * Retrieve the validator.
     * @return computer validator object
     */
    public static ComputerValidator getInstance() {
        if (instance == null) {
            instance = new ComputerValidator();
        }
        return instance;
    }

    /**
     * Verify if a computer object does not break any assumption of system.
     * @throws ComputerDateException if user enter a discontinued date when no introduced date or if introduced date > discontinued date
     * @param computer to validate
     * @param date to insert
     * @param isIntroDate is true if user wants to update introduced date else false if discontinued date is updated
     */
    public void validateComputerDate(Computer computer, Timestamp date, boolean isIntroDate)
            throws ComputerDateException {
        LocalDate dateToInsert = date.toLocalDateTime().toLocalDate();
        if (!isIntroDate) {
            if (computer.getIntroduced() == null) {
                throw new ComputerDateException(NO_INTRO_DATE);
            } else if (computer.getIntroduced().compareTo(dateToInsert) > 0) {
                throw new ComputerDateException(FORBIDDEN_DATE);
            }
        } else if (computer.getDiscontinued() != null) {
            if (computer.getDiscontinued().compareTo(dateToInsert) < 0) {
                throw new ComputerDateException(FORBIDDEN_DATE);
            }
        }
    }
}
