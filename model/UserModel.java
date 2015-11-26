package model;

/**
 * 
 *
 */
public class UserModel
{
  /**
 * 
 * This enum represents what kind of user the user is.
 */
public enum UserType {NPO, EMPLOYEE, BIDDER}
  
  private UserType myUserType;
  private String myUserName;

  
  /**
 * @param theUserName
 * @param theUserType
 */
public UserModel(String theUserName, UserType theUserType)
  {
	  myUserName = theUserName;
	  myUserType = theUserType;	  
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
	
//	protected String menuTitle(Command myCurrentState)
//	{
//	String answerString = "";
//	
//		switch (myCurrentState)
//		 {
//		 case VIEWCALENDAR:
//	 		answerString = "Calendar Menu";
//			break;
//		case VIEWITEM:
//			answerString = "Item Menu";
//			break;	
//		case VIEWMAINMENU:
//			answerString = myHomePageMessage;
//			break;
//		case VIEWMYAUCTION:
//			answerString = "My Auction";
//			break;
//		case VIEWBIDDERAUCTIONS:
//			answerString ="All Upcomming Auctions";
//			break;
//		default:
//			//System.out.println("need to add menu title");
//			break;
//		
//		 }
//		return answerString;
//	}
//	/**
//	 * Returns the menu title based on the current state.
//	 * 
//	 * @return a String of the menu title
//	 */
//	protected String getCommandName(Command myCurrentState) 
//	{
//		String answerString = "";
//	
//		switch (myCurrentState)
//		 {
//	 	case VIEWCALENDAR:
//	 		answerString = "View Calendar";
//			break;
//		case VIEWITEM:
//			answerString = "View Items";
//			break;		
//		case VIEWEDITITEM:
//			answerString ="View/Edit Items";
//			break;
//		case ADDAUCTION:
//			answerString ="Add Auction";
//			break;
//		case ADDITEM:
//			answerString ="Add Item";
//			break;
//		case BID:
//			answerString ="Bid";
//			break;
//		case EDITAUCTION:
//			answerString ="Edit My Auction";
//			break;
//		case EDITBID:
//			answerString ="Edit Bid";
//			break;
//		case EDITITEM:
//			answerString ="Edit Item";
//			break;
//		case GOBACK:
//			answerString ="Go Back to Previous Menu";
//			break;
//		case VIEWBIDS:
//			answerString ="View Bids";
//			break;
//		case VIEWBIDDERAUCTIONS:
//			answerString ="View All Auctions";
//			break;
//		case VIEWMYAUCTION:
//			answerString ="View My Auction";
//			break;
//		default:
//			answerString = "myCurrentState Command";
//			break;	
//		 }
//		return answerString;
//	}
//	
//	
//  
//  /**
// * @param theCommand
// * @param theCalendar
// * @param theAuction
// * @param theItem
// * @return
// */
//public boolean ExecuteCommand(Command theCommand, CalendarModel theCalendar, AuctionModel theAuction, ItemModel theItem)
//  {
//	  boolean answer = false;
//	  //System.out.println("Command Not Recognized");
//	  return answer;
//  }
//
//public UserModel.Command goForwardState(UserModel.Command theCurrentState, UserModel.Command theCurrentCommand)
//{
//	UserModel.Command answer = theCurrentState;
//	if (theCurrentCommand == User.UserModel.VIEWCALENDAR)
//	 {
//		answer = User.UserModel.VIEWCALENDAR;
//	 } 
//	 else if (theCurrentCommand == User.UserModel.VIEWMAINMENU)
//	 {
//		 answer = User.UserModel.VIEWMAINMENU;
//	 } 		 
//	return answer;
//}
//
//public UserModel.Command getNextState(UserModel.Command theCurrentState, UserModel.Command theCurrentCommand)
//{
//	UserModel.Command answer = theCurrentState;
//	if (theCurrentCommand == User.UserModel.GOBACK)
//	{
//		answer = goBackState(theCurrentState);
//	} else {
//		answer = goForwardState(theCurrentState, theCurrentCommand);
//	}
//	return answer;
//}
//  
//  public UserModel.Command goBackState(UserModel.Command theCurrentState) 
//	{
//	  UserModel.Command answer = theCurrentState;
//		switch (theCurrentState)
//		 {			
//			case VIEWCALENDAR:
//				answer = User.UserModel.VIEWMAINMENU;
//				break;
//			case VIEWAUCTIONDETAILS:
//				answer = User.UserModel.VIEWCALENDAR;
//				break;
//	 		default:
//	 			System.out.println("Cannot Go Back");
//	 			break;						 
//		 }		
//		return answer;
//	}
	
	  /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		  return myUserName + " " + myUserType.toString();
	  }
	
//	  public ArrayList<Command> GetMenu(Command theCommand, ItemModel theItem)
//	  {
//			ArrayList<Command> answer = new ArrayList<Command>();
//			switch (theCommand) {
//			case VIEWAUCTIONDETAILS:
//				answer.add(User.UserModel.GOBACK);
//				break;
//			case VIEWCALENDAR:
//				answer.add(User.UserModel.GOBACK);
//				answer.add(User.UserModel.VIEWAUCTIONDETAILS);
//				break;
//			case VIEWMAINMENU:
//				answer.add(User.UserModel.VIEWCALENDAR);
//				break;
//			default:
//				System.out.println("Menu Not Recognized");
//				break;
//			}
//			return answer;
//	  }
  
}
