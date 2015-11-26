package view;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import model.*;



/**
 * @author Group 5
 * The bidder class represents the bidders that use our system and produces
 * the menus and allows the bidder to bid and edit his bid.
 */
public class BidderController extends UserController
{
	private BidderModel myBidderModel;
	  
    public BidderController(UserModel theModel) {
		super("Bidder");
		myBidderModel = (BidderModel) theModel;
	}

	/**
     * This function accepts the item and the bid and then puts the bid in the item.
     * 
	 * @param theItem
	 * @param theBid
	 * @return if fail return false, also print to console why failed.
	 */
	public boolean bid(ItemModel theItem, double theBid){
		  boolean answer = false;
		  boolean allDone = false;
		  if (theItem.getStartingBid() > theBid) {
			  allDone = true;
			  System.out.println("Bid does not meet minimum bid for this item.");
		  }
		  if (!allDone) {
			  myBidderModel.addBid(theItem, theBid);
			  theItem.bidOnItem(myBidderModel, theBid);
			  answer = true;
		  }	  	  
		return answer;
	  }
	
	/* (non-Javadoc)
	 * @see User#ExecuteCommand(User.Command, Calendar, Auction, Item)
	 */

	@Override
	public boolean ExecuteCommand(Command theCommand, CalendarModel theCalendar,
			AuctionModel theAuction, ItemModel theItem) {
		boolean answer = false;
		Scanner user_input = new Scanner( System.in );
		switch (theCommand) {
			case BID:
				bidOnItem(user_input, theItem);
				answer = true;
				break;
			case EDITBID:
				System.out.println("This is the previous Bid: $");
				System.out.println(myBidderModel.getBids().get(theItem).toString());
				System.out.println("Please Enter a new Bid:");
				bidOnItem(user_input, theItem);
				answer = true;
				break;
			case VIEWBIDS:
				Scanner myScanner = new Scanner(System.in);
				if(myBidderModel.getBids().size() == 0)
				{
					System.out.println("You have not placed any bids yet.");
				}
				else{
					ArrayList<ItemModel> bidsToEdit = new ArrayList<ItemModel>();
					System.out.println("Bids you have placed:");
					int i = 1;
					for(Entry<ItemModel, Double> entry : myBidderModel.getBids().entrySet())
					{
						System.out.println(i + ") Item Name: " + entry.getKey().getItemName() + " Your bid: " + entry.getValue());
						bidsToEdit.add(entry.getKey());
					}
					System.out.println("Which would you like to edit?");
					int auctionNum = myScanner.nextInt();
					if(auctionNum > bidsToEdit.size())
					{
						System.out.println("Invalid bid.");
					}
					else
					{
						bidOnItem(myScanner, bidsToEdit.get(auctionNum - 1));
					}
				}
				answer = true;
				break;
			default:
				break;
		}
		return answer;
	}
	

	private void bidOnItem(Scanner user_input, ItemModel theItem) {
		double bid;
		System.out.println("Please Enter a Bid:");
		bid = user_input.nextDouble();
		if (theItem.getStartingBid() > bid) {
			System.out.println("Bid is below minimum bid for this item.");
		}else {
			myBidderModel.getBids().put(theItem, bid);
			System.out.println("Bid entered.");
		}	
	}
	
	@Override
	public ArrayList<Command> GetMenu(Command theCurrentState, ItemModel theItem, UserModel theUser) {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCurrentState)
		 {
			case VIEWBIDDERAUCTIONS:
				answer.add(UserController.Command.GOBACK);
				answer.add(UserController.Command.VIEWITEM);
				break;
			case VIEWITEM:
				answer.add(UserController.Command.GOBACK);
				if(myBidderModel.getBids().containsKey(theItem)) {
					answer.add(UserController.Command.EDITBID);
				} else {
					answer.add(UserController.Command.BID);
				}				
				break;
			case VIEWMAINMENU:
				answer.add(UserController.Command.VIEWBIDDERAUCTIONS);
				answer.add(UserController.Command.VIEWBIDS);
				break;
			case BID:
				answer.add(UserController.Command.VIEWCALENDAR);
				answer.add(UserController.Command.VIEWBIDS);
				break;
			default:
				break;
		 }
		return answer;
	}
	
	@Override
	public UserController.Command goForwardState(UserController.Command theCurrentState, UserController.Command theCurrentCommand)
	{
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.VIEWMAINMENU)
		 {
			 answer = UserController.Command.VIEWMAINMENU;
		 } 
		 else if (theCurrentCommand == UserController.Command.VIEWBIDDERAUCTIONS)
		 {
			 //this should be uncommented when implemented:
			 //answer = User.Command.VIEWBIDDERAUCTIONS;
			 System.out.println("Auctions for Bidders not implemented yet.");
		 }
		 else if (theCurrentCommand == UserController.Command.VIEWMYAUCTION)
		 {
			 answer = UserController.Command.VIEWMYAUCTION;
		 }				 
		return answer;
	}


	@Override
	public UserController.Command goBackState(UserController.Command theCurrentState) 
	{
	  UserController.Command answer = null;
		switch (theCurrentState)
		 {
		 	case VIEWBIDDERAUCTIONS:
		 		answer = UserController.Command.VIEWMAINMENU;
		 		break;
	 		case VIEWITEM:
	 			answer = UserController.Command.VIEWBIDDERAUCTIONS;
	 			break;						
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
		return answer;
	}
}