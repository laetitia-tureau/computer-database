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

	private MysqlConnect mysqlConnect = MysqlConnect.getDbConnection();
	private static final String INSERT_COMPUTER = "INSERT INTO computer (name) VALUES (?)";
	private static final String ALL_COMPUTERS = "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
			+ "company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id";
	private static final String FIND_COMPUTER = ALL_COMPUTERS + " WHERE computer.id = ?";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	private static final String UPDATE_NAME = "UPDATE computer SET name = ? WHERE id = ?";
	private static final String UPDATE_INTRODUCED = "UPDATE computer SET introduced = ? WHERE id = ?";
	private static final String UPDATE_DISCONTINUED = "UPDATE computer SET discontinued = ? WHERE id = ?";
	private static final String UPDATE_MANUFACTURER = "UPDATE computer SET company_id = ? WHERE id = ?";
	
	public ComputerDAO() {}
	
	public ResultSet getAllComputers() throws SQLException {
		Statement stmt = mysqlConnect.connexion.createStatement();
		return stmt.executeQuery(ALL_COMPUTERS);
	}
	
	public int createComputer(String computerName) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(INSERT_COMPUTER);
		stmt.setString(1, computerName);
		return stmt.executeUpdate();
	}
	
	public ResultSet findById(Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(FIND_COMPUTER);
		stmt.setLong(1, id);
		return stmt.executeQuery();
	}
	
	public int deleteComputer(Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(DELETE_COMPUTER);
		stmt.setLong(1, id);
		return stmt.executeUpdate();
	}
	
	public int updateName(String name, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_NAME);
		stmt.setString(1, name);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}
	
	public int updateIntroduced(Timestamp date, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_INTRODUCED);
		stmt.setTimestamp(1, date);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}
	
	public int updateDiscontinued(Timestamp date, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_DISCONTINUED);
		stmt.setTimestamp(1, date);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}
	
	public int updateManufacturer(Long company_id, Long id) throws SQLException {
		PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(UPDATE_MANUFACTURER);
		stmt.setLong(1, company_id);
		stmt.setLong(2, id);
		return stmt.executeUpdate();
	}

}
