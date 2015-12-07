package model;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents an auction.
 * 
 * @author TCSS 360 Group 5
 */
public class AuctionModel implements Serializable
{

  private static final long serialVersionUID = 1L;
  public List<ItemModel> myInventory;
  public String myOrgName;
  public String myAuctionName;
  
  //Fields for local date
  private LocalDateTime myStartTime;
  private LocalDateTime myEndTime;
  private String myUserName;
  
  /**
   * Constructor to create a new auction with no items.
   * 
   * @param theOrgName the organization name for this auction.
   * @param theUserName the username for the user who created this auction.
   * @param theStartTime the starting time for this auction.
   * @param theEndTime the ending time for this auction.
   */
  public AuctionModel(String theOrgName, String theUserName, LocalDateTime theStartTime, LocalDateTime theEndTime)
  {
	  this(theOrgName, theUserName, theStartTime, theEndTime, null);
  }
  
  /**
   * Copy constructor for a new auction to include items.
   * 
   * @param theOrgName the organization name for this auction.
   * @param theUserName the username for the user who created this auction.
   * @param theStartTime the starting time for this auction.
   * @param theEndTime the ending time for this auction.
   * @param theInventory the list of items for this auction's inventory.
   */
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
  
  /**
   * Helper method to update the auction's name.
   */
  private void updateAuctionName() 
  {
	  myAuctionName = myOrgName.replace(' ', '-') + "-" + myStartTime.getMonth().name() + "-" + myStartTime.getDayOfMonth() + "-" + myStartTime.getYear();
  }
  
  /**
   * Returns the organization name.
   * 
   * @return a String of the organization name.
   */
  public String getAuctionOrg()
  {
    return myOrgName;
  }
  
  /**
   * Returns the list of inventory items for this auction.
   * 
   * @return a List of ItemModel items belonging to this auction.
   */
  public List<ItemModel> getAuctionItems()
  {
    return myInventory;
  }
  
  /**
   * Adds an item to this auction's inventory.
   * 
   * @param theItem the item to be added.
   */
  public void addItem(ItemModel theItem)
  {
	  myInventory.add(theItem);
  }
  
  /**
   * Removes an item from this auction's inventory.
   * 
   * @param theItem the item to be removed.
   */
  public void removeItem(ItemModel theItem) {
	  myInventory.remove(theItem);
  }
  
  /**
   * Returns a string representing this auction.
   * 
   * @return a string of the auction's name.
   */
  public String getAuctionName()
  {
	  return myAuctionName;
  }
  
  /**
   * Returns the start time for this auction.
   * 
   * @return a LocalDateTime for the start time.
   */
  public LocalDateTime getStartTime()
  {
	  return myStartTime;
  }
  
  /**
   * Sets the start time for this auction.
   * 
   * @param theStartTime the updated start time.
   * @return
   */
  public void setStartTime(LocalDateTime theStartTime)
  {
	  myStartTime = theStartTime;
	  updateAuctionName();
  }
  
  /**
   * Returns the end time for this auction.
   * 
   * @return a LocalDateTime for the end time.
   */
  public LocalDateTime getEndTime()
  {
	  return myEndTime;
  }
  
  /**
   * Sets the end time for this auction.
   * 
   * @param theEndTime the updated end time.
   */
  public void setEndTime(LocalDateTime theEndTime)
  {
	  myEndTime = theEndTime;
  }
  
  /**
   * Sets the date for this auction.
   * 
   * @param theAuctionStart a LocalDateTime for the start of the auction
   * @param theAuctionEnd a LocalDateTime for the end of the auction
   */
  public void setAuctionDate(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd) {
	myEndTime = theAuctionEnd;
	myStartTime = theAuctionStart;
	updateAuctionName();
  }
  
  /**
   * Returns the username for the user who created this auction.
   * 
   * @return a string of the username.
   */
  public String getUserName() {
	  return myUserName;
  }

  
  /**
   * Returns a simple to String for this auction.
   * 
   * @return the auction name and date.
   */
  public String toStringSimple(){
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myAuctionName + "\n");
	  answer.append("Date: " + myStartTime.getMonth() + " " + myStartTime.getDayOfMonth() +", " + myStartTime.getYear() + "\n");
	  return answer.toString();  
  }
  
  /**
   * Returns a String representation of this auction.
   * 
   * @return the name, org name, date, and inventory.
   */
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
