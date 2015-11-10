import java.util.List;

import java.time.LocalDateTime;

public class Auction
{
  // public for now
  public List<Item> inventory;
  public String orgName;
  //public Date auctionDate;
  public String auctionName;
  
  //Fields for local date
  LocalDateTime startTime;
  LocalDateTime endTime;
  
  //create auction constructor
  public Auction(String theOrgName, String myAuctionName, LocalDateTime myStartTime, LocalDateTime myEndTime)
  {
	  orgName = theOrgName;
	  auctionName = myAuctionName;
	  startTime = myStartTime;
	  endTime = myEndTime;
  }
  
  //create another auction constructor with same inventory list - copy constructor?
  public Auction(String theOrgName, String myAuctionName, LocalDateTime myStartTime, LocalDateTime myEndTime, List<Item> myInventory)
  {
	  orgName = theOrgName;
	  auctionName = myAuctionName;
	  startTime = myStartTime;
	  endTime = myEndTime;
	  inventory = myInventory;
  }
  
  //get the auction organization
  public String getAuctionOrg()
  {
    return orgName;
  }
  
  //set the auction's organization name
  public boolean setAuctionOrg(String theOrgName)
  {
    orgName = theOrgName;
    return true;
  }
  
  //return the list of auction items
  public List<Item> getAuctionItems()
  {
    return inventory;
  }
  
  //add item to auction inventory list
  public boolean addItem(Item myItem)
  {
	  inventory.add(myItem);
	  return true;
  }
  
  //edit the item name
  public boolean editItemName(Item myItem, String newName)
  {
	  inventory.get(inventory.indexOf(myItem)).setItemName(newName);
	  return true;
  }
  
  //edit the starting bid of the item
  public boolean editItemStartingBid(Item myItem, double myStartingBid)
  {
	  inventory.get(inventory.indexOf(myItem)).setStartingBid(myStartingBid);
	  return true;
  }
  
  //method to edit item's description
  public boolean editItemDescription(Item myItem, String description)
  {
	  inventory.get(inventory.indexOf(myItem)).setDescription(description);
	  return true;
  }
 
  //get auction name
  public String getAuctionName()
  {
	  return auctionName;
  }
  
  //set auction name
  public void setAuctionName(String myAuctionName)
  {
	  auctionName = myAuctionName;
  }
  
  //returns start time of the auction
  public LocalDateTime getStartTime()
  {
	  return endTime;
  }
  
  //sets the starting time of the auction
  public boolean setStartTime(LocalDateTime myStartTime)
  {
	  endTime = myStartTime;
	  return true;	  
  }
  
  //returns the ending time of the auction
  public LocalDateTime getEndTime()
  {
	  return endTime;
  }
  
  //set end time of the auction
  public boolean setEndTime(LocalDateTime myEndTime)
  {
	  endTime = myEndTime;
	  return true;	  
  }

}
