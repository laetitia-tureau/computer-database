package com.excilys.formation.java.cdb.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.text.ParseException;

import com.excilys.formation.java.cdb.mappers.DateMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;

public class UserInterface {
	
	private static void menuUpdate() {
		System.out.println(" -> What do you want to update ? ");
		System.out.println("Select operation:");
		System.out.println(MenuUpdate.COMPUTER_NAME);
		System.out.println(MenuUpdate.INTRODUCED_DATE);
		System.out.println(MenuUpdate.DISCONTINUED_DATE);
		System.out.println(MenuUpdate.MANUFACTURER);
		System.out.println(MenuUpdate.QUIT);
	}
	
	private static void menu() {
		System.out.println(" *** Computer database application *** ");
		System.out.println("Select operation:");
		System.out.println(MenuChoice.COMPUTER_LIST);
		System.out.println(MenuChoice.COMPANY_LIST);
		System.out.println(MenuChoice.COMPUTER_DETAIL);
		System.out.println(MenuChoice.CREATE_COMPUTER);
		System.out.println(MenuChoice.UPDATE_COMPUTER);
		System.out.println(MenuChoice.DELETE_COMPUTER);
		System.out.println(MenuChoice.QUIT);
	}
	
	private void printList(Scanner scan, List<?> list) {
		if (list.isEmpty()) {
			System.out.println("No computers");
		} else {
			Page.list(scan, list);
		}
	}
	
	private Long retrieveID(Scanner scanner, String action, boolean update) {
		System.out.println("Enter id of " + action);
		if (update) {
			scanner.nextLine();
		}
		return scanner.nextLong();
	}
	
	private String retrieveName(Scanner scanner, String action) {
		System.out.println("Enter name " + action);
		scanner.nextLine();
		return scanner.nextLine();
	}
	
	private Timestamp retriveTimestamp(Scanner scanner) throws ParseException {
		System.out.println("Enter date with format yyyy-MM-dd :");
		scanner.nextLine();
		return DateMapper.nextTimestamp(scanner);
	}
	
	private int decide(Scanner scanner) {
		System.out.println("\nEnter 0 to continue or 8 to exit");
		return scanner.nextInt();
	}
	
	
	//TODO implement ERROR
	public void start() throws SQLException, ParseException {
		ComputerService computerService = new ComputerService();
		CompanyService companyService = new CompanyService();
		Scanner scanner = new Scanner(System.in);
		do {
			menu();
			int featureSelector = scanner.nextInt();
			MenuChoice choice = MenuChoice.fromEntry(featureSelector);
			switch (choice) {
				case COMPUTER_LIST:
					printList(scanner, computerService.listAll());
				    break;
				case COMPANY_LIST:
					printList(scanner, companyService.listAll());
					break;
				case COMPUTER_DETAIL:
					Optional<Computer> computer_1 = computerService.findById(retrieveID(scanner, "computer to display", false));
					System.out.println(computer_1.isPresent() ? computer_1.get() : "No computer found");
					break;
				case CREATE_COMPUTER:
					computerService.createComputer(retrieveName(scanner, "of computer to create"));
					break;
				case DELETE_COMPUTER:
					computerService.deleteComputer(retrieveID(scanner, "computer to delete", false));
					break;
				case UPDATE_COMPUTER:
					do {
						Long id_update = retrieveID(scanner, "computer to update", false);
						Optional<Computer> optional_computer = computerService.findById(id_update);
						if (!optional_computer.isPresent()) {
							System.out.println("No computer with id : "+id_update);
						} else {
							menuUpdate();
							int selector = scanner.nextInt();
							MenuUpdate choice_2 = MenuUpdate.fromEntry(selector);
							
							switch(choice_2) {
								case COMPUTER_NAME:
									computerService.updateName(retrieveName(scanner, ""), id_update);
									break;
								case INTRODUCED_DATE:
									computerService.updateIntroduced(retriveTimestamp(scanner), id_update);
									break;
								case DISCONTINUED_DATE:
									computerService.updateDiscontinued(retriveTimestamp(scanner), id_update);
									break;
								case MANUFACTURER:
									Long company_id = retrieveID(scanner, "company", true);
									Optional<Company> optional_company = companyService.findById(company_id);
									if (!optional_company.isPresent()) {
										System.out.println("No company with id : "+ company_id);
									} else {
										computerService.updateManufacturer(company_id, id_update);
									}
									break;
								default:
									break;
							}
						}
					} while(decide(scanner) != 8);
					break;
				default:
					break;
			}
		} while(decide(scanner) != 8);
		scanner.close();
	}

}
