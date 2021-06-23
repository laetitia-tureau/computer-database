package com.excilys.formation.java.cdb.console;

import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

/** Represents pagination in lists.
 * @author Laetitia Tureau
 */
public class Page {

    /** Represents the maximum number of elements in a page. */
    private static final int LIST_LENGTH = 10;

    /** Index of current page. */
    private static int currentPage;

    /** Index of last page. */
    private static int maxPage;

    /** Represents categories for companies fields. */
    private static final String CATEGORY_COMPANY = "\nid\t|\tname\n";

    /** Represents categories for computers fields. */
    private static final String CATEGORY_COMPUTER = "\nid\t|\tname\t|\t"
            + "introduced\t|\tdiscontinued\t|\tid Company\t|\tCompany name\n";

    /**
     * Paginate the given list.
     * @param scan A Scanner for page's number
     * @param list The list to paginate
     */
    public static void list(Scanner scan, List<?> list) {
        currentPage = 1;
        maxPage = (int) Math.ceil(list.size() / LIST_LENGTH);
        while (true) {
            if (list.get(0) instanceof Computer) {
                System.out.println(CATEGORY_COMPUTER);
            } else if (list.get(0) instanceof Company) {
                System.out.println(CATEGORY_COMPANY);
            }
            int index = currentPage != maxPage ? currentPage * LIST_LENGTH : list.size();
            for (int i = (currentPage - 1) * LIST_LENGTH; i < index; i++) {
                System.out.println(list.get(i).toString());
            }
            System.out.println(currentPage + "/" + maxPage);
            System.out.println("Enter page number (0 to quit) :");
            int choice = scan.nextInt();
            if (choice == 0 || choice > maxPage) {
                break;
            }
            currentPage = choice;
        }
    }
}
