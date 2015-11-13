import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class NonProfit extends User
{
  private int myLastAuctionYear;
  // since we have this, maybe they don't need to see the calendar?
  // or maybe they should see to schedule
  private Auction myAuction;
  private String myNPOName;
  private String myUserName;
  
  // schedules an auction and enters auction info
  public NonProfit(String theUserName, UserType theUserType, String theNPOName, int theLastAuctionYear){
	  super(theUserName, theUserType);
	  myNPOName = theNPOName;
	  myLastAuctionYear = theLastAuctionYear;
	  myUserName = theUserName;
  }
  
  public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	  ArrayList<Command> answer = new ArrayList<Command>();
	  int year;
	  int month;
	  int day;
	  int hour;
	  int minutes;
	  int duration;
	  String ItemName;
	  double MinimumPrice;
	  String Description;
	  Scanner user_input = new Scanner( System.in );
	  switch (theCommand) {
		  case ADDAUCTION:	  
			  year = implementYear(user_input);				  
			  System.out.println("Please enter the Auction month:");
			  month = user_input.nextInt();
			  System.out.println("Please enter the Auction day:");
			  day = user_input.nextInt();
			  System.out.println("Please enter the Auction hour:");
			  hour = user_input.nextInt();
			  System.out.println("Please enter the Auction minute:");
			  minutes = user_input.nextInt();
			  System.out.println("Please enter the duration (in hours) of the Auction");
			  duration = user_input.nextInt();
			  
			  myAuction = new Auction(myNPOName, LocalDateTime.of(year, month, day, hour, minutes), 
					  LocalDateTime.of(year, month, day, hour+duration, minutes));
			  theCalendar.addAuction(myUserName, myAuction, LocalDateTime.of(year, month, day, hour, minutes), 
					  LocalDateTime.of(year, month, day, hour+duration, minutes));
			  break;
		  case EDITAUCTION:

			  System.out.println("The current Auction details:");
			  System.out.println(theAuction.toString());
			  year = implementYear(user_input);				  
			  System.out.println("Please enter the Auction month:");
			  month = user_input.nextInt();
			  System.out.println("Please enter the Auction day:");
			  day = user_input.nextInt();
			  System.out.println("Please enter the Auction hour:");
			  hour = user_input.nextInt();
			  System.out.println("Please enter the Auction minute:");
			  minutes = user_input.nextInt();
			  System.out.println("Please enter the duration (in hours) of the Auction");
			  duration = user_input.nextInt();
			  
			  myAuction = new Auction(myNPOName, LocalDateTime.of(year, month, day, hour, minutes), LocalDateTime.of(year, month, day, hour+duration, minutes));
			  try
			  {
				  theCalendar.removeAuction(theAuction);
				  theCalendar.addAuction(myUserName, myAuction, LocalDateTime.of(year, month, day, hour, minutes), LocalDateTime.of(year, month, day, hour+duration, minutes));
			  } catch (Exception e) {
				  System.out.println("Auction wasn't added.");
			  }			  
			  break;
		  case ADDITEM:
	  
			  System.out.println("Please enter the Item name:");
			  ItemName = user_input.nextLine();
			  System.out.println("Please enter the Minimum Bid:");
			  MinimumPrice = user_input.nextDouble();
			  System.out.println("Please enter the Item Description:");
			  Description = user_input.nextLine();
			  try
			  {
				  theAuction.addItem(new Item(ItemName, MinimumPrice, Description));
			  } catch (Exception e) {
				  
			  }
			  break;
		  case EDITITEM:
			  System.out.println("The current Item details:");
			  System.out.println(theItem.toString());
			  System.out.println("Please enter the Item name:");
			  ItemName = user_input.nextLine();
			  System.out.println("Please enter the Minimum Bid:");
			  MinimumPrice = user_input.nextDouble();
			  System.out.println("Please enter the Item Description:");
			  Description = user_input.nextLine();
			  try
			  {
				  theAuction.addItem(new Item(ItemName, MinimumPrice, Description));
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
			  answer.add(User.Command.VIEWCALENDAR);
			  break;
		  default:
			  break;	  
	  }
	  user_input.close();
	  return answer;
  }
   
  private int implementYear(Scanner user_input) {
	  int year;
	  System.out.println("Please enter the Auction year:");
	  year = user_input.nextInt();
	  if (year <= myLastAuctionYear) {
		  System.out.println("You already have an auction this year.");
		  year = implementYear(user_input);
	  }
	return year;
  }
  
  public int getLastAuctionYear() {
	  return myLastAuctionYear;
  }
  
  public void setLastAuctionYear(int theNewYear) {
	  myLastAuctionYear = theNewYear;
  }

// edits auction info
  
  // adds to auction inventory
  
  // edits auction inventory
  
  public Auction getAuction(){
	return myAuction;}
  
  public boolean setAuction(Auction theAuction){
      myAuction = theAuction;
	return false;
  }
  
}