import java.util.List;

import java.time.LocalDateTime;

public class Auction
{
  // public for now
  public List<Item> myInventory;
  public String myOrgName;
  //public Date auctionDate;
  public String myAuctionName;
  
  //Fields for local date
  LocalDateTime myStartTime;
  LocalDateTime myEndTime;
  
  //create auction constructor
  public Auction(String theOrgName, String theAuctionName, LocalDateTime theStartTime, LocalDateTime theEndTime)
  {
	  myOrgName = theOrgName;
	  myAuctionName = theAuctionName;
	  myStartTime = theStartTime;
	  myEndTime = theEndTime;
  }
  
  //create another auction constructor with same inventory list - copy constructor?
  public Auction(String theOrgName, String theAuctionName, LocalDateTime theStartTime, LocalDateTime theEndTime, List<Item> theInventory)
  {
	  myOrgName = theOrgName;
	  myAuctionName = theAuctionName;
	  myStartTime = theStartTime;
	  myEndTime = theEndTime;
	  myInventory = theInventory;
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
    return true;
  }
  
  //return the list of auction items
  public List<Item> getAuctionItems()
  {
    return myInventory;
  }
  
  //add item to auction inventory list
  public boolean addItem(Item myItem)
  {
	  myInventory.add(myItem);
	  return true;
  }
  
  //edit the item name
  public boolean editItemName(Item myItem, String newName)
  {
	  myInventory.get(myInventory.indexOf(myItem)).setItemName(newName);
	  return true;
  }
  
  //edit the starting bid of the item
  public boolean editItemStartingBid(Item myItem, double myStartingBid)
  {
	  myInventory.get(myInventory.indexOf(myItem)).setStartingBid(myStartingBid);
	  return true;
  }
  
  //method to edit item's description
  public boolean editItemDescription(Item myItem, String description)
  {
	  myInventory.get(myInventory.indexOf(myItem)).setDescription(description);
	  return true;
  }
 
  //get auction name
  public String getAuctionName()
  {
	  return myAuctionName;
  }
  
  //set auction name
  public void setAuctionName(String theAuctionName)
  {
	  myAuctionName = theAuctionName;
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
  }
  
  public String toString(){
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myAuctionName + "/n");
	  answer.append("org: " + myOrgName + "/n");
	  answer.append("Start: " + myStartTime + "/n");
	  answer.append("End: " + myEndTime + "/n");
	  answer.append("Items: " + myInventory.size() + "/n/n");
	  return answer.toString();
  }

}
