
/**
 * Class: Driver
 * Author: s3682248
 * Date: 21st May 2018
 * Version 2
 *
 * This class is used to control the flow of the program run and interact with the end user.
 * The class assumes user names are unique.
 *
 * The class is capable of below actions
 * List all users in network
 * Add a friend
 * Remove a friend
 * Update Image
 * Update Status
 * Check given users are friends
 * View details of selected user
 */

import java.util.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;

import javafx.scene.layout.*;
import javafx.geometry.Orientation;
import javafx.scene.control.ChoiceBox;

public class Driver extends Application {

	ArrayList<Person> allProfiles = new ArrayList<Person>();
	Person curPerson = null;
	final Scanner sc = new Scanner(System.in);
	ChoiceBox<String> choiceBox;
	ChoiceBox<String> choiceBoxFr;
	Label lbPersonInfo;

	Label lbSystemInfo;
	Label lbSystemInfoNewPerson;

	Stage window;
	Scene scene1, scene2;

	String info = "";

	TextField newName;
	TextField newAge;
	TextField newStatus;

	public void start(Stage primaryStage) {

		window = primaryStage;

		loadDefaultData();
		primaryStage.setTitle("Mininet Application");

		FlowPane pane = new FlowPane(Orientation.VERTICAL, 5, 10);// hGap=5,vGap = 5
		pane.setPadding(new Insets(11, 12, 13, 14));

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		Button mbtnAddPsn = new Button("Add New Person");
		mbtnAddPsn.setOnAction(e -> window.setScene(scene2));
		GridPane.setConstraints(mbtnAddPsn, 0, 0);

		Button mbtnClose = new Button("Exit Application");
		mbtnClose.setOnAction(e -> window.close());
		GridPane.setConstraints(mbtnClose, 1, 0);

		Label lbSelect = new Label("Select a person");
		GridPane.setConstraints(lbSelect, 0, 1);

		Button mbtnDelPsn = new Button("Delete Selected Person from Network");
		mbtnDelPsn.setOnAction(e -> deletePerson());

		choiceBox = new ChoiceBox<>();

		updatePersonList();

		GridPane.setConstraints(choiceBox, 1, 1);
		choiceBox.setOnAction(e -> getChoice());

		Button mbtnAddFrnd = new Button("Make Below Person A Friend");
		mbtnAddFrnd.setOnAction(e -> addNewFriend());

		Button mbtnCheckFriends = new Button("Check selected are friends");
		mbtnCheckFriends.setOnAction(e -> checkSelectedAreFriends());

		grid.getChildren().addAll(mbtnAddPsn, lbSelect, choiceBox, mbtnAddFrnd, mbtnClose);

		pane.getChildren().add(grid);

		lbPersonInfo = new Label();
		pane.getChildren().add(lbPersonInfo);

		pane.getChildren().add(mbtnDelPsn);
		pane.getChildren().add(mbtnAddFrnd);
		pane.getChildren().add(mbtnCheckFriends);

		choiceBoxFr = new ChoiceBox<>();
		pane.getChildren().add(choiceBoxFr);

		lbSystemInfo = new Label();
		pane.getChildren().add(lbSystemInfo);

		scene1 = new Scene(pane, 650, 550);
		window.setScene(scene1);

		FlowPane paneNewPerson = new FlowPane(Orientation.VERTICAL, 5, 10);
		paneNewPerson.setPadding(new Insets(11, 12, 13, 14));

		newName = new TextField("Enter name here");
		newAge = new TextField("Enter age here");
		newStatus = new TextField("Enter status here");

		Button addnewPsn = new Button("Add to network");
		addnewPsn.setOnAction(e -> addNewPerson());
		Button backToMain = new Button("Go to Main Menu");
		backToMain.setOnAction(e -> window.setScene(scene1));

		paneNewPerson.getChildren().add(newName);
		paneNewPerson.getChildren().add(newAge);
		paneNewPerson.getChildren().add(newStatus);
		paneNewPerson.getChildren().add(addnewPsn);
		paneNewPerson.getChildren().add(backToMain);

		lbSystemInfoNewPerson = new Label();
		paneNewPerson.getChildren().add(lbSystemInfoNewPerson);

		scene2 = new Scene(paneNewPerson, 600, 300);

		window.show();

	}

