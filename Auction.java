package user;

import java.util.ArrayList;
import java.util.Calendar;

public class Auction
{
  // public for now
  // I thought it would be good to have the item name easily available since users will be choosing
  // them by name?
  private ArrayList<Item> myInventory;
  public NonProfit myNPO;
  public Calendar myAuctionDate;
  
  // may have to add a time argument as well
  public Auction(NonProfit theNPO, Calendar theAuctionDate)
  {
  }
  
  // may have to add a time argument as well
  // public Auction(String theOrgName, Date theAuctionDate, Map<String, Item> theInventory)
//   {
//   }
  
  public boolean setAuctionOrg(NonProfit theNPO)
  {
    myNPO = theNPO;
	return false;
  }
  
  public boolean setAuctionDate(Calendar theAuctionDate)
  {
    myAuctionDate = theAuctionDate;
	return false;
  }
  
  public NonProfit getAuctionOrg()
  {
    return myNPO;
  }
  
  public Calendar getAuctionDate()
  {
    return myAuctionDate;
  }
  
  public ArrayList<Item> getAuctionItems()
  {
    return myInventory;
  }
  
  public boolean editItemName(Item theItem, String theName)
  {
	return false;
  }
  
  public boolean addItem(Item theItem) {
	  return false;
  }
  
  public boolean editItemStartingBid(Item theItem, double theStartingBid)
  {
	return false;
  }
  
  public boolean editItemDescription(Item theItem, String theDescription)
  {
	return false;
  }
  
  public boolean placeBid(Item theItem, double theBid)
  {
	return false;
  }

}