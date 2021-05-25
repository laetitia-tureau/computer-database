package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.dtos.ComputerDTO.ComputerBuilderDTO;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Company.CompanyBuilder;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Computer.ComputerBuilder;

/**
 * Mapper class for computers.
 * @author Laetitia Tureau
 */
public class ComputerMapper {

    private static LocalDate introduced;
    private static LocalDate discontinued;
    private static Company manufacturer;

    /**
     * Convert a ResultSet into a Computer.
     * @throws SQLException for database access error, or closed ResultSet, or argument passed in ResultSet getters not valid
     * @param result A ResultSet to convert
     * @return the computer resulting
     */
    public static Computer mapFromResultSet(ResultSet result) throws SQLException {
        ComputerBuilder builder = new Computer.ComputerBuilder(result.getLong(1), result.getString(2));

        if (result.getDate(3) != null) {
            introduced = result.getDate(3).toLocalDate();
            builder.introduced(introduced);
        }
        if (result.getDate(4) != null) {
            discontinued = result.getDate(4).toLocalDate();
            builder.discontinued(discontinued);
        }
        if (result.getLong(5) != 0) {
            // manufacturer = new Company(result.getLong(5), result.getString(6));
            manufacturer = new CompanyBuilder(result.getLong(5), result.getString(6)).build();
            builder.manufacturer(manufacturer);
        }
        return new Computer(builder);
    }

    /**
     * Convert a ComputerDTO into a Computer.
     * @param computerDTO A ComputerDTO to convert
     * @return the computer resulting
     */
    public static Computer mapFromDTOtoModel(ComputerDTO computerDTO) {
        Company company = new Company.CompanyBuilder("").build();
        Long id = Long.parseLong(computerDTO.getId());
        ComputerBuilder builder = new Computer.ComputerBuilder(id, computerDTO.getName());

        if (computerDTO.getIntroduced() != null && computerDTO.getIntroduced().compareTo("") != 0) {
            builder.introduced(LocalDate.parse(computerDTO.getIntroduced()));
        }
        if (computerDTO.getDiscontinued() != null && computerDTO.getDiscontinued().compareTo("") != 0) {
            builder.discontinued(LocalDate.parse(computerDTO.getDiscontinued()));
        }
        if (computerDTO.getManufacturer() != null && computerDTO.getManufacturer().compareTo("") != 0) {
            company = new Company.CompanyBuilder(Long.parseLong(computerDTO.getManufacturer())).build();
            builder.manufacturer(company);
        }
        return new Computer(builder);
    }

    /**
     * Convert a Computer into a ComputerDTO.
     * @param computer A Computer to convert
     * @return the computerDTO resulting
     */
    public static ComputerDTO mapFromModelToDTO(Computer computer) {
        String id = String.valueOf(computer.getId());
        ComputerBuilderDTO builder = new ComputerDTO.ComputerBuilderDTO(id, computer.getName());

        if (computer.getDiscontinued() != null) {
            builder.discontinued(String.valueOf(computer.getDiscontinued()));
        }
        if (computer.getIntroduced() != null) {
            builder.introduced(String.valueOf(computer.getIntroduced()));
        }
        if (computer.getManufacturer() != null) {
            builder.manufacturer(String.valueOf(computer.getManufacturer().getId()));
        }
        return new ComputerDTO(builder);
    }

}
