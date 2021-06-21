package com.excilys.formation.java.cdb.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.excilys.formation.java.cdb.mappers.DateMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Computer.ComputerBuilder;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.CompanyRepository;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerRepository;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;

/**
 * Represents Command-line interface.
 * @author Laetitia Tureau
 */
public class UserInterface {

    private static ComputerService computerService;
    private static CompanyService companyService;
    private static Computer computer;
    private static Company company;
    private static final Logger LOGGER = Logger.getLogger(UserInterface.class);

    /**
     * Creates cdb user interface.
     * @param computerRepo computer's jpa repo
     * @param companyRepo company's dao
     * @param computerDAO computer's jdbc dao
     * @param companyDAO company's jdbc dao
     */
    public UserInterface(ComputerRepository computerRepo, CompanyRepository companyRepo, ComputerDAO computerDAO, CompanyDAO companyDAO) {
        computerService = new ComputerService(computerRepo, computerDAO);
        companyService = new CompanyService(companyRepo, companyDAO);
    }

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
     * @return the entered id
     */
    private Long retrieveID(Scanner scanner, String action) {
        System.out.println("Enter id of " + action);
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
     * @throws ParseException when date format entered not valid
     * @param scanner A scanner to read the date
     * @return the entered date as a Timestamp
     */
    private Timestamp retrieveTimestamp(Scanner scanner) throws ParseException {
        System.out.println("Enter date with format yyyy-MM-dd :");
        String date = scanner.nextLine();
        return DateMapper.nextTimestamp(date);
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
     * Print general menu and select an option.
     * @param scanner A scanner to read the choice
     * @return the user choice as a MenuChoice
     */
    private MenuChoice choiceFromMenu(Scanner scanner) {
        menu();
        int selector = scanner.nextInt();
        return MenuChoice.fromEntry(selector);
    }

    /**
     * Find Company or Computer given an id and assign the object retrieved to the corresponding static optional object.
     * @param scanner A scanner to read the id
     * @param action A message to display the use of the id
     * @param isCompany if the object is a company or not
     * @return the id entered
     */
    private Long find(Scanner scanner, String action, boolean isCompany) {
        Long id = retrieveID(scanner, action);
        if (isCompany) {
            company = companyService.findById(id);
        } else {
            computer = computerService.findById(id).get();
        }
        return id;
    }

    /**
     * Update a Computer.
     * @param scanner A scanner to read the id and the user's choice
     * @param create if a create action is requested, update action otherwise
     * @throws ParseException when parsing a date
     */
    private void edit(Scanner scanner, boolean create) throws ParseException {
        Long id;
        ComputerBuilder builder = new Computer.ComputerBuilder();
        if (!create) {
            id = find(scanner, "computer to update", false);
            while (computer == null) {
                // TODO cas d'arret entrer un chiffre
                LOGGER.warn("No computer with id: " + id);
                id = find(scanner, "computer to update", false);
            }
            builder.id(id);
        }

        String name = retrieveName(scanner, "");
        if (StringUtils.isBlank(name)) {
            name = computer.getName();
        }

        Timestamp timeIntro = retrieveTimestamp(scanner);
        Timestamp timeDist = retrieveTimestamp(scanner);
        LocalDate dateIntro = timeIntro == null ? null : timeIntro.toLocalDateTime().toLocalDate();
        LocalDate dateDist = timeDist == null ? null : timeDist.toLocalDateTime().toLocalDate();

        find(scanner, "related company", true);

        builder.name(name).introduced(dateIntro).discontinued(dateDist).manufacturer(company);
        computerService.save(builder.build());

    }

    /**
     * Represents Command-line interface.
     * @throws SQLException for crud operations
     * @throws ParseException when parsing a date
     */
    public void start() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);
        do {
            switch (choiceFromMenu(scanner)) {
            case COMPUTER_LIST:
                printList(scanner, computerService.getComputers());
                break;
            case COMPANY_LIST:
                printList(scanner, companyService.getCompanies());
                break;
            case COMPUTER_DETAIL:
                find(scanner, "computer to show", false);
                System.out.println(computer);
                break;
            case CREATE_COMPUTER:
                edit(scanner, true);
                break;
            case DELETE_COMPUTER:
                computerService.deleteComputer(retrieveID(scanner, "computer to delete"));
                break;
            case DELETE_COMPANY:
                companyService.deleteCompany(retrieveID(scanner, "company to delete"));
                break;
            case UPDATE_COMPUTER:
                do {
                    edit(scanner, false);
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
        System.out.println(MenuChoice.DELETE_COMPANY);
        System.out.println(MenuChoice.QUIT);
    }
}
