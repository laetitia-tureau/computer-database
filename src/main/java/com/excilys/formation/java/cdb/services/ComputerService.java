package com.excilys.formation.java.cdb.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.exceptions.ComputerDateException;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

/** Represents a computer service.
 * @author Laetitia Tureau
 */
public class ComputerService {

	/**
	 * A DAO used to encapsulate the logic for retrieving,
	 * saving and updating table computer data into the database.
	 */
	private ComputerDAO computerDAO;
	
	/** class logger */
	private static final Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	/** 
	 * Creates a service to computer operations.
	 */
	public ComputerService() {
		this.computerDAO = new ComputerDAO();
	}
	
	/** 
	 * Retrieve all the computers in the database.
	 * @return a list of computers
	 */
	public List<Computer> listAll() {
		List<Computer> computers = new ArrayList<>();
		try {
			ResultSet res = computerDAO.getAllComputers();
			while (res.next()) {
				computers.add(ComputerMapper.convert(res));
			}
		} catch (SQLException sqle) {
			logger.error("Erreur lors de l'exécution de la requête", sqle);
		}
		return computers;
	}
	
	/** 
	 * Create a computer.
	 * @param name A String containing the computer's name
	 * @return the number of rows inserted
	 */
	public int createComputer(String name) {
		try {
			return computerDAO.createComputer(name);
		} catch(SQLException sqle) {
			logger.error("Erreur lors de l'exécution de la requête", sqle);
		}
		return 0;
	}
	
	/** 
	 * Retrieve a computer with a specific id.
	 * @param id the computer's id
	 * @return An empty Optional if nothing found else a Optional containing a computer
	 */
	public Optional<Computer> findById(Long id) {
		try {
			ResultSet result = computerDAO.findById(id);
			if (result.next()) {
				return Optional.of(ComputerMapper.convert(result));
			}
		} catch(SQLException sqle) {
			logger.error("Erreur lors de l'exécution de la requête", sqle);
		}
		return Optional.empty();
	}
	
	/** 
	 * Delete a computer.
	 * @param id A Long containing the computer's id
	 * @return the number of rows deleted
	 */
	public int deleteComputer(Long id) {
		try {
			return computerDAO.deleteComputer(id);
		} catch (SQLException sqle) {
			logger.error("Erreur lors de l'exécution de la requête", sqle);
		}
		return 0;
	}
	
	/** 
	 * Update a computer's name.
	 * @param name A String containing the computer's name
	 * @param id A Long containing the computer's id
	 * @return the number of rows updated
	 */
	public int updateName(String name, Long id) {
		try {
			return computerDAO.updateName(name, id);
		} catch (SQLException sqle) {
			logger.error("Erreur lors de l'exécution de la requête", sqle);
		}
		return 0;
	}
	
	/** 
	 * Update a computer's introduced date.
	 * @param date A Timestamp as the computer's introduced date
	 * @param id A Long containing the computer's id
	 * @return An empty Optional if no computer found else a Optional containing the updated computer
	 */
	public Optional<Computer> updateIntroduced(Timestamp date, Long id) {
		Optional<Computer> computer = findById(id);
		if (computer.isPresent()) {
			try {
				validateComputerObject(computer.get(), false);
				computerDAO.updateIntroduced(date, id);
			} catch (ComputerDateException | SQLException exe) {
				logger.error("Erreur lors de l'exécution de la requête", exe);
			}
		}
		return computer;
	}
	
	/** 
	 * Update a computer's discontinued date.
	 * @param date A Timestamp as the computer's discontinued date
	 * @param id A Long containing the computer's id
	 * @return An empty Optional if no computer found else a Optional containing the updated computer
	 */
	public Optional<Computer> updateDiscontinued(Timestamp date, Long id) {
		Optional<Computer> computer = findById(id);
		if (computer.isPresent()) {
			try {
				boolean firstInit = computer.get().getDiscontinued() ==  null ? true : false;
				validateComputerObject(computer.get(), firstInit);
				computerDAO.updateDiscontinued(date, id);
			} catch (ComputerDateException | SQLException exe) {
				logger.error("Erreur lors de l'exécution de la requête", exe);
			}
		}
		return computer;
	}
	
	/** 
	 * Update a computer's manufacturer.
	 * @param company_id A Long containing the computer's manufacturer id
	 * @param id A Long containing the computer's id
	 * @return the number of rows updated
	 */
	public int updateManufacturer(Long company_id, Long id) {
		try {
			return computerDAO.updateManufacturer(company_id, id);
		} catch(SQLException sqle) {
			logger.error("Erreur lors de l'exécution de la requête", sqle);
		}
		return 0;
	}
	
	/**
	 * Verify if a computer object does not break any assumption of system
	 * @param computer to validate
	 * @throws ComputerDateException 
	 */
	private void validateComputerObject(Computer computer, boolean discontinued1stInit) throws ComputerDateException {
		if (computer.getIntroduced() == null && computer.getDiscontinued() != null || discontinued1stInit) {
			throw new ComputerDateException("computer must have an introduced date");
		}
		
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getIntroduced().compareTo(computer.getDiscontinued()) < 0) {
				throw new ComputerDateException("discontinued date must be greater than introduced date");
			}
		}
	}
}
