import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AuctionCentralMain {
	
	public static void main(String theArgs[]) throws FileNotFoundException {
//		Calendar calendar = new Calendar();
		File userFile = new File("users.txt");
		PrintStream out = new PrintStream(userFile);

		User user;
		List<User> userList = new ArrayList<User>();
		
		List<Auction> auctionList = new ArrayList<Auction>();

		try {
	        Scanner s = new Scanner(userFile);
	        while (s.hasNext()) {
//	        	userList.add(s.nextLine());
	        	String userName = s.next();
	        	String userType = s.next();
	        	switch(userType) {
	        	case "EMPLOYEE":
	        		userList.add(new Employee(userName, User.UserType.EMPLOYEE));
	        		break;
	        	case "NPO":
	        		userList. add(new NonProfit(userName, User.UserType.NPO, "", 0));
	        		break;
	        	case "BIDDER":
        			userList.add(new Bidder(userName, User.UserType.BIDDER));
	        	}
	        }
	        s.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
				
//		List<Auction> auctionList = new ArrayList<Auction>();
//		import Auctions from text file into this list.
		
		int option = 0;
		
		System.out.println("Welcome to AuctionCentral!");
		System.out.print("Please enter your username: ");		// check to see if already in system
		Scanner sc = new Scanner(System.in);
		String userName = sc.next();
		System.out.println("Hello, " + userName + "!");
		System.out.println("Are you an AuctionCentral employee, a non-profit organization member, or a bidder?");
		System.out.println("1) AuctionCentral Employee\n2) Non-Profit Organization\n3) Bidder");
		
		int userType = sc.nextInt();
		if(userType == 1) {
			user = new Employee(userName, User.UserType.EMPLOYEE);
			if(checkLogin(userList, user)) {
				System.out.println("Welcome back, " + userName + "!");
			} else {
				userList.add(user);
			}
			System.out.println("\nAuctionCentral Employee Homepage");
			System.out.println("----------------------------------");
			System.out.println("What would you like to do?");
			System.out.println("1) View calendar");
			System.out.println("2) View auctions");
			
			option = sc.nextInt();
			if (option == 1) {
//				System.out.println(calendar);
			} else if (option == 2) {
				System.out.println("Enter the number of an auction to view its details.");
				// print out auction list
			}
		} else if(userType == 2) {
			user = new NonProfit(userName, User.UserType.NPO, "", 0);
			if(checkLogin(userList, user)) {
				System.out.println("Welcome back, " + userName + "!");
			} else {
				userList.add(user);
			}
			System.out.println("\nNon-Profit Organization Staff Member Homepage");
			System.out.println("------------------------------------------------");
			System.out.println("What would you like to do?");
			System.out.println("1) Schedule a new auction");
			System.out.println("2) Edit auction information");
			System.out.println("3) Add new inventory items");
			System.out.println("4) Edit inventory items");
			
			option = sc.nextInt();
			
			if(option == 1) { 
				System.out.println("\nAdd new auction");
				System.out.println("------------------------------------------------");
				System.out.print("Non-Profit Organization name: ");
				String NPOname = sc.next();
				System.out.print("Date of auction: \nMonth name: ");
				String dateMonth = sc.next();
				System.out.print("Day (DD): ");
				int dateDay = sc.nextInt();
				System.out.print("Year (YYYY): ");
				int dateYear = sc.nextInt();
				
				// check the database of auctions to see if this non-profit already has an auction scheduled.
				// if not, create new auction with the info and add to list
				// Auction auction = new Auction(NPOname, dateMonth, dateDay, dateYear);
				System.out.println("Scheduled a new auction for " + NPOname + " on " + dateMonth + " " + dateDay + ", " + dateYear);
			}
		} else if (userType == 3) {
			user = new Bidder(userName, User.UserType.BIDDER);
			if(checkLogin(userList, user)) {
				System.out.println("Welcome back, " + userName + "!");
			} else {
				userList.add(user);
			}
		}
		
		for(int i = 0; i < userList.size(); i++) {
			out.print(userList.get(i).toString()+"\n");
		}
		
	}
	
	public static boolean checkLogin(List<User> theList, User theUser) {
		boolean result = false;
		for(int i = 0; i < theList.size(); i++) {
			if(theList.get(i).getUserName().equals(theUser.getUserName()) && theList.get(i).getUserType().equals(theUser.getUserType())) {
				result = true;
			}
		}
		return result;
	}
}