package view;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import model.*;

/**
 * The BidderController class represents the bidders that use our system.
 * It controls the Bidder menus and functions for our system.
 * 
 * @author TCSS 360 Group 5
 */
public class BidderController extends UserController
{
	private BidderModel myBidderModel;
	  
	/**
	 * Creates a BidderController using the passed BidderModel.
	 * 
	 * @param theModel a UserModel (BidderModel) that will be used for the controller.
	 * @author Quinn
	 */
    public BidderController(UserModel theModel) {
		super("Bidder");
		myBidderModel = (BidderModel) theModel;
	}

	/**
     * Accepts the item and the bid and then puts the bid in the item.
     * If the bid is too low, a message is printed to the console and the bid is not added.
     * 
	 * @param theItem the item being bidded on
	 * @param theBid the amount for the bid
	 * @return true IFF the bid is greater than the starting bid
	 * @author Quinn
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
	
	/**
	 * This method executes commands if the command is something the user can execute and 
	 * does nothing otherwise.
	 * 
	 * @param theCommand this is the action the user wants to take
	 * @param theCalendar this is the calendar object needed in some situations 
	 * @param theAuction this is the auction the user is currently looking at if they are in the Auction details menu
	 * @param theItem this is the item the user is currently looking at if they are at the item menu
	 * @author Quinn
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
				System.out.println(myBidderModel.getBids().get(theItem));
				bidOnItem(user_input, theItem);
				break;
			case VIEWBIDS:
				Scanner myScanner = new Scanner(System.in);
				if(myBidderModel.getBids().size() == 0)
				{
					System.out.println("You have not placed any bids yet.");
				}
				else{
					DecimalFormat df = new DecimalFormat("#.00");
					ArrayList<ItemModel> bidsToEdit = new ArrayList<ItemModel>();
					System.out.println("Bids you have placed:");
					int i = 1;
					for(Entry<ItemModel, Double> entry : myBidderModel.getBids().entrySet())
					{
						System.out.println(i + ") Item Name: " + entry.getKey().getItemName() + " Your bid: $" + df.format(entry.getValue()));
						bidsToEdit.add(entry.getKey());
					}
					System.out.println("Which would you like to edit?");
					int auctionNum = myScanner.nextInt();
					myScanner.nextLine();
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

	/**
	 * Used to get bids from the user and then calls the item to make sure they are valid.
	 * 
	 * @param theUserInput the Scanner used to read user input from console
	 * @param theItem the item being bidded on
	 * @author Quinn
	 */
	private void bidOnItem(Scanner user_input, ItemModel theItem) 
	{
		Double bid = null;
		String actionMessage = "Please Enter a Bid, Press Q to cancel:";
		System.out.println("Found in bids: " + myBidderModel.getBids().containsKey(theItem));
		String exitString = "Q";

		boolean exitBid = false;
		boolean validBid = false;
		do 
		{
			boolean validDouble = false;
			System.out.println(actionMessage);
			String UserInputString = user_input.next();
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
	 * 
	 * @param theCurrentState this is the current state of the view
	 * @param theItem this is the item the user is looking at and is used to see if the bidder has bid on that item
	 * @param theUser not used in the bidder class implementation of this method
	 * @return returns a list of menu options for this user.
	 * @author Quinn
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
	 * 
	 * @param theCurrentState this is the current state of the view
	 * @param theCurrentCommand this is what the user is trying to do
	 * @return returns the next view state or current view state if there isn't a valid move
	 * @author Quinn
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

	/**
	 * Changes the view state by going back one if possible.
	 * 
	 * @param theCurrentState this is the current state of the view
	 * @return returns the new view state
	 * @author Quinn
	 */
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
	
	/**
	 * Returns the BidderModel associated with this BidderController.
	 * (Used for testing purposes)
	 * 
	 * @return myBidderModel
	 * @author Shannon
	 */
	public BidderModel getBidderModel() {
		return myBidderModel;
	}
}