	private void updatePersonList() {

		choiceBox.getItems().clear();
		for (int i = 0; i < allProfiles.size(); i++) {
			choiceBox.getItems().add(allProfiles.get(i).getName());
		}

	}

	/*
	 * this method handles the actions required when change the person from the
	 * current person drop down.
	 */
	private void getChoice() {
		
		lbSystemInfo.setText("");

		if (choiceBox.getValue() != null) {
			curPerson = getPerson(choiceBox.getValue());
			info = "Name: " + curPerson.getName() + "\n\r" + "Age: " + String.valueOf(curPerson.getAge()) + "\n\r"
					+ "Status: " + curPerson.getStatus() + "\n\r" + "State: " + curPerson.getState() + "\n\r"
					+ "Friends: " + curPerson.getFriendList() + "\n\r";

			if (curPerson instanceof Adult) {
				info = info + "Childeren: " + ((Adult) curPerson).showChildren() + "\n\r" + "Partner: ";
				if (((Adult) curPerson).getPartner() != null) {
					info = info + "Childeren: " + ((Adult) curPerson).getPartner().getName() + "\n\r" + "Partner: ";
				}

			}

			if (curPerson instanceof Child) {
				info = info + "Parent 1 : " + ((Child) curPerson).getParentName1() + "\n\r";
				info = info + "Parent 2 : " + ((Child) curPerson).getParentName2() + "\n\r";
			}

			lbPersonInfo.setText(info);

			choiceBoxFr.getItems().clear();

			for (int i = 0; i < allProfiles.size(); i++) {
				if (curPerson != allProfiles.get(i)) {
					choiceBoxFr.getItems().add(allProfiles.get(i).getName());
				}

			}
		}

	}

	/*
	 * this method check two persons are friends.
	 */
	private void checkSelectedAreFriends() {

		if (choiceBoxFr.getValue() == null || choiceBox.getValue() == null) {
			lbSystemInfo.setText("Please select a person in each dropdowns.");
		} else {
			if (getPerson(choiceBox.getValue()).isFriendOf(getPerson(choiceBoxFr.getValue()))) {
				lbSystemInfo.setText("Yes, They are friends.");

			} else {
				lbSystemInfo.setText("No, they are not friends.");

			}
		}

	}

	/*
	 * this method delete a person from network.
	 */
	private void deletePerson() {

		if (choiceBox.getValue() == null) {
			lbSystemInfo.setText("Please select a person to delete.");
		} else {
			// Some action to implement to remove the selected person from friend lists
			// before delete
			try {
			//allProfiles.remove(getPerson(choiceBox.getValue()));
			//getChoice();
			lbSystemInfo.setText("Successfully deleted from network.");
			}catch(Exception e) {
				lbSystemInfo.setText("Error: "+e.getMessage());
			}

		}

	}

	/*
	 * this method adds a new person to the network.
	 */
	private void addNewPerson() {

		// this function is not completed and need to do age check and other validations

		if (getPerson(newName.getText()) != null) {
			lbSystemInfoNewPerson.setText("Error: Person with the same name is already exist. Use Another name.");

		} else {
			
			try {
				
				Person P = new Adult(newName.getText(), Integer.parseInt(newAge.getText()), "pt1.jpg", "", "VIC", "M",
						new ArrayList<Person>(), new ArrayList<Person>());
				allProfiles.add(P);
				updatePersonList();
				lbSystemInfoNewPerson.setText(newName.getText() + " added to the network.");
			} catch (Exception e) {
				lbSystemInfoNewPerson.setText("Error: " + e.getMessage());
			}
			
		}

	}

	/*
	 * this method handles adding a friend actions for the selected user.
	 */
	private void addNewFriend() {

		if (choiceBoxFr.getValue() == null || choiceBoxFr.getValue() == null) {
			lbSystemInfo.setText("Please select a person in each check box.");
		} else {

			try {
				curPerson.addedTo(getPerson(choiceBoxFr.getValue()));
				if (getPerson(choiceBoxFr.getValue()).isFriendOf(curPerson) == false) {
					if(getPerson(choiceBoxFr.getValue()).addedTo(curPerson)) {
						lbSystemInfo.setText("Congratulations: " + choiceBoxFr.getValue() + " was added as a Friend: ");
					}else {
						lbSystemInfo.setText("Error: Cannot be added due to some unknown reason.");
					}
				}else {
					
					lbSystemInfo.setText("Error: They are already friends. So cannot be added again. ");
					
				}

				
				getChoice();

			} catch (NotToBeFriendsException nbe) {
				lbSystemInfo.setText("Error: " + nbe.getMessage());
			} catch (TooYoungException tye) {
				lbSystemInfo.setText("Error: " + tye.getMessage());
			}
		}

	}

