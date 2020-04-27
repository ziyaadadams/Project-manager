/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poised;

import java.util.*;
import java.sql.*;

/**
 * 
 * @author ziyaad
 *
 *         The contractors.java file allows the methods to be called which
 *         allows the program to edit, view and add new contractors to the
 *         database
 */

public class Contractor {

	public static String name;
	public static String email;
	public static String telephone;
	public static String address;
	public static String ID;

	// Add contractor method
	public static void addContractors() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			Scanner input = new Scanner(System.in);
			Scanner inScanner = new Scanner(System.in);

			System.out.println("Please Enter the Contractor's Name:");
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
			String sqlInsert = "Insert into contractors (name, telephone, email, address) " + "values (" + "\"" + name
					+ "\"" + ", " + "\"" + telephone + "\"" + ", " + "\"" + email + "\"" + ", " + "\"" + address + "\""
					+ ");";
			// prints the sql query
			System.out.println("The SQL query is: " + "\nArchitect ID: " + ID + "\nProject Name: " + name + "\nEmail: "
					+ email + "\nAddress: " + address + "\nPhone Number: " + telephone);

			int countInserted = stmt.executeUpdate(sqlInsert);
			System.out.println(countInserted + " records inserted\n");
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// View contractors method
	public static void viewContractors() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			System.out.println("List of Contractors Will Be Displayed Below");

			// Calls all records from the contractors table
			String searchIdString = "Select * from contractors;";
			ResultSet rSet = stmt.executeQuery(searchIdString);

			while (rSet.next()) {
				System.out.println("\nContractor ID: " + rSet.getInt("id") + "\nName: " + rSet.getString("name")
						+ "\nEmail: " + rSet.getString("email") + "\nAddress: " + rSet.getString("address")
						+ "\nPhone Number: " + rSet.getString("telephone"));
				System.out.println(" ");
			}
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void updateContractors() {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
				"ziyaad", "Saabiras22"); Statement stmt = conn.createStatement();

		)

		{
			Scanner in = new Scanner(System.in);

			int select = 0;

			System.out.println("Would you like to view existing Contractors? ");
			System.out.println("1 - yes" + "\n2 - no");
			int viewExisting = in.nextInt();

			if (viewExisting == 1) {
				// show current current Contractors in the database

				viewContractors();

				System.out.println(" ");

			}

			else {
				System.out.println("No Contractors will be shown ");
			}

			System.out.println("Please insert the Contractors ID to update: ");
			ID = in.next();

			// While loop to loop until a user selects to exit the loop
			while (select != 5) {
				System.out.println("Please select an Option");
				System.out.println("Update a Contractors's:" + "\n1 - Name" + "\n2 - Email" + "\n3 - Address"
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
					System.out.println("You Selected to update the phone number");

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
					System.out.println("Please insert the " + column + " You would like to change");
					String changeString = inScanner.nextLine();

					// Sql command which converts all user inputs into a single string which can
					// be converted into a sql command and edit a database table
					String strUpdate = "update contractors set " + column + " = " + "\"" + changeString + "\""
							+ " where id = " + "(" + ID + ")" + ";";
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
