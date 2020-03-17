package com.excilys.formation.java.cdb.ui;

public enum MenuUpdate {
	COMPUTER_NAME (1, "Computer's name"),
	INTRODUCED_DATE (2, "Computer's introduced date"),
	DISCONTINUED_DATE (3, "Computer's discontinued date"),
	MANUFACTURER (4, "Computer's company"),
	QUIT(8, "Exit"),
	ERROR(9, "Error");
	
	private final int choice;
	private final String dialog;
	
	MenuUpdate(int _choice, String _dialog) {
		this.choice = _choice;
		this.dialog = _dialog;
	}
	
	public int getChoice() {
		return this.choice;
	}
	
	public String getDialog() {
		return this.dialog;
	}
	
	public String toString() {
		return new StringBuilder().append(this.choice).append(". ").append(this.dialog).toString();
	}
	
	public static MenuUpdate fromEntry(int entry) {
		switch(entry) {
			case 1:
				return COMPUTER_NAME;
			case 2:
				return INTRODUCED_DATE;
			case 3:
				return DISCONTINUED_DATE;
			case 4:
				return MANUFACTURER;
			case 8:
				return QUIT;
			default:
				return ERROR;
		}
	}
}
