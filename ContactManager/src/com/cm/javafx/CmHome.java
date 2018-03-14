/******************************************************************************
* CONTACT MANAGER APPLICATION
* 
* This application keep tracks of all the contacts in your database.
* It is not only useful for retrieving the contact details of the users. 
* But also enables a user to add, delete, edit and update the contacts.
* 
* The Show all contact button is used to show all the contacts in the contact manager
* The program can add new contacts when the Add button is clicked.
* When the Edit button is clicked, the program modifies the selected contact.
* The search button facilitates searching of specific contacts.
* The delete button is used to delete the selected contact.
* 
* Written by Nayana Thomas for 6360.004, assignment 4, starting October 23, 2017.
    NetID: nxt170630
 * 
  */
package com.cm.javafx;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import com.cm.db.dbConnection;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/***
 * This class is the main class for the contact manager application. It has the
 * implementation for the basic UI of the application. All the inputs from the
 * user are fetched as an observable list of Contacts from the UI in this class
 * and is passed to the contactManagerDetail class for processing.
 **/
public class CmHome extends Application {
	Button btn, allBtn;
	Scene scene1, editScene, addScene;
	TableView<contactManagerDetail> table;
	TextField fnameEdit, minitEdit, lnameEdit, genderEdit, phoneEdit;
	TextField fnameEdit1, minitEdit1, lnameEdit1, genderEdit1, phoneEdit1;
	TextField fnameEdit2, minitEdit2, lnameEdit2, genderEdit2, phoneEdit2;
	Label fname1, minit1, lname1, gender1, phone1;
	Label fname2, minit2, lname2, gender2, phone2;
	Integer contactId;

