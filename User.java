import java.util.ArrayList;

public class User
{
  public enum UserType {NPO, EMPLOYEE, BIDDER}
  public enum Command {VIEWCALENDAR, VIEWMAINMENU, VIEWMAINAUCTIONS, VIEWITEM, VIEWMYAUCTION, VIEWCALENDARAUCTIONS,
	  VIEWONEAUCTION, ADDAUCTION, EDITAUCTION, ADDITEM, EDITITEM, GOBACK, BID, EDITBID, LOGIN, VIEWBIDS}
  
  private UserType myUserType;
  private String myUserName;
  
  public User(String theUserName, UserType theUserType)
  {
	  myUserName = theUserName;
	  myUserType = theUserType;
  }
  
  public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	  ArrayList<Command> answer = new ArrayList<Command>();
	  answer.add(User.Command.LOGIN);
	  return answer;
  }

  
  public UserType getUserType(){
	  return myUserType;
  }
  public String getUserName(){
	  return myUserName;
  }
  public void setUserType(UserType theUserType){
	  myUserType = theUserType;
  }
  public void setUserName(String theUsername){
	  myUserName = theUsername;
  }
  
  public User.Command goBackState(User.Command theCurrentState) 
	{
	  return null;
	}

  public String toString() {
	  return myUserName + " " + myUserType.toString();
  }
  
}
