package user;

import java.util.Map;

public class Item
{
  String itemName;
  double myMinimumBid;
  String description;
  //double sellingPrice;
  private Map<User, Double> bids;
  
  public Item(String theItemName, double theMinimumBid, String theDescription)
  {
	  
  }
  
  public Item(Item theItem) {
	  
  }
  
  public void setItemName(String theItemname)
  {
  }
  
  public void setMinimumBid(double theMinimumBid)
  {
  }
  
  public void setDescription(String theDescription)
  {
  }
  
  public String getItemName()
  {
	return description;
  }
  
  public double getMinimumBid()
  {
	return myMinimumBid;
  }
  
  public String getDescription()
  {
	return description;
  }
  
  public Map<User, Double> getBids()
  {
	return bids;
  }
  
  public void bidOnItem(User user, double bid)
  {
  }
  
  public void updateBid(User user, double bid)
  {
  }
  
}
