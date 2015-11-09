import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AuctionCentralMain {
	
	public static void main(String theArgs[]) throws FileNotFoundException {
		Calendar calendar = new Calendar();
		File userFile = new File("users.txt");
		PrintStream out = new PrintStream(userFile);

		User user;
		List<User> userList = new ArrayList<User>();
		
		
		List<String> auctionList = new ArrayList<String>();		
//		List<Auction> auctionList = new ArrayList<Auction>();

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
				System.out.println(calendar);
			} else if (option == 2) {
				System.out.println("Enter the number of an auction to view its details.");
				// print out auction list
			}
		} else if(userType == 2) {
			System.out.println("What is the name of your Non-Profit Organization?");
			String NPOname = sc.nextLine();
			user = new NonProfit(userName, User.UserType.NPO, NPOname, 0);
			if(checkLogin(userList, user)) {
				System.out.println("Welcome back, " + userName + "!");
			} else {
				userList.add(user);
			}
			
			System.out.println("\nNon-Profit Organization Staff Member Homepage");
			System.out.println("------------------------------------------------");
			boolean existingAuction = false;
			if(checkAuctions(auctionList, NPOname)) {
				existingAuction = true;
				System.out.println("You have an auction scheduled already.");
				System.out.println("What would you like to do?");
				System.out.println("1. Edit auction information");
				System.out.println("2. Add new inventory items");
				System.out.println("3. Edit inventory items");
			} else {
				System.out.println("What would you like to do?");
				System.out.println("1) Schedule an auction");
			}
			
			option = sc.nextInt();
			
			
			
			switch(option) { 
				
			case 1:
				if(existingAuction) {
					user.ExecuteCommand(User.Command.EDITAUCTION, calendar, null, null);
				} else {
					user.ExecuteCommand(User.Command.ADDAUCTION, calendar, null, null);
				}
				
				// Need a way to get the auction created back to main, so that it can be added to the text file.
				// user.myAuction ? 
				
				break;
			case 2: 
				user.ExecuteCommand(User.Command.ADDITEM, calendar, null, null);
				break;
			case 3: 
				user.ExecuteCommand(User.Command.EDITITEM, calendar, null, null);
			}
		} else if (userType == 3) {
			user = new Bidder(userName, User.UserType.BIDDER);
			if(checkLogin(userList, user)) {
				System.out.println("Welcome back, " + userName + "!");
			} else {
				userList.add(user);
			}
			
			System.out.println("\nBidder Homepage");
			System.out.println("------------------------------------------------");
			System.out.println("What would you like to do?");
			System.out.println("1) View auctions available for bidding");
			System.out.println("2) View/Edit my bids");
			
			option = sc.nextInt();
			
			switch(option) {
				case 1: 
					// print out auction list
					break;
				case 2:
					// print out the list of this bidder's bids
					break;
			}
		}
		
		for(int i = 0; i < userList.size(); i++) {
			out.print(userList.get(i).toString()+"\n");
		}
		
	}
	
	/**
	 * Check the list of users to see if this user has registered already.
	 * 
	 * @param theList
	 * @param theUser
	 * @return true if the user exists, false otherwise
	 */
	public static boolean checkLogin(List<User> theList, User theUser) {
		boolean result = false;
		for(int i = 0; i < theList.size(); i++) {
			if(theList.get(i).getUserName().equals(theUser.getUserName()) && theList.get(i).getUserType().equals(theUser.getUserType())) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Check the list of Auctions to see if the NPO has an auction scheduled already.
	 * 
	 * @param theAuctions
	 * @param theNPOname
	 * @return true if an auction exists, false otherwise
	 */
	public static boolean checkAuctions(List<String> theAuctions, String theNPOname) {
		return theAuctions.contains(theNPOname);
	}
	
}