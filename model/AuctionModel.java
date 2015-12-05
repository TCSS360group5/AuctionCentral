package model;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AuctionModel implements Serializable
{
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
	
	
  public List<ItemModel> myInventory;
  public String myOrgName;
  public String myAuctionName;
  
  //Fields for local date
  LocalDateTime myStartTime;
  LocalDateTime myEndTime;
  //LocalDateTime myDate;
  String myUserName;
  
  //create auction constructor
  public AuctionModel(String theOrgName, String theUserName, LocalDateTime theStartTime, LocalDateTime theEndTime)
  {
	  this(theOrgName, theUserName, theStartTime, theEndTime, null);
  }
  


//create another auction constructor with same inventory list - copy constructor?
  public AuctionModel(String theOrgName, String theUserName, LocalDateTime theStartTime, LocalDateTime theEndTime, List<ItemModel> theInventory)
  {
	  myOrgName = theOrgName;  
	  myUserName = theUserName;
	  myStartTime = theStartTime;
	  updateAuctionName();
	  myEndTime = theEndTime;
	  if (theInventory != null)
	  {
		  myInventory = theInventory;
	  }
	  else 
	  {
		  myInventory = new ArrayList<ItemModel>();
	  }
  }
  
  private void updateAuctionName() 
  {
	  myAuctionName = myOrgName.replace(' ', '-') + "-" + myStartTime.getMonth().name() + "-" + myStartTime.getDayOfMonth() + "-" + myStartTime.getYear();
  }
  
  //get the auction organization
  public String getAuctionOrg()
  {
    return myOrgName;
  }
  
  //set the auction's organization name
  public boolean setAuctionOrg(String theOrgName)
  {
    myOrgName = theOrgName;
    updateAuctionName();
    return true;
  }
  
  //return the list of auction items
  public List<ItemModel> getAuctionItems()
  {
    return myInventory;
  }
  
  //add item to auction inventory list
  public boolean addItem(ItemModel theItem)
  {
	  myInventory.add(theItem);
	  return true;
  }
  
  //remove item
  public boolean removeItem(ItemModel theItem) {
	  myInventory.remove(theItem);
	  return true;
  }
  
  //get auction name
  public String getAuctionName()
  {
	  return myAuctionName;
  }
  
  //returns start time of the auction
  public LocalDateTime getStartTime()
  {
	  return myStartTime;
  }
  
  //sets the starting time of the auction
  public boolean setStartTime(LocalDateTime theStartTime)
  {
	  myStartTime = theStartTime;
	  updateAuctionName();
	  return true;	  
  }
  
  //returns the ending time of the auction
  public LocalDateTime getEndTime()
  {
	  return myEndTime;
  }
  
  //set end time of the auction
  public boolean setEndTime(LocalDateTime theEndTime)
  {
	  myEndTime = theEndTime;
	  return true;	  
  }
  
  //sets the auction date
  public void setAuctionDate(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd) {
	myEndTime = theAuctionEnd;
	myStartTime = theAuctionStart;
	updateAuctionName();
  }
  
  //returns the user name
  public String getUserName() {
	  return myUserName;
  }


  public String toStringSimple(){
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myAuctionName + "\n");
	  answer.append("Date: " + myStartTime.getMonth() + " " + myStartTime.getDayOfMonth() +", " + myStartTime.getYear() + "\n");
	  return answer.toString();  
  }
  
  public String toString(){
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myAuctionName + "\n");
	  answer.append("Organization name: " + myOrgName + "\n");
	  answer.append("Date: " + myStartTime.getMonth() + " " + myStartTime.getDayOfMonth() +", " + myStartTime.getYear() + "\n");
	  answer.append("Start time: " + myStartTime.getHour() + ":" + myStartTime.getMinute() + "\n");
	  answer.append("End: " + myEndTime.getHour() + ":" + myEndTime.getMinute() + "\n");
	  if(myInventory!= null) 
	  {
		  answer.append("Items: " + myInventory.size());
	  } 
	  else 
	  {
		  answer.append("Items: 0");
	  }
	  return answer.toString();
  }

}
