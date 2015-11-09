import java.util.ArrayList;

public abstract class User
{
  String username;
  public enum UserType {NPO, EMPLOYEE, BIDDER}
  public enum Command {VIEWCALENDAR, VIEWMAINMENU, VIEWAUCTION, VIEWITEM,
	  ADDAUCTION, EDITAUCTION, ADDITEM, EDITITEM, GOBACK, BID, EDITBID}
  
  private UserType myUserType;
  private String myUserName;
  
  public User(String theUserName, UserType theUserType)
  {
	  myUserName = theUserName;
	  myUserType = theUserType;
  }
  
  public abstract ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem);
  
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

  public String toString() {
	  return myUserName + " " + myUserType.toString();
  }
  
}
