package com.excilys.formation.java.cdb.models;

import java.time.LocalDate;

/** Represents a computer.
 * @author Laetitia Tureau
*/
public class Computer {

	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	
	/** Represents the computer's company.
	 */
	private Company manufacturer;
	
	/** Creates a computer using a ComputerBuilder.
	 * @param builder
	 */
	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.manufacturer = builder.manufacturer;
	}
	
	/** Gets the computer's id.
	 * @return this.id
	 */
	public long getId() {
		return this.id;
	}
	
	/** Gets the computer's name.
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}
	
	/** Gets the computer's introduced date.
	 * @return this.introduced
	 */
	public LocalDate getIntroduced() {
		return this.introduced;
	}
	
	/** Gets the computer's discontinued date.
	 * @return this.discontinued
	 */
	public LocalDate getDiscontinued() {
		return this.discontinued;
	}
	
	/**
	 * Gets the computer's company..
	 * @return this.manufacturer
	 */
	public Company getManufacturer() {
		return this.manufacturer;
	}
	
	@Override
	public String toString() {
		String str = this.id + "\t|\t" + this.name + "\t|\t";
		str += introduced != null ? this.introduced + "\t|\t" : "NULL\t|\t";
		str += discontinued != null ? this.discontinued + "\t|\t" : "NULL\t|\t";
		str += manufacturer != null ? this.manufacturer + "\t|\t" : "NULL\t|\t";
		return str;
	}
	
	/** 
	 * Implementation of Builder pattern that allows to create a computer
	 * @author Laetitia Tureau
	 */
	public static class ComputerBuilder {
		
		private final Long id;
		private final String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		
		/** Represents the computer's company.
		 */
		private Company manufacturer;
		
		/** Creates a ComputerBuilder with the specified id.
		 * @param _name The computer's name.
		 */
		public ComputerBuilder(String _name) {
			this.id = null;
			this.name = _name;
		}
		
		/** Creates a ComputerBuilder with specific id and name.
		 * @param _id The computer's id.
		 * @param _name The computer's name.
		 */
		public ComputerBuilder(Long _id, String _name) {
			this.id = _id;
			this.name = _name;
		}
		
		/**
		 * Initialize attribute introduced of the ComputerBuilder
		 * @param _introduced A LocalDate
		 * @return this
		 */
		public ComputerBuilder introduced(LocalDate _introduced) {
			this.introduced = _introduced;
			return this;
		}
		
		/**
		 * Initialize attribute discontinued of the ComputerBuilder
		 * @param _discontinued A LocalDate
		 * @return this
		 */
		public ComputerBuilder discontinued(LocalDate _discontinued) {
			this.discontinued = _discontinued;
			return this;	
		}
		
		/**
		 * Initialize attribute manufacturer of the ComputerBuilder
		 * @param _manufacturer A Company
		 * @return this
		 */
		public ComputerBuilder manufacturer(Company _manufacturer) {
			this.manufacturer = _manufacturer;
			return this;
		}
		
		/**
		 * Create a Computer from the ComputerBuilder
		 * @return computer resulting
		 */
		public Computer build() {
			Computer computer = new Computer(this);
			return computer;
		}	
	}
}
