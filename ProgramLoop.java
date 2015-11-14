import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class ProgramLoop {
	private static final String myACFileName = "AuctionCentral/";
	private static final String myUserFile = myACFileName + "users.txt";
	private static final String myAuctionFile = myACFileName + "auction.txt";
	
	private User myUser;
	private Calendar myCalendar;
	private ArrayList<User> myUserList;
	private ArrayList<Auction> myAuctionList;
	private Scanner myScanner;
	private String myHomePageMessage;
	private User.Command myCurrentState;
	private Auction myCurrentAuction;
	private Item myCurrentItem;
	private Map<String, Integer> myMonths;
	
	//region Strings
	private static final String myHomePageMessageEnd = " Homepage\n"
		+ "----------------------------------\n"
		+ "What would you like to do?\n";
	//endregion
	
	public ProgramLoop()
	{
		myCurrentState = User.Command.VIEWMAINMENU;
		myCalendar = new Calendar();
		myUserList = new ArrayList<User>();
		myAuctionList = new ArrayList<Auction>();
		myMonths = createMonths();
		myScanner = new Scanner(System.in);
	}
	
	public void startProgram() 
	{
		//Calendar calendar = new Calendar();
		//Scanner myScanner = new Scanner(System.in);
		
		//if the file doesn't exist, it creates it
		if (!(new File(myUserFile).isFile())) 
		{
			outputUsers(new File(myUserFile), myUserList);
		}
		if (!(new File(myAuctionFile).isFile())) 
		{
			outputAuctions(new File(myAuctionFile), myAuctionList);
		}
		
		File userFile = new File(myUserFile);
		File auctionFile = new File(myAuctionFile);

		loadUsers(userFile, myUserList);
		loadAuctions(auctionFile, myAuctionList, myCalendar);
		
		
		
		System.out.println("Welcome to AuctionCentral!");
		System.out.print("Please enter your username: ");
		String userName = myScanner.next();
		System.out.println("Hello, " + userName + "!");
		System.out.println("Are you an AuctionCentral employee, a non-profit organization member, or a bidder?");
		System.out.println("1) AuctionCentral Employee\n2) Non-Profit Organization\n3) Bidder");
		
		int userType = myScanner.nextInt();
		myScanner.nextLine();
		if(userType == 1) 
		{
			myUser = new Employee(userName, User.UserType.EMPLOYEE);
		} 
		else if(userType == 2)
		{
			System.out.println("What is the name of your Non-Profit Organization?");
			String NPOname = myScanner.nextLine();
			System.out.println("Your NPO is " + NPOname);
			myUser = new NonProfit(userName, User.UserType.NPO, NPOname, 0);
		} 
		else if (userType == 3)
		{
			myUser = new Bidder(userName, User.UserType.BIDDER);
		}
		checkLogin(myUserList, myUser);
		
		executeProgramLoop();//myScanner);
		
		outputUsers(userFile, myUserList);
		outputAuctions(auctionFile, myAuctionList);
		System.out.println("Changes Have Been Saved.\nThankyou for using Auction Central.");
		myScanner.close();
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
	public void loadAuctions(File auctionFile, ArrayList<Auction> auctionList, Calendar calendar) {
			// map to convert month name strings to ints
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
	        String userName = s.next();
	        Auction newAuction = new Auction(orgName, LocalDateTime.of(year, myMonths.get(month), day, startHour, startMinute), 
	        		LocalDateTime.of(year, myMonths.get(month), day, endHour, endMinute));
	        newAuction.setUserName(userName);
	        auctionList.add(newAuction);
	        
	        calendar.addAuction(newAuction);
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
		if (result) 
		{
			System.out.println("Welcome back, " + theUser.getUserName() + "!");
		} else {
			theList.add(theUser);
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
	public static boolean checkAuctions(List<Auction> theAuctions, String theNPOname, String theUserName) 
	{
		boolean result = false;
		for(int i = 0; i < theAuctions.size(); i++) {
			if(theAuctions.get(i).getAuctionOrg().equals(theNPOname) && theAuctions.get(i).getUserName().equals(theUserName)) {
				result = true;
			}
		}
		return result;
	}
	
	private void executeProgramLoop()//Scanner theScanner)
	{
		//Scanner myScanner = new Scanner(System.in);
		myHomePageMessage = "\nAuctionCentral " + myUser.getUserType() + myHomePageMessageEnd;
		System.out.println(myHomePageMessage);
		boolean notQuit = true;
		
		
		Auction currentAuction = null;
		Item currentItem = null;
		do
		{
			 ArrayList<User.Command> currentCommands = myUser.ExecuteCommand(myCurrentState, myCalendar, currentAuction, currentItem);
			 System.out.println("0) Quit");
			 //print out available commands
			 for (int i = 0; i < currentCommands.size(); i++)
			 {
				 System.out.print(i + 1 + ") ");
				 switch (currentCommands.get(i))
				 {
				 case VIEWCALENDAR:
					 System.out.println("View Calendar");
					 break;
				case ADDAUCTION:
					System.out.println("Add Auction");
					break;
				case ADDITEM:
					System.out.println("Add Item");
					break;
				case BID:
					System.out.println("Bid");
					break;
				case EDITAUCTION:
					System.out.println("Edit Auction");
					break;
				case EDITBID:
					System.out.println("Edit Bid");
					break;
				case EDITITEM:
					System.out.println("Edit Item");
					break;
				case GOBACK:
					System.out.println("Go Back to Previous Menu");
					break;
				case VIEWAUCTION:
					System.out.println("View Auctions");
					break;
				case VIEWITEM:
					System.out.println("View Items");
					break;
				default:
					break;						 
				 }				 
			 }
			 
			 //get command 

			 String TempString = myScanner.nextLine();

			 boolean validCommand = false;
			 int commandInt = -1;
			 try 
			 {
				 commandInt = Integer.parseInt(TempString);
				 validCommand = true;
			 } catch (Exception e) {
				 System.out.println("Enter a Number");
			 }
			 
			 if (validCommand)
			 {
				 if (commandInt == 0)
				 {
					 notQuit = false;
				 } 
				 else if (commandInt <= currentCommands.size())
				 {
					 //System.out.println("You selected " + commandInt);	
					 User.Command thisCommand = currentCommands.get(commandInt - 1);
					 if (thisCommand == User.Command.VIEWCALENDAR)
					 {
						 myCurrentState = User.Command.VIEWCALENDAR;
						 viewCalendarAuctions();
						 //System.out.println(myCalendar.toString(LocalDate.now()));						
					 } 
					 else if (thisCommand == User.Command.VIEWMAINMENU)
					 {
						 myCurrentState = User.Command.VIEWMAINMENU;
						 executeProgramLoop();//theScanner);
					 } 
					 else if (thisCommand == User.Command.VIEWAUCTION)
					 {
						 myCurrentState = User.Command.VIEWAUCTION;
					 } 
					 else if (thisCommand == User.Command.GOBACK)
					 {
						 goBackState();						 
					 } 					 
					 else 
					 {
						 myUser.ExecuteCommand(thisCommand, myCalendar, myCurrentAuction, myCurrentItem);
					 }					 
				 } 
				 else 
				 {
					 System.out.println("Please enter a valid selection.");
				 }
			 }
		} while (notQuit);		
	}
	
	
	
	private void goBackState() {
		switch (myCurrentState)
		 {
		 	case VIEWCALENDAR:
		 		myCurrentState = User.Command.VIEWMAINMENU;
				break;
		 	case VIEWAUCTION:
		 		myCurrentState = User.Command.VIEWCALENDAR;
		 		break;
	 		case VIEWITEM:
	 			myCurrentState = User.Command.VIEWAUCTION;
	 			break;						
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
	}

	private void viewCalendarAuctions() {
		
		myAuctionList.clear();
		Map<LocalDate, ArrayList<Auction>> theAuctionList = myCalendar.displayCurrentMonth();
		if (theAuctionList.size() == 0)
		{
			System.out.println("No Auctions to view");
		}
		else 
		{
			System.out.println("Auctions for " + LocalDate.now().getMonth().name());
			int AuctionListNumber = 1;
			for (Entry<LocalDate, ArrayList<Auction>> entry: theAuctionList.entrySet())
			{
				ArrayList<Auction> temp = entry.getValue();
				for (int i = 0; i < temp.size(); i++) 
				{
					Auction tempAuction = temp.get(i);
					myAuctionList.add(tempAuction);
					System.out.print(AuctionListNumber + ") ");
					System.out.println(tempAuction.getAuctionName());
					AuctionListNumber++;
					
				}
				System.out.println("");
			}
			int userAnswer = 0;
			if (myAuctionList.size() > 0) 
			{
				System.out.println("Enter the number of an auction to view its details.\nPress 0 to go back.");
				userAnswer = getNumberFromUser();
			}
			if (userAnswer > 0) {
				myCurrentState = User.Command.VIEWAUCTION;
				myCurrentAuction = myAuctionList.get(userAnswer);
				System.out.println(myCurrentAuction.toString());
			}
		}		
	}
	
	private int getNumberFromUser()
	{
		int answer = -1;
		if(myScanner.hasNextInt()) {
			answer = myScanner.nextInt();
		}
		myScanner.nextLine();
		return answer;
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
	public void executeNPO(String theUserName, ArrayList<User> theUserList, ArrayList<Auction> theAuctionList, Calendar theCalendar, Scanner theScanner) {
		int option;
		Scanner myScanner = new Scanner(System.in);
		System.out.println("What is the name of your Non-Profit Organization?");
		String NPOname = myScanner.next();
		User user = new NonProfit(theUserName, User.UserType.NPO, NPOname, 0);
		if(checkLogin(theUserList, user)) 
		{
			System.out.println("Welcome backz, " + theUserName + "!");
		} 
		else 
		{
			theUserList.add(user);
		}
		
		System.out.println("\nNon-Profit Organization Staff Member Homepage");
		System.out.println("------------------------------------------------");
		boolean loginPass = false;
		boolean existingAuction = false;
		boolean auctionFound = false;
		Auction currentNPOauction = null;
		int i = 0;
		if(!theAuctionList.isEmpty()){
			while(!auctionFound) {
			if(theAuctionList.get(i).getAuctionOrg().equals(NPOname) && theAuctionList.get(i).getUserName().equals(theUserName)) {
				currentNPOauction = theAuctionList.get(i);
				System.out.println("You have an auction scheduled already.");
				System.out.println("What would you like to do?");
				System.out.println("1. Edit auction information");
				System.out.println("2. Add new inventory items");
				System.out.println("3. Edit inventory items");
				loginPass = true;
				existingAuction = true;
				auctionFound = true;
			} else if(theAuctionList.get(i).getAuctionOrg().equals(NPOname) && !theAuctionList.get(i).getUserName().equals(theUserName)) {
				System.out.println("Someone else from your Non-Profit organization has already scheduled an auction.");
				System.out.println("Only one person from each Non-Profit organization may schedule an auction for that organization.");
				auctionFound = true;
			} else if(!theAuctionList.get(i).getAuctionOrg().equals(NPOname) && theAuctionList.get(i).getUserName().equals(theUserName)) {
				System.out.println("This username is already registered for a different Non-Profit organization.");
				System.out.println("You may only represent one Non-Profit Organization.");
				auctionFound = true;
			} else if(!(theAuctionList.get(i).getAuctionOrg().equals(NPOname) || theAuctionList.get(i).getUserName().equals(theUserName))) {
				System.out.println("What would you like to do?");
				System.out.println("1) Schedule an auction");			
				loginPass = true;
				existingAuction = false;
				auctionFound = true;
			}
			i++;
		}
		} else {
			System.out.println("What would you like to do?");
			System.out.println("1) Schedule an auction");	
			loginPass = true;
			existingAuction = false;
		}
		
		option = theScanner.nextInt();
		
		switch(option) 
		{ 
			
		case 1:
			if(loginPass && existingAuction) 		// the NPO has an auction already, so edit
			{
				user.ExecuteCommand(User.Command.EDITAUCTION, theCalendar, currentNPOauction, null);
				theAuctionList.clear();
				Collection<ArrayList<Auction>> auctions = theCalendar.myAuctionByDateList.values();
				Iterator <ArrayList<Auction>> it = auctions.iterator();
				while(it.hasNext()) {
					ArrayList<Auction> a = (ArrayList<Auction>) it.next();
					for(int j = 0; j < a.size(); j++) {
						Auction auction = a.get(j);
						theAuctionList.add(auction);
					}
				}
			}
			else if(loginPass && !existingAuction)				// NPO doesn't have an auction so add one
			{							
				user.ExecuteCommand(User.Command.ADDAUCTION, theCalendar, null, null);
				Collection<ArrayList<Auction>> auctions = theCalendar.myAuctionByDateList.values();
				Iterator <ArrayList<Auction>> it = auctions.iterator();
				while(it.hasNext()) {
					ArrayList<Auction> a = (ArrayList<Auction>) it.next();
					for(int j = 0; j < a.size(); j++) {
						Auction auction = a.get(j);
						if(auction.myOrgName.equals(NPOname)) {
							theAuctionList.add(auction);
						}
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
		myScanner.close();
	}
	
	/**
	 * Runs the Bidder menu.
	 * 
	 * @param theUserName
	 * @param theUserList
	 * @param theScanner
	 */
	public static void executeBidder(String theUserName, ArrayList<User> theUserList, Scanner theScanner, ArrayList<Auction> theAuctionList) {
		int option;
		User user = new Bidder(theUserName, User.UserType.BIDDER);
		if(checkLogin(theUserList, user)) 
		{
			System.out.println("Welcome backz, " + theUserName + "!");
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
				System.out.println(theAuctionList);		// this will probably need more formatting
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
		
//		System.out.println(theAuctionList.size());
		
		for(int i = 0; i < theAuctionList.size(); i++)
		{
			Auction auction = theAuctionList.get(i);
			outputAuctions.println(auction.getAuctionName()); // orgnameMonth-day-year
//			outputAuctions.println("Organization: " + auction.getAuctionOrg()); 
			outputAuctions.println(auction.getStartTime().getHour() + " " + auction.getStartTime().getMinute());
			outputAuctions.println(auction.getEndTime().getHour() + " " + auction.getEndTime().getMinute());
			outputAuctions.println(auction.getAuctionItems());
			outputAuctions.println(auction.getUserName());
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
