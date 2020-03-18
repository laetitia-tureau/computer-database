package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

/** Represents pagination in lists.
 * @author Laetitia Tureau
 */
public class Page {

	/** Represents the maximum number of elements in a page */
	private final static int LIST_LENGTH = 10;
	
	/** Index of current page */
	private static int current_page;
	
	/** Index of last page */
	private static int max_page;
	
	/** Represents categories for companies fields */
	private final static String CATEGORY_COMPANY = "\nid\t|\tname\n";
	
	/** Represents categories for computers fields */
	private final static String CATEGORY_COMPUTER = "\nid\t|\tname\t|\t"
			+ "introduced\t|\tdiscontinued\t|\tid Company\t|\tCompany name\n";
	
	/** 
	 * Paginate the given list.
	 * @param scan A Scanner for page's number
	 * @param list The list to paginate
	 */
	public static void list(Scanner scan, List<?> list) {
		current_page = 1;
		max_page = (int) Math.ceil(list.size()/LIST_LENGTH);
		while(true) {
			if(list.get(0) instanceof Computer) {
				System.out.println(CATEGORY_COMPUTER);
			} else if(list.get(0) instanceof Company) {
				System.out.println(CATEGORY_COMPANY);
			}
			int index = current_page != max_page ? current_page * LIST_LENGTH : list.size();
			for(int i = (current_page - 1) * LIST_LENGTH; i < index; i++) {
				System.out.println(list.get(i).toString());
			}
			System.out.println(current_page + "/" + max_page);
			System.out.println("Enter page number (0 to quit) :");
			int choice = scan.nextInt();
			if(choice == 0 || choice > max_page) {
				break;
			}
			current_page = choice;
		}
	}
}
