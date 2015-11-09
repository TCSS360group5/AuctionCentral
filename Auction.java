import java.time.LocalDateTime;

import java.util.Map;


public class Auction
{
  // public for now
  // I thought it would be good to have the item name easily available since users will be choosing
  // them by name?
  private Map<String, Item> myInventory;
  private String myOrgName;
  private String myAuctionName;
  private LocalDateTime myAuctionStart;
  private LocalDateTime myAuctionEnd;
  
  //Fields for local date
//  LocalDate startTime;
//  LocalDate endTime;
  
  // may have to add a time argument as well
  public Auction(String theOrgName, String theAuctionName, LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd)
  {
	  myOrgName = theOrgName;
     myAuctionName = theAuctionName;
	  myAuctionStart = theAuctionStart;
     myAuctionEnd = theAuctionEnd;
  }
  
  // may have to add a time argument as well
  public Auction(String theOrgName, String theAuctionName, LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd, Map<String, Item> theInventory)
  {
	  myOrgName = theOrgName;
     myAuctionName = theAuctionName;
     myAuctionStart = theAuctionStart;
     myAuctionEnd = theAuctionEnd;
	  myInventory = theInventory;
  }
  
  public boolean setAuctionOrg(String theOrgName)
  {
    myOrgName = theOrgName;
    return true;
  }
  
  public boolean setAuctionName(String theName)
  {
      myAuctionName = theName;
      return true;
  }
  
  public boolean setAuctionDate(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd)
  {
     myAuctionStart = theAuctionStart;
     myAuctionEnd = theAuctionEnd;
    return true;
  }
  
  public String getAuctionOrg()
  {
    return myOrgName;
  }
  
  public String getAuctionName()
  {
    return myAuctionName;
  }
  
    public LocalDateTime getStartDate()
  {
    return myAuctionStart;
  }
  
   public LocalDateTime getEndDate()
  {
    return myAuctionEnd;
  }
  
  public Map<String, Item> getAuctionItems()
  {
    return myInventory;
  }
  
  public boolean addItem(Item item){
	      myInventory.put(item.getItemName(), item);
	 return true;
  }
  public boolean editItemName(Item item, String newName)
  {
	  myInventory.remove(item);
     
	  //inventory.put(item, newName);
	  return true;
  }
  
  public boolean editItemStartingBid(Item item, double startingBid)
  {
	  
	  return true;
  }
  
  public boolean editItemDescription(Item item, String description)
  {
	  //inventory.put(item.getItemName());
	  return true;
  }
  
  public void updateItem(Item item){
	  
  }

}
