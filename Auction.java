import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Auction
{
  public List<Item> myInventory;
  public String myOrgName;
  public String myAuctionName;
  
  //Fields for local date
  LocalDateTime myStartTime;
  LocalDateTime myEndTime;
  LocalDateTime myDate;
  String myUserName;
  
  //create auction constructor
  public Auction(String theOrgName, String theUserName, LocalDateTime theStartTime, LocalDateTime theEndTime)
  {
	  this(theOrgName, theUserName, theStartTime, theEndTime, null);
  }
  
  private void updateAuctionName() 
  {
	  myAuctionName = myOrgName.replace(' ', '-') + "-" + myStartTime.getMonth().name() + "-" + myStartTime.getDayOfMonth() + "-" + myStartTime.getYear();
  }

//create another auction constructor with same inventory list - copy constructor?
  public Auction(String theOrgName, String theUserName, LocalDateTime theStartTime, LocalDateTime theEndTime, List<Item> theInventory)
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
		  myInventory = new ArrayList<Item>();
	  }
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
  public List<Item> getAuctionItems()
  {
    return myInventory;
  }
  
  //add item to auction inventory list
  public boolean addItem(Item theItem)
  {
	  myInventory.add(theItem);
	  return true;
  }
  
  //remove item
  public boolean removeItem(Item theItem) {
	  myInventory.remove(theItem);
	  return true;
  }
  
  //edit the item name
//  public boolean editItemName(Item theItem, String theNewName)
//  {
//	  myInventory.get(myInventory.indexOf(theItem)).setItemName(theNewName);
//	  return true;
//  }
  
  //edit the starting bid of the item
//  public boolean editItemStartingBid(Item theItem, double theStartingBid)
//  {
//	  myInventory.get(myInventory.indexOf(theItem)).setStartingBid(theStartingBid);
//	  return true;
//  }
  
  //method to edit item's description
//  public boolean editItemDescription(Item theItem, String theDescription)
//  {
//	  myInventory.get(myInventory.indexOf(theItem)).setDescription(theDescription);
//	  return true;
//  }
 
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

  public void setAuctionDate(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd) {
	myEndTime = theAuctionEnd;
	myStartTime = theAuctionStart;
	updateAuctionName();
  }
  
  public String getUserName() {
	  return myUserName;
  }
  
  public void setUserName(String theUserName) {
	  myUserName = theUserName;
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
