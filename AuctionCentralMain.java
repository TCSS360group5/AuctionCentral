import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main controller for AuctionCentral
 * Saves/loads files, runs menu driven I/O
 * 
 * @author Shannon Murphy
 * @version 11/11/2015
 */
public class AuctionCentralMain 
{	
	public static void main(String theArgs[]) 
	{
		Calendar calendar = new Calendar();
		ArrayList<User> userList = new ArrayList<User>();
		ArrayList<Auction> auctionList = new ArrayList<Auction>();
		File userFile = new File("users.txt");
		File auctionFile = new File("auctions.txt");

		loadUsers(userFile, userList);
		loadAuctions(auctionFile, auctionList, calendar);
		
		Scanner userScanner = new Scanner(System.in);
		
		System.out.println("Welcome to AuctionCentral!");
		System.out.print("Please enter your username: ");
		String userName = userScanner.next();
		System.out.println("Hello, " + userName + "!");
		System.out.println("Are you an AuctionCentral employee, a non-profit organization member, or a bidder?");
		System.out.println("1) AuctionCentral Employee\n2) Non-Profit Organization\n3) Bidder");
		
		int userType = userScanner.nextInt();
		if(userType == 1) 
		{
			executeEmployee(userName, userList, calendar, userScanner);
		} 
		else if(userType == 2)
		{
			executeNPO(userName, userList, auctionList, calendar, userScanner);
		} 
		else if (userType == 3)
		{
			executeBidder(userName, userList, userScanner);
		}
		
		outputUsers(userFile, userList);
		outputAuctions(auctionFile, auctionList);
		userScanner.close();
	}
	
