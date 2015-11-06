import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class AuctionCentralMain {
	public static void main(String theArgs[]) {
//		List<Auction> auctionList = new ArrayList<Auction>();
//		import Auctions from text file into this list.
//		while(text file isnt empty) Auction auction = new Auction() auctionList.add(auction)
		
		List<User> userList = new ArrayList<User>();
//		import users from stored text file of registered users
		
		
		int option = 0;
		
		System.out.println("Welcome to AuctionCentral!");
		System.out.print("Please enter your username: ");
		Scanner sc = new Scanner(System.in);
		String userName = sc.next();
		System.out.println("Hello, " + userName + "!");
		System.out.println("Are you an AuctionCentral employee, a non-profit organization member, or a bidder?");
		System.out.println("1) AuctionCentral Employee\n2) Non-Profit Organization\n3) Bidder");
		
		int userType = sc.nextInt();
		User user = new User(userName, userType);
		
		if(user.myUserType == "AuctionCentral") {
			System.out.println("\nAuctionCentral Employee Homepage");
			System.out.println("----------------------------------");
			System.out.println("What would you like to do?");
			System.out.println("1) View calendar");
			System.out.println("2) View auctions");
			
			option = sc.nextInt();
			if (option == 1) {
				// print out calendar
			} else if (option == 2) {
				// print out auction list
			}
		} else if(user.myUserType == "NPO") {
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
		}
		
	}
}