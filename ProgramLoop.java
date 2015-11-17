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
	private static final String myUserFile = "users.txt";
	private static final String myAuctionFile = "auction.txt";
	private User myUser;
	private static Calendar myCalendar;
	private ArrayList<User> myUserList;
	private ArrayList<Auction> myAuctionList;
	private Scanner myScanner;
	private String myHomePageMessage;
	private User.Command myCurrentState;
	private Auction myCurrentAuction;
	private Item myCurrentItem;
	
	private static final String myHomePageMessageEnd = " Homepage\n"
		+ "----------------------------------\n"
		+ "What would you like to do?\n";
	
	/**
	 * Constructor for ProgramLoop. Initializes some of the fields.
	 */
	public ProgramLoop()
	{
		myCurrentState = User.Command.VIEWMAINMENU;
		myCalendar = new Calendar();
		myUserList = new ArrayList<User>();
		myAuctionList = new ArrayList<Auction>();
		myScanner = new Scanner(System.in);
	}
	
	/**
	 * Loads and stores the files, handles user log in, and outputs files.
	 */
	public void startProgram() 
	{	
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
		String userName = myScanner.nextLine();
		//see if this user already has a profile
		User theUser = FindUser(userName);
		if (theUser == null)
			{
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
					
					myUser = new NonProfit(userName, User.UserType.NPO, NPOname, LocalDate.now().minusYears(1), hasExistingAuction(NPOname));

				} 
				else if (userType == 3)
				{
					myUser = new Bidder(userName, User.UserType.BIDDER);
				}
				System.out.println("Welcome to Auction Central " + myUser.getUserName());
				myUserList.add(myUser);
			} 
			else //user already is in user list
			{
				myUser = theUser;
				System.out.println("Welcome back, " + myUser.getUserName() + "!");
			}
		
		
		executeProgramLoop();
		outputUsers(userFile, myUserList);
		outputAuctions(auctionFile, myAuctionList);
		System.out.println("Thank you for using Auction Central.");
		myScanner.close();
	}
	
	/**
	 * Read in text file of existing users using a Scanner and import them into an ArrayList of users.
	 *  
	 * @param theFile the file being read
	 * @param theUserList the list that the users from the file are loading into
	 */
	public static void loadUsers(File theFile, ArrayList<User> theUserList) {
		try 
		{
	        Scanner s = new Scanner(theFile);
	        while (s.hasNext()) {
	        	String userName = s.nextLine();
	        	String userType = s.nextLine();
	        	switch(userType) {
	        	case "EMPLOYEE":
	        		theUserList.add(new Employee(userName, User.UserType.EMPLOYEE));
	        		break;
	        	case "NPO":
	        		String userNPOname = s.nextLine();	 
	        		if(s.hasNextInt()) {		// if in the user file there is a date saved, it means the NPO has an auction
		        		int year = s.nextInt();	        		
		        		int month = s.nextInt();
		        		int day = s.nextInt();
		        		s.nextLine(); //clears the line
		        		theUserList. add(new NonProfit(userName, User.UserType.NPO, userNPOname, LocalDate.of(year, month, day), true));
	        		} else {					// this NPO doesn't have an auction saved, last parameter is set appropriately
		        		theUserList. add(new NonProfit(userName, User.UserType.NPO, userNPOname, LocalDate.now().minusYears(1), false));
	        		}
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
	 * @param theAuctionFile the file of previous auctions
	 * @param theAuctionList the list the auctions will be loaded into
	 * @param theCalendar the main calendar for this program
	 */
	public void loadAuctions(File theAuctionFile, ArrayList<Auction> theAuctionList, Calendar theCalendar) {
		try 
		{
	        Scanner s = new Scanner(theAuctionFile);
	        while (s.hasNext()) {
		        String orgName = s.nextLine();
		        int month = s.nextInt();
		        int day = s.nextInt();
		        int year = s.nextInt();
		        int startHour = s.nextInt();
		        int startMinute = s.nextInt();
		        int endHour = s.nextInt();
		        int endMinute = s.nextInt();
		        s.nextLine();
		        String userName = s.nextLine();
		        int NumItems = s.nextInt();
		        s.nextLine();
		        List<Item> ItemList = new ArrayList<Item>();
				for (int j = 0; j < NumItems; j++)
				{
					String ItemName = s.nextLine();
					Double StartingBid = s.nextDouble();
					s.nextLine();
					String Description = s.nextLine();
					
					Item oneItem = new Item(ItemName, StartingBid, Description);
					
					Map<User, Double> bidList = new HashMap<User, Double>();
					int NumBids = s.nextInt();
					if (NumBids > 0)
					{
						for (int k = 0; k < NumBids; k++) 
						{
							String Bidder = s.next();
							Double theBid = s.nextDouble();
							User theUser = FindUser(Bidder);
							if (theUser != null)
							{
								bidList.put(theUser, theBid);
								((Bidder)theUser).addBid(oneItem, theBid);
							} 
							else 
							{
								System.out.println("User not found in incomming bid list");
							}
						}
						oneItem.setBids(bidList);						
					}	
					ItemList.add(oneItem);
					if(s.hasNextLine()) 		// bug fix for input mismatch exceptions
					{
						s.nextLine();
					}
				}			
		        Auction newAuction = new Auction(orgName, userName, LocalDateTime.of(year, month, day, startHour, startMinute), 
		        		LocalDateTime.of(year, month, day, endHour, endMinute), ItemList);
		        theAuctionList.add(newAuction);
		        User userToAdd = FindUserByNPOName(orgName);
		        ((NonProfit)userToAdd).setAuction(newAuction);
		        theCalendar.addAuction(newAuction);
		        
		        if(s.hasNextLine()) 			// bug fix for input mismatch exceptions
		        {
		        s.nextLine();
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
	 * Check the list of users to see if this user has registered already.
	 * 
	 * @param theUserName the user name being searched for
	 * @return a user if one exists, null otherwise
	 */
	private User FindUser(String theUserName)
	{
		User answerUser = null;
		for (User theUser: myUserList)
		{
			if (theUser.getUserName().compareTo(theUserName) == 0)
			{
				answerUser = theUser;
			} 
		}
		return answerUser;
	}
	
	/**
	 * Finds a NonProfit Organization member by the name of their organization.
	 * 
	 * @param NPOName the name of the organization
	 * @return the user with that NPO name
	 */
	private User FindUserByNPOName(String NPOName)
	{
		User answerUser = null;
		for (User theUser: myUserList)
		{
			if(theUser.getUserType() == User.UserType.NPO)
			{
				if(((NonProfit)theUser).getNPOName().equals(NPOName))
					{
						answerUser = theUser;
					}
			}
		}
		return answerUser;
	}
	
		
	
	/**
	 * Check the list of Auctions to see if the NPO has an auction scheduled already.
	 * 
	 * @param theAuctions the list of auctions
	 * @param theNPOname the name of the NPO being searched for
	 * @return true if an auction exists, false otherwise
	 */
	public static boolean checkAuctions(List<Auction> theAuctions, String theNPOname, String theUserName) 
	{
		boolean result = false;
		for(int i = 0; i < theAuctions.size(); i++) {
			if(theAuctions.get(i).getAuctionOrg().equals(theNPOname)) {// && theAuctions.get(i).getUserName().equals(theUserName)) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Executes the program.
	 */
	private void executeProgramLoop()
	{
		if(myUser.getUserType() == User.UserType.NPO)
		{
			if(((NonProfit)myUser).hasAuction() == true)
			{
				myCurrentAuction = ((NonProfit)myUser).getAuction();
			}
		}
		myHomePageMessage = "\nAuctionCentral " + myUser.getUserType() + myHomePageMessageEnd;
		boolean notQuit = true;
		Auction currentAuction = null;
		Item currentItem = null;
		do
		{
			System.out.println(menuTitle());
			 ArrayList<User.Command> currentCommands = myUser.ExecuteCommand(myCurrentState, myCalendar, currentAuction, currentItem);
			 System.out.println("0) Quit");
			 //print out available commands
			 for (int i = 0; i < currentCommands.size(); i++)
			 {
				 System.out.print(i + 1 + ") ");
				 switch (currentCommands.get(i))
				 {
				 case VIEWCALENDAR:
					 if(myUser.getUserType().equals(User.UserType.BIDDER)) {
						 System.out.println("View Auctions");
					 } else {
						 System.out.println("View Calendar");
					 }
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
					System.out.println("View/Edit My Auction");
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
					 if(myUser.getUserType().equals(User.UserType.BIDDER)) {
						 System.out.println("View Items");
					 } else {
						 System.out.println("View/Edit Items");
					 }
					break;
				case VIEWBIDS:
					System.out.println("View Bids");
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
					 User.Command thisCommand = currentCommands.get(commandInt - 1);
					 if (thisCommand == User.Command.VIEWCALENDAR)
					 {
						 myCurrentState = User.Command.VIEWCALENDAR;
						 viewCalendarAuctions();
					 } 
					 else if (thisCommand == User.Command.VIEWMAINMENU)
					 {
						 myCurrentState = User.Command.VIEWMAINMENU;
						 executeProgramLoop();
					 } 
					 else if (thisCommand == User.Command.VIEWAUCTION)
					 {
						 myCurrentState = User.Command.VIEWAUCTION;						 
						 viewCalendarAuctions();
					 } 
					 else if (thisCommand == User.Command.VIEWITEM)
					 {
						 viewItems();
					 }					 
					 else if (thisCommand == User.Command.GOBACK)
					 {
						 goBackState();						 
					 } 		
					 else 
					 {
						 myUser.ExecuteCommand(thisCommand, myCalendar, myCurrentAuction, myCurrentItem);
					 }	
					 
					 // neccessary to transfer the newest auction that was added to the Calendar to our local AuctionList
					 if(thisCommand.equals(User.Command.ADDAUCTION)) {
							Collection<ArrayList<Auction>> auctions =myCalendar.myAuctionByDateList.values();
							Iterator<ArrayList<Auction>> it = auctions.iterator();
							myAuctionList.clear();
							while(it.hasNext()) {
								ArrayList<Auction> a = (ArrayList<Auction>) it.next();
								for(int j = 0; j < a.size(); j++) {
									Auction auction = a.get(j);
									myAuctionList.add(auction);
								}
							}
					 }
					 
				 } 
				 else 
				 {
					 System.out.println("Please enter a valid selection.");
				 }
			 }
		} while (notQuit);		
	}
	
	/**
	 * Displays/controls menu for viewing items of the current auction.
	 */
	private void viewItems() {
		 List<Item> theItems = myCurrentAuction.getAuctionItems();
		 int theItemIndex = 1;
		 if (theItems == null || theItems.size() == 0) {
			 System.out.println("No items found for current auction");
		 }
		 else 
		 {
			 for (Item theItem: theItems)
				{
					System.out.print(theItemIndex + ") ");
					System.out.println(theItem.getItemName());
					theItemIndex++;
				}
				System.out.println("");
				int userAnswer = 0;
				if (myAuctionList.size() > 0) 
				{
					System.out.println("Enter the number of an item to view/edit its details.\nEnter 0 to go back.");
					userAnswer = getNumberFromUser();
				}
				if(userAnswer < 0 || userAnswer > theItems.size())
				{
					do
					{
						System.out.println("Invalid answer. Enter the number of an item to view/edit its details.\nEnter 0 to go back.");
						userAnswer = getNumberFromUser();
					} while(userAnswer < 0 || userAnswer > theItems.size());
				}
				if (userAnswer > 0) {
					myCurrentState = User.Command.VIEWITEM;
					myCurrentItem = theItems.get(userAnswer - 1);
					System.out.println(myCurrentItem.toString());
				}
		 }		
	}

	/**
	 * Determines which menu will be displayed when "go back" is selected, depending on the current menu or "state"
	 */
	private void goBackState() {
		switch (myCurrentState)
		 {
		 	case VIEWCALENDAR:
		 		myCurrentState = User.Command.VIEWMAINMENU;
				break;
		 	case VIEWAUCTION:
		 		if(myUser.getUserType().equals(User.UserType.NPO)) {
		 			myCurrentState = User.Command.VIEWMAINMENU;
		 		} else {
		 			myCurrentState = User.Command.VIEWCALENDAR;
		 		}
		 		break;
	 		case VIEWITEM:
		 		if(myUser.getUserType().equals(User.UserType.NPO)) {
		 			myCurrentState = User.Command.VIEWMAINMENU;
		 		} else {
		 			myCurrentState = User.Command.VIEWAUCTION;
		 		}
	 			break;						
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
	}

	/**
	 * Displays/Controls the View Calendar menu.
	 */
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
				myCurrentAuction = myAuctionList.get(userAnswer - 1);
				System.out.println(myCurrentAuction.toString() + "\n");
			}
		}		
	}
	
	/**
	 * Gets the user input.
	 * 
	 * @return the user's selection
	 */
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
	 * Prints the existing users to the users.txt file.
	 * 
	 * @param theUserFile the file of users
	 * @param theUserList the list of users
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
			User tempUser = theUserList.get(i);
			outputUsers.println(tempUser.getUserName());
			outputUsers.println(tempUser.getUserType());
			if (tempUser.getUserType() == User.UserType.NPO)
			{		
				NonProfit tempNPOUser = (NonProfit) tempUser;
				outputUsers.println(tempNPOUser.getNPOName());
				if(tempNPOUser.hasAuction()) {
					LocalDate LastAuctionDate = tempNPOUser.getLastAuctionDate();
					outputUsers.println(LastAuctionDate.getYear() + " " + LastAuctionDate.getMonthValue() + " " + LastAuctionDate.getDayOfMonth());
				}
			}
		}
		outputUsers.close();
	}
	
	/**
	 * Returns the menu title based on the current state.
	 * 
	 * @return a String of the menu title
	 */
	private String menuTitle() 
	{
		String answerString = "";
	
		switch (myCurrentState)
		 {
	 	case VIEWCALENDAR:
	 		answerString = "Calendar Menu";
			break;
	 	case VIEWAUCTION:
	 		answerString = "Auction Menu";
	 		break;
 		case VIEWITEM:
 			answerString = "Item Menu";
 			break;			
		case VIEWMAINMENU:
			answerString = myHomePageMessage;
			break;
		case ADDAUCTION:
			answerString = "1 Menu";
			break;
		case ADDITEM:
			answerString = "2 Menu";
			break;
		case BID:
			answerString = "3 Menu";
			break;
		case EDITAUCTION:
			answerString = "4 Menu";
			break;
		case EDITBID:
			answerString = "5 Menu";
			break;
		case EDITITEM:
			answerString = "6 Menu";
			break;
		case VIEWBIDS:
			answerString = "7 Menu";
			break;
		default:
			answerString = "8 Menu";
			break;
		 }
		return answerString;
	}
	
	/**
	 * Outputs the existing auctions to the auctions.txt file.
	 * 
	 * @param theAuctionFile the file of auctions
	 * @param theAuctionList the list of auctions
	 */
	public static void outputAuctions(File theAuctionFile, ArrayList<Auction> theAuctionList) {
		
		Map<LocalDate, ArrayList<Auction>> auctionMap = myCalendar.myAuctionByDateList;
		Collection<ArrayList<Auction>> auctionLists = auctionMap.values();
		theAuctionList.clear();
		
		Iterator<ArrayList<Auction>> it = auctionLists.iterator();
		while(it.hasNext()) {
			ArrayList<Auction> auctionList = (ArrayList<Auction>) it.next();
			for(int i = 0; i < auctionList.size(); i++) {
				theAuctionList.add(auctionList.get(i));
			}
		}
		
		PrintStream outputAuctions = null;
		
		try 
		{
			outputAuctions = new PrintStream(theAuctionFile);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		for(int i = 0; i < theAuctionList.size(); i++)
		{
			Auction auction = theAuctionList.get(i);
			outputAuctions.println(auction.getAuctionOrg()); 
			outputAuctions.println(auction.getStartTime().getMonthValue()); 
			outputAuctions.println(auction.getStartTime().getDayOfMonth());
			outputAuctions.println(auction.getStartTime().getYear());
			outputAuctions.println(auction.getStartTime().getHour() + " " + auction.getStartTime().getMinute());
			outputAuctions.println(auction.getEndTime().getHour() + " " + auction.getEndTime().getMinute());			
			outputAuctions.println(auction.getUserName());
			List<Item> ItemList = auction.getAuctionItems();
			int ItemListSize;
			if (ItemList != null)
			{
				ItemListSize = ItemList.size();
				outputAuctions.println(ItemListSize);
				for (int j = 0; j < ItemListSize; j++)
				{
					Item oneItem = ItemList.get(j);
				
					outputAuctions.println(oneItem.myItemName);
					outputAuctions.println(oneItem.myStartingBid);
					outputAuctions.println(oneItem.myDescription);				
					Map<User, Double> bidList = oneItem.getBids();
					outputAuctions.println(bidList.size());
					for (Entry<User, Double> entry: bidList.entrySet()) 
					{
						outputAuctions.println(entry.getKey().getUserName());
						outputAuctions.println(entry.getValue());
					}
				}
			}
			outputAuctions.println();
		}
		outputAuctions.close();
	}
	
	/**
	 * Returns whether or not the specified NPO has an auction already.
	 * 
	 * @param theNPOname the name of the NonProfit Organization we are checking
	 * @return true if an auction exists, false otherwise
	 */
	public boolean hasExistingAuction(String theNPOname) {
		Auction auction;
		boolean result = false;
		for(int i = 0; i < myAuctionList.size(); i++) {
			auction = myAuctionList.get(i);
			if(auction.myOrgName.equals(theNPOname)) {
				result = true;
			}
		}
		return result;
	}
}
