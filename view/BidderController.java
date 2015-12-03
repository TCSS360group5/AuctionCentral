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
	public void ExecuteCommand(Command theCommand, CalendarModel theCalendar,
			AuctionModel theAuction, ItemModel theItem) {
		Scanner user_input = new Scanner( System.in );
		switch (theCommand) {
			case BID:
				bidOnItem(user_input, theItem);
				break;
			case EDITBID:
				System.out.println("This is the previous Bid: $");
				System.out.println(myBidderModel.getBids().get(theItem).toString());
				bidOnItem(user_input, theItem);
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
				break;
			default:
				break;
		}
	}

	private void bidOnItem(Scanner user_input, ItemModel theItem) 
	{
		Double bid = null;
		String actionMessage = "Please Enter a Bid, Press Q to cancel:";
		String exitString = "Q";

		boolean exitBid = false;
		boolean validBid = false;
		do 
		{
			boolean validDouble = false;
			System.out.println(actionMessage);
			String UserInputString = user_input.nextLine();
			if (UserInputString.startsWith(exitString))
			{
				exitBid = true;
			} else {
				try
				{
					bid = Double.valueOf(UserInputString);
					validDouble = true;
				} catch (NumberFormatException e)
				{
					System.out.println("Invalid input");
					validDouble = false;
				}
				if (validDouble) 
				{
					if (!theItem.isBidAboveStartingBid(bid)) {
						System.out.println("Bid is below minimum bid for this item.");
					}else {
						myBidderModel.getBids().put(theItem, bid);
						System.out.println("Bid entered.");
						validBid = true;
					}	
				}
			}
		} while (!validBid && !exitBid);	
	}
	
	/**
	 * These are the menu items available for this type of user.
	 */
	@Override
	public ArrayList<Command> GetMenu(Command theCurrentState, ItemModel theItem, UserModel theUser) {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCurrentState)
		 {
			case VIEWAUCTIONDETAILS:
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
			default:
				break;
		 }
		return answer;
	}
	
	/**
	 * This method returns the current state of which menu the user is in if and only 
	 * if the current command is an option to view another valid menu for this user.
	 */
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
			 answer = UserController.Command.VIEWBIDDERAUCTIONS;
		 }
		 else if (theCurrentCommand == UserController.Command.VIEWAUCTIONDETAILS)
		 {
			 answer = UserController.Command.VIEWAUCTIONDETAILS;
		 }			
		 else if ((theCurrentCommand == UserController.Command.VIEWITEM))
		 {
			 answer = UserController.Command.VIEWITEM;
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
	 		case VIEWAUCTIONDETAILS:
	 			answer = UserController.Command.VIEWBIDDERAUCTIONS;
	 			break;
	 		case VIEWITEM:
	 			answer = UserController.Command.VIEWAUCTIONDETAILS;
	 			break;
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
		return answer;
	}
}