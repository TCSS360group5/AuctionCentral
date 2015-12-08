package view;

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

import model.AuctionModel;
import model.BidderModel;
import model.CalendarModel;
import model.EmployeeModel;
import model.ItemModel;
import model.NonProfitModel;
import model.UserModel;

/**
 * This is the main loop class of the program. It logs the user in, helps with
 * displaying the menus, calls the user classes to spread computational labor
 * and does some activities that were difficult for the user classes to
 * implement.
 * 
 * @author UWT Group 5
 */
public class ProgramLoop {
	private UserModel myUserModel;
	private UserController myUserController;
	private static CalendarModel myCalendar;
	private ArrayList<UserModel> myUserList;
	private ArrayList<AuctionModel> myAuctionList;
	private Scanner myScanner;
	private UserController.Command myCurrentState;
	private AuctionModel myCurrentAuction;
	private ItemModel myCurrentItem;

	/**
	 * Constructor for ProgramLoop. Initializes some of the fields.
	 */
	public ProgramLoop() {
		myCurrentState = UserController.Command.VIEWMAINMENU;
		myCalendar = new CalendarModel();
		myUserList = new ArrayList<UserModel>();
		myAuctionList = new ArrayList<AuctionModel>();
		myScanner = new Scanner(System.in);
	}

