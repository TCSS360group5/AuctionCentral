package view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.AuctionException;
import model.*;

/**
 * Controller for a NonProfit using the system.
 * 
 * @author TCSS 360 Group 5
 *
 */
public class NonProfitController extends UserController
{
	private NonProfitModel myNonProfitModel;
  
  public NonProfitController(UserModel theModel){
	  super("Non Profit");
	  myNonProfitModel = (NonProfitModel) theModel;
  }
  
  @Override
  public ArrayList<Command> GetMenu(Command theCommand, ItemModel theItem, UserModel theUser)
  {
	  ArrayList<Command> answer = new ArrayList<Command>();
	  switch (theCommand)
	  {
	  case VIEWMYAUCTION:
		  System.out.println("\n" + auctionToString(((NonProfitModel)theUser).getAuction()));
		  answer.add(UserController.Command.GOBACK);
		  answer.add(UserController.Command.EDITAUCTION);
		  answer.add(UserController.Command.VIEWITEM);
		  answer.add(UserController.Command.ADDITEM);
		  break;
	  case VIEWITEM:
		  answer.add(UserController.Command.GOBACK);
		  answer.add(UserController.Command.EDITITEM);
		  break;
	  case VIEWMAINMENU:
		  answer.add(UserController.Command.ADDAUCTION);
		  answer.add(UserController.Command.VIEWMYAUCTION);
//		  if(myNonProfitModel.canAddAuction()) {
//			  answer.add(UserController.Command.ADDAUCTION);			  
//		  } else {
//			  answer.add(UserController.Command.VIEWMYAUCTION);
//		  }
	  break;
		default:
			System.out.println("Menu Command Not Recognized");
			break;
	  }
	return answer;	  
  }

@Override
  public void ExecuteCommand(Command theCommand, CalendarModel theCalendar, AuctionModel theAuction, ItemModel theItem)
  {
	  Scanner user_input = new Scanner( System.in );
	  switch (theCommand) {
		  case ADDAUCTION:	  
			  addAuction(user_input, theCalendar);
			  break;
		  case EDITAUCTION:				  
			  editAuction(user_input, theCalendar);	
			  break;
		  case ADDITEM:
			  ItemModel tempItem = getItemDetailsFromUser(user_input);	
			 addItemToAuction(myNonProfitModel.getAuction(), tempItem);
			  break;
		  case EDITITEM:
			  editItem(theItem, user_input, theAuction);
			  break;
		  default:
			  break;	  
	  }
  }
  
  @Override
	public UserController.Command goForwardState(UserController.Command theCurrentState, UserController.Command theCurrentCommand)
	{
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.VIEWMAINMENU)
		 {
			 answer = UserController.Command.VIEWMAINMENU;
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
		 	case VIEWMYAUCTION:
		 		answer = UserController.Command.VIEWMAINMENU;
		 		break;
	 		case VIEWITEM:
	 			answer = UserController.Command.VIEWMYAUCTION;
	 			break;						
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
		return answer;
	}

	private void editItem(ItemModel theItem, Scanner user_input, AuctionModel theAuction)
	{
		System.out.println("The current Item details:");
		  System.out.println(itemToString(theItem));
		  ItemModel tempEditItem = getItemDetailsFromUser(user_input);				  
		  try
		  {
			  theAuction.removeItem(theItem);
			  theAuction.addItem(tempEditItem);
			  System.out.println("Edited Item details:");
			  System.out.println(itemToString(tempEditItem));
		  } catch (Exception e) {
			  theAuction.addItem(theItem);
		  }
	}
  
  private void addAuction(Scanner user_input, CalendarModel theCalendar)
  {
	  LocalDateTime startTime;
	  LocalDateTime endTime;
	  int duration;
	  startTime = getAuctionDateTimeFromUser(user_input);
	  System.out.println("Please enter the duration (in hours) of the Auction");
	  duration = user_input.nextInt();
	  endTime = startTime.plusHours(duration);
	  boolean auctionCanBeAdded = false;
	  if(startTime.getDayOfMonth() != endTime.getDayOfMonth())
	  {
		  System.out.println("Your auction is scheduled for more than one calendar day. It has not been scheduled.");
	  }
	  else {
		  try {
			  myNonProfitModel.check365(startTime.toLocalDate());
			  auctionCanBeAdded = true;
		  } catch (AuctionException e){
			  System.out.println(e.getExceptionString());
		  }
	  }
  	  if (auctionCanBeAdded){
		  AuctionModel tempAuction = new AuctionModel(myNonProfitModel.getNPOName(), myNonProfitModel.getUserName(), startTime, endTime);
		  try
		  {
			  theCalendar.addAuction(tempAuction);
			  System.out.println("Auction added!");
			  myNonProfitModel.setAuction(tempAuction);
		  }
		  catch (Exception e)
		  {
			  System.out.println(e.getMessage());
		  }
	  }
  }
  
