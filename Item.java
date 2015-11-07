public class Item
{
  String itemName;
  double startingBid;
  String description;
  double sellingPrice;
  Map<User, double> bids;
  
  public Item(String theItemName, double theStartingBid, String the Description)
  {
  }
  
  public boolean setItemName(String theItemname)
  {
  }
  
  public boolean setStartingBid(double theStartingBid)
  {
  }
  
  public boolean setDescription(String theDescription)
  {
  }
  
  public String getItemName()
  {
  }
  
  public double getStartingBid()
  {
  }
  
  public String getDescription()
  {
  }
  
  public Map<User, double> getBids()
  {
  }
  
  public void bidOnItem(User user, double bid)
  {
  }
  
  public void updateBid(User user, double bid)
  {
  }
  
}
