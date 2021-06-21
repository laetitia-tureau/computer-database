package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

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
@Component
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
    public Computer mapFromResultSet(ResultSet result) throws SQLException {
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
    public Computer mapFromDTOtoModel(ComputerDTO computerDTO) {
        Company company = new Company.CompanyBuilder().build();
        ComputerBuilder builder = new Computer.ComputerBuilder();

        if (computerDTO.getId() != null && !computerDTO.getId().isEmpty()) {
            builder.id(Long.parseLong(computerDTO.getId()));
        }
        if (computerDTO.getName() != null && !computerDTO.getName().isEmpty()) {
            builder.name(computerDTO.getName());
        }
        if (computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().isEmpty()) {
            builder.introduced(LocalDate.parse(computerDTO.getIntroduced()));
        }
        if (computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().isEmpty()) {
            builder.discontinued(LocalDate.parse(computerDTO.getDiscontinued()));
        }
        if (computerDTO.getManufacturer() != null && !computerDTO.getManufacturer().isEmpty()) {
            company = new Company.CompanyBuilder().id(Long.parseLong(computerDTO.getManufacturer())).build();
            builder.manufacturer(company);
        }
        return new Computer(builder);
    }

    /**
     * Convert a Computer into a ComputerDTO.
     * @param computer A Computer to convert
     * @return the computerDTO resulting
     */
    public ComputerDTO mapFromModelToDTO(Computer computer) {
        String id = String.valueOf(computer.getId());
        String dis = null;
        String intro = null; 
        String companyId = null;

        if (computer.getDiscontinued() != null) {
            dis = String.valueOf(computer.getDiscontinued());
        }
        if (computer.getIntroduced() != null) {
            intro = String.valueOf(computer.getIntroduced());
        }
        if (computer.getManufacturer() != null) {
            companyId = String.valueOf(computer.getManufacturer().getId());
        }
        return new ComputerDTO.ComputerBuilderDTO(id, computer.getName()).discontinued(dis).introduced(intro).manufacturer(companyId).build();
    }

}
