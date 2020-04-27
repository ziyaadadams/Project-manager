package poised;

import java.util.*;
import java.lang.invoke.CallSite;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * 
 * @author ziyaad
 *
 *         The Project.java file contains methods which allows the program to
 *         call these methods to add, edit, view and delete projects from the
 *         database
 *         
 *         This file calls methods from architect.java , customer.java and Contractor.java
 *         These allow to call the methods in the project.java methods
 *         
 *         All methods within the project.java file are based on the project table for the mysql database
 */
public class Project {

	int num;
	static String name;
	static String type;
	static String address;
	static int ERF;
	static int fee;
	static int paid;
	static String deadline;
	static int cID;
	static int arcID;
	static int conID;

	/*
	 * This method allows users to add a project to the mysql database
	 */
	public static void addProject() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

				)

		{

			Scanner input = new Scanner(System.in);

			// Necessary questions which will be added to the corresponding database columns
			// in a new row
			System.out.println("Please Enter the Project Name:");
			name = input.nextLine();
			

			System.out.println("Please Enter the Building Type: ");
			type = input.next();
			input.nextLine();

			System.out.println("Please Enter the Address: ");
			address = input.next();
			input.nextLine();

			System.out.println("Please Enter the ERF number: ");
			ERF = input.nextInt();
			input.nextLine();

			System.out.println("Please Enter the Fee of the Project: ");
			fee = input.nextInt();
			input.nextLine();

			System.out.println("Please Enter the Total Paid to Date: ");
			paid = input.nextInt();
			input.nextLine();

			// Breaks the format up to allow little user error and is later added as one
			// string
			System.out.println("Please insert the date (yyyy-mm-dd) ");
			System.out.println("Enter the Day: ");
			String day = input.next();

			System.out.println("Enter the Month as a numerical: ");
			String month = input.next();

			System.out.println("Enter the year:");
			String year = input.next();
			// Added each input into one date in the correct format to help Mysql understund
			// what is being asked
			deadline = year + "-" + month + "-" + day;

			// Menu to ask user to select an option to either add a new customer
			// or select an existing customer from the database
			System.out.println("Please select a Customer by:");
			System.out.println("1 - Add New Customer");
			System.out.println("2 - Select an Existing Customer");
			int cusSelect = input.nextInt();
			input.nextLine();

			if (cusSelect == 1) {

				// calls the addCustomer method from Customer.java file if a user has selected
				// option 1
				Customer.addCustomers();
			}

			else if (cusSelect == 2) {
				// calls the viewCustomer method from the Customer.java file
				// if option 2 was inserted
				Customer.viewCustomers();
				// once a customer has selected to view existing customer
				// they then have to insert a customer's ID which is printed out by vewCustomer
				// method
				System.out.println("\nPlease Insert the Customer's ID of whom you would like to select: ");

				cID = input.nextInt();

			}

			// Menu to ask user to select an option to either add a new Architect
			// or select an existing Architect from the database
			System.out.println("Please select the architect who will be working on this project");
			System.out.println("1 - Add new Architect");
			System.out.println("2 - Select an Existing Architect");
			int archSelect = input.nextInt();
			input.nextLine();

			if (archSelect == 1) {
				// calls the addArchitect method from the Architect.java file
				// if option 1 was inserted
				Architect.addArchitect();

			}

			else if (archSelect == 2) {

				// calls the viewrAchitect method from the Architect.java file
				// if option 2 was inserted
				Architect.viewArchitects();
				System.out.println("\nPlease Insert the Architect's ID of whom you would like to select: ");
				arcID = input.nextInt();
			}
			// Menu to ask user to select an option to either add a new Constractor
			System.out.println("Please select a Contractor by:");
			System.out.println("1 - Add New Contractor");
			System.out.println("2 - Select a existing Conractors");
			int conSelect = input.nextInt();
			input.nextLine();

			if (conSelect == 1) {
				// Calls the addContractor method from the Contractor.java file
				// if option 1 is inserted
				Contractor.addContractors();

			} else if (conSelect == 2) {

				Contractor.viewContractors();
				// calls the viewrContractor method from the Contractor.java file
				// if option 2 was inserted
				System.out.println("\nPlease Insert the Contractor's ID of whom you would like to select: ");
				conID = input.nextInt();
			}

			// The if statement runs if a user does not input a project name
			// The project name will be named by the type of building and the customer's name
			if (name.isEmpty()) {
				
				String cusString = "Select * from  customers where id = "+ cID+ ";";
				ResultSet rSetC = stmt.executeQuery(cusString);
				
				while(rSetC.next()) {
				String cName = null;	
				cName = rSetC.getString("name");
				name = type + " " + cName;
				}
				
			}
			// Adds all the user inputs and options into an sql query string
			String sqlInsert = "Insert into projects (name, type, address, erf, fee, paid, deadline, cid, aid, conid) "
					+ "values (" + "\"" + name + "\"" + ", " + "\"" + type + "\"" + ", " + "\"" + address + "\"" + ", "
					+ ERF + ", " + fee + ", " + paid + ", " + "\"" + deadline + "\"" + ", " + cID + ", " + arcID + ", "
					+ conID + ");";
			
			// Prints out the sql query that the user inserted
			System.out.println("The SQL query is: " + "\nProject Name: " + name + "\nBuilding Type: " + type
					+ "\nAddress: " + address + "\nERF Number: " + ERF + "\nFee: R" + fee + "\nPaid: R" + paid
					+ "\nDeadline: " + deadline + "\nCustomer ID: " + cID + "\nContractor ID: " + conID
					+ "\nArchitect ID: " + arcID);

			// calls how many data was affected or changed
			int countInserted = stmt.executeUpdate(sqlInsert);
			// prints how many records where added
			System.out.println(countInserted + " records inserted\n");
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// View Project method
	// This allows the method to be called when necessary
	public static void viewProject() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

				)

		{
			Scanner in = new Scanner(System.in);

			// Variables
			String numString = "number";
			String nameString = "name";
			String typeString = "type";
			String erfString = "erf";
			String deadlineString = "deadline";
			String searchString = " ";
			int select = 0;

			// While loop for the menu
			// 7 allows the loop to stop and allows the user to go back to the previous menu
			while (select != 7) {
				System.out.println("Please Select a Catergory To View Projects By:" + "\n1 - Project Number"
						+ "\n2 - Project Name" + "\n3 - Project Type" + "\n4 - ERF Number" + "\n5 - Not Completed"
						+ "\n6 - Overdue" + "\n7 - Exit");
				select = in.nextInt();

				// Switch case based on the above menu
				switch (select) {
				case 1:
					System.out.println("Please insert the project number: ");
					String searchFor = in.next();
					searchString = "where " + numString + " = " + searchFor;
					break;
				case 2:
					System.out.println("Please insert the project name: ");
					searchFor = in.next();
					searchString = "where " + nameString + " = " + "\"" + searchFor + "\"";

					break;
				case 3:
					System.out.println("Please insert the project type: ");
					searchFor = in.next();
					searchString = "where " + typeString + " = " + "\"" + searchFor + "\"";
					break;
				case 4:
					System.out.println("Please insert the project erf number: ");
					searchFor = in.next();
					searchString = "where " + erfString + " = " + "\"" + searchFor + "\"";
					break;
				case 5:
					searchString = "where completed =" + "\"" + "No" + "\"";
					System.out.println("You selected to view Projects which have not been completed:");
					break;
				case 6:
					searchString = "where deadline > " + java.time.LocalDate.now();
					System.out.println("You selected to view projects which are overdue");
					break;
				case 7:
					System.out.println("You will now exit to the main menu\n");
					break;
				default:
					System.out.println("You have inserted an invalid option");
					break;
				}

				// Converts all user inputs for the options selected and data inserted
				// into a string for a mysql command
				String searchIdString = "select * from projects " + searchString + ";";
				ResultSet rSet = stmt.executeQuery(searchIdString);

				// while loop to print all data according to what the above command is
				// which is shaped by user inputs
				while (rSet.next()) {
					System.out.println("\nProject Number: " + rSet.getInt("number") + "\nProject Name: "
							+ rSet.getString("name") + "\nBuilding Type: " + rSet.getString("type") + "\nAddress: "
							+ rSet.getString("address") + "\nERF Number: " + rSet.getInt("erf") + "\nTotal Fee: "
							+ rSet.getInt("fee") + "\nTotal Paid: " + rSet.getInt("paid") + "\nDeadline: "
							+ rSet.getDate("deadline") + "\nProject Customer ID: " + rSet.getInt("cid")
							+ "\nProject Architect ID: " + rSet.getInt("aid") + "\nProject Contractor ID: "
							+ rSet.getInt("conid") + "\nCompleted: " + rSet.getString("Completed")
							+ "\nCompletion Date: " + rSet.getDate("comdate"));
					System.out.println(" ");
				}

			}
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Update a specific project according to certain options
	// This method is allowed to be called from any java file within the environment
	public static void updateProject() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

				)

		{
			Scanner in = new Scanner(System.in);

			int select = 0;

			System.out.println("Would you like to view existing projects? ");
			// menu to allow users to have a recap if they do not know the ID of the project
			// they woould like to edit
			System.out.println("1 - yes" + "\n2 - no");
			int viewExisting = in.nextInt();

			// if function
			if (viewExisting == 1) {
				// show current projects
				System.out.println("These are the current Projects");

				// sql command to call all projects
				String strSelect = "Select * from projects;";
				System.out.println("\nProjects: ");
				ResultSet rSet = stmt.executeQuery(strSelect);
				// while loop to print all projects according the above sql command
				while (rSet.next()) {

					// Shows projects
					System.out.println("\nProject Number: " + rSet.getInt("number") + "\nProject Name: "
							+ rSet.getString("name") + "\nBuilding Type: " + rSet.getString("type") + "\nAddress: "
							+ rSet.getString("address") + "\nERF Number: " + rSet.getInt("erf") + "\nTotal Fee: "
							+ rSet.getInt("fee") + "\nTotal Paid: " + rSet.getInt("paid") + "\nDeadline: "
							+ rSet.getDate("deadline") + "\nProject Customer ID: " + rSet.getInt("cid")
							+ "\nProject Architect ID: " + rSet.getInt("aid") + "\nProject Contractor ID: "
							+ rSet.getInt("conid") + "\nCompleted: " + rSet.getString("Completed"));
				}
				System.out.println(" ");

			}

			else {
				System.out.println("No projects will be shown ");
			}
			// asks user to insert a project number
			System.out.println("Please insert the Project Number to update: ");
			int projNum = in.nextInt();

			// While loop to loop until a user decides to exit
			// which will take them back to the previous menu
			while (select != 5) {
				System.out.println("Please select an Option");
				System.out.println("Update a project's:" + "\n1 - Name" + "\n2 - Type" + "\n3 - Address"
						+ "\n4 - Completion" + "\n5 - Deadline" + "\n6 - Exit");
				select = in.nextInt();

				String column = " ";
				// switch case with 6 options which allows users to update certain data in row
				switch (select) {
				case 1:
					column = "name";
					System.out.println("You selected to update a name");
					break;
				case 2:
					column = "type";
					System.out.println("You selected to update the type");
					break;
				case 3:
					column = "address";
					System.out.println("You Select to update the address");
					break;

				case 4:
					column = "deadline";
					System.out.println("You Selected to update the Deadline" + "\nPlease use (yyyy-mm-dd) date format");
					break;
				case 5:
					System.out.println("You will go back");
				default:
					System.out.println("You have inserted an invalid option");
					break;
				}

				System.out.println("Please insert the " + column + " You would like to change");
				String changeString = in.next();
				// Converts user options inserted to a sql command
				String strUpdate = "update projects set " + column + " = " + "\"" + changeString + "\""
						+ " where number = " + "(" + projNum + ")" + ";";
				System.out.println("The SQL query is: " + strUpdate);
				int countUpdated = stmt.executeUpdate(strUpdate);
				System.out.println(countUpdated + " records affected");

			}
		}

		catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Invoice method which generates an invoice if a user has selected the
	 * necessary options and if total paid is less than total fee
	 */
	public static void invoice() {

		// mysql connection
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

				) {

			Scanner in = new Scanner(System.in);
			// Prompts user the option to view existing projects based on the viewProjects()
			// method
			System.out.println("Would you like to view the current list of projects:" + "\n1 - Nes" + "\n2 - No ");
			int select = in.nextInt();
			if (select == 1) {

				viewProject();

			}

			else {
				System.out.println("You have selected not to view existing projects");

			}

			System.out.println("Please insert the project number you would like to finalise");

			int projNum = in.nextInt();

			// Converts user options inserted to a sql command
			String strUpdate = "update projects set Completed = " + "\"" + "Finalised" + "\"" + ", comdate = " + "\""
					+ java.time.LocalDate.now() + "\"" + " where number = " + "(" + projNum + ");";
			System.out.println("The SQL query is: " + strUpdate);
			int countUpdated = stmt.executeUpdate(strUpdate);
			System.out.println(countUpdated + " records affected");

			// sql command to call all projects
			String strSelect = "Select * from projects where number = (" + projNum + ")";

			ResultSet rSet = stmt.executeQuery(strSelect);
			// while loop to print all projects according the above sql command
			int paid = 0;
			int total = 0;
			int customerID = 0;
			while (rSet.next()) {

				paid = rSet.getInt("paid");
				fee = rSet.getInt("fee");
				total = fee - paid;
				customerID = rSet.getInt("cid");

			}
			if (fee == paid) {
				System.out.println("Everything has been paid for ");
				System.out.println("No Invoice");
			}

			else if (paid < fee) {
				// mySql command to get information from the database table customers where user
				// inputed which project
				String cuString = "select * from customers where id = " + customerID + ";";
				ResultSet rSetInvoice = stmt.executeQuery(cuString);

				while (rSetInvoice.next()) {
					System.out.println("\nInvoice: " + "\nCustomer Name: " + rSetInvoice.getString("name")
					+ "\nCustomer Email: " + rSetInvoice.getString("email") + "\nCustomer Address: "
					+ rSetInvoice.getString("address") + "\nCustomer Phone Number: "
					+ rSetInvoice.getString("telephone") + "\nTotal Fee: " + fee + "\nTotal Paid: " + paid
					+ "\nAmount still Owed: " + total + "\n");

				}
			}

		}

		catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Delete Project method which allows the program to call this method and delete
	 * projects based on user input
	 */
	public static void deleteProjec() {

		Scanner in = new Scanner(System.in);
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

				) {
			// Prompts user to insert a number 1 or 2 to display existing projects
			System.out.println("Would you like to view existing projects: " + "\n1 - Yes" + "\n2 - No");
			int yesNo = in.nextInt();

			if (yesNo == 1) {
				System.out.println("These are the current Projects");

				// sql command to call all projects
				String strSelect = "Select * from projects;";
				System.out.println("\nProjects: ");
				ResultSet rSet = stmt.executeQuery(strSelect);
				// while loop to print all projects according the above sql command
				while (rSet.next()) {

					// Shows projects
					System.out.println("\nProject Number: " + rSet.getInt("number") + "\nProject Name: "
							+ rSet.getString("name") + "\nBuilding Type: " + rSet.getString("type") + "\nAddress: "
							+ rSet.getString("address") + "\nERF Number: " + rSet.getInt("erf") + "\nTotal Fee: "
							+ rSet.getInt("fee") + "\nTotal Paid: " + rSet.getInt("paid") + "\nDeadline: "
							+ rSet.getDate("deadline") + "\nProject Customer ID: " + rSet.getInt("cid")
							+ "\nProject Architect ID: " + rSet.getInt("aid") + "\nProject Contractor ID: "
							+ rSet.getInt("conid") + "\nCompleted: " + rSet.getString("Completed"));
				}
				System.out.println(" ");

			}

			else if (yesNo == 2) {
				System.out.println("No projects will be shown: ");

			}

			// prompts user to insert a project number to get deleted
			System.out.println("Please enter the number of the project you would like to delete:");
			int deleteId = in.nextInt();

			// Mysql Command which is carried out according to user insertion of the project
			// nummber
			String sqlDelete = "Delete from projects where number = " + deleteId + ";";
			System.out.println("The Project number " + deleteId + " will be deleted");
			int countDeleted = stmt.executeUpdate(sqlDelete);
			System.out.println(countDeleted + " records deleted\n");
		}

		catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}