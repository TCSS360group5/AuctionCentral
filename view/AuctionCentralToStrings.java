package view;

import java.time.LocalDateTime;
import java.util.List;

import model.AuctionModel;
import model.ItemModel;

public class AuctionCentralToStrings {
	public static String auctionToString(AuctionModel theAuctionModel){
		  StringBuilder answer = new StringBuilder();
		  answer.append("Name: " + theAuctionModel.getAuctionName() + "\n");
		  answer.append("Organization name: " + theAuctionModel.getAuctionOrg() + "\n");
		  LocalDateTime theStartDateTime = theAuctionModel.getStartTime();
		  answer.append("Date: " + theStartDateTime.getMonth() + " " + theStartDateTime.getDayOfMonth() +", " + theStartDateTime.getYear() + "\n");
		  answer.append("Start time: " + theStartDateTime.getHour() + ":" + theStartDateTime.getMinute() + "\n");
		  LocalDateTime theEndDateTime = theAuctionModel.getStartTime();
		  answer.append("End: " + theEndDateTime.getHour() + ":" + theEndDateTime.getMinute() + "\n");
		  
		  List<ItemModel> items = theAuctionModel.getAuctionItems();  
		  if(items!= null) 
		  {
			  answer.append("Items: " + items.size());
		  } 
		  else 
		  {
			  answer.append("Items: 0");
		  }
		  return answer.toString();
	  }
	
	  public static String itemToString(ItemModel theItemModel) {
		  StringBuilder answer = new StringBuilder();
		  answer.append("Name: " + theItemModel.getItemName() + "\n");
		  answer.append("Description: " + theItemModel.getDescription() + "\n");
		  answer.append("Starting Bid: " + String.format("%.2f", theItemModel.getStartingBid())   + "\n");
		  return answer.toString();
	  }
}
