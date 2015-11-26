package view;
import java.util.ArrayList;

import model.AuctionModel;
import model.CalendarModel;
import model.ItemModel;
import model.UserModel;

/**
 * 
 *
 */
public class UserController
{

  /**
 * These are the different menu options that a user might select.
 *
 */
public enum Command {VIEWCALENDAR, VIEWMAINMENU, VIEWITEM, VIEWMYAUCTION, VIEWAUCTIONDETAILS, VIEWEDITITEM,
	  ADDAUCTION, EDITAUCTION, ADDITEM, EDITITEM, GOBACK, BID, EDITBID, LOGIN, VIEWBIDS, VIEWBIDDERAUCTIONS}
  
  private String myHomePageMessage;
  
	private static final String myHomePageMessageEnd = " Homepage\n"
	+ "----------------------------------\n"
	+ "What would you like to do?\n";
  
  /**
 * @param theUserName
 * @param theUserType
 */
public UserController(String theUserType)
  {
	  myHomePageMessage = "\nAuctionCentral " + theUserType + myHomePageMessageEnd;
  }

	protected String menuTitle(Command myCurrentState)
	{
	String answerString = "";
	
		switch (myCurrentState)
		 {
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
			answerString ="All Upcomming Auctions";
			break;
		default:
			//System.out.println("need to add menu title");
			break;
		
		 }
		return answerString;
	}
	/**
	 * Returns the menu title based on the current state.
	 * 
	 * @return a String of the menu title
	 */
	protected String getCommandName(Command myCurrentState) 
	{
		String answerString = "";
	
		switch (myCurrentState)
		 {
	 	case VIEWCALENDAR:
	 		answerString = "View Calendar";
			break;
		case VIEWITEM:
			answerString = "View Items";
			break;		
		case VIEWEDITITEM:
			answerString ="View/Edit Items";
			break;
		case ADDAUCTION:
			answerString ="Add Auction";
			break;
		case ADDITEM:
			answerString ="Add Item";
			break;
		case BID:
			answerString ="Bid";
			break;
		case EDITAUCTION:
			answerString ="Edit My Auction";
			break;
		case EDITBID:
			answerString ="Edit Bid";
			break;
		case EDITITEM:
			answerString ="Edit Item";
			break;
		case GOBACK:
			answerString ="Go Back to Previous Menu";
			break;
		case VIEWBIDS:
			answerString ="View Bids";
			break;
		case VIEWBIDDERAUCTIONS:
			answerString ="View All Auctions";
			break;
		case VIEWMYAUCTION:
			answerString ="View My Auction";
			break;
		default:
			answerString = "myCurrentState Command";
			break;	
		 }
		return answerString;
	}
	
	
  
  /**
 * @param theCommand
 * @param theCalendar
 * @param theAuction
 * @param theItem
 * @return
 */
public boolean ExecuteCommand(Command theCommand, CalendarModel theCalendar, AuctionModel theAuction, ItemModel theItem)
  {
	  boolean answer = false;
	  //System.out.println("Command Not Recognized");
	  return answer;
  }

	
	public UserController.Command goForwardState(UserController.Command theCurrentState, UserController.Command theCurrentCommand)
	{
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.VIEWCALENDAR)
		 {
			answer = UserController.Command.VIEWCALENDAR;
		 } 
		 else if (theCurrentCommand == UserController.Command.VIEWMAINMENU)
		 {
			 answer = UserController.Command.VIEWMAINMENU;
		 } 		 
		return answer;
	}
	
	public UserController.Command getNextState(UserController.Command theCurrentState, UserController.Command theCurrentCommand)
	{
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.GOBACK)
		{
			answer = goBackState(theCurrentState);
		} else {
			answer = goForwardState(theCurrentState, theCurrentCommand);
		}
		return answer;
	}
	  
	  public UserController.Command goBackState(UserController.Command theCurrentState) 
		{
		  UserController.Command answer = theCurrentState;
			switch (theCurrentState)
			 {			
//				case VIEWCALENDAR:
//					answer = UserController.Command.VIEWMAINMENU;
//					break;
				case VIEWAUCTIONDETAILS:
					answer = UserController.Command.VIEWCALENDAR;
					break;
		 		default:
		 			System.out.println("Cannot Go Back");
		 			break;						 
			 }		
			return answer;
		}
	
	  public ArrayList<Command> GetMenu(Command theCommand, ItemModel theItem, UserModel theUser)
	  {
			ArrayList<Command> answer = new ArrayList<Command>();
			switch (theCommand) {
			case VIEWAUCTIONDETAILS:
				answer.add(UserController.Command.GOBACK);
				break;
			//CALENDAR has its own menu	
//			case VIEWCALENDAR:
//				answer.add(UserController.Command.GOBACK);
//				answer.add(UserController.Command.VIEWAUCTIONDETAILS);
//				break;
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
