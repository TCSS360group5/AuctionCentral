import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonProfit extends User
{
  private LocalDate myLastAuctionYear;
  // since we have this, maybe they don't need to see the calendar?
  // or maybe they should see to schedule
  private Auction myAuction;
  private String myNPOName;
  private boolean myExistingAuctionStatus;
  
  // schedules an auction and enters auction info
  public NonProfit(String theUserName, UserType theUserType, String theNPOName, LocalDate theLastAuctionYear, boolean theAuctionStatus){
	  super(theUserName, theUserType);
	  myNPOName = theNPOName;
	  myLastAuctionYear = theLastAuctionYear;
	  myExistingAuctionStatus = theAuctionStatus;
  }
  
  public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	  LocalDateTime startTime;
	  LocalDateTime endTime;
	  ArrayList<Command> answer = new ArrayList<Command>();
	  int duration;
	  
	  Scanner user_input = new Scanner( System.in );
	  switch (theCommand) {
		  case ADDAUCTION:	  
			  startTime = getAuctionDateTimeFromUser(user_input);
			  System.out.println("Please enter the duration (in hours) of the Auction");
			  duration = user_input.nextInt();
			  endTime = startTime.plusHours(duration);
			  if(startTime.getDayOfMonth() != endTime.getDayOfMonth())
			  {
				  System.out.println("Your auction is scheduled for more than one calendar day. It has not been scheduled.");
			  }
			  else{
				  myAuction = new Auction(myNPOName, super.getUserName(), startTime, endTime);
				  if(theCalendar.addAuction(myAuction))
				  {
					  System.out.println("Auction added!");
					  this.myExistingAuctionStatus = true;
				  }
				  else
					  System.out.println("There was an error adding your auction.");
			  }
			  break;
		  case EDITAUCTION:
			  System.out.println("The current Auction details:");
			  System.out.println(myAuction.toString());			  
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
				  System.out.println("Please enter the duration (in hours) of the Auction");
				  duration = user_input.nextInt();
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
					  }
					  else
					  {
						  theCalendar.addAuction(myAuction);
						  System.out.println("There was an error. Your auction has not been edited.");
					  }
				  }
			  }
			  System.out.println("What would you like to do next?");
			  break;
		  case ADDITEM:
			  Item tempItem = getItemDetailsFromUser(user_input);	
			  addItemToAuction(theAuction, tempItem);
			  break;
		  case EDITITEM:
			  System.out.println("The current Item details:");
			  System.out.println(theItem.toString());

			  Item tempEditItem = getItemDetailsFromUser(user_input);	
			  
			  try
			  {
				  theAuction.removeItem(theItem);
				  theAuction.addItem(tempEditItem);
			  } catch (Exception e) {
				  
			  }
			  break;
		  case VIEWAUCTION:
			  answer.add(User.Command.GOBACK);
			  answer.add(User.Command.EDITAUCTION);
			  answer.add(User.Command.VIEWITEM);
			  answer.add(User.Command.ADDITEM);
			  break;
		  case VIEWCALENDAR:
			  answer.add(User.Command.GOBACK);
			  answer.add(User.Command.VIEWAUCTION);
			  answer.add(User.Command.ADDAUCTION);
			  break;
		  case VIEWITEM:
			  answer.add(User.Command.GOBACK);
			  answer.add(User.Command.EDITITEM);
			  break;
		  case VIEWMAINMENU:
			  if(myExistingAuctionStatus) {
				  answer.add(User.Command.EDITAUCTION);
				  answer.add(User.Command.ADDITEM);
				  answer.add(User.Command.VIEWITEM);
//				  answer.add(User.Command.VIEWAUCTION);
			  } else {
				  answer.add(User.Command.ADDAUCTION);
			  }
			  
			  
			  break;
		  default:
			  System.out.println("Command Not Recognized");
			  break;	  
	  }
	  return answer;
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
  
  public void setExistingActionStatus() 
  {
	  myExistingAuctionStatus = true;
  }
   
  private int implementYear(Scanner user_input) 
  {
	  int year;
	  System.out.println("Please enter the Auction year:");
	  year = user_input.nextInt();
	  if (year <= myLastAuctionYear.getYear()) 
	  {
		  System.out.println("You already have an auction this year.");
		  year = implementYear(user_input);
	  }
	return year;
  }
  
  public String getNPOName()
  {
	  return myNPOName;
  }
  
  public void setNPOName(String theNewName)
  {
	  myNPOName = theNewName;
  }
  
  public LocalDate getLastAuctionDate() 
  {
	  return myLastAuctionYear;
  }
  
  public void setLastAuctionDate(LocalDate theNewYear) 
  {
	  myLastAuctionYear = theNewYear;
  }
  
  public Auction getAuction()
  {
	  return myAuction;
  }
  
  public boolean setAuction(Auction theAuction)
  {
	  myAuction = theAuction;
	  myExistingAuctionStatus = true;
	  return false;
  }
  
  public boolean hasAuction()
  {
	  return myExistingAuctionStatus;
  }
  
  private LocalDateTime getAuctionDateTimeFromUser(Scanner theInput)
  {
	  int year;
	  int month;
	  int day;
	  int hour;
	  int minutes;
	  
	  year = implementYear(theInput);				  
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