import java.util.ArrayList;

/**
 * 
 *
 */
public class User
{
  /**
 * 
 * This enum represents what kind of user the user is.
 */
public enum UserType {NPO, EMPLOYEE, BIDDER}
  /**
 * These are the different menu options that a user might select.
 *
 */
public enum Command {VIEWCALENDAR, VIEWMAINMENU, VIEWITEM, VIEWMYAUCTION, VIEWAUCTIONDETAILS, VIEWEDITITEM,
	  ADDAUCTION, EDITAUCTION, ADDITEM, EDITITEM, GOBACK, BID, EDITBID, LOGIN, VIEWBIDS, VIEWBIDDERAUCTIONS}
  
  private UserType myUserType;
  private String myUserName;
  private String myHomePageMessage;
  
	private static final String myHomePageMessageEnd = " Homepage\n"
	+ "----------------------------------\n"
	+ "What would you like to do?\n";
  
  /**
 * @param theUserName
 * @param theUserType
 */
public User(String theUserName, UserType theUserType)
  {
	  myUserName = theUserName;
	  myUserType = theUserType;
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
public boolean ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	  boolean answer = false;
	  //System.out.println("Command Not Recognized");
	  return answer;
  }

  
	  /**
	 * @return
	 */
	public UserType getUserType(){
		  return myUserType;
	  }
	  /**
	 * @return
	 */
	public String getUserName(){
		  return myUserName;
	  }
	  /**
	 * @param theUserType
	 */
	public void setUserType(UserType theUserType){
		  myUserType = theUserType;
	  }
	  /**
	 * @param theUsername
	 */
	public void setUserName(String theUsername){
		  myUserName = theUsername;
	  }
	
	public User.Command goForwardState(User.Command theCurrentState, User.Command theCurrentCommand)
	{
		User.Command answer = theCurrentState;
		if (theCurrentCommand == User.Command.VIEWCALENDAR)
		 {
			answer = User.Command.VIEWCALENDAR;
		 } 
		 else if (theCurrentCommand == User.Command.VIEWMAINMENU)
		 {
			 answer = User.Command.VIEWMAINMENU;
		 } 		 
		return answer;
	}
	
	public User.Command getNextState(User.Command theCurrentState, User.Command theCurrentCommand)
	{
		User.Command answer = theCurrentState;
		if (theCurrentCommand == User.Command.GOBACK)
		{
			answer = goBackState(theCurrentState);
		} else {
			answer = goForwardState(theCurrentState, theCurrentCommand);
		}
		return answer;
	}
	  
	  public User.Command goBackState(User.Command theCurrentState) 
		{
		  User.Command answer = theCurrentState;
			switch (theCurrentState)
			 {			
				case VIEWCALENDAR:
					answer = User.Command.VIEWMAINMENU;
					break;
				case VIEWAUCTIONDETAILS:
					answer = User.Command.VIEWCALENDAR;
					break;
		 		default:
		 			System.out.println("Cannot Go Back");
		 			break;						 
			 }		
			return answer;
		}
	
	  /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		  return myUserName + " " + myUserType.toString();
	  }
	
	  public ArrayList<Command> GetMenu(Command theCommand, Item theItem)
	  {
			ArrayList<Command> answer = new ArrayList<Command>();
			switch (theCommand) {
			case VIEWAUCTIONDETAILS:
				answer.add(User.Command.GOBACK);
				break;
			case VIEWCALENDAR:
				answer.add(User.Command.GOBACK);
				answer.add(User.Command.VIEWAUCTIONDETAILS);
				break;
			case VIEWMAINMENU:
				answer.add(User.Command.VIEWCALENDAR);
				break;
			default:
				System.out.println("Menu Not Recognized");
				break;
			}
			return answer;
	  }
  
}
