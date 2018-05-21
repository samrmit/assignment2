
/**
 * Class: Driver
 * Author: s3682248
 * Date: 18th May 2018
 * Version 1
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

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		Button mbtnAddPsn = new Button("Add New Person");
		mbtnAddPsn.setOnAction(e -> window.setScene(scene2));
		GridPane.setConstraints(mbtnAddPsn, 0, 0);
		Button mbtnDelPsn = new Button("Delete Selected Person from Network");
		mbtnDelPsn.setOnAction(e -> deletePerson());

		Label lbSelect = new Label("Select a person");
		GridPane.setConstraints(lbSelect, 0, 1);
		choiceBox = new ChoiceBox<>();

		updatePersonList();

		GridPane.setConstraints(choiceBox, 1, 1);
		choiceBox.setOnAction(e -> getChoice());

		Button mbtnAddFrnd = new Button("Make Below Person A Friend");

		mbtnAddFrnd.setOnAction(e -> addNewFriend());

		grid.getChildren().addAll(mbtnAddPsn, lbSelect, choiceBox, mbtnAddFrnd);

		FlowPane pane = new FlowPane(Orientation.VERTICAL, 5, 10);// hGap=5,vGap = 5
		pane.setPadding(new Insets(11, 12, 13, 14));

		pane.getChildren().add(grid);

		lbPersonInfo = new Label();
		pane.getChildren().add(lbPersonInfo);

		pane.getChildren().add(mbtnDelPsn);
		pane.getChildren().add(mbtnAddFrnd);

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

	private void getChoice() {

		curPerson = getPerson(choiceBox.getValue());
		info = "Name: " + curPerson.getName() + "\n\r" + "Age: " + String.valueOf(curPerson.getAge()) + "\n\r"
				+ "Status: " + curPerson.getStatus() + "\n\r" + "State: " + curPerson.getState() + "\n\r" + "Friends: "
				+ curPerson.getFriendList() + "\n\r";

		if (curPerson instanceof Adult) {
			info = info + "Childeren: " + ((Adult) curPerson).showChildren() + "\n\r";
		}

		if (curPerson instanceof Dependent) {
			info = info + "Parent 1 : " + ((Dependent) curPerson).getParentName1() + "\n\r";
			info = info + "Parent 2 : " + ((Dependent) curPerson).getParentName2() + "\n\r";
		}

		lbPersonInfo.setText(info);

		choiceBoxFr.getItems().clear();

		for (int i = 0; i < allProfiles.size(); i++) {
			if (curPerson != allProfiles.get(i)) {
				choiceBoxFr.getItems().add(allProfiles.get(i).getName());
			}

		}

	}

	private void deletePerson() {
		
		if (choiceBox.getValue() == null) {
			lbSystemInfo.setText("Please select a person to delete.");
		} else {
			// Some action to implement to remove the selected person from friend lists
			// before delete
			// allProfiles.remove(getPerson(choiceBox.getValue()));
			lbSystemInfo.setText("This action is not yet implemented.");
		}

	}

	private void addNewPerson() {

		// this function is not completed and need to do age check and other validations
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

	private void addNewFriend() {

		if (choiceBoxFr.getValue() == null) {
			lbSystemInfo.setText("Please select a person from above check box.");
		} else {

			try {
				curPerson.addedTo(getPerson(choiceBoxFr.getValue()));
				lbSystemInfo.setText("Congratulations: " + choiceBoxFr.getValue() + " was added as a Friend: ");
				getChoice();

			} catch (NotToBeFriendsException nbe) {
				lbSystemInfo.setText("Error: " + nbe.getMessage());
			} catch (TooYoungException tye) {
				lbSystemInfo.setText("Error: " + tye.getMessage());
			}
		}

	}

	/*
	 * this method triggers the initial step of the program run
	 */
	private void topLevelHandler() {

		int opt1 = getMainMenu(sc);
		menuAction(opt1);

	}

	/*
	 * this methods handle top level menu actions.
	 */
	private void menuAction(int option) {

		switch (option) {
		case 1: { // list everyone
			displayNet();
			topLevelHandler();
			break;
		}
		case 2: { // select a person

			selectPerson(option);
			break;
		}
		case 3: { // Check given two users are friends
			checkFriends(option);
			break;
		}

		case 4: { // exit program
			System.out.println("\nYou quit the MiniNet");
			System.exit(0);
		}
		}
	}

	/*
	 * this method handles second level menu actions (after selecting a user).
	 */
	private void subMenuAction() {
		
		System.out.print("\nCurrent user: " + curPerson.getName());
		int choice = getSubMenu(sc);
		if (choice == 1) {
			System.out.println("\n--------------------------");
			curPerson.viewDetails();
			System.out.println("\n--------------------------");
			subMenuAction();

		} else if (choice == 2) { // Add a friend

			try {
				addFriend();
			} catch (Exception e) {
				System.err.println(e.getMessage());

			}

		} else if (choice == 3) { // Delete a friend
			deleteFriend();

		} else if (choice == 4) { // Show details of friend
			showFriendDetails();

		} else if (choice == 5) { // Update Status
			updateStatus();

		} else if (choice == 6) { // Update Image
			updateImage();

		} else if (choice == 7) { // go to top menu
			topLevelHandler();

		} else if (choice == 8) { // exit program
			menuAction(4);
		}
		
	}

	/*
	 * this method handles adding a friend actions for the selected user.
	 */
	private void addFriend() {
		displayNet();
		System.out.println("Select a name you want to make friend with " + curPerson.getName());
		String nameAdd = sc.next();

		// Person newPerson1 = getPerson(nameAdd);

		if (curPerson == getPerson(nameAdd)) {
			System.out.println("You must select a person other than " + curPerson.getName());
			subMenuAction();
		}

		if (getPerson(nameAdd) != null) {

			try {
				curPerson.addedTo(getPerson(nameAdd));
			} catch (NotToBeFriendsException nbe) {
			} catch (TooYoungException tye) {
			}

			System.out.println("Current friends of " + curPerson.getName());
			System.out.println(curPerson.showFriends());

			subMenuAction();

		} else {
			System.out.println("Sorry, " + nameAdd + " is not a valid network user. Enter a name from below list.");
			subMenuAction();

		}

	}

	/*
	 * this method handles removing a friend actions for the selected user.
	 */
	private void deleteFriend() {

		System.out.println("friends of " + curPerson.getName() + " : " + curPerson.showFriends());
		System.out.println("Enter the name of the friend you want to remove ");
		String nameDel = sc.next();

		if (curPerson == getPerson(nameDel)) {
			System.out.println("You must select a person other than " + curPerson.getName());
			subMenuAction();
		}

		if (getPerson(nameDel) != null) {

			curPerson.deletedFrom(getPerson(nameDel));

			/*
			 * if (curPerson.deletedFrom(newPerson2)) { updateAllProfilesList(curPerson);
			 * updateAllProfilesList(newPerson2); }
			 */

			System.out.println("Current friends of " + curPerson.getName());
			System.out.println(curPerson.showFriends());
			subMenuAction();

		} else {
			System.out.println("Sorry, " + nameDel + " is not a valid network user.");
			subMenuAction();
		}
	}

	/*
	 * this method updates current user status.
	 */
	private void updateStatus() {
		System.out.println("Enter the new status for " + curPerson.getName());
		String newStatus = sc.next();
		curPerson.setStatus(newStatus);
		// updateAllProfilesList(curPerson);
		subMenuAction();

	}

	/*
	 * this method updates current user image.
	 */
	private void updateImage() {
		System.out.println("Enter the new image name for " + curPerson.getName());
		String newImage = sc.next();
		curPerson.setImage(newImage);
		// updateAllProfilesList(curPerson);
		subMenuAction();

	}

	private void showFriendDetails() {
		System.out.println("Friends of " + curPerson.getName() + " : " + curPerson.showFriends());
		System.out.println("Enter a friend's name to get details");
		String nameLook = sc.next();

		Person newPerson3 = getPerson(nameLook);

		if (newPerson3 != null) {
			if (curPerson.isFriendOf(newPerson3)) {
				System.out.println("\n--------------------------");
				newPerson3.viewDetails();
				System.out.println("\n--------------------------");
			} else {
				System.out.println("Entered user is not a friend of " + curPerson.getName());
			}
		} else {
			System.out.println("Not a valid user.");
		}
		subMenuAction();
	}

	/*
	 * this method lists all users in the network.
	 */
	private void displayNet() {
		System.out.println("All Profiles\n====================" + this.allProfiles.size());

		for (int i = 0; i < allProfiles.size(); i++) {
			String userType = null;

			if (allProfiles.get(i) instanceof Adult) {
				userType = "Adult";
			} else {
				userType = "Dependant";
			}

			System.out.println("Name: " + allProfiles.get(i).getName() + " (Type: " + userType + " )");
		}
	}

	/*
	 * this method lists all users in the network.
	 */
	private void checkFriends(int option) {
		displayNet();
		System.out.println("Enter two names (you will be prompted twice to enter two names) :");
		System.out.println("Enter name of first user :");
		String f1 = sc.next();
		Person P1 = getPerson(f1);

		if (P1 != null) {
			System.out.println("Enter name of second user :");
			String f2 = sc.next();
			Person P2 = getPerson(f2);
			if (P2 != null) {

				if (P1.isFriendOf(P2)) {
					System.out.println(P1.getName() + " and " + P2.getName() + " are friends.");
				} else {
					System.out.println(P1.getName() + " and " + P2.getName() + " are not friends.");

				}
				topLevelHandler();
			} else {
				System.out.println("Invalid name :");
				menuAction(option);
			}

		} else {
			System.out.println("Invalid name :");
			menuAction(option);
		}

	}

	/*
	 * this method select a user from the network based on the entered name.
	 */
	private void selectPerson(int option) {
		displayNet();
		System.out.println("Select a person by entering a name from above list:");
		String name = sc.next();
		curPerson = getPerson(name);

		if (curPerson != null) {
			System.out.println("You selected " + curPerson.getName());
			subMenuAction();

		} else {
			System.out.println("Invalid name:");
			menuAction(option);

		}
	}

	/*
	 * this method lists main menu options and get user entries.
	 */
	private static int getMainMenu(Scanner sc) {
		int opt1 = 0;
		boolean done1 = false;
		String mainMenu[] = { "\nMiniNet Menu", "================================= ", "1. List everyone",
				"2. Select a person (Sub menu will appear after selecting a Person))",
				"3. Check given two users are friends ", "4. Exit" };

		for (int i = 0; i < mainMenu.length; i++)
			System.out.println(mainMenu[i]);
		System.out.print("\nEnter an option from 1,2,3 and 4: ");

		do {
			try {
				opt1 = sc.nextInt();
				if (opt1 <= 0 || opt1 > 4)
					System.out.print("\nInvalid input. Your option must be between 1-4.\nEnter an option: ");
				else
					done1 = true;
			} catch (InputMismatchException ex) {
				System.out.print("\nInvalid input. Your option must be an Integer. \nEnter an option: ");
				sc.nextLine();
			}
		} while (!done1);

		return opt1;
	}

	/*
	 * this method lists sub menu options and get user entries.
	 */
	private static int getSubMenu(Scanner sc) {
		int opt2 = 0;
		boolean done1 = false;

		String menu[] = { "\nMiniNet Sub-Menu", "=====================", "1. Show user details", "2. Add a friend",
				"3. Unfriend a friend ", "4. Show selected friend's details", "5. Update Status", "6. Update image",
				"7. Go to main menu", "8. Exit" };

		for (int i = 0; i < menu.length; i++)
			System.out.println(menu[i]);
		System.out.print("\nEnter an option from 1 to 8: ");

		do {
			try {
				opt2 = sc.nextInt();
				if (opt2 <= 0 || opt2 > 8)
					System.out.print("\nInvalid input. Your option must be between 1-8.\nEnter an option: ");
				else
					done1 = true;
			} catch (InputMismatchException ex) {
				System.out.print("\nInvalid input. Your option must be an Integer. \nEnter an option: ");
				sc.nextLine();
			}
		} while (!done1);

		return opt2;
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
		System.out.print(Arrays.deepToString(relations));

		// creating Adults
		for (int r = 0; r < people.length; r++) {
			String name = people[r][0];
			String photo = people[r][1];
			String status = people[r][2];
			String sex = people[r][3];
			int age = Integer.parseInt(people[r][4]);
			String state = people[r][5];

			if (age >= Person.adultMinAge) {

				Person P = new Adult(name, age, photo, status, state, sex, new ArrayList<Person>(),
						new ArrayList<Person>());
				allProfiles.add(P);

			}

		}

		this.createDependents(people, relations);

		// add friends relations
		for (int f = 0; f < relations.length; f++) {
			if (relations[f][2].toLowerCase().equals("friends")) {

				Person Psn1 = getPerson(relations[f][0]);
				Person Psn2 = getPerson(relations[f][1]);

				if (Psn1 != null && Psn2 != null) {

					try {
						Psn1.addedTo(Psn2);
					} catch (Exception e) {

					}

				}
			}

		}
  

		//allProfiles.get(6).viewDetails();
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

			if (age < Person.adultMinAge) {

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

					Person P = new Dependent(name, age, photo, status, state, sex, new ArrayList<Person>(), parent1,
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

	/*
	 * this method updates modified Person in the network
	 */
	private void updateAllProfilesList(Person P) {

		for (int i = 0; i < allProfiles.size(); i++) {

			if (allProfiles.get(i).getName().toLowerCase().equals(P.getName())) {
				allProfiles.set(i, P);
				break;
			}
		}

	}

}
