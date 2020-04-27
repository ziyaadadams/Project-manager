


package poised;



import java.util.*;
import java.io.IOException;
import java.sql.*;

/**
 * 
 * @author ziyaad
 * 
 * 
 * Main.java file which is used to run the program
 * This file calls methods from Project.java, Architect.java, Contractors.java and customer.java
 * 
 * Please ensure:
 * 
 * projects table columns:
 * number   - INT - Not Null - Auto Increment - primary key
 * name     - VARCHAR(100)   
 * type     - VARCHAR(100)
 * address  - VARCHAR(100)
 * erf      - INT
 * fee      - INT
 * paid     - INT
 * deadline - DATE
 * cid      - INT - Foreign Key from customers table
 * aid		- INT - Foreign Key from Architects table 
 * conid    - INT - Foreign Key from Contractors table
 * Completed- VARCHAR(100) 
 * comdate  - DATE
 * 
 * architects table columns:
 * name      - VARCHAR(100) - not null
 * telephone - VARCHAR(100) - not null
 * email     - VARCHAR(100) 
 * address   - VARCHAR(100)
 * id        - INT          - Not Null - Auto Increment - primary key
 * 
 * 
 * customers table columns:
 * name      - VARCHAR(100) - not null - primary key
 * telephone - VARCHAR(100) - not null
 * email     - VARCHAR(100) 
 * address   - VARCHAR(100)
 * id        - INT          - Not Null - Auto Increment - primary key
 * 
 * contractors table columns:
 * name      - VARCHAR(100) - not null - primary key
 * telephone - VARCHAR(100) - not null
 * email     - VARCHAR(100) 
 * address   - VARCHAR(100)
 * id        - INT          - Not Null - Auto Increment - primary key
 */

public class Main {

	public static void main(String[] args) throws IOException {

		/**
		 * Main method 
		 */
		// MySQL connector with try-catch
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			Scanner input = new Scanner(System.in);
			int selection = 0;

			// Menu for action selection and user input
			while (selection != 8) {
				System.out.println("Please enter the number corresponding to the action you would like to take:\n"
						+ "1 - Add New Project\n" + "2 - Update/Delete Existing Project\n" + "3 - View Project\n"
						+ "4 - Add/Update Contractor Info\n" + "5 - Add/Update Architect info\n"
						+ "6 - Add/Update Customer info\n" + "7 - Finalise Project\n" + "8 - Exit");

				selection = input.nextInt();

				switch (selection) {
				// calls the addProject method from the project.java file
				case 1:
					Project.addProject();

					break;
				// calls the updateProject method from the project.java file
				case 2:
					System.out.println(
							"Please select what you would like to do to a project:" + "\n1 - Delete" + "\n2 - Update");
					int upDel = input.nextInt();

					if (upDel == 1) {

						Project.deleteProjec();
					} else if (upDel == 2) {
						Project.updateProject();

					}

					break;
				case 3:
					// calls the viewProject method from the project.java file
					Project.viewProject();

					break;
				case 4:
					int s = 0;
					// While loop to allow it to continuously loop until a user inputs 3
					while (s != 3) {
						// Selection menu for case 4 to update and add contractors
						System.out.println("You select to Add/Update a contractor");
						System.out.println("Would you like to:" + "\n1 - Add a Contractor"
								+ "\n2 - Update Existing info" + "\n3 - Exit");
						s = input.nextInt();

						// Statement Necessary for options to occur for different
						// methods to be called
						if (s == 1) {
							// calls addConstractor method from the Contractor.java file
							Contractor.addContractors();

						}

						else if (s == 2) {
							// Calls the UpdateContractors method from the Contractor.java Files
							Contractor.updateContractors();
						} else if (s == 3) {
							// This allows users to end the while loop to proceed to the previous menu
							System.out.println("You will go back");
						} else {
							System.out.println("You have inserted an invalid option");
						}
					}
					break;
				case 5:

					int aSelect = 0;
					// A while loop to loop the menu forever until a user inputs 3
					while (aSelect != 3) {
						// Selection Menu
						System.out.println("You select to Add/Update a Architect");
						System.out.println("Would you like to:" + "\n1 - Add a Architect" + "\n2 - Update Existing info"
								+ "\n3 - Exit");
						aSelect = input.nextInt();

						if (aSelect == 1) {
							// Calls the addArchitect method from the Architect.java file
							Architect.addArchitect();

						}

						else if (aSelect == 2) {
							// Calls the updateArchitect method from the Architect.java file
							Architect.updateArchitects();
						} else if (aSelect == 3) {
							// Allows the user to end the loop and proceed to the previous menu
							System.out.println("You will go back");
						} else {
							System.out.println("You have inserted an invalid option");
						}
					}
					break;

				case 6:
					int cSelect = 0;
					// while loop to loop until a user inputs 3
					while (cSelect != 3) {
						// Selection menu
						System.out.println("You select to Add/Update a Customer:");
						System.out.println("\nWould you like to:" + "\n1 - Add a Contractor"
								+ "\n2 - Update Existing info" + "\n3 - Exit");
						cSelect = input.nextInt();

						if (cSelect == 1) {
							// Calls the addCustomer method from the Customer.java file
							Customer.addCustomers();

						}

						else if (cSelect == 2) {
							// Calls the upadteCustomer Method from the Customer.java file
							Customer.updateCustomers();
						} else if (cSelect == 3) {
							// Allows a user to select 3 and proceed to the previous menu
							System.out.println("You will go back");
						} else {
							System.out.println("You have inserted an invalid option");
						}
					}
					break;

				case 7:
					System.out.println("You Selected to update Completion");
					Project.invoice();

					break;
				case 8:
					// Allows the user to close the program which stops the while loop
					System.out.println("Program Will Close shortly");
					break;

				default:
					System.out.println("\nYou have selected an invalid option");
					break;
				}

			}

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
