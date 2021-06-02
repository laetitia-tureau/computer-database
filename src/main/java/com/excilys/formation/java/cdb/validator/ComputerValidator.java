package com.excilys.formation.java.cdb.validator;

import java.time.LocalDate;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Computer.ComputerBuilder;

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
     * Verify if a computerDTO object does not break any assumption of system.
     * @param computerDTO the computerDTO to validate
     * @throws MyPersistenceException if not a valid computerDTO
     */
    public static void validateComputerDTO(ComputerDTO computerDTO) throws MyPersistenceException {
        validateName(computerDTO.getName());
        validateDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
    }

    /**
     * Verify if the computer's name is not null or empty.
     * @param name the computer's name
     */
    private static void validateName(String name) {
        if (name == null || "".equals(name) || name.isEmpty()) {
            throw new MyPersistenceException("Name must not be empty !");
        }
    }

    /**
     * Verify if the computerDTO's dates do not break any assumption of system.
     * @param introduced the computerDTO's introduced date
     * @param discontinued the computerDTO's discontinued date
     */
    private static void validateDate(String introduced, String discontinued) {
        LocalDate introducedDate = null;
        LocalDate discontinuedDate = null;
        ComputerBuilder builder = new Computer.ComputerBuilder();
        if (introduced != null && !introduced.isEmpty()) {
            introducedDate = LocalDate.parse(introduced);
            builder.introduced(introducedDate);
        }
        if (discontinued != null && !discontinued.isEmpty()) {
            discontinuedDate = LocalDate.parse(discontinued);
            builder.discontinued(discontinuedDate);
        }
        validateComputerDate(builder.build());
    }

    /**
     * Verify if a computer object does not break any assumption of system.
     * @throws MyPersistenceException if user enter a discontinued date when no introduced date or if introduced date > discontinued date
     * @param computer to validate
     */
    public static void validateComputerDate(Computer computer) throws MyPersistenceException {
        if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
            if (computer.getDiscontinued().compareTo(computer.getIntroduced()) < 0) {
                throw new MyPersistenceException(FORBIDDEN_DATE);
            }
        } else if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
            throw new MyPersistenceException(NO_INTRO_DATE);
        }
    }

}
