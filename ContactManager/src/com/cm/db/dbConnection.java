package com.cm.db;

/**Written by Nayana Thomas for 6360.004, assignment 4, starting October 28, 2017.
    NetID: nxt170630
*/

import java.sql.*;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class mainly used for all the database related operations
 */
public class dbConnection {
	public static Connection conn = null;

	/**
	 * This method is used to fetch all the contacts of the contact manager from the
	 * contact database
	 */
	public static ResultSet dbConnDetails() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/Contact_Manager??autoReconnect=true&useSSL=false", "root", "123456");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CONTACTS;");
			return rs;
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			System.out.println("Error in connection: " + ex.getMessage());
			return null;
		}
	}

	/**
	 * This method is used to fetch the details of the contact which the user have searched from the
	 * contact database
	 */
	public static ResultSet searchDB(String fname) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/Contact_Manager??autoReconnect=true&useSSL=false", "root", "123456");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CONTACTS WHERE Fname = '" + fname + "';");
			return rs;
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			System.out.println("Error in connection: " + ex.getMessage());
			return null;
		}
	}

	/**
	 * This method is used to update the details of the contact which the user have selected.
	 */
	public static void updateDB(Integer contactId, String fname, String minit, String lname, String gender,
			Integer phone) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/Contact_Manager??autoReconnect=true&useSSL=false", "root", "123456");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE CONTACTS SET Fname = '" + fname + "',Minit='" + minit + "',Gender= '" + gender
					+ "',Lname= '" + lname + "' WHERE Contact_Id = '" + contactId + "';");
			
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}

	/**
	 * This method is used to delete the contact which the user have selected.
	 */
	public static void deleteDB(int ContactId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/Contact_Manager??autoReconnect=true&useSSL=false", "root", "123456");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM CONTACTS WHERE Contact_Id = " + ContactId + ";");
		}catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}

	/**
	 * This method is used to insert details a new contact to the contact manager database
	 */
	public static void insertDB(String fname, String lname, String minit, String gender, Date bdate, String mStatus,
			Date firstKnown, String company, String jobTitle, String webSite, String notes, Integer phone) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/Contact_Manager??autoReconnect=true&useSSL=false", "root", "123456");
			Statement statement = conn.createStatement();
			String Query = "INSERT INTO CONTACTS(Fname,Minit,Lname,Gender,Birthday,Marital_Status,First_Known_On,Company,Job_Title,Website,Notes) VALUES('"
					+ fname + "','" + minit + "','" + lname + "','" + gender + "',null,null,null,null,null,null,null)";
			System.out.println(Query);
			statement.executeUpdate(
					"INSERT INTO CONTACTS(Fname,Minit,Lname,Gender,Birthday,Marital_Status,First_Known_On,Company,Job_Title,Website,Notes) VALUES('"
							+ fname + "','" + minit + "','" + lname + "','" + gender
							+ "',null,null,null,null,null,null,null)");
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				long Contact_Id;
				if (generatedKeys.next()) {
					Contact_Id = generatedKeys.getLong(1);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}

				statement.executeUpdate("INSERT INTO CONTACT_PHONE VALUES('" + Contact_Id + "'," + phone + ");");
			}
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
			System.out.println("Error in connection: " + ex.getMessage());
		}

	}
}
