package au.edu.rmit.cosc1295.a1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Driver {

	private Scanner input;

	private Map<Integer, Person> users;

	public Driver() {
		input = new Scanner(System.in);
		users = new HashMap<Integer, Person>();
		
		//Entering demo users

		Person alice = new Person ("Alice", "Smith", "03/05/1980", "adult", 0, 0);
		alice.addFriend(2);
		alice.addFriend(4);
		users.put(1, alice);

		Person bob = new Person ("Bob", "Jones", "23/06/1983", "adult", 0, 0);
		bob.addFriend(2);
		bob.addFriend(4);
		users.put(2, bob);

		users.put(3, new Person ("Cathy", "Albertson", "14/12/1972", "adult", 0, 0));
		users.put(4, new Person ("Don", "Richards", "15/07/1964", "adult", 0, 0, "http://images.google.com/popcorn/",
				"Please like my status."));
		users.put(5, new Person ("Eddie", "Murphy", "01/01/2015", "child", 1, 2));
		users.put(6, new Person ("Jemma", "Louise", "01/09/2008", "child", 3, 4));
		users.put(7, new Person ("George", "Jean", "09/11/2017", "infant", 1, 2));
	}
	
	//Display Menu 

	public void printMenu() {
		System.out.println("1. Add a person to the network.");
		System.out.println("2. List all people on network.");
		System.out.println("3. Select a User and Display that Person's Profile.");
		System.out.println("4. Connect friends through the network.");
		System.out.println("5. Display list of friends.");
		System.out.println("6. Display a child's parents.");
		System.out.println("0. Exit program.");
		System.out.println("   ");
		System.out.println("Enter a selection: ");
	}
	
	//Method to read number input from user

	public int readUserInput() {
		int selection = input.nextInt();
		return selection;
	}
	
	//Method to add any type of user to network

	public void addUser() {

		System.out.println("ADD NEW USER: ");

		System.out.println("Enter First Name: ");
		String firstName = input.next();

		System.out.println("Enter Surname: ");
		String surname = input.next();

		System.out.println("Please enter your date of birth (in format dd/mm/yyyy): ");

		String dob = input.next();

		// Declare date format
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dob, dateFormatter);
		String dobFormatted = date.format(dateFormatter);

		System.out.println("Date of Birth: " + dobFormatted);

		LocalDate end = LocalDate.now(); // use for age-calculation
		int age = (int) ChronoUnit.YEARS.between(date, end);
		System.out.println("Age: " + age);

		int userId = users.size() + 1;

		Person p = null;
		if (age >= 16) { //To create Adult Profile

			System.out.println("----------------------------");
			System.out.println("You are creating an Adult Profile.");
			p = new Person (firstName, surname, dob, "adult", 0, 0);

		} else if (age < 16) { //To create Child or Infant Profile

			System.out.println("----------------------------");
			System.out.println("You are creating a Child or Infant Profile. ");

			listAll();

			int parentID1;
			int parentID2;

			Person p1;
			Person p2;

			do {
				System.out.println("Please enter your first parent User ID: ");
				parentID1 = input.nextInt();
				p1 = users.get(parentID1);
			} while (!(p1.getProfileType() == "adult")); //Error checking both parents are adults

			do {
				System.out.println("Please enter your second parent User ID: ");
				parentID2 = input.nextInt();
				p2 = users.get(parentID1);
			} while (!(p2.getProfileType() == "adult")); 
			if (p1.hasFriend(p2.getProfileID()) && (p2.hasFriend(p1.getProfileID()))) { //Error checking both parents are friends
				if (age > 2) {
					p = new Person(firstName, surname, dob, "child",  parentID1, parentID2);
				} else {
					p = new Person(firstName, surname, dob, "infant", parentID1, parentID2);
				}
			} else {
				System.out.println("Please make sure the parents are friends!");
			}
		}
		users.put(userId, p);
	}

	//List all users on the network.
	
	public void listAll() {
		for (Object k : users.keySet()) {
			users.get((int) k).printShortProfile();
		}
	}
	
	//Select one user and print their profile.

	public void selectUser() {
		listAll();
		System.out.println("Please input User Profile Number: ");
		int UserNumber = input.nextInt();
		users.get(UserNumber).printProfile();
	}
	
	
	//Connect two people as friends on the network.

	public void connectFriends() {
		listAll();
		System.out.println("Select first User to connect: ");
		int userID1 = input.nextInt();
		System.out.println("Select second User to connect: ");
		int userID2 = input.nextInt();

		Person user1 = users.get(userID1);
		Person user2 = users.get(userID2);

		if (user1.getProfileType() == "infant" || user2.getProfileType() == "infant") {
			System.out.println("Infants cannot make connections on this network.");
		} else if (user1.getProfileType() == "adult" && user2.getProfileType() == "adult") {
			users.get(userID1).addFriend(userID2);
			users.get(userID2).addFriend(userID1);

			// Children must be within 3 years of age of each other to be friends.
		} else if (user1.getProfileType() == "child" && user2.getProfileType() == "child") {
			if (Math.abs(user1.getAge() - user2.getAge()) < 3) {
				users.get(userID1).addFriend(userID2);
				users.get(userID2).addFriend(userID1);
			}

		}

	}
	
	//Display user's friends 

	public void displayFriends() {
		listAll();
		System.out.println("Select User: ");
		int user1 = input.nextInt();
		for (int userID : users.get(user1).getFriends()) {
			users.get(userID).printShortProfile();
		}
	}
	
	//List an adult's children
	
	public void listAllChildren() {
		for (int userID : users.keySet()) {
			Person user = users.get(userID);
			if (user.getProfileType() == "child") {
				users.get(userID).printShortProfile();
			}
		}
	}
	
	//Display a child's parents
	
	public void displayParents() {
		listAllChildren();
		System.out.println("Select (Child) User : ");
		int user1 = input.nextInt();
		int parent1 = users.get(user1).getParent1();
		int parent2 = users.get(user1).getParent2();
		users.get(parent1).printShortProfile();
		users.get(parent2).printShortProfile();	
		
		
	}

}
