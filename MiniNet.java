package au.edu.rmit.cosc1295.a1;

public class MiniNet {
	
//Menu system for MiniNet Social Network

	public static void main(String[] args) {
		Driver d = new Driver();
		boolean exit = false;
		while (exit == false) {
			d.printMenu();
			int selection = d.readUserInput();
			System.out.println(selection);
			switch(selection) {
			case 1:
				d.addUser();
				break;
			case 2:
				d.listAll();
				break;
			case 3:
				d.selectUser();
				break;
			case 4:
				d. connectFriends();
				break;
			case 5:
				d. displayFriends();
				break;
			case 6:
				d. displayParents();
				break;
			case 0:
				System.out.println("Goodbye!");
				exit = true;
				break;
			default:
				System.out.println("Unrecognised option.");
			}
		}
	}

}