	/*
	 * this method updates current user status.
	 */
	private void updateStatus() {
		String newStatus = sc.next();
		curPerson.setStatus(newStatus);

	}

	/*
	 * this method creates 5 Adults and 3 Dependants in the network for the drive.
	 */
	public void drive(String[] args) {
		launch(args);
	}

	public void loadDefaultData() {

		LoadData.loadInitData();
		String[][] people = LoadData.peopleArray;
		String[][] relations = LoadData.relationsArray;

		// creating Adults
		for (int r = 0; r < people.length; r++) {
			String name = people[r][0];
			String photo = people[r][1];
			String status = people[r][2];
			String sex = people[r][3];
			int age = Integer.parseInt(people[r][4]);
			String state = people[r][5];

			if (age >= Adult.ADULT_MIN_AGE) {

				Person P = new Adult(name, age, photo, status, state, sex, new ArrayList<Person>(),
						new ArrayList<Person>());
				allProfiles.add(P);

			}

		}

		this.createDependents(people, relations);

		// add friends, couple relations
		for (int f = 0; f < relations.length; f++) {

			Person Psn1 = getPerson(relations[f][0]);
			Person Psn2 = getPerson(relations[f][1]);

			if (relations[f][2].toLowerCase().equals("friends")) {

				if (Psn1 != null && Psn2 != null) {
					try {
						Psn1.addedTo(Psn2);
					} catch (Exception e) {
						lbSystemInfo.setText(e.getMessage());

					}

				}
			} else if (relations[f][2].toLowerCase().equals("couple")) {
				try {

					((Adult) Psn1).setPartner((Adult) Psn2);
					((Adult) Psn2).setPartner((Adult) Psn1);

				} catch (Exception e) {

					lbSystemInfo.setText(e.getMessage());

				}

			}

		}

		// allProfiles.get(6).viewDetails();
		// topLevelHandler();
	}

	public void createDependents(String[][] people, String[][] relations) {
		// create Dependents
		for (int r = 0; r < people.length; r++) {

			String name = people[r][0];
			String photo = people[r][1];
			String status = people[r][2];
			String sex = people[r][3];
			int age = Integer.parseInt(people[r][4]);
			String state = people[r][5];

			Adult parent1 = null;
			Adult parent2 = null;

			if (age < Adult.ADULT_MIN_AGE) {

				for (int s = 0; s < relations.length; s++) {

					if (relations[s][2].toLowerCase().equals("parent")
							&& (relations[s][0].toLowerCase().equals(name.toLowerCase())
									|| relations[s][1].toLowerCase().equals(name.toLowerCase()))) {

						/*
						 * In relation text file there is no order of child and parent.It could be first
						 * or second and below condition is to handle that
						 */

						if (relations[s][0].toLowerCase().equals(name.toLowerCase())) {
							if (parent1 == null) {
								parent1 = getAdult(relations[s][1]);
							} else {
								parent2 = getAdult(relations[s][1]);
							}

						} else if (relations[s][1].toLowerCase().equals(name.toLowerCase())) {
							if (parent1 == null) {
								parent1 = getAdult(relations[s][0]);
							} else {
								parent2 = getAdult(relations[s][0]);
							}

						}

					}

				}

				if (parent1 != null && parent2 != null) {

					Person P = new Child(name, age, photo, status, state, sex, new ArrayList<Person>(), parent1,
							parent2);
					allProfiles.add(P);
					parent1.addChild(P);
					parent2.addChild(P);

				}

				// add friends relations after creating all default persons

			}

		}

	}

	/*
	 * this method returns the Person from network
	 */
	private Person getPerson(String personName) {
		for (Person psn : allProfiles) {
			if (psn.getName().toLowerCase().equals(personName.trim().toLowerCase())) {
				return psn;
			}
		}
		return null;
	}

	// get adult by name
	private Adult getAdult(String adultName) {

		for (Person psn : allProfiles) {
			if (psn.getName().toLowerCase().equals(adultName.trim().toLowerCase()) && (psn instanceof Adult)) {
				return (Adult) psn;
			}
		}
		return null;
	}

}
