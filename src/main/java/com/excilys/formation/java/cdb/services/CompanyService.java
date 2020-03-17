package com.excilys.formation.java.cdb.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

/** Represents a company service.
 * @author Laetitia Tureau
 */
public class CompanyService {

	/**
	 * A DAO used to encapsulate the logic for retrieving,
	 * saving and updating table company data into the database.
	 */
	private CompanyDAO companyDAO;
	
	/** 
	 * Creates a service to company operations.
	 */
	public CompanyService() {
		this.companyDAO = new CompanyDAO();
	}
	
	/** 
	 * Retrieve all the companies in the database.
	 * @return a list of companies
	 */
	public List<Company> listAll() {
		List<Company> companies = new ArrayList<>();
		try {
			ResultSet result = companyDAO.getAllCompanies();
			while (result.next()) {
				companies.add(new Company(result.getLong(1), result.getString(2)));
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return companies;
	}
	
	/** 
	 * Retrieve a company with a specific id
	 * @param A empty Optional if nothing found else a Optional containing a company
	 */
	public Optional<Company> findById(Long id) {
		try {
			ResultSet result = companyDAO.findById(id);
			if (result.next()) {
				return Optional.of(new Company(result.getLong(1)));
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
		return Optional.empty();
	}

}
