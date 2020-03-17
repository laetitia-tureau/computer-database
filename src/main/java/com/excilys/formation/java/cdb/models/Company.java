package com.excilys.formation.java.cdb.models;

/** Represents a company.
 * @author Laetitia Tureau
 */
public class Company {

	private Long id;
	private String name;
	
	/** Creates a company with the specified id.
	 * @param _id The employee’s id.
	 */
	public Company(Long _id) {
		this.id = _id;
	}
	
	/** Creates an employee with the specified name.
	 * @param _id The employee’s id.
	 * @param _name The employee’s name.
	 */
	public Company(Long _id, String _name) {
		this.id = _id;
		this.name = _name;
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
	 * @param _id A long containing the company's id.
	 */
	public void setId(Long _id) {
		this.id = _id;
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
	 * @param _name A String containing the company's name.
	 */
	public void setName(String _name) {
		this.name = _name;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.id).append("\t|\t").append(this.name);
		return str.toString();
	}
}
