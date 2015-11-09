import java.util.Map;

public class Auction
{
  // public for now
  // I thought it would be good to have the item name easily available since users will be choosing
  // them by name?
  public Map<String, Item> inventory;
  public String orgName;
  public String dateMonth;
  public int dateDay;
  public int dateYear;
  
  
  // may have to add a time argument as well
  public Auction(String theOrgName, String theMonth, int theDay, int theYear)
  {
  }
  
  // may have to add a time argument as well
  public Auction(String theOrgName, String theMonth, int theDay, int theYear, Map<String, Item> theInventory)
  {
  }
  
  public void setAuctionOrg(String theOrgName)
  {
    orgName = theOrgName;
  }
  
  public void setAuctionDate(Date theAuctionDate)
  {
    auctionDate = theAuctionDate;
  }
  
  public String getAuctionOrg()
  {
    return orgName;
  }
  
  public Date getAuctionDate()
  {
    return auctionDate;
  }
  
  public Map<String, Item> getAuctionItems()
  {
    return inventory;
  }
  
  public boolean editItemName(Item item, String name)
  {
  }
  
  public boolean editItemStartingBid(Item item, double startingBid)
  {
  }
  
  public boolean editItemDescription(Item item, String description)
  {
  }

}
