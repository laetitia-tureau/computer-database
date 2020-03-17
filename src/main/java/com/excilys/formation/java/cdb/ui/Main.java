package com.excilys.formation.java.cdb.ui;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {

	public static void main(String[] args) throws SQLException, ParseException {
		UserInterface cli = new UserInterface();
		cli.start();
	}

}
