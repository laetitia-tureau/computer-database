package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.excilys.formation.java.cdb.persistence.MysqlConnect;

/** Represents a computer DAO.
 * @author Laetitia Tureau
 */
public class ComputerDAO {

	/** The connexion to mySQL database */
	private MysqlConnect mysqlConnect = MysqlConnect.getDbConnection();
	
	/** Represents query to create a computer */
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name) VALUES (?)";
	
	/** Represents query to retrieve all computers */
	private static final String ALL_COMPUTERS = "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
			+ "company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id";
	
	/** Represents query to retrieve a specific computer */
	private static final String FIND_COMPUTER = ALL_COMPUTERS + " WHERE computer.id = ?";
	
	/** Represents query to delete a computer */
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	
	/** Represents query to update a computer's name */
	private static final String UPDATE_NAME = "UPDATE computer SET name = ? WHERE id = ?";
	
	/** Represents query to update a computer's introduced date */
	private static final String UPDATE_INTRODUCED = "UPDATE computer SET introduced = ? WHERE id = ?";
	
	/** Represents query to update a computer's discontinued date */
	private static final String UPDATE_DISCONTINUED = "UPDATE computer SET discontinued = ? WHERE id = ?";
	
	/** Represents query to update a computer's manufacturer */
	private static final String UPDATE_MANUFACTURER = "UPDATE computer SET company_id = ? WHERE id = ?";
	
	/** Creates a DAO to computer operations into database */
	public ComputerDAO() {}
	
	/** 
	 * Retrieve all the computers in the database
	 * @throws SQLException
	 * @return A ResultSet as a result of the executed query
	 */
	public ResultSet getAllComputers() throws SQLException {
		Statement stmt = mysqlConnect.connexion.createStatement();
		return stmt.executeQuery(ALL_COMPUTERS);
	}
	
	/** 
	 * Insert a computer in the database
	 * @throws SQLException
	 * @param the computer's name
	 * @return the number of rows inserted
	 */
	public int createComputer(String computerName) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(INSERT_COMPUTER);
		stmt.setString(1, computerName);
		return stmt.executeUpdate();
	}
	
	/** 
	 * Retrieve a computer
	 * @throws SQLException
	 * @param the computer's id
	 * @return A ResultSet as a result of the executed query
	 */
	public ResultSet findById(Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(FIND_COMPUTER);
		stmt.setLong(1, id);
		return stmt.executeQuery();
	}
	
	/** 
	 * Delete a computer in the database
	 * @throws SQLException
	 * @param the computer's id
	 * @return the number of rows deleted
	 */
	public int deleteComputer(Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(DELETE_COMPUTER);
		stmt.setLong(1, id);
		return stmt.executeUpdate();
	}
	
	/** 
	 * Update a computer's name in the database
	 * @throws SQLException
	 * @param the computer's name
	 * @param the computer's id
	 * @return the number of rows updated
	 */
	public int updateName(String name, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_NAME);
		stmt.setString(1, name);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}
	
	/** 
	 * Update a computer's introduced date in the database
	 * @throws SQLException
	 * @param the computer's introduced date
	 * @param the computer's id
	 * @return the number of rows updated
	 */
	public int updateIntroduced(Timestamp date, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_INTRODUCED);
		stmt.setTimestamp(1, date);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}
	
	/** 
	 * Update a computer's discontinued date in the database
	 * @throws SQLException
	 * @param the computer's discontinued date
	 * @param the computer's id
	 * @return the number of rows updated
	 */
	public int updateDiscontinued(Timestamp date, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_DISCONTINUED);
		stmt.setTimestamp(1, date);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}
	
	/** 
	 * Update a computer's company_id in the database
	 * @throws SQLException
	 * @param the computer's id
	 * @return the number of rows updated
	 */
	public int updateManufacturer(Long company_id, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_MANUFACTURER);
		stmt.setLong(1, company_id);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}

}
