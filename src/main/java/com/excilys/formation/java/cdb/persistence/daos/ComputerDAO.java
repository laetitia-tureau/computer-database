package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Computer.ComputerBuilder;
import com.excilys.formation.java.cdb.persistence.DBConnexion;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Pagination;

/** Represents a computer DAO.
 * @author Laetitia Tureau
 */
public class ComputerDAO {

    /** The connexion to mySQL database. */
    private DBConnexion dbConnexion;

    /** Class logger. */
    private static final Logger LOGGER = Logger.getLogger(ComputerService.class);

    /** Represents query to create a computer. */
    private static final String INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";

    /** Represents query to retrieve all computers. */
    private static final String ALL_COMPUTERS = "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
            + "company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id";

    /** Represents query to retrieve a specific computer. */
    private static final String FIND_COMPUTER = ALL_COMPUTERS + " WHERE computer.id = ?";

    /** Represents query to delete a computer. */
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";

    /** Represents query to update a computer's name. */
    private static final String UPDATE_NAME = "UPDATE computer SET name = ? WHERE id = ?";

    /** Represents query to update a computer's introduced date. */
    private static final String UPDATE_INTRODUCED = "UPDATE computer SET introduced = ? WHERE id = ?";

    /** Represents query to update a computer's discontinued date. */
    private static final String UPDATE_DISCONTINUED = "UPDATE computer SET discontinued = ? WHERE id = ?";

    /** Represents query to update a computer's manufacturer. */
    private static final String UPDATE_MANUFACTURER = "UPDATE computer SET company_id = ? WHERE id = ?";

    /** Creates a DAO to computer operations into database. */
    private ComputerDAO() {
        this.dbConnexion = DBConnexion.getInstance();
    }

    public static ComputerDAO getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ComputerDAO INSTANCE = new ComputerDAO();
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getAllComputers() {
        List<Computer> computers = new ArrayList<>();
        try (Connection connexion = dbConnexion.getConnection();
                Statement stmt = connexion.createStatement();
                ResultSet resultSet = stmt.executeQuery(ALL_COMPUTERS)) {
            while (resultSet.next()) {
                computers.add(ComputerMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return computers;
    }

    /**
     * Retrieve all the computers in the database.
     * @param page current page
     * @return a list of computers
     */
    public List<Computer> getPaginatedComputers(Pagination page) {
        List<Computer> computers = new ArrayList<>();
        String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
        try (Connection connexion = dbConnexion.getConnection();
                Statement stmt = connexion.createStatement();
                ResultSet resultSet = stmt.executeQuery(ALL_COMPUTERS + withLimit)) {
            while (resultSet.next()) {
                computers.add(ComputerMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return computers;
    }

    /**
     * Insert a computer in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param computer object to create
     * @return the computer saved in database
     */
    public Computer createComputer(Computer computer) {
        ComputerBuilder builder = new Computer.ComputerBuilder().name(computer.getName());
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(INSERT_COMPUTER)) {
            int i = 1;
            stmt.setString(i++, computer.getName());
            if (computer.getIntroduced() != null) {
                stmt.setDate(i++, java.sql.Date.valueOf(computer.getIntroduced()));
                builder.introduced(computer.getIntroduced());
            } else {
                stmt.setNull(i++, 0);
            }
            if (computer.getDiscontinued() != null) {
                stmt.setDate(i++, java.sql.Date.valueOf(computer.getDiscontinued()));
                builder.introduced(computer.getDiscontinued());
            } else {
                stmt.setNull(i++, 0);
            }
            if (computer.getManufacturer() != null) {
                if (computer.getManufacturer().getId() != null) {
                    stmt.setLong(i++, computer.getManufacturer().getId());
                    builder.manufacturer(computer.getManufacturer());
                }
            } else {
                stmt.setNull(i++, 0);
            }
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return computer;
    }

    /**
     * Retrieve a computer.
     * @param id the computer's id
     * @return An empty Optional if nothing found else a Optional containing a computer
     */
    public Optional<Computer> findById(Long id) {
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(FIND_COMPUTER)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(ComputerMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return Optional.empty();
    }

    /**
     * Delete a computer in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param id the computer's id
     * @return the number of rows deleted
     */
    public int deleteComputer(Long id) {
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(DELETE_COMPUTER)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return 0;
    }

    /**
     * Update a computer's name in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setters
     * @param name the computer's name
     * @param id the computer's id
     * @return the number of rows updated
     */
    public int updateName(String name, Long id) {
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(UPDATE_NAME)) {
            stmt.setString(1, name);
            stmt.setLong(2, id);
            return stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return 0;

    }

    /**
     * Update a computer's introduced date in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setters
     * @param date the computer's introduced date
     * @param id the computer's id
     * @return the number of rows updated
     */
    public int updateIntroduced(Timestamp date, Long id) {
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(UPDATE_INTRODUCED)) {
            stmt.setTimestamp(1, date);
            stmt.setLong(2, id);
            return stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return 0;
    }

    /**
     * Update a computer's discontinued date in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setters
     * @param date the computer's discontinued date
     * @param id the computer's id
     * @return the number of rows updated
     */
    public int updateDiscontinued(Timestamp date, Long id) {
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(UPDATE_DISCONTINUED)) {
            stmt.setTimestamp(1, date);
            stmt.setLong(2, id);
            return stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return 0;
    }

    /**
     * Update a computer's company_id in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param id the computer's id
     * @param companyID the computer's manufacturer id
     * @return the number of rows updated
     */
    public int updateManufacturer(Long companyID, Long id) {
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(UPDATE_MANUFACTURER)) {
            stmt.setLong(1, companyID);
            stmt.setLong(2, id);
            return stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return 0;
    }

}