	@Override
	/**
	 * Handles creation of JavaFX Scene and Stage objects as well as modification of
	 * scene graph operations to live objects.
	 * 
	 * @param mainStage:
	 *            renders the main UI
	 */
	public void start(Stage mainStage) throws Exception {
		Label fname = new Label("First Name: ");
		fnameEdit = new TextField();
		Label minit = new Label("Minit: ");
		minitEdit = new TextField();
		Label lname = new Label("Last Name: ");
		lnameEdit = new TextField();
		Label gender = new Label("Gender: ");
		genderEdit = new TextField();
		Label phoneNumber = new Label("Phone: ");
		phoneEdit = new TextField();
		btn = new Button("Search");
		allBtn = new Button("Show All Contacts");
		Button add = new Button("Add");
		add.setOnAction(e -> mainStage.setScene(addScene));
		Button delete = new Button("Delete");
		delete.setOnAction(e -> deleteButtonClicked());

		Button editBtn = new Button("Edit");

		VBox leftMenu = new VBox();
		leftMenu.getChildren().addAll(fname, fnameEdit, minit, minitEdit, lname, lnameEdit, gender, genderEdit,
				phoneNumber, phoneEdit);
		leftMenu.setPadding(new Insets(0, 20, 10, 20));

		HBox downMenu = new HBox();
		downMenu.getChildren().addAll(add, editBtn, btn, allBtn);
		downMenu.setAlignment(Pos.BOTTOM_LEFT);

		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(leftMenu);
		borderPane.setRight(downMenu);

		final ObservableList<contactManagerDetail> data = FXCollections.observableArrayList();
		final ObservableList<contactManagerDetail> allData = FXCollections.observableArrayList();

		Scene scene = new Scene(borderPane, 1000, 800);
		mainStage.setTitle("Contact Manager Application");
		mainStage.setScene(scene);
		mainStage.show();

		/**
		 * This is triggered when the search button is clicked. This action displays the
		 * details of the searched contact in the table view. Currently searching using
		 * first name is only supported.
		 */
		btn.setOnAction(e -> {
			table.getItems().clear();
			ResultSet rs = dbConnection.searchDB(fnameEdit.getText());
			fnameEdit.clear();
			minitEdit.clear();
			phoneEdit.clear();
			lnameEdit.clear();
			genderEdit.clear();
			try {
				while (rs.next()) {
					data.add(new contactManagerDetail(Integer.parseInt(rs.getString("contact_id")),
							rs.getString("fname"), rs.getString("lname"), rs.getString("minit"), rs.getString("gender"),
							rs.getDate("birthday"), rs.getString("marital_status"), rs.getDate("first_known_on"),
							rs.getString("company"), rs.getString("job_title"), rs.getString("website"),
							rs.getString("notes")));
					System.out.println("lname: " + rs.getString("lname"));
					System.out.println("minit: " + rs.getString("minit"));
					table.setItems(data);
				}
				dbConnection.conn.close();
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/**
		 * Displays all the contact details in the contact manager when the show all
		 * contacts button is clicked.
		 */
		allBtn.setOnAction(e -> {
			ResultSet rs = dbConnection.dbConnDetails();
			try {
				table.getItems().clear();
				while (rs.next()) {
					allData.add(new contactManagerDetail(Integer.parseInt(rs.getString("contact_id")),
							rs.getString("fname"), rs.getString("lname"), rs.getString("minit"), rs.getString("gender"),
							rs.getDate("birthday"), rs.getString("marital_status"), rs.getDate("first_known_on"),
							rs.getString("company"), rs.getString("job_title"), rs.getString("website"),
							rs.getString("notes")));
					System.out.println("lname: " + rs.getString("lname"));
					System.out.println("minit: " + rs.getString("minit"));
					table.setItems(allData);
				}
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		/**
		 * This is used to populate the table view with the heading of the columns as
		 * well as the data fetched form the database.
		 */
		TableColumn<contactManagerDetail, String> fnameColumn = new TableColumn<>("First Name");
		fnameColumn.setMinWidth(45);
		fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));

		TableColumn<contactManagerDetail, String> lnameColumn = new TableColumn<>("Last Name");
		lnameColumn.setMinWidth(45);
		lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));

		TableColumn<contactManagerDetail, String> minitColumn = new TableColumn<>("Middle Initial");
		minitColumn.setMinWidth(45);
		minitColumn.setCellValueFactory(new PropertyValueFactory<>("mInit"));

		TableColumn<contactManagerDetail, String> genderColumn = new TableColumn<>("Gender");
		genderColumn.setMinWidth(10);
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn<contactManagerDetail, String> phoneColumn = new TableColumn<>("Phone");
		phoneColumn.setMinWidth(45);
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

		TableColumn<contactManagerDetail, String> mStatusColumn = new TableColumn<>("Marital Status");
		mStatusColumn.setMinWidth(10);
		mStatusColumn.setCellValueFactory(new PropertyValueFactory<>("mStatus"));

		TableColumn<contactManagerDetail, Date> bDateColumn = new TableColumn<>("Birth Date");
		bDateColumn.setMinWidth(45);
		bDateColumn.setCellValueFactory(new PropertyValueFactory<>("bdate"));

		TableColumn<contactManagerDetail, Date> firstKnownCol = new TableColumn<>("Known Since");
		firstKnownCol.setMinWidth(45);
		firstKnownCol.setCellValueFactory(new PropertyValueFactory<>("firstKnown"));

		TableColumn<contactManagerDetail, String> compColumn = new TableColumn<>("Company");
		compColumn.setMinWidth(45);
		compColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

		TableColumn<contactManagerDetail, String> webSiteColumn = new TableColumn<>("Website");
		webSiteColumn.setMinWidth(10);
		webSiteColumn.setCellValueFactory(new PropertyValueFactory<>("webSite"));

		TableColumn<contactManagerDetail, String> jobColumn = new TableColumn<>("Job Title");
		jobColumn.setMinWidth(10);
		jobColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

		TableColumn<contactManagerDetail, String> notesColumn = new TableColumn<>("Notes");
		notesColumn.setMinWidth(200);
		notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

		/**
		 * These are delete and the table view components present in the Main Scene
		 * Table provides a table view for all the data in the database. The delete
		 * button deletes the selected contact. a Vertical Box Layout is used to add all
		 * these component to the scene
		 */
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10, 10, 10, 10));
		hBox.setSpacing(10);
		hBox.getChildren().addAll(delete);

		table = new TableView<>();
		table.getColumns().addAll(fnameColumn, lnameColumn, minitColumn, phoneColumn, genderColumn, mStatusColumn,
				bDateColumn, firstKnownCol, compColumn, webSiteColumn, jobColumn, notesColumn);

		VBox contactListLayout = new VBox();
		contactListLayout.getChildren().addAll(table, hBox);
		borderPane.setBottom(contactListLayout);

		/**
		 * These are the main components of the Edit Scene TThe scene has labels, text
		 * fields, save and back button The save button save the updated details and
		 * shows a dialog box saying contact is updated. a Vertical Box Layout is used
		 * to add all these component to the scene
		 */
		fname1 = new Label("First Name: ");
		minit1 = new Label("Minit: ");
		lname1 = new Label("Last Name: ");
		gender1 = new Label("Gender: ");
		phone1 = new Label("Phone: ");
		fnameEdit1 = new TextField();
		minitEdit1 = new TextField();
		lnameEdit1 = new TextField();
		genderEdit1 = new TextField();
		phoneEdit1 = new TextField();
		Button saveBtn = new Button("Save");
		Button backBtn = new Button("Back");
		VBox contactEditLayout = new VBox();
		contactEditLayout.getChildren().addAll(fname1, fnameEdit1, minit1, minitEdit1, lname1, lnameEdit1, gender1,
				genderEdit1, phone1, phoneEdit1, saveBtn, backBtn);
		editScene = new Scene(contactEditLayout, 1000, 800);

		/**
		 * These are the two buttons available inside Edit scene. The back button is
		 * used to go back to the home page. The save button save the updated details
		 * and shows a dialog box saying contact is updated.
		 */
		backBtn.setOnAction(e -> mainStage.setScene(scene));
		saveBtn.setOnAction(e -> {
			dbConnection.updateDB(contactId, fnameEdit1.getText(), minitEdit1.getText(), lnameEdit1.getText(),
					genderEdit1.getText(), Integer.parseInt(phoneEdit1.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setContentText("Contact is updated");
			alert.showAndWait();
			mainStage.setScene(scene);
		});

		/**
		 * Opens a new scene to edit details for the specified contact. If the user
		 * clicks Save, the changes are saved into the provided contact manager object
		 */
		editBtn.setOnAction(e -> {
			contactManagerDetail selectedContact = table.getSelectionModel().getSelectedItem();
			if (selectedContact != null) {
				contactId = selectedContact.getContactId();
				System.out.println("contact: " + contactId);
				mainStage.setScene(editScene);
				fnameEdit1.setText(selectedContact.getFname());
				minitEdit1.setText(selectedContact.getmInit());
				lnameEdit1.setText(selectedContact.getLname());
				genderEdit1.setText(selectedContact.getGender());
				phoneEdit1.setText(selectedContact.getPhone());
			} else {
				Alert alert1 = new Alert(AlertType.WARNING, "Select a row to edit", ButtonType.OK);
				alert1.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert1.show();
			}
		});

		fname2 = new Label("First Name: ");
		minit2 = new Label("Minit: ");
		lname2 = new Label("Last Name: ");
		gender2 = new Label("Gender: ");
		phone2 = new Label("Phone: ");
		fnameEdit2 = new TextField();
		minitEdit2 = new TextField();
		lnameEdit2 = new TextField();
		genderEdit2 = new TextField();
		phoneEdit2 = new TextField();
		Button createBtn = new Button("Create");
		Button homeBtn = new Button("Back");
		VBox contactAddLayout = new VBox();
		contactAddLayout.getChildren().addAll(fname2, fnameEdit2, minit2, minitEdit2, lname2, lnameEdit2, gender2,
				genderEdit2, phone2, phoneEdit2, createBtn, homeBtn);
		addScene = new Scene(contactAddLayout, 1000, 800);

		homeBtn.setOnAction(e -> mainStage.setScene(scene));
		createBtn.setOnAction(e -> {
			fnameEdit2.clear();
			minitEdit2.clear();
			lnameEdit2.clear();
			genderEdit2.clear();
			phoneEdit2.clear();
			dbConnection.insertDB(fnameEdit2.getText(), minitEdit2.getText(), lnameEdit2.getText(),
					genderEdit2.getText(), null, null, null, null, null, null, null,
					Integer.parseInt(phoneEdit2.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setContentText("Contact is updated");
			alert.showAndWait();
			mainStage.setScene(scene);
		});
	}

	/**
	 * Deletes the selected COntact from the contact list. Before deleting it ask
	 * for user confirmation and once it is confirmed the contact is deleted
	 */
	public void deleteButtonClicked() {
		ObservableList<contactManagerDetail> contactsSelected, allContacts;
		allContacts = table.getItems();
		contactsSelected = table.getSelectionModel().getSelectedItems();
		contactManagerDetail selectedContact = table.getSelectionModel().getSelectedItem();
		if (selectedContact != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete the contact?");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			Optional<ButtonType> action = alert.showAndWait();
			if (action.get() == ButtonType.OK) {
				dbConnection.deleteDB(selectedContact.getContactId());
				contactsSelected.forEach(allContacts::remove);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Select a row to delete", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
		}
	}

	public static void main(String args[]) {
		launch(args);
	}

}