	/**
	 * Loads and stores the files, handles user log in, and outputs files.
	 * 
	 * @author Shannon, Quinn
	 */
	public void startProgram() {
		FileSaving.loadAll(myUserList, myAuctionList, myCalendar);
		boolean notQuit = true;
		do {
			boolean doneWithMenu = false;
			do {
				System.out.println("Welcome to AuctionCentral!");
				System.out.println("0) Quit");
				System.out.println("1) Login");
				int selection = getNumberFromUserWithinRange(0, 1, 99);
				switch (selection) {
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
			} while (!doneWithMenu);

			if (notQuit) {
				myCurrentState = UserController.Command.VIEWMAINMENU;
				executeProgramLoop();
			}
			FileSaving.saveAll(myUserList, myAuctionList, myCalendar);
			System.out.println("Thank you for using Auction Central.\n");
		} while (notQuit);
		myScanner.close();
	}

	/**
	 * This method is the UI for logging in. It calls the find user by name
	 * method and finds the user if they are in the user list and then loads the
	 * user into the program according to their type.
	 * 
	 * @return returns true if the login was successful, false otherwise.
	 * @author Quinn.
	 */
	private boolean login() {
		boolean foundUser = false;
		System.out.print("Please enter your username: ");
		String userName = myScanner.nextLine();
		UserModel theUser = FileSaving.findUserByName(userName, myUserList);
		if (theUser != null) {
			foundUser = true;
			myUserModel = theUser;
			switch (theUser.getUserType()) {
			case BIDDER:
				myUserController = new BidderController(myUserModel);
				break;
			case NPO:
				myUserController = new NonProfitController(myUserModel);
				break;
			default:
				myUserController = new EmployeeController(myUserModel);
				break;
			}
			System.out.println("Welcome Back " + userName);
		} else {
			System.out.println("User " + userName + " not found.\n");
		}
		return foundUser;
	}

	/**
	 * This method is not a user story and it is therefore hidden from the user
	 * interface, however it is still accessable by a hidden command for use by
	 * system admins. This method creates a new user with one of the new types
	 * and if needed, gets the NPO name as well.
	 * 
	 * @return returns true if a user was created, false otherwise.
	 * @author Quinn
	 */
	private boolean createNewUser() {
		boolean addedUser = false;
		System.out.print("Please enter your username: ");
		String userName = myScanner.nextLine();
		// see if this user already has a profile
		UserModel theUser = FileSaving.findUserByName(userName, myUserList);
		if (theUser == null) {
			System.out
					.println("Are you an AuctionCentral employee, a non-profit organization member, or a bidder?");
			System.out
					.println("1) AuctionCentral Employee\n2) Non-Profit Organization\n3) Bidder");

			int userType = myScanner.nextInt();
			myScanner.nextLine();
			if (userType == 1) {
				myUserModel = new EmployeeModel(userName,
						UserModel.UserType.EMPLOYEE);
				myUserController = new EmployeeController(myUserModel);
			} else if (userType == 2) {
				System.out
						.println("What is the name of your Non-Profit Organization?");
				String NPOname = myScanner.nextLine();
				System.out.println("Your NPO is " + NPOname);

				myUserModel = new NonProfitModel(userName,
						UserModel.UserType.NPO, NPOname, LocalDate.now()
								.minusYears(1).minusDays(1));
				myUserController = new NonProfitController(myUserModel);
			} else if (userType == 3) {
				myUserModel = new BidderModel(userName,
						UserModel.UserType.BIDDER);
				myUserController = new BidderController(myUserModel);
			}
			System.out.println("Welcome to Auction Central "
					+ myUserModel.getUserName());
			myUserList.add(myUserModel);
			addedUser = true;
		} else // user already is in user list
		{
			System.out.println("Cannot add the user name "
					+ userName
					+ " as it is already in our system!");
		}
		return addedUser;
	}

	// /**
	// * Check the list of Auctions to see if the NPO has an auction scheduled
	// already.
	// *
	// * @param theAuctions the list of auctions
	// * @param theNPOname the name of the NPO being searched for
	// * @return true if an auction exists, false otherwise
	// */
	//
	// public static boolean checkAuctions(List<AuctionModel> theAuctions,
	// String theNPOname, String theUserName)
	// {
	// boolean result = false;
	// for(int i = 0; i < theAuctions.size(); i++) {
	// if(theAuctions.get(i).getAuctionOrg().equals(theNPOname)) {// &&
	// theAuctions.get(i).getUserName().equals(theUserName)) {
	// result = true;
	// }
	// }
	// return result;
	// }

	/**
	 * This is the main program loop while a user is logged in. It executes the
	 * program including moving through the menus and calling the user
	 * controllers if more needs to be done.
	 * 
	 * @author Quinn.
	 */
	private void executeProgramLoop() {
		if (myUserModel.getUserType() == UserModel.UserType.NPO) {
			if (((NonProfitModel) myUserModel).getAuction() != null) {
				myCurrentAuction = ((NonProfitModel) myUserModel).getAuction();
			}
		}
		boolean notLogout = true;
		do {
			// print out the menu title
			System.out.println("\n"
					+ myUserController.menuTitle(myCurrentState));
			if (myCurrentState == UserController.Command.VIEWAUCTIONDETAILS) {
				System.out.println(AuctionCentralToStrings
						.auctionToString(myCurrentAuction));
			}
			ArrayList<UserController.Command> currentCommands = myUserController
					.GetMenu(myCurrentState, myCurrentItem, myUserModel);

			// print out available commands
			System.out.println("0) Logout");
			for (int i = 0; i < currentCommands.size(); i++) {
				System.out.print(i + 1 + ") ");
				System.out.println(myUserController
						.getCommandName(currentCommands.get(i)));
			}
			int commandInt = getNumberFromUserWithinRange(0,
					currentCommands.size());
			if (commandInt >= 0) {
				if (commandInt == 0) {
					notLogout = false;
				} else if (commandInt <= currentCommands.size()) {
					UserController.Command thisCommand = currentCommands
							.get(commandInt - 1);
					myCurrentState = myUserController.getNextState(
							myCurrentState, thisCommand);
					if (myCurrentState == UserController.Command.VIEWCALENDAR) {
						viewCalendarAuctions();
					} else if (myCurrentState == UserController.Command.VIEWBIDDERAUCTIONS) {
						viewBidderAuctions();
					}

					if (thisCommand == UserController.Command.VIEWITEM) {
						viewItems();
					}

					myUserController.ExecuteCommand(thisCommand, myCalendar,
							myCurrentAuction, myCurrentItem);

					if (myUserModel.getUserType() == UserModel.UserType.NPO
							&& thisCommand == UserController.Command.ADDAUCTION) {
						myCurrentAuction = ((NonProfitModel) myUserModel)
								.getAuction();
					}
					// neccessary to transfer the newest auction that was added
					// to the Calendar to our local AuctionList
					if (thisCommand.equals(UserController.Command.ADDAUCTION)) {
						Collection<ArrayList<AuctionModel>> auctions = myCalendar.myAuctionByDateList
								.values();
						Iterator<ArrayList<AuctionModel>> it = auctions
								.iterator();
						myAuctionList.clear();
						while (it.hasNext()) {
							ArrayList<AuctionModel> a = (ArrayList<AuctionModel>) it
									.next();
							for (int j = 0; j < a.size(); j++) {
								AuctionModel auction = a.get(j);
								myAuctionList.add(auction);
							}
						}
					}
				}

			} else {
				System.out.println("Please enter a valid selection.");
			}
		} while (notLogout);
	}

	/**
	 * This method displays the auctions that a bidder can bid on and allows the
	 * bidder to select one of them and look at its details.
	 * 
	 * @author Quinn
	 */
	private void viewBidderAuctions() {
		System.out.println("Future Auctions View");
		ArrayList<AuctionModel> futureAuctions = auctionMapToList(myCalendar
				.getAllFutureAuctions());
		if (futureAuctions == null || futureAuctions.size() == 0) {
			System.out.println("No Future Auctions Found.");
		} else {
			int AuctionListNumber = printOutAllAuctions(futureAuctions);
			int userAnswer = 0;
			System.out
					.println("Enter the number of an auction to view its details.\nPress 0 to go back.");
			userAnswer = getNumberFromUserWithinRange(0, AuctionListNumber);
			if (userAnswer > 0 && userAnswer <= AuctionListNumber - 1) {
				myCurrentState = UserController.Command.VIEWAUCTIONDETAILS;
				myCurrentAuction = futureAuctions.get(userAnswer - 1);
				// System.out.println(AuctionCentralToStrings.auctionToString(myCurrentAuction));
			} else {
				myCurrentState = UserController.Command.VIEWMAINMENU;
			}
		}
	}

	/**
	 * This method displays a list of items of the current auction and allows
	 * the user to choose one of the item to look at its details.
	 * 
	 * @author Quinn, Shannon
	 */
	private void viewItems() {

		List<ItemModel> theItems = myCurrentAuction.getAuctionItems();
		int theItemIndex = 1;
		if (theItems == null || theItems.size() == 0) {
			System.out.println("No items found for current auction.\n");
			myCurrentState = UserController.Command.VIEWAUCTIONDETAILS;
			// System.out.println(AuctionCentralToStrings.auctionToString(myCurrentAuction)
			// + "\n");
		} else {
			System.out.println("Item List View");
			for (ItemModel theItem : theItems) {
				System.out.print(theItemIndex + ") ");
				System.out.println(theItem.getItemName());
				theItemIndex++;
			}
			System.out.println("");
			int userAnswer = 0;
			if (myAuctionList.size() > 0) {
				System.out
						.println("Enter the number of an item to view/edit its details.\nEnter 0 to go back.");
				userAnswer = getNumberFromUserWithinRange(0, theItemIndex - 1);
			}
			if (userAnswer > 0) {
				myCurrentState = UserController.Command.VIEWITEM;
				myCurrentItem = theItems.get(userAnswer - 1);
				System.out.println(AuctionCentralToStrings
						.itemToString(myCurrentItem));
			} else {
				myCurrentState = UserController.Command.VIEWAUCTIONDETAILS;
				// System.out.println(AuctionCentralToStrings.auctionToString(myCurrentAuction));
			}
		}
	}

	/**
	 * Displays/Controls the View Calendar menu. This allows a user to look at a
	 * list of auctions and select one of them to view more details or go back
	 * to a previous menu.
	 * 
	 * @author Shannon, Quinn
	 */
	private void viewCalendarAuctions() {
		myAuctionList.clear();
		ArrayList<AuctionModel> theAuctionList = auctionMapToList(myCalendar
				.getAuctionsForCurrentMonth());
		if (theAuctionList.size() == 0) {
			System.out.println("No Auctions to view");
		} else {
			System.out.println("Auctions for "
					+ LocalDate.now().getMonth().name());
			int AuctionListNumber = printOutAllAuctions(theAuctionList);

			int userAnswer = 0;
			System.out
					.println("Enter the number of an auction to view its details.\nPress 0 to go back.");
			userAnswer = getNumberFromUserWithinRange(0, AuctionListNumber);
			if (userAnswer > 0 && userAnswer <= AuctionListNumber) {
				myCurrentState = UserController.Command.VIEWAUCTIONDETAILS;
				myCurrentAuction = theAuctionList.get(userAnswer - 1);
				// System.out.println(AuctionCentralToStrings.auctionToString(myCurrentAuction));
			} else {
				myCurrentState = UserController.Command.VIEWMAINMENU;
			}
		}
	}

	/**
	 * This method converts a map of auctions that came from the calendar and
	 * converts them to a list of AuctionModels.
	 * 
	 * @param theAuctionMap
	 *            This is the auction map that comes from the calendar.
	 * @return returns an arrayList of Auction Models
	 * @author Quinn
	 */
	private ArrayList<AuctionModel> auctionMapToList(
			Map<LocalDate, ArrayList<AuctionModel>> theAuctionMap) {
		ArrayList<AuctionModel> allAuctions = new ArrayList<AuctionModel>();
		if (theAuctionMap != null) {
			for (Entry<LocalDate, ArrayList<AuctionModel>> entry : theAuctionMap
					.entrySet()) {
				allAuctions.addAll(entry.getValue());
			}
		}
		return allAuctions;
	}

	/**
	 * This method is used to print out a list of auctions with a number next to
	 * it so the user can know which one they want to select.
	 * 
	 * @param theAuctionList
	 *            This is the auction list to be printed out.
	 * @return This returns the number of auctions in the auction list
	 * @author Shannon, Quinn
	 */
	private int printOutAllAuctions(ArrayList<AuctionModel> theAuctionList) {
		int AuctionListNumber = 1;
		for (int i = 0; i < theAuctionList.size(); i++) {
			AuctionModel thisAuction = theAuctionList.get(i);
			myAuctionList.add(thisAuction);
			System.out.print(AuctionListNumber + ") ");
			System.out.println(thisAuction.getAuctionName());
			AuctionListNumber++;
		}
		System.out.println("");
		return AuctionListNumber;
	}

	/**
	 * Gets the user input between a range of integer values. Will not let the
	 * user continue unless they select a value within the range.
	 * 
	 * @return the user's selection as an int
	 * @author Quinn
	 */
	private int getNumberFromUserWithinRange(int Start, int End) {
		return getNumberFromUserWithinRange(Start, End, Start);
	}

	/**
	 * Gets the user input between a range of integer values and includes a
	 * hidden value option. Will not let the user continue unless they select a
	 * value within the range or the hidden value.
	 * 
	 * @return the user's selection as an int
	 * @author Quinn
	 */
	private int getNumberFromUserWithinRange(int Start, int End,
			int HiddenOption) {
		int commandInt = -1;
		boolean validCommand = false;
		do {
			String TempString = myScanner.nextLine();
			try {
				commandInt = Integer.parseInt(TempString);
			} catch (Exception e) {
				validCommand = false;
			}
			if ((commandInt < Start || commandInt > End)
					&& commandInt != HiddenOption) {
				validCommand = false;
				System.out.println("Enter a Number between " + Start + " and "
						+ End);
			} else {
				validCommand = true;
			}
		} while (!validCommand);
		return commandInt;
	}
}
