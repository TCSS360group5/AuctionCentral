import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class ProgramLoop {
//	private static final String myACFileName = "./AuctionCentral/";
	private static final String myUserFile = "users.txt";
	private static final String myAuctionFile = "auction.txt";
	
	private User myUser;
	private Calendar myCalendar;
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
	
	public ProgramLoop()
	{
		myCurrentState = User.Command.VIEWMAINMENU;
		myCalendar = new Calendar();
		myUserList = new ArrayList<User>();
		myAuctionList = new ArrayList<Auction>();
		myScanner = new Scanner(System.in);
	}
	
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
		
		//checkLogin(myUserList, myUser);
		
		executeProgramLoop();
		
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
	        	String userName = s.nextLine();
	        	String userType = s.nextLine();
	        	switch(userType) {
	        	case "EMPLOYEE":
	        		theUserList.add(new Employee(userName, User.UserType.EMPLOYEE));
	        		break;
	        	case "NPO":
	        		String userNPOname = s.nextLine();	        		
	        		int year = s.nextInt();	        		
	        		int month = s.nextInt();
	        		int day = s.nextInt();
	        		s.nextLine(); //clears the line
	        		theUserList. add(new NonProfit(userName, User.UserType.NPO, userNPOname, LocalDate.of(year, month, day), true));
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
		        //String auctionName = s.next();
		        
		        // int j = auctionName.indexOf('-');			// finding the indexes of - to skip over them
		        //int k = auctionName.indexOf('-', j+1);
		        //int l = auctionName.indexOf('-', k+1);
		        String orgName = s.nextLine();//auctionName.substring(0, j); // name
		        int month = s.nextInt();//auctionName.substring(j+1, k); // month
		        int day = s.nextInt();//Integer.parseInt(auctionName.substring(k+1, l)); // day
		        int year = s.nextInt();//Integer.parseInt(auctionName.substring(l+1, auctionName.length())); // year
		        
		        int startHour = s.nextInt();
		        int startMinute = s.nextInt();
		        int endHour = s.nextInt();
		        int endMinute = s.nextInt();
		        s.nextLine();
		        //Map<String, Integer> myMonths = createMonthsCap();
		        String userName = s.nextLine();
		        int NumItems = s.nextInt();
		        s.nextLine();//clear the input
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
							String Bidder = s.nextLine();
							Double theBid = s.nextDouble();
							User theUser = FindUser(Bidder);
							if (theUser != null)
							{
								bidList.put(theUser, theBid);
							} 
							else 
							{
								System.out.println("User not found in incomming bid list");
							}
						}
						oneItem.setBids(bidList);						
					}	
					ItemList.add(oneItem);
				}			
		        Auction newAuction = new Auction(orgName, userName, LocalDateTime.of(year, month, day, startHour, startMinute), 
		        		LocalDateTime.of(year, month, day, endHour, endMinute), ItemList);
		        //newAuction.setUserName(userName);
		        auctionList.add(newAuction);
		        User userToAdd = FindUserByNPOName(orgName);
		        ((NonProfit)userToAdd).setAuction(newAuction);
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
	 * @param theUserName
	 * @return if the user exists, it returns the user, null otherwise
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
			else 
			{
				//System.out.println("didnt find user");
			}
		}
		return answerUser;
	}
	
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
	 * @param theAuctions
	 * @param theNPOname
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
	
	
	private void executeProgramLoop()//Scanner theScanner)
	{
		//Scanner myScanner = new Scanner(System.in);
		if(myUser.getUserType() == User.UserType.NPO)
		{
			if(((NonProfit)myUser).hasAuction() == true)
			{
				myCurrentAuction = ((NonProfit)myUser).getAuction();
			}
		}
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
				 } 
				 else 
				 {
					 System.out.println("Please enter a valid selection.");
				 }
			 }
		} while (notQuit);		
	}
	
		
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
				}
				System.out.println("");
				int userAnswer = 0;
				if (myAuctionList.size() > 0) 
				{
					System.out.println("Enter the number of an Item to view its details.\nPress 0 to go back.");
					userAnswer = getNumberFromUser();
				}
				if (userAnswer > 0) {
					myCurrentState = User.Command.VIEWITEM;
					myCurrentItem = theItems.get(userAnswer - 1);
					System.out.println(myCurrentItem.toString());
				}
		 }		
	}

	
	private void goBackState() {
		switch (myCurrentState)
		 {
		 	case VIEWCALENDAR:
		 		myCurrentState = User.Command.VIEWMAINMENU;
				break;
		 	case VIEWAUCTION:
		 		myCurrentState = User.Command.VIEWCALENDAR;
		 		System.out.println("Calendar Menu");
		 		break;
	 		case VIEWITEM:
	 			myCurrentState = User.Command.VIEWAUCTION;
	 			System.out.println("Auction Menu");
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
				myCurrentAuction = myAuctionList.get(userAnswer - 1);
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
			User tempUser = theUserList.get(i);
			outputUsers.println(tempUser.getUserName());
			outputUsers.println(tempUser.getUserType());
			if (tempUser.getUserType() == User.UserType.NPO)
			{		
				NonProfit tempNPOUser = (NonProfit) tempUser;
				outputUsers.println(tempNPOUser.getNPOName());
				LocalDate LastAuctionDate = tempNPOUser.getLastAuctionDate();
				outputUsers.println(LastAuctionDate.getYear() + " " + LastAuctionDate.getMonthValue() + " " + LastAuctionDate.getDayOfMonth());
			}
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
			e1.printStackTrace();
		}
		
//		System.out.println(theAuctionList.size());
		
		for(int i = 0; i < theAuctionList.size(); i++)
		{
			Auction auction = theAuctionList.get(i);
			//outputAuctions.println(auction.getAuctionName()); // orgnameMonth-day-year
			outputAuctions.println(auction.getAuctionOrg()); 
			outputAuctions.println(auction.getStartTime().getMonthValue()); 
			outputAuctions.println(auction.getStartTime().getDayOfMonth());
			outputAuctions.println(auction.getStartTime().getYear());
			outputAuctions.println(auction.getStartTime().getHour() + " " + auction.getStartTime().getMinute());
			outputAuctions.println(auction.getEndTime().getHour() + " " + auction.getEndTime().getMinute());			
			outputAuctions.println(auction.getUserName());
			List<Item> ItemList = auction.getAuctionItems();
			int ItemListSize = 0;
			if (ItemList != null)
			{
				ItemListSize = ItemList.size();
			}
			outputAuctions.println(ItemListSize);
			for (int j = 0; j < ItemListSize; j++)
			{
				Item oneItem = ItemList.get(i);
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
		outputAuctions.close();
	}
	
	
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
	
	
	public static HashMap<String, Integer> createMonthsCap() {
		HashMap<String, Integer> months = new HashMap<String, Integer>();
		months.put("JANUARY", 1);
		months.put("FEBRUARY", 2);
		months.put("MARCH", 3);
		months.put("APRIL", 4);
		months.put("MAY", 5);
		months.put("JUNE", 6);
		months.put("JULY", 7);
		months.put("AUGUST", 8);
		months.put("SEPTEMBER", 9);
		months.put("OCTOBER", 10);
		months.put("NOVEMBER", 11);
		months.put("DECEMBER", 12);
		return months;
	}
}
