

package poised;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * @author ziyaad
 * 
 * Architect.java allows to call the methods to edit, view and add to
 * the mysql database which directly affects the architects table
 * 
 * The methods within this file calls methods from other files
 */
public class Architect {

	public static String name;
	public static String email;
	public static String telephone;
	public static String address;
	public static String arcID;

	// Add architect method
	public static void addArchitect() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			Scanner input = new Scanner(System.in);
			Scanner inScanner = new Scanner(System.in);

			// user inputs to provide necessary data to be inserted into the data base
			System.out.println("Please Enter the Architect's Name:");
			name = inScanner.nextLine();
			inScanner.reset();

			System.out.println("Please Enter the Email Address: ");
			email = input.next();
			input.reset();

			System.out.println("Please Enter the Address: ");
			address = inScanner.nextLine();
			inScanner.reset();

			System.out.println("Please Enter the Phone Number: ");
			telephone = inScanner.nextLine();

			// Sql command which converts all user inputs into a single string which can
			// be converted into a sql command and edit a database table
			String sqlInsert = "Insert into architects (name, telephone, email, address) " + "values (" + "\"" + name
					+ "\"" + ", " + "\"" + telephone + "\"" + ", " + "\"" + email + "\"" + ", " + "\"" + address + "\""
					+ ");";

			// prints the outcome of all data that has been inserted by the user
			System.out.println("The SQL query is: " + "\nArchitect ID: " + arcID + "\nProject Name: " + name
					+ "\nEmail: " + email + "\nAddress: " + address + "\nPhone Number: " + telephone);

			int countInserted = stmt.executeUpdate(sqlInsert);
			System.out.println(countInserted + " records inserted\n");
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// View Architects command
	// this allows users to view architects listed in the database
	public static void viewArchitects() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			System.out.println("List of Architects Will Be Displayed Below");

			// SQL command to call all data within the architects table
			String searchIdString = "Select * from architects; ";
			ResultSet rSet = stmt.executeQuery(searchIdString);

			// Prints all data out in this format
			while (rSet.next()) {
				System.out.println("\nArchitect ID: " + rSet.getInt("id") + "\nName: " + rSet.getString("name")
						+ "\nEmail: " + rSet.getString("email") + "\nAddress: " + rSet.getString("address")
						+ "\nPhone Number: " + rSet.getString("telephone"));
				System.out.println(" ");
			}
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Update Architect method which allows to
	// Update current information about the existing architects
	public static void updateArchitects() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			Scanner in = new Scanner(System.in);

			int select = 0;
			// option give to help users have a recap if they do not know what is
			// the ID number for the architect
			System.out.println("Would you like to view existing Architects? ");
			System.out.println("1 - yes" + "\n2 - no");
			int viewExisting = in.nextInt();

			if (viewExisting == 1) {
				// show current current Architects in the database

				viewArchitects();

				System.out.println(" ");

			}

			else {
				System.out.println("No Architects will be shown ");
			}

			System.out.println("Please insert the Architect ID to update: ");
			arcID = in.next();

			// Menu looped util a user selects exit which takes them back to the previous
			// menu
			// this menu allows users to insert an option they would like to achieve
			while (select != 5) {
				System.out.println("Please select an Option");
				System.out.println("Update an Architect's:" + "\n1 - Name" + "\n2 - Email" + "\n3 - Address"
						+ "\n4 - Telephone" + "\n5 - Exit");
				select = in.nextInt();

				String column = " ";
				switch (select) {
				case 1:
					column = "name";
					System.out.println("You selected to update a name");
					break;
				case 2:
					column = "email";
					System.out.println("You selected to update the type");
					break;
				case 3:
					column = "address";
					System.out.println("You Select to update the address");
					break;
				case 4:
					column = "telephone";
					System.out.println("You Selected to update Completion");
					break;
				case 5:
					System.out.println("You will go back");
					break;

				default:
					System.out.println("You have inserted an invalid option");
					break;
				}

				in.reset();
				if (select != 5) {

					Scanner inScanner = new Scanner(System.in);
					// Converts all user inputs and options into an MySQL command
					System.out.println("Please insert the " + column + " You would like to change");
					String changeString = inScanner.nextLine();

					// Converts all user inputs and options into an MySQL command
					String strUpdate = "update architects set " + column + " = " + "\"" + changeString + "\""
							+ " where id = " + "(" + arcID + ")" + ";";
					System.out.println("The SQL query is: " + strUpdate);
					int countUpdated = stmt.executeUpdate(strUpdate);
					System.out.println(countUpdated + " records affected");
				}

			}
		}

		catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