  private void editAuction(Scanner user_input, CalendarModel theCalendar) 
  {
	  System.out.println("The current Auction details:");
	  System.out.println(auctionToString(myNonProfitModel.getAuction()));	
	  LocalDateTime startTime;
	  LocalDateTime endTime;
	  System.out.println("Would you like to edit the auction? (Enter 0 to go back, 1 to edit)");
	  int decision = user_input.nextInt();
	  if(decision < 0 || decision > 1)
	  {
		  do
		  {
			  System.out.println("Invalid input. Would you like to edit the auction? (Enter 0 to go back, 1 to edit)");
			  decision = user_input.nextInt();
		  } while(decision < 0 || decision > 1);
	  }
	  if(decision == 1)
	  {
		  startTime = getAuctionDateTimeFromUser(user_input);
		  int duration = 0;
		  boolean keepGoing = true;
		  do
		  {
			  System.out.println("Please enter the duration (in hours) of the Auction");
			  duration = user_input.nextInt();
			  if (duration < 1)
			  {
				  System.out.println("Must be at least 1 hour");
			  } 
			  else if (duration > 24)
			  {
				  System.out.println("Duration can only be up to 23 hours.");
			  }
			  else 
			  {
				  keepGoing = false;
			  }
		  } while (keepGoing);

		  endTime = startTime.plusHours(duration);
		  if(startTime.getDayOfMonth() != endTime.getDayOfMonth())
		  {
			  System.out.println("Your auction is scheduled for more than one calendar day. It has not been scheduled.");
		  }
		  else
		  {
			  List<ItemModel> auctionItems = myNonProfitModel.getAuction().getAuctionItems();
			  AuctionModel newAuction = new AuctionModel(myNonProfitModel.getNPOName(), myNonProfitModel.getUserName(), startTime, endTime, auctionItems);
			  
			  try 
			  {
				  theCalendar.removeAuction(myNonProfitModel.getAuction());
				  theCalendar.addAuction(newAuction);
				  myNonProfitModel.setAuction(newAuction);
				  System.out.println("Auction has been edited.");
				  System.out.println("Edited Auction Details:");
				  System.out.println(auctionToString(myNonProfitModel.getAuction()));
				  //System.out.println(myNonProfitModel.getAuction().toString());
			  } catch(Exception e) {
				  System.out.println(e.getMessage());
				  
				  //theCalendar.addAuction(myNonProfitModel.getAuction());
			  	//System.out.println("There was an error. Your auction has not been edited.");
			  }
		  }
	  }
	  System.out.println("What would you like to do next?");	
}

private ItemModel getItemDetailsFromUser(Scanner user_input) 
  {
	  System.out.println("Please enter the Item name:");
	  String itemName = user_input.nextLine();
	  System.out.println("Please enter the Minimum Bid:");
	  double minimumPrice = user_input.nextDouble();
	  user_input.nextLine();
	  System.out.println("Please enter the Item Description:");
	  String description = user_input.nextLine();
	  return new ItemModel(itemName, minimumPrice, description);
  }
  
  private void addItemToAuction(AuctionModel theAuction, ItemModel theItem)
  {
	  try
	  {
		  theAuction.addItem(theItem);
	  } catch (Exception e) {
		  System.out.println("Item could not be added");
	  }
  }

  
  private LocalDateTime getAuctionDateTimeFromUser(Scanner theInput)
  {
	  int year;
	  int month;
	  int day;
	  int hour;
	  int minutes;
	  
	  boolean yearNotValid = true;
	  do
	  {
		  System.out.println("Please enter the Auction year:");
		  year = theInput.nextInt();
		  if (year < LocalDate.now().getYear() || year > LocalDate.now().plusYears(1).getYear())
		  {
			  System.out.println("Auctions can only be sheduled this year and next year.");
		  } else {
			  try
			  {
				  myNonProfitModel.checkYear(year);
				  yearNotValid = false;
			  } catch (AuctionException e)
			  {
				  System.out.println(e.getExceptionString());
			  }
		  }
	  } while (yearNotValid);
				  
	  System.out.println("Please enter the Auction month:");
	  month = theInput.nextInt();
	  if(month > 12 || month < 1)
	  {
		  do
		  {
			  System.out.println("Invalid month. Please enter the Auction month:");
			  month = theInput.nextInt();
		  } while(month > 12 || month < 1);
	  }
	  System.out.println("Please enter the Auction day:");
	  day = theInput.nextInt();
	  if(day > (Month.of(month).length(((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)))) || day < 1)
	  {
		  do
		  {
			  System.out.println("Invalid day. Please enter the Auction day:");
			  day = theInput.nextInt();
		  } while(day > (Month.of(month).length(((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)))) || day < 1);
	  }
	  System.out.println("Please enter the Auction hour:");
	  hour = theInput.nextInt();
	  if(hour > 23 || hour < 0)
	  {
		  do
		  {
			  System.out.println("Invalid hour. Please enter the Auction hour:");
			  hour = theInput.nextInt();
		  } while(hour > 23 || hour < 0);
	  }
	  System.out.println("Please enter the Auction minute:");
	  minutes = theInput.nextInt();
	  if(minutes > 59 || minutes < 0)
	  {
		  do
		  {
			  System.out.println("Invalid minute. Please enter the Auction minute:");
			  minutes = theInput.nextInt();
		  } while(minutes > 59 || minutes < 0);
	  }  
	  return LocalDateTime.of(year, month, day, hour, minutes);
  } 
  
  public String itemToString(ItemModel theItemModel) {
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + theItemModel.getItemName() + "\n");
	  answer.append("Description: " + theItemModel.getDescription() + "\n");
	  answer.append("Starting Bid: " + String.format("%.2f", theItemModel.getStartingBid())   + "\n");
	  return answer.toString();
  }
  
  public String auctionToString(AuctionModel theAuctionModel){
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
}