package view;

import java.time.LocalDateTime;
import java.util.List;

import model.AuctionModel;
import model.ItemModel;

/**
 * These are for the console based UI so anywhere in the program, the proper string representing the model can be called.
 * 
 * @author UWT group5
 *
 */
public class AuctionCentralToStrings {
	
	/**
	 * This method makes a string that represents how an Auction will be printed to the UI.
	 * 
	 * @param theAuctionModel this is the Auction whose information we are displaying
	 * @return returns the string formated for the UI
	 * @author Quinn
	 */
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
	
	/**
	 * This method makes a string that represents how an Item will be printed to the UI.
	 * 
	 * @param theItemModel this is the item whose details will be displayed to the UI.
	 * @return returns the string formated for the UI
	 * @author Quinn
	 */
	  public static String itemToString(ItemModel theItemModel) {
		  StringBuilder answer = new StringBuilder();
		  answer.append("Name: " + theItemModel.getItemName() + "\n");
		  answer.append("Description: " + theItemModel.getDescription() + "\n");
		  answer.append("Starting Bid: " + String.format("%.2f", theItemModel.getStartingBid())   + "\n");
		  return answer.toString();
	  }
}
