import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonProfit extends User
{
  private LocalDate myLastAuctionDate;
  private Auction myAuction; 
  private String myNPOName;
  
  public NonProfit(String theUserName, UserType theUserType, String theNPOName, LocalDate theLastAuctionDate){
	  super(theUserName, theUserType);
	  myNPOName = theNPOName;
	  myLastAuctionDate = theLastAuctionDate;
  }
  
  public ArrayList<Command> GetMenu(Command theCommand, Item theItem)
  {
	  ArrayList<Command> answer = new ArrayList<Command>();
	  switch (theCommand)
	  {
	  case VIEWMYAUCTION:
		  System.out.println("\n" + myAuction.toString());
		  answer.add(User.Command.GOBACK);
		  answer.add(User.Command.EDITAUCTION);
		  answer.add(User.Command.VIEWITEM);
		  answer.add(User.Command.ADDITEM);
		  break;
	  case VIEWITEM:
		  answer.add(User.Command.GOBACK);
		  answer.add(User.Command.EDITITEM);
		  break;
	  case VIEWMAINMENU:
		  if(canAddAuction()) {
			  answer.add(User.Command.ADDAUCTION);			  
		  } else {
			  answer.add(User.Command.VIEWMYAUCTION);
		  }
	  break;
		default:
			System.out.println("Menu Command Not Recognized");
			break;
	  }
	return answer;	  
  }


  public boolean ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	  //ArrayList<Command> answer = new ArrayList<Command>();
	  boolean answer = false;
	  Scanner user_input = new Scanner( System.in );
	  switch (theCommand) {
		  case ADDAUCTION:	  
			  addAuction(user_input, theCalendar);
			  answer = true;
			  break;
		  case EDITAUCTION:				  
			  editAuction(user_input, theCalendar);	
			  answer = true;
			  break;
		  case ADDITEM:
			  Item tempItem = getItemDetailsFromUser(user_input);	
			  addItemToAuction(myAuction, tempItem);
			  answer = true;
			  break;
		  case EDITITEM:
			  editItem(theItem, user_input, theAuction);
			  answer = true;
			  break;
		  default:
			  //System.out.println("Command Not Recognized");
			  answer = false;
			  break;	  
	  }
	  return answer;
  }
  
  @Override
	public User.Command goForwardState(User.Command theCurrentState, User.Command theCurrentCommand)
	{
		User.Command answer = theCurrentState;
//		switch (theCurrentCommand)
//		{
//		case VIEWCALENDAR :
//			answer = User.Command.VIEWCALENDAR;
//			break;
//		default:
//			break;		
//		}
		if (theCurrentCommand == User.Command.VIEWCALENDAR)
		 {
			answer = User.Command.VIEWCALENDAR;
		 } 
		 else if (theCurrentCommand == User.Command.VIEWMAINMENU)
		 {
			 answer = User.Command.VIEWMAINMENU;
		 } 
		 else if (theCurrentCommand == User.Command.VIEWMYAUCTION)
		 {
			 answer = User.Command.VIEWMYAUCTION;
		 }				 
		return answer;
	}
   
  @Override
  public User.Command goBackState(User.Command theCurrentState) 
	{
	  User.Command answer = null;
		switch (theCurrentState)
		 {
		 	case VIEWMYAUCTION:
		 		answer = User.Command.VIEWMAINMENU;
		 		break;
	 		case VIEWITEM:
	 			answer = User.Command.VIEWMYAUCTION;
	 			break;						
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
		return answer;
	}

	private void editItem(Item theItem, Scanner user_input, Auction theAuction)
	{
		System.out.println("The current Item details:");
		  System.out.println(theItem.toString());
		  Item tempEditItem = getItemDetailsFromUser(user_input);				  
		  try
		  {
			  theAuction.removeItem(theItem);
			  theAuction.addItem(tempEditItem);
			  System.out.println("Edited Item details:");
			  System.out.println(tempEditItem.toString());
		  } catch (Exception e) {
			  theAuction.addItem(theItem);
		  }
	}
	
	  
	private boolean canAddAuction() {	
		return (myAuction == null);
	}
  
  private void addAuction(Scanner user_input, Calendar theCalendar)
  {
	  LocalDateTime startTime;
	  LocalDateTime endTime;
	  int duration;
	  startTime = getAuctionDateTimeFromUser(user_input);
	  System.out.println("Please enter the duration (in hours) of the Auction");
	  duration = user_input.nextInt();
	  endTime = startTime.plusHours(duration);
	  if(startTime.getDayOfMonth() != endTime.getDayOfMonth())
	  {
		  System.out.println("Your auction is scheduled for more than one calendar day. It has not been scheduled.");
	  }
	  else if (check365(startTime.toLocalDate()))
  	  {
		  Auction tempAuction = new Auction(myNPOName, super.getUserName(), startTime, endTime);
		  if(theCalendar.addAuction(tempAuction))
		  {
			  System.out.println("Auction added!");
			  myAuction = tempAuction;
		  }
		  else
			  System.out.println("There was an error adding your auction.");
	  }
  }
  
  private void editAuction(Scanner user_input, Calendar theCalendar) 
  {
	  System.out.println("The current Auction details:");
	  System.out.println(myAuction.toString());	
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
			  List<Item> auctionItems = myAuction.getAuctionItems();
			  Auction newAuction = new Auction(myNPOName, super.getUserName(), startTime, endTime, auctionItems);
			  theCalendar.removeAuction(myAuction);
			  if(theCalendar.addAuction(newAuction))
			  {
				  myAuction = newAuction;
				  System.out.println("Auction has been edited.");
				  System.out.println("Edited Auction Details:");
				  System.out.println(myAuction.toString());
			  }
			  else
			  {
				  theCalendar.addAuction(myAuction);
				  System.out.println("There was an error. Your auction has not been edited.");
			  }
		  }
	  }
	  System.out.println("What would you like to do next?");
	
}

