package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.AuctionException;
import model.*;

/**
 * Controller for a NonProfit user using the system and gives the functionality
 * to move around the menus.
 * 
 * @author TCSS 360 Group 5
 */
public class NonProfitController extends UserController {
	/**
	 * This is the NonProfitModel associated with this class.
	 */
	private NonProfitModel myNonProfitModel;

	/**
	 * Creates a new NonProfitController using the EmployeeModel passed in.
	 * 
	 * @param theModel
	 *            the NonProfitModel to be used by the controller.
	 * @author Quinn
	 */
	public NonProfitController(UserModel theModel) {
		super("Non Profit");
		myNonProfitModel = (NonProfitModel) theModel;
	}

	/**
	 * This method accepts a menu as a user command and returns all the menu
	 * options for that menu for this user if applicable. If there are no menu
	 * options available for that menu for that user, then it returns an empty
	 * list.
	 * 
	 * @param theCommand
	 *            This is the menu that needs menu options
	 * @param theItem
	 *            This is used in the bidder class for menu selection
	 * @param theUser
	 * @return returns a list of all the commands that will be put into the next
	 *         user menu
	 * @author Quinn
	 */
	@Override
	public ArrayList<Command> GetMenu(Command theCommand, ItemModel theItem,
			UserModel theUser) {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCommand) {
		case VIEWMYAUCTION:
			if(((NonProfitModel)theUser).getAuction() != null)
			{
			System.out.println("\n"
					+ AuctionCentralToStrings
							.auctionToString(((NonProfitModel) theUser)
									.getAuction()));
			answer.add(UserController.Command.GOBACK);
			answer.add(UserController.Command.EDITAUCTION);
			answer.add(UserController.Command.VIEWITEM);
			answer.add(UserController.Command.ADDITEM);
			}
			else
			{
				System.out.println("You do not have an auction scheduled to edit.");
				answer.add(UserController.Command.ADDAUCTION);
				answer.add(UserController.Command.GOBACK);
			}
			break;
		case VIEWITEM:
			answer.add(UserController.Command.GOBACK);
			answer.add(UserController.Command.EDITITEM);
			break;
		case VIEWMAINMENU:
			answer.add(UserController.Command.ADDAUCTION);
			answer.add(UserController.Command.VIEWMYAUCTION);
			// if(myNonProfitModel.canAddAuction()) {
			// answer.add(UserController.Command.ADDAUCTION);
			// } else {
			// answer.add(UserController.Command.VIEWMYAUCTION);
			// }
			break;
		default:
			System.out.println("Menu Command Not Recognized");
			break;
		}
		return answer;
	}

	/**
	 * This method executes commands if the command is something the user can
	 * execute and does nothing otherwise.
	 * 
	 * @param theCommand
	 *            this is the action the user wants to take
	 * @param theCalendar
	 *            this is the calendar object needed in some situations
	 * @param theAuction
	 *            this is the auction the user is currently looking at if they
	 *            are in the Auction details menu
	 * @param theItem
	 *            this is the item the user is currently looking at if they are
	 *            at the item menu
	 * @author Quinn
	 */
	@Override
	public void ExecuteCommand(Command theCommand, CalendarModel theCalendar,
			AuctionModel theAuction, ItemModel theItem) {
		Scanner user_input = new Scanner(System.in);
		switch (theCommand) {
		case ADDAUCTION:
			addAuction(user_input, theCalendar);
			break;
		case EDITAUCTION:
			editAuction(user_input, theCalendar);
			break;
		case ADDITEM:
			ItemModel tempItem = getItemDetailsFromUser(user_input);
			myNonProfitModel.getAuction().addItem(tempItem);
			break;
		case EDITITEM:
			editItem(theItem, user_input, theAuction);
			break;
		default:
			break;
		}
	}

	/**
	 * This method returns the current state of which menu the user is in if and
	 * only if the current command is an option to view another valid menu for
	 * this user.
	 * 
	 * @param theCurrentState
	 *            This is the menu the user is currently in
	 * @param theCurrentCommand
	 *            This is the command the user just selected
	 * @return returns the next state if available for this user.
	 * @author Quinn
	 */
	@Override
	public UserController.Command goForwardState(
			UserController.Command theCurrentState,
			UserController.Command theCurrentCommand) {
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.VIEWMAINMENU) {
			answer = UserController.Command.VIEWMAINMENU;
		} else if (theCurrentCommand == UserController.Command.VIEWMYAUCTION) {
			answer = UserController.Command.VIEWMYAUCTION;
		}
		return answer;
	}

	/**
	 * This method determines the previous state for the user and returns it. If
	 * there is no previous state from the current state, it displays a message
	 * to the user and then returns the unchanged current state.
	 * 
	 * @param theCurrentState
	 *            This is what menu the user is currently in.
	 * @return returns the previous menu if possible and returns the current
	 *         state otherwise
	 * @author Quinn
	 */
	@Override
	public UserController.Command goBackState(
			UserController.Command theCurrentState) {
		UserController.Command answer = null;
		switch (theCurrentState) {
		case VIEWMYAUCTION:
			answer = UserController.Command.VIEWMAINMENU;
			break;
		case VIEWITEM:
			answer = UserController.Command.VIEWMYAUCTION;
			break;
		default:
			System.out.println("Cannot Go Back");
			break;
		}
		return answer;
	}

	/**
	 * This method is the User Interface for editing an item.
	 * 
	 * @param theItem  This is the item to be edited
	 * @param user_input  This is the scanner to get user input
	 * @param theAuction  This is the auction that the item is in.
	 * @author Quinn
	 */
	private void editItem(ItemModel theItem, Scanner user_input,
			AuctionModel theAuction) {
		System.out.println("The current Item details:");
		System.out.println(AuctionCentralToStrings.itemToString(theItem));
		ItemModel tempEditItem = getItemDetailsFromUser(user_input);
		try {
			theAuction.removeItem(theItem);
			theAuction.addItem(tempEditItem);
			System.out.println("Edited Item details:");
			System.out.println(AuctionCentralToStrings
					.itemToString(tempEditItem));
		} catch (Exception e) {
			theAuction.addItem(theItem);
		}
	}

	/**
	 * This method is the user interface to add an auction and checks the model to make 
	 * sure that the auction can be added.
	 * 
	 * @param user_input This is the scanner to get user input.
	 * @param theCalendar This is the calendar that the auction will be added to.
	 * @author Quinn
	 */
	private void addAuction(Scanner user_input, CalendarModel theCalendar) {
		LocalDateTime startTime;
		LocalDateTime endTime;
		int duration;
		startTime = getAuctionDateTimeFromUser(user_input);
		System.out
				.println("Please enter the duration (in hours) of the Auction");
		duration = user_input.nextInt();
		endTime = startTime.plusHours(duration);
		boolean auctionCanBeAdded = false;
		if (startTime.getDayOfMonth() != endTime.getDayOfMonth()) {
			System.out
					.println("Your auction is scheduled for more than one calendar day. It has not been scheduled.");
		} else {
			try {
				myNonProfitModel.check365(startTime.toLocalDate());
				auctionCanBeAdded = true;
			} catch (AuctionException e) {
				System.out.println(e.getExceptionString());
			}
		}
		if (auctionCanBeAdded) {
			AuctionModel tempAuction = new AuctionModel(
					myNonProfitModel.getNPOName(),
					myNonProfitModel.getUserName(), startTime, endTime);
			try {
				theCalendar.addAuction(tempAuction);
				System.out.println("Auction added!");
				myNonProfitModel.setAuction(tempAuction);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * This is the user interface to edit the auction and checks the model to make
	 * sure that the auction can be added in the way the user wishes without 
	 * violating business rules
	 * 
	 * @param user_input This is the scanner to get user input.
	 * @param theCalendar This is the calendar object that holds the auctions
	 * @author Quinn
	 */
	private void editAuction(Scanner user_input, CalendarModel theCalendar) {
		System.out.println("The current Auction details:");
		System.out.println(AuctionCentralToStrings
				.auctionToString(myNonProfitModel.getAuction()));
		LocalDateTime startTime;
		LocalDateTime endTime;
		System.out
				.println("Would you like to edit the auction? (Enter 0 to go back, 1 to edit)");
		int decision = user_input.nextInt();
		if (decision < 0 || decision > 1) {
			do {
				System.out
						.println("Invalid input. Would you like to edit the auction? (Enter 0 to go back, 1 to edit)");
				decision = user_input.nextInt();
			} while (decision < 0 || decision > 1);
		}
		if (decision == 1) {
			startTime = getAuctionDateTimeFromUser(user_input);
			int duration = 0;
			boolean keepGoing = true;
			do {
				System.out
						.println("Please enter the duration (in hours) of the Auction");
				duration = user_input.nextInt();
				if (duration < 1) {
					System.out.println("Must be at least 1 hour");
				} else if (duration > 24) {
					System.out.println("Duration can only be up to 23 hours.");
				} else {
					keepGoing = false;
				}
			} while (keepGoing);

			endTime = startTime.plusHours(duration);
			if (startTime.getDayOfMonth() != endTime.getDayOfMonth()) {
				System.out
						.println("Your auction is scheduled for more than one calendar day. It has not been scheduled.");
			} else {
				List<ItemModel> auctionItems = myNonProfitModel.getAuction()
						.getAuctionItems();
				AuctionModel newAuction = new AuctionModel(
						myNonProfitModel.getNPOName(),
						myNonProfitModel.getUserName(), startTime, endTime,
						auctionItems);

				try {
					theCalendar.removeAuction(myNonProfitModel.getAuction());
					theCalendar.addAuction(newAuction);
					myNonProfitModel.setAuction(newAuction);
					System.out.println("Auction has been edited.");
					System.out.println("Edited Auction Details:");
					System.out.println(AuctionCentralToStrings
							.auctionToString(myNonProfitModel.getAuction()));
					// System.out.println(myNonProfitModel.getAuction().toString());
				} catch (Exception e) {
					System.out.println(e.getMessage());

					// theCalendar.addAuction(myNonProfitModel.getAuction());
					// System.out.println("There was an error. Your auction has not been edited.");
				}
			}
		}
		System.out.println("What would you like to do next?");
	}

	/**
	 * This is the user interface to get the details of an item.
	 * 
	 * @param user_input This is the scanner to get user input.
	 * @return returns the item created from the user input values.
	 * @author Quinn
	 */
	private ItemModel getItemDetailsFromUser(Scanner user_input) {
		System.out.println("Please enter the Item name:");
		String itemName = user_input.nextLine();
		System.out.println("Please enter the Minimum Bid:");
		double minimumPrice = user_input.nextDouble();
		user_input.nextLine();
		System.out.println("Please enter the Item Description:");
		String description = user_input.nextLine();
		return new ItemModel(itemName, minimumPrice, description);
	}

	/**
	 * This is the user interface to get the auction date and time details. 
	 * 
	 * @param theInput This is the scanner to get user input.
	 * @return returns the start time for the auction.
	 * @author Quinn
	 */
	private LocalDateTime getAuctionDateTimeFromUser(Scanner theInput) {
		int year;
		int month;
		int day;
		int hour;
		int minutes;

		boolean yearNotValid = true;
		do {
			System.out.println("Please enter the Auction year:");
			year = theInput.nextInt();
			if (year < LocalDate.now().getYear()
					|| year > LocalDate.now().plusYears(1).getYear()) {
				System.out
						.println("Auctions can only be sheduled this year and next year.");
			} else {
				try {
					myNonProfitModel.checkYear(year);
					yearNotValid = false;
				} catch (AuctionException e) {
					System.out.println(e.getExceptionString());
				}
			}
		} while (yearNotValid);
		System.out.println("Please enter the Auction month:");
		month = theInput.nextInt();
		if (month > 12 || month < 1) {
			do {
				System.out
						.println("Invalid month. Please enter the Auction month:");
				month = theInput.nextInt();
			} while (month > 12 || month < 1);
		}
		System.out.println("Please enter the Auction day:");
		day = theInput.nextInt();
		if (day > (Month.of(month)
				.length(((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))))
				|| day < 1) {
			do {
				System.out
						.println("Invalid day. Please enter the Auction day:");
				day = theInput.nextInt();
			} while (day > (Month.of(month)
					.length(((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))))
					|| day < 1);
		}
		System.out.println("Please enter the Auction hour:");
		hour = theInput.nextInt();
		if (hour > 23 || hour < 0) {
			do {
				System.out
						.println("Invalid hour. Please enter the Auction hour:");
				hour = theInput.nextInt();
			} while (hour > 23 || hour < 0);
		}
		System.out.println("Please enter the Auction minute:");
		minutes = theInput.nextInt();
		if (minutes > 59 || minutes < 0) {
			do {
				System.out
						.println("Invalid minute. Please enter the Auction minute:");
				minutes = theInput.nextInt();
			} while (minutes > 59 || minutes < 0);
		}
		return LocalDateTime.of(year, month, day, hour, minutes);
	}
}