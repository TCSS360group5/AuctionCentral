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
>>>>>>> 791c892da5d07b9a1eed7c3a2a17e0efa15e1edc
  }
  
  public abstract ArrayList<Command> ExecuteCommand(Command theCommand, AuctionCalendar theCalendar, Auction theAuction, Item theItem);
  
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

}