private Item getItemDetailsFromUser(Scanner user_input) 
  {
	  System.out.println("Please enter the Item name:");
	  String itemName = user_input.nextLine();
	  System.out.println("Please enter the Minimum Bid:");
	  double minimumPrice = user_input.nextDouble();
	  user_input.nextLine();
	  System.out.println("Please enter the Item Description:");
	  String description = user_input.nextLine();
	  return new Item(itemName, minimumPrice, description);
  }
  
  private void addItemToAuction(Auction theAuction, Item theItem)
  {
	  try
	  {
		  theAuction.addItem(theItem);
	  } catch (Exception e) {
		  System.out.println("Item could not be added");
	  }
  }
   
  private int checkYear(Scanner user_input) 
  {
	  int year;
	  System.out.println("Please enter the Auction year:");
	  year = user_input.nextInt();
	  if (year < LocalDate.now().getYear() || year > LocalDate.now().plusYears(1).getYear())
	  {
		  System.out.println("Auctions can only be sheduled this year and next year.");
		  year = checkYear(user_input);
	  }
	return year;
  }
  
  public boolean check365(LocalDate theDate)
  {
	  boolean answer = false;
	  if(theDate.minusDays(365).isAfter(myLastAuctionDate))
	  {
		  answer = true;
	  } else {
		  System.out.println("Your next auction must be 365 days after your last auction");
	  }
	  return answer;
  }
  
  public String getNPOName()
  {
	  return myNPOName;
  }
  
  public void setNPOName(String theNewName)
  {
	  myNPOName = theNewName;
  }
  
  public LocalDate getCurrentAuctionDate() 
  {
	  if (myAuction != null)
	  {
		  return myAuction.getStartTime().toLocalDate();
	  } 
	  else 
	  {
		  return null;
	  }	  
  }
  
  public LocalDate getLastAuctionDate() {
	return myLastAuctionDate;
  }
  
  public void setLastAuctionDate(LocalDate myLastAuctionDate) {
	this.myLastAuctionDate = myLastAuctionDate;
  }

public Auction getAuction()
  {
	  return myAuction;
  }
  
  public boolean setAuction(Auction theAuction)
  {
	  myAuction = theAuction;
	  return false;
  }
  
//  public boolean hasAuction()
//  {
//	  
//	  return myExistingAuctionStatus;
//  }
  public boolean hasAuction()
  {
	  return myAuction != null;
  }
  
  private LocalDateTime getAuctionDateTimeFromUser(Scanner theInput)
  {
	  int year;
	  int month;
	  int day;
	  int hour;
	  int minutes;
	  
	  year = checkYear(theInput);				  
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
}