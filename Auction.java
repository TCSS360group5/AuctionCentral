public class Auction
{
  // public for now
  // I thought it would be good to have the item name easily available since users will be choosing
  // them by name?
  public Map<String, Item> inventory;
  public String orgName;
  public Date auctionDate;
  
  // may have to add a time argument as well
  public Auction(String theOrgName, Date theAuctionDate)
  {
  }
  
  // may have to add a time argument as well
  public Auction(String theOrgName, Date theAuctionDate, Map<String, Item> theInventory)
  {
  }
  
  public boolean setAuctionOrg(String theOrgName)
  {
    orgName = theOrgName;
  }
  
  public boolean setAuctionDate(Date theAuctionDate)
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

}
