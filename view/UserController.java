package view;

import java.util.ArrayList;

import model.AuctionModel;
import model.CalendarModel;
import model.ItemModel;
import model.UserModel;

/**
 * This is the parent class for all user controllers.  It holds the main menu message and 
 * holds the titles to all the menus
 * @author UWT group 5
 */
public class UserController {

	/**
	 * These are the different menu options that a user might select and some are also the names
	 * of the menus that result from that selection.
	 * @author Quinn
	 */
	public enum Command {
		VIEWCALENDAR, VIEWMAINMENU, VIEWITEM, VIEWMYAUCTION, VIEWAUCTIONDETAILS, VIEWEDITITEM, ADDAUCTION, EDITAUCTION, ADDITEM, EDITITEM, GOBACK, BID, EDITBID, LOGIN, VIEWBIDS, VIEWBIDDERAUCTIONS
	}

	/**
	 * This is the message that will be displayed at the home page of the user indicating the user type.
	 */
	private String myHomePageMessage;
	
	/**
	 * This is used in makeing the home page message.
	 */
	private static final String myHomePageMessageEnd = " Homepage\n"
			+ "----------------------------------\n"
			+ "What would you like to do?\n";

	/**
	 * This Constructor takes in the user string and creates the home page message.
	 * 
	 * @param theUserTypeString this is the user type string that will be displayed on the home page message
	 * @author Quinn
	 */
	public UserController(String theUserTypeString) {
		myHomePageMessage = "\nAuctionCentral " + theUserTypeString
				+ myHomePageMessageEnd;
	}

	/**
	 * Takes in a user command and then if that command is a view command, returns the menu title
	 * for that view.
	 * 
	 * @param myCurrentState this is the menu that needs a title
	 * @return returns the title of the current state
	 * @author Quinn
	 */
	protected String menuTitle(Command myCurrentState) {
		String answerString = "";

		switch (myCurrentState) {
		case VIEWCALENDAR:
			answerString = "Calendar Menu";
			break;
		case VIEWITEM:
			answerString = "Item Menu";
			break;
		case VIEWMAINMENU:
			answerString = myHomePageMessage;
			break;
		case VIEWMYAUCTION:
			answerString = "My Auction";
			break;
		case VIEWBIDDERAUCTIONS:
			answerString = "All Upcomming Auctions";
			break;
		case VIEWAUCTIONDETAILS:
			answerString = "Auction Details";
			break;
		default:
			break;
		}
		return answerString;
	}

	/**
	 * Returns the command string based on the command passed in.  This is used to 
	 * produce the menus for the user to select from.
	 * 
	 * @param theCurrentCommand This is the command that the user can select
	 * @return a String of the menu title
	 * @author Quinn
	 */
	protected String getCommandName(Command theCurrentCommand) {
		String answerString = "";

		switch (theCurrentCommand) {
		case VIEWCALENDAR:
			answerString = "View Calendar";
			break;
		case VIEWITEM:
			answerString = "View Items";
			break;
		case VIEWEDITITEM:
			answerString = "View/Edit Items";
			break;
		case ADDAUCTION:
			answerString = "Add Auction";
			break;
		case ADDITEM:
			answerString = "Add Item";
			break;
		case BID:
			answerString = "Bid";
			break;
		case EDITAUCTION:
			answerString = "Edit My Auction";
			break;
		case EDITBID:
			answerString = "Edit Bid";
			break;
		case EDITITEM:
			answerString = "Edit Item";
			break;
		case GOBACK:
			answerString = "Go Back to Previous Menu";
			break;
		case VIEWBIDS:
			answerString = "View Bids";
			break;
		case VIEWBIDDERAUCTIONS:
			answerString = "View All Auctions";
			break;
		case VIEWMYAUCTION:
			answerString = "View My Auction";
			break;
		default:
			answerString = "myCurrentState Command";
			break;
		}
		return answerString;
	}

	/**
	 * This method executes commands if the command is something the user can execute and 
	 * does nothing otherwise.
	 * 
	 * @param theCommand this is the action the user wants to take
	 * @param theCalendar this is the calendar object needed in some situations 
	 * @param theAuction this is the auction the user is currently looking at if they are in the Auction details menu
	 * @param theItem this is the item the user is currently looking at if they are at the item menu
	 * @author Quinn
	 */
	public void ExecuteCommand(Command theCommand, CalendarModel theCalendar,
			AuctionModel theAuction, ItemModel theItem) {
	}

	/**
	 * This method returns the current state of which menu the user
	 * is in if and only if the current command is an option to view
	 * another valid menu for this user.
	 * 
	 * @param theCurrentState This is the menu the user is currently in
	 * @param theCurrentCommand This is the command the user just selected
	 * @return returns the next state if available for this user.
	 * @author Quinn
	 */
	public UserController.Command goForwardState(
			UserController.Command theCurrentState,
			UserController.Command theCurrentCommand) {
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.VIEWCALENDAR) {
			answer = UserController.Command.VIEWCALENDAR;
		} else if (theCurrentCommand == UserController.Command.VIEWMAINMENU) {
			answer = UserController.Command.VIEWMAINMENU;
		}
		return answer;
	}

	/**
	 * This method simply determines whether to call the go back method or go forward method
	 * 
	 * @param theCurrentState This is what menu the user is currently in
	 * @param theCurrentCommand  This is the action the user would like to perform
	 * @return returns a new menu/view state if the user command required it
	 * @author Quinn
	 */
	public UserController.Command getNextState(
			UserController.Command theCurrentState,
			UserController.Command theCurrentCommand) {
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.GOBACK) {
			answer = goBackState(theCurrentState);
		} else {
			answer = goForwardState(theCurrentState, theCurrentCommand);
		}
		return answer;
	}

	/**
	 * This method determines the previous state for the user and returns it.  If there is no previous state
	 * from the current state, it displays a message to the user and then returns the unchanged current state.
	 * 
	 * @param theCurrentState This is what menu the user is currently in.
	 * @return returns the previous menu if possible and returns the current state otherwise
	 * @author Quinn
	 */
	public UserController.Command goBackState(
			UserController.Command theCurrentState) {
		UserController.Command answer = theCurrentState;
		switch (theCurrentState) {
		case VIEWAUCTIONDETAILS:
			answer = UserController.Command.VIEWCALENDAR;
			break;
		default:
			System.out.println("Cannot Go Back");
			break;
		}
		return answer;
	}

	/**
	 * This method accepts a menu as a user command and returns all the menu options
	 * for that menu for this user if applicable.  If there are no menu options available
	 * for that menu for that user, then it returns an empty list.
	 * 
	 * @param theCommand This is the menu that needs menu options
	 * @param theItem This is used in the bidder class for menu selection
	 * @param theUser 
	 * @return returns a list of all the commands that will be put into the next user menu
	 * @author Quinn
	 */
	public ArrayList<Command> GetMenu(Command theCommand, ItemModel theItem,
			UserModel theUser) {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCommand) {
		case VIEWAUCTIONDETAILS:
			answer.add(UserController.Command.GOBACK);
			break;
		// CALENDAR has its own menu
		case VIEWMAINMENU:
			answer.add(UserController.Command.VIEWCALENDAR);
			break;
		default:
			System.out.println("Menu Not Recognized");
			break;
		}
		return answer;
	}

}
