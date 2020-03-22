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
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Page;

/**
 * Represents Command-line interface.
 * @author Laetitia Tureau
 */
public class UserInterface {

    private static ComputerService computerService = new ComputerService();
    private static CompanyService companyService = new CompanyService();

    /**
     * Display a paginated list.
     * @param scan A scanner to choose a page
     * @param list the list to be display
     */
    private void printList(Scanner scan, List<?> list) {
        if (list.isEmpty()) {
            System.out.println("No computers");
        } else {
            Page.list(scan, list);
        }
    }

    /**
     * Retrieve the id entered in the cli.
     * @param scanner A scanner to read the id
     * @param action A message to display the use of the id
     * @param update If the action is an update then scanner will read next line
     * @return the entered id
     */
    private Long retrieveID(Scanner scanner, String action, boolean update) {
        System.out.println("Enter id of " + action);
        if (update) {
            scanner.nextLine();
        }
        return scanner.nextLong();
    }

    /**
     * Retrieve the name entered in the cli.
     * @param scanner A scanner to read the name
     * @param action A message to display the use of the name
     * @return the entered name
     */
    private String retrieveName(Scanner scanner, String action) {
        System.out.println("Enter name " + action);
        scanner.nextLine();
        return scanner.nextLine();
    }

    /**
     * Retrieve the date entered in the cli.
     * @throws ParseException
     * @param scanner A scanner to read the date
     * @return the entered date as a Timestamp
     */
    private Timestamp retrieveTimestamp(Scanner scanner) throws ParseException {
        System.out.println("Enter date with format yyyy-MM-dd :");
        scanner.nextLine();
        return DateMapper.nextTimestamp(scanner);
    }

    /**
     * Retrieve the user choice entered in the cli.
     * @param scanner A scanner to read the date
     * @return the entered choice
     */
    private int decide(Scanner scanner) {
        System.out.println("\nEnter 1 to continue or 0 to exit");
        return scanner.nextInt();
    }

    /**
     * Represents Command-line interface.
     * @throws SQLException
     * @throws ParseException
     */
    //TODO implement ERROR
    public void start() throws SQLException, ParseException {
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
                    Long computerID = retrieveID(scanner, "computer to display", false);
                    Optional<Computer> computer = computerService.findById(computerID);
                    System.out.println(computer.isPresent() ? computer.get() : "No computer with id : " + computerID);
                    break;
                case CREATE_COMPUTER:
                    computerService.createComputer(retrieveName(scanner, "of computer to create"));
                    break;
                case DELETE_COMPUTER:
                    computerService.deleteComputer(retrieveID(scanner, "computer to delete", false));
                    break;
                case UPDATE_COMPUTER:
                    do {
                        Long updateID = retrieveID(scanner, "computer to update", false);
                        Optional<Computer> optionalComputer = computerService.findById(updateID);
                        if (!optionalComputer.isPresent()) {
                            System.out.println("No computer with id : " + updateID);
                        } else {
                            menuUpdate();
                            int selector = scanner.nextInt();
                            choice = MenuChoice.fromEntryUpdate(selector);

                            switch (choice) {
                                case COMPUTER_NAME:
                                    computerService.updateName(retrieveName(scanner, ""), updateID);
                                    break;
                                case INTRODUCED_DATE:
                                    computerService.updateIntroduced(retrieveTimestamp(scanner), updateID);
                                    break;
                                case DISCONTINUED_DATE:
                                    computerService.updateDiscontinued(retrieveTimestamp(scanner), updateID);
                                    break;
                                case MANUFACTURER:
                                    Long companyID = retrieveID(scanner, "company", true);
                                    Optional<Company> optionalCompany = companyService.findById(companyID);
                                    if (!optionalCompany.isPresent()) {
                                        System.out.println("No company with id : " + companyID);
                                    } else {
                                        computerService.updateManufacturer(companyID, updateID);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    } while (decide(scanner) != 0);
                    break;
                default:
                    break;
            }
        } while (decide(scanner) != 0);
        scanner.close();
    }

    /** Print menu. */
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

    /** Print menu for updating a computer's attribute. */
    private static void menuUpdate() {
        System.out.println(" -> What do you want to update ? ");
        System.out.println("Select operation:");
        System.out.println(MenuChoice.COMPUTER_NAME);
        System.out.println(MenuChoice.INTRODUCED_DATE);
        System.out.println(MenuChoice.DISCONTINUED_DATE);
        System.out.println(MenuChoice.MANUFACTURER);
        System.out.println(MenuChoice.QUIT);
    }

}