	/**
	 * Read in text file of existing users using a Scanner and import them into an ArrayList of users.
	 *  
	 * @param theFile
	 * @param theUserList
	 */
	public static void loadUsers(File theFile, ArrayList<User> theUserList) {
		try 
		{
	        Scanner s = new Scanner(theFile);
	        while (s.hasNext()) {
	        	String userName = s.next();
	        	String userType = s.next();
	        	switch(userType) {
	        	case "EMPLOYEE":
	        		theUserList.add(new Employee(userName, User.UserType.EMPLOYEE));
	        		break;
	        	case "NPO":
	        		theUserList. add(new NonProfit(userName, User.UserType.NPO, "", 0));
	        		break;
	        	case "BIDDER":
        			theUserList.add(new Bidder(userName, User.UserType.BIDDER));
	        	}
	        }
	        s.close();
	    } 
	    catch (FileNotFoundException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Read in text file of existing auctions using a Scanner. Import the auctions into an ArrayList of auctions, and add to Calendar.
	 * 
	 * @param auctionFile
	 * @param auctionList
	 * @param calendar
	 */
	public static void loadAuctions(File auctionFile, ArrayList<Auction> auctionList, Calendar calendar) {
		Map<String, Integer> months = createMonths();	// map to convert month name strings to ints
		try 
		{
	        Scanner s = new Scanner(auctionFile);
	        while (s.hasNext()) {
	        String auctionName = s.next();
	        
	        int j = auctionName.indexOf('-');			// finding the indexes of - to skip over them
	        int k = auctionName.indexOf('-', j+1);
	        int l = auctionName.indexOf('-', k+1);

	        String orgName = auctionName.substring(0, j); // name
	        String month = auctionName.substring(j+1, k); // month
	        int day = Integer.parseInt(auctionName.substring(k+1, l)); // day
	        int year = Integer.parseInt(auctionName.substring(l+1, auctionName.length())); // year
	        
	        int startHour = s.nextInt();
	        int startMinute = s.nextInt();
	        int endHour = s.nextInt();
	        int endMinute = s.nextInt();
	        
	        String item = s.next();
	        
	        auctionList.add(new Auction(orgName, auctionName, LocalDateTime.of(year, months.get(month), day, startHour, startMinute), 
	        		LocalDateTime.of(year, months.get(month), day, endHour, endMinute)));
	        
	        calendar.addAuction(orgName, LocalDateTime.of(year, months.get(month), day, startHour, startMinute), 
	        		LocalDateTime.of(year, months.get(month), day, endHour, endMinute));
	        }
	        s.close();
	    } 
	    catch (FileNotFoundException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Check the list of users to see if this user has registered already.
	 * 
	 * @param theList
	 * @param theUser
	 * @return true if the user exists, false otherwise
	 */
	public static boolean checkLogin(List<User> theList, User theUser) 
	{
		boolean result = false;
		for(int i = 0; i < theList.size(); i++) 
		{
			if(theList.get(i).getUserName().equals(theUser.getUserName()) && theList.get(i).getUserType().equals(theUser.getUserType()))
			{
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
	public static boolean checkAuctions(List<Auction> theAuctions, String theNPOname) 
	{
		boolean result = false;
		for(int i = 0; i < theAuctions.size(); i++) {
			if(theAuctions.get(i).getAuctionOrg().equals(theNPOname)) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Runs the Employee menu.
	 * 
	 * @param userName
	 * @param userList
	 * @param theCalendar
	 * @param theScanner
	 */
	public static void executeEmployee(String userName, ArrayList<User> userList, Calendar theCalendar, Scanner theScanner) {
		User user = new Employee(userName, User.UserType.EMPLOYEE);
		if(checkLogin(userList, user))
		{
			System.out.println("Welcome back, " + userName + "!");
		} 
		else 
		{
			userList.add(user);
		}
		System.out.println("\nAuctionCentral Employee Homepage");
		System.out.println("----------------------------------");
		System.out.println("What would you like to do?");
		System.out.println("1) View calendar");
		System.out.println("2) View auctions");
		
		int option = theScanner.nextInt();
		if (option == 1) 		
		{
			System.out.println(theCalendar);
		} 
		else if (option == 2) 
		{
			System.out.println("Enter the number of an auction to view its details.");
			// print out auction list
		}
	}
	
	/**
	 * Runs the NPO menu.
	 * 
	 * @param theUserName
	 * @param theUserList
	 * @param theAuctionList
	 * @param theCalendar
	 * @param theScanner
	 */
	public static void executeNPO(String theUserName, ArrayList<User> theUserList, ArrayList<Auction> theAuctionList, Calendar theCalendar, Scanner theScanner) {
		int option;
		System.out.println("What is the name of your Non-Profit Organization?");
		String NPOname = theScanner.next();
		User user = new NonProfit(theUserName, User.UserType.NPO, NPOname, 0);
		if(checkLogin(theUserList, user)) 
		{
			System.out.println("Welcome back, " + theUserName + "!");
		} 
		else 
		{
			theUserList.add(user);
		}
		
		System.out.println("\nNon-Profit Organization Staff Member Homepage");
		System.out.println("------------------------------------------------");
		boolean existingAuction = false;
		if(checkAuctions(theAuctionList, NPOname)) 
		{
			existingAuction = true;
			System.out.println("You have an auction scheduled already.");
			System.out.println("What would you like to do?");
			System.out.println("1. Edit auction information");
			System.out.println("2. Add new inventory items");
			System.out.println("3. Edit inventory items");
		} 
		else 
		{
			System.out.println("What would you like to do?");
			System.out.println("1) Schedule an auction");
		}
		
		option = theScanner.nextInt();
		
		switch(option) 
		{ 
			
		case 1:
			if(existingAuction) 		// the NPO has an auction already, so edit
			{
				user.ExecuteCommand(User.Command.EDITAUCTION, theCalendar, null, null);
			}
			else 				// NPO doesn't have an auction so add one
			{							
				user.ExecuteCommand(User.Command.ADDAUCTION, theCalendar, null, null);
				Collection<ArrayList<Auction>> auctions = theCalendar.myAuctionByDateList.values();
				Iterator it = auctions.iterator();
				while(it.hasNext()) {
					ArrayList<Auction> a = (ArrayList<Auction>) it.next();
					for(int i = 0; i < a.size(); i++) {
						Auction auction = a.get(i);
						theAuctionList.add(auction);
					}
				}
			}
			break;
		case 2: 
			user.ExecuteCommand(User.Command.ADDITEM, theCalendar, null, null);
			break;
		case 3: 
			user.ExecuteCommand(User.Command.EDITITEM, theCalendar, null, null);
		}
	}
	
	/**
	 * Runs the Bidder menu.
	 * 
	 * @param theUserName
	 * @param theUserList
	 * @param theScanner
	 */
	public static void executeBidder(String theUserName, ArrayList<User> theUserList, Scanner theScanner) {
		int option;
		User user = new Bidder(theUserName, User.UserType.BIDDER);
		if(checkLogin(theUserList, user)) 
		{
			System.out.println("Welcome back, " + theUserName + "!");
		}
		else 
		{
			theUserList.add(user);
		}
		
		System.out.println("\nBidder Homepage");
		System.out.println("------------------------------------------------");
		System.out.println("What would you like to do?");
		System.out.println("1) View auctions available for bidding");
		System.out.println("2) View/Edit my bids");
		
		option = theScanner.nextInt();
		
		switch(option)
		{
			case 1: 
				// print out auction list
				break;
			case 2:
				// print out the list of this bidder's bids
				break;
		}
	}
	
	/**
	 * Prints the existing users to the users.txt file.
	 * 
	 * @param theUserFile
	 * @param theAuctionFile
	 * @param theUserList
	 * @param theAuctionList
	 */
	public static void outputUsers(File theUserFile, ArrayList<User> theUserList) {
		PrintStream outputUsers = null;		// to write users to file
		try 
		{
			outputUsers = new PrintStream(theUserFile);
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i = 0; i < theUserList.size(); i++) 
		{
			outputUsers.print(theUserList.get(i).toString()+"\n");
		}
		outputUsers.close();
	}
	
	/**
	 * Outputs the existing auctions to the auctions.txt file.
	 * 
	 * @param theAuctionFile
	 * @param theAuctionList
	 */
	public static void outputAuctions(File theAuctionFile, ArrayList<Auction> theAuctionList) {
		PrintStream outputAuctions = null;	// to write auctions to file
		
		try 
		{
			outputAuctions = new PrintStream(theAuctionFile);
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i = 0; i < theAuctionList.size(); i++)
		{
			Auction auction = theAuctionList.get(i);
			outputAuctions.println(auction.getAuctionName()); // orgnameMonth-day-year
//			outputAuctions.println("Organization: " + auction.getAuctionOrg()); 
			outputAuctions.println(auction.getStartTime().getHour() + " " + auction.getStartTime().getMinute());
			outputAuctions.println(auction.getEndTime().getHour() + " " + auction.getEndTime().getMinute());
			outputAuctions.println(auction.getAuctionItems());
		}
		outputAuctions.close();
	}
	
	/**
	 * Creates a Map of month names to their corresponding integer values.
	 * Useful for converting file input.
	 * 
	 * @return
	 */
	public static HashMap<String, Integer> createMonths() {
		HashMap<String, Integer> months = new HashMap<String, Integer>();
		months.put("January", 1);
		months.put("February", 2);
		months.put("March", 3);
		months.put("April", 4);
		months.put("May", 5);
		months.put("June", 6);
		months.put("July", 7);
		months.put("August", 8);
		months.put("September", 9);
		months.put("October", 10);
		months.put("November", 11);
		months.put("December", 12);
		return months;
	}
	
}
