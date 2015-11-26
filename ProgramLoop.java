//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintStream;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class ProgramLoop {
	private FileSaving myFileSaver;
//	private static final String myUserFileString = "users.txt";
//	private static final String myAuctionFileString = "auction.txt";
//	private File myUserFile;
//	private File myAuctionFile;
	private User myUser;
	private static Calendar myCalendar;
	private ArrayList<User> myUserList;
	private ArrayList<Auction> myAuctionList;
	private Scanner myScanner;
//	private String myHomePageMessage;
	private User.Command myCurrentState;
	private Auction myCurrentAuction;
	private Item myCurrentItem;
	
//	private static final String myHomePageMessageEnd = " Homepage\n"
//		+ "----------------------------------\n"
//		+ "What would you like to do?\n";
	
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
		myFileSaver = new FileSaving();
	}
	
	/**
	 * Loads and stores the files, handles user log in, and outputs files.
	 */
	public void startProgram() 
	{	
		myFileSaver.loadAll(myUserList, myAuctionList, myCalendar);
		boolean notQuit = true;
		do
		{			
			boolean doneWithMenu = false;
			do 
			{
				System.out.println("Welcome to AuctionCentral!");
				System.out.println("0) Quit");
				System.out.println("1) Login");
				int selection = getNumberFromUser();
				switch (selection){
					case 0:
						notQuit = false;
						doneWithMenu = true;
						break;
					case 1:
						doneWithMenu = login();
						break;
					case 99:
						doneWithMenu = createNewUser();
						break;
					default:
						System.out.println("Selection not recognized.");
						break;
				}
			}while (!doneWithMenu);
			
			if (notQuit)
			{
				myCurrentState = User.Command.VIEWMAINMENU;
				executeProgramLoop();
			}		
			myFileSaver.saveAll(myUserList, myAuctionList, myCalendar);
			System.out.println("Thank you for using Auction Central.\n");						
		} while (notQuit);						
		myScanner.close();
	}
	
	private boolean login() {
		boolean foundUser = false;
		System.out.print("Please enter your username: ");
		String userName = myScanner.nextLine();
		User theUser = myFileSaver.FindUser(userName, myUserList);
		if (theUser != null)
		{
			foundUser = true;
			myUser = theUser;
			System.out.println("Welcome Back " + myUser);
		} else {
			System.out.println("User " + userName + " not found.\n");
		}
		return foundUser;
	}	
	
	private boolean createNewUser()
	{
		boolean addedUser = false;
		System.out.print("Please enter your username: ");
		String userName = myScanner.nextLine();
		//see if this user already has a profile
		User theUser = myFileSaver.FindUser(userName, myUserList);
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
					
					myUser = new NonProfit(userName, User.UserType.NPO, NPOname, LocalDate.now().minusYears(1).minusDays(1));

				} 
				else if (userType == 3)
				{
					myUser = new Bidder(userName, User.UserType.BIDDER);
				}
				System.out.println("Welcome to Auction Central " + myUser.getUserName());
				myUserList.add(myUser);
				addedUser = true;
			} 
			else //user already is in user list
			{
				System.out.println("Cannot add the user name " + myUser.getUserName() + " as it is already in our system!");
			}
		return addedUser;
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
			if(((NonProfit)myUser).getAuction() != null)
			{
				myCurrentAuction = ((NonProfit)myUser).getAuction();
			}			
		}
		boolean notLogout = true;
//		Auction currentAuction = null;
//		Item currentItem = null;
		do
		{
			 System.out.println(myUser.menuTitle(myCurrentState));
			 ArrayList<User.Command> currentCommands = myUser.GetMenu(myCurrentState, myCurrentItem);
			 
			 //print out available commands
			 System.out.println("0) Logout");
			 for (int i = 0; i < currentCommands.size(); i++)
			 {
				 System.out.print(i + 1 + ") ");
				 System.out.println(myUser.getCommandName(currentCommands.get(i)));			 
			 }
			 
			 //get command 
			 //String TempString = myScanner.nextLine();

			 //boolean validCommand = true;
			 int commandInt = getNumberFromUserWithinRange(0, currentCommands.size() - 1);
			 
			 if (commandInt >= 0)
			 {
				 if (commandInt == 0)
				 {
					 notLogout = false;
				 } 
				 else if (commandInt <= currentCommands.size())
				 {
					 User.Command thisCommand = currentCommands.get(commandInt - 1);
					 myCurrentState = myUser.getNextState(myCurrentState, thisCommand);
//					 if (tempState != null) {
//						 myCurrentState = tempState;
//					 }
					 
					 if (myCurrentState == User.Command.VIEWCALENDAR)
					 {
						 viewCalendarAuctions();
					 } 
//					 else if (myCurrentState == User.Command.VIEWMAINMENU)
//					 {
//						 executeProgramLoop();
//					 } 
					 else if (myCurrentState == User.Command.VIEWBIDDERAUCTIONS)
					 {
						 System.out.println("Auctions for Bidders not implemented yet.");
						 //viewCalendarAuctions();
					 }

					 if (thisCommand == User.Command.VIEWITEM)
					 {
						 viewItems();
					 }					 
//					 else if (thisCommand == User.Command.GOBACK)
//					 {
//						 User.Command moveState = myUser.goBackState(myCurrentState);
//						 if (moveState != null) 
//						 {
//							 myCurrentState = moveState;
//						 }			
//						 else 
//						 {
//							 System.out.println("Cannot go Back.");
//						 }
//					 } 		

						 myUser.ExecuteCommand(thisCommand, myCalendar, myCurrentAuction, myCurrentItem);
						 
						 if(myUser.getUserType() == User.UserType.NPO && thisCommand == User.Command.ADDAUCTION)
						 {
							 myCurrentAuction = ((NonProfit)myUser).getAuction();
						 }

					 
					 // neccessary to transfer the newest auction that was added to the Calendar to our local AuctionList
					 if(thisCommand.equals(User.Command.ADDAUCTION)) {
							Collection<ArrayList<Auction>> auctions = myCalendar.myAuctionByDateList.values();
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
					 
				 } 
				 else 
				 {
					 System.out.println("Please enter a valid selection.");
				 }
		} while (notLogout);	
	
	}
	
	/**
	 * Displays/controls menu for viewing items of the current auction.
	 */
	private void viewItems() {
		System.out.println("Item List View");
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
			if (userAnswer > 0) {
				myCurrentState = User.Command.VIEWITEM;
				myCurrentItem = theItems.get(userAnswer - 1);
				System.out.println(myCurrentItem.toString());
			}
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
				myCurrentState = User.Command.VIEWAUCTIONDETAILS;
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
	
	private int getNumberFromUserWithinRange(int Start, int End)
	{
		 int commandInt = -1;
		 String TempString = myScanner.nextLine();
		 try 
		 {
			 commandInt = Integer.parseInt(TempString);
		 } catch (Exception e) {
			 System.out.println("Enter a Number between " + Start + " and " + End);
			 commandInt = getNumberFromUserWithinRange(Start, End);
		 }
		return commandInt;
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
