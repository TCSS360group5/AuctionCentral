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
public enum Command {VIEWCALENDAR, VIEWMAINMENU, VIEWAUCTION, VIEWITEM,
	  ADDAUCTION, EDITAUCTION, ADDITEM, EDITITEM, GOBACK, BID, EDITBID, LOGIN, VIEWBIDS}
  
  private UserType myUserType;
  private String myUserName;
  
  /**
 * @param theUserName
 * @param theUserType
 */
public User(String theUserName, UserType theUserType)
  {
	  myUserName = theUserName;
	  myUserType = theUserType;
  }
  
  /**
 * @param theCommand
 * @param theCalendar
 * @param theAuction
 * @param theItem
 * @return
 */
public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	  ArrayList<Command> answer = new ArrayList<Command>();
	  answer.add(User.Command.LOGIN);
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

  /* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
public String toString() {
	  return myUserName + " " + myUserType.toString();
  }
  
}
