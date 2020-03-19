package com.excilys.formation.java.cdb.dtos;

/** Represents a company.
 * @author Laetitia Tureau
 */
public class CompanyDTO {

	private Long id;
	private String name;

	/** Creates a company with the specified id.
	 * @param companyID The employee’s id.
	 */
	public CompanyDTO(Long companyID) {
		this.id = companyID;
	}

	/** Creates an employee with the specified name.
	 * @param companyID The employee’s id.
	 * @param companyName The employee’s name.
	 */
	public CompanyDTO(Long companyID, String companyName) {
		this.id = companyID;
		this.name = companyName;
	}
	
	/**
	 * Gets the company's id.
	 * @return this.id
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Sets the company's id.
	 * @param companyID A long containing the company's id.
	 */
	public void setId(Long companyID) {
		this.id = companyID;
	}
	
	/**
	 * Gets the company's name.
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the company's name.
	 * @param companyName A String containing the company's name.
	 */
	public void setName(String companyName) {
		this.name = companyName;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.id).append("\t|\t").append(this.name);
		return str.toString();
	}
}
