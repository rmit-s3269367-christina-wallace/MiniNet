package au.edu.rmit.cosc1295.a1;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Person {

	public static int counter;
	private final int PROFILEID;
	
	private String _FirstName;
	private String _Surname;
	private String _DOB;
	private String _ImageURL;
	private String _Status;
	
	private Set<Integer> friends;
	
	private String profileType;

	private int parent1;
	private int parent2;

	//Constructor
	
	public Person (String firstName, String surname, String dob, String profileType, int parent1, int parent2) {
		_FirstName = firstName;
		_Surname = surname;
		_DOB = dob;
		PROFILEID = ++counter;
		friends = new HashSet<Integer>();  //Friends hashmap
		this.profileType = profileType;
		
		if (profileType == "child" || profileType == "infant") {
			this.parent1 = parent1;
			this.parent2 = parent2;
		}
	}
	
	//Overloaded constructor
	
	public Person (String firstName, String surname, String dob, String profileType, int parent1, int parent2, String imageURL, String status) {
		_FirstName = firstName;
		_Surname = surname;
		_DOB = dob;
		this.profileType = profileType;
		_ImageURL = imageURL;
		_Status = status;
		PROFILEID = ++counter;
		friends = new HashSet<Integer>();
		
		if (profileType == "child" || profileType == "infant") {
			this.parent1 = parent1;
			this.parent2 = parent2;
		}
	}
	
	public void addFriend (int userID) {
		friends.add(userID);
	}
	
	public Set<Integer> getFriends() {
		return friends;
	}
	
	public boolean hasFriend(int userID) {
		return friends.contains(userID);
	}
	
	//Accessors

	public String getFirstName() {
		return _FirstName;
	}

	public String getSurname() {
		return _Surname;
	}

	public String getDOB() {
		return _DOB;
	}

	public String getImageURL() {
		return _ImageURL;
	}
	
	public String getStatus() {
		return _Status;
	}
	
	public int getProfileID() {
		return PROFILEID;
	}
	
	public String getProfileType() {
		return profileType;
	}

	public int getParent1() {
		return parent1;
	}
	
	public int getParent2() {
		return parent2;
	}
	
public int getAge() {
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(getDOB(), dateFormatter);
		String dobFormatted = date.format(dateFormatter);
		LocalDate end = LocalDate.now(); // use for age-calculation
		int age = (int) ChronoUnit.YEARS.between(date, end);
		return age;
	}
	
	public void printShortProfile() { 
		System.out.println(getProfileID() + " " + getFirstName() + " " + getSurname());
	}

	public void printProfile() {
		System.out.println("Display Profile: ");
		System.out.println("Name: " + getFirstName() + " " + getSurname());
		System.out.println("DOB: " + getDOB());
		System.out.println("Profile ID: " + getProfileID());
	}

	//Modifiers	to be implemented.
	
	public void updateFirstName(String firstName) {
		_FirstName = firstName;
	}
	
	public void updateSurname(String surname) {
		_Surname = surname;
	}
	
	public void updateImageURL(String imageURL) {
		_ImageURL = imageURL;
	}
	
	public void updateStatus(String status) {
		_Status = status;
	}
	
	
}