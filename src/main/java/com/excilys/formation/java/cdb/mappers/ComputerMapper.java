package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.formation.java.cdb.models.Company;
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
	 * @param result A ResultSet to convert.
	 * @return the computer resulting
	 */
	public static Computer convert(ResultSet result) throws SQLException {
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
			manufacturer = new Company(result.getLong(5), result.getString(6));
			builder.manufacturer(manufacturer);
		}
		return new Computer(builder);
	}

}
