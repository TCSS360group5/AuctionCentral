import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonProfit extends User
{
  private Auction myAuction;
  private String myNPOName;
  
  public NonProfit(String theUserName, UserType theUserType, String theNPOName){
	  super(theUserName, theUserType);
	  myNPOName = theNPOName;
  }
  
  public ArrayList<Command> GetMenu(Command theCommand)
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
			System.out.println("Movement Command Not Recognized");
			break;
	  }
	return answer;	  
  }
  
  private boolean canAddAuction() {
	// TODO Auto-generated method stub
	return false;
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
	  String itemName;
	  double minimumPrice;
	  String description;
	  
	  Scanner user_input = new Scanner( System.in );
	  switch (theCommand) {
		  case ADDAUCTION:	  
			  year = checkYear(user_input);				  
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
			  Auction tempAuction = new Auction(myNPOName, super.getUserName(), LocalDateTime.of(year, month, day, hour, minutes), 
					  LocalDateTime.of(year, month, day, hour+duration, minutes));
			  System.out.println(tempAuction);
			  
			  
			  if(theCalendar.addAuction(tempAuction))
			  {
				  System.out.println("Auction added!");
				  myAuction = tempAuction;
			  }
			  else
				  System.out.println("There was an error adding your auction.");
			  break;
		  case EDITAUCTION:

			  System.out.println("The current Auction details:");
			  System.out.println(myAuction.toString());
			  year = checkYear(user_input);				  
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
			  
			  List<Item> auctionItems = myAuction.getAuctionItems();
			  Auction newAuction = new Auction(myNPOName, super.getUserName(), LocalDateTime.of(year, month, day, hour, minutes), LocalDateTime.of(year, month, day, hour+duration, minutes), auctionItems);
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
			  System.out.println("\n What would you like to do next?");
			  break;
		  case ADDITEM:
	  
			  System.out.println("Please enter the Item name:");
			  itemName = user_input.nextLine();
			  System.out.println("Please enter the Minimum Bid:");
			  minimumPrice = user_input.nextDouble();
			  user_input.nextLine();
			  System.out.println("Please enter the Item Description:");
			  description = user_input.nextLine();
			  try
			  {
				  theAuction.addItem(new Item(itemName, minimumPrice, description));
			  } catch (Exception e) {
				  System.out.println("Item could not be added");
			  }
			  
//			  System.out.println("Current items: " + theAuction.getAuctionItems());
			  
			  break;
		  case EDITITEM:
			  System.out.println("The current Item details:");
			  System.out.println(theItem.toString());
			  System.out.println("Please enter the Item name:");
			  itemName = user_input.nextLine();
			  System.out.println("Please enter the Minimum Bid:");
			  minimumPrice = user_input.nextDouble();
			  user_input.nextLine();
			  System.out.println("Please enter the Item Description:");
			  description = user_input.nextLine();
			  // there is a bug here.. console is not waiting for the user input
			  
			  try
			  {
				  theAuction.removeItem(theItem);
				  theAuction.addItem(new Item(itemName, minimumPrice, description));
			  } catch (Exception e) {
				  
			  }
			  break;
		  default:
			  System.out.println("Command Not Recognized");
			  break;	  
	  }
	  return answer;
  }
   
  private int checkYear(Scanner user_input) 
  {
	  int year;
	  System.out.println("Please enter the Auction year:");
	  year = user_input.nextInt();
	  if (year < LocalDate.now().getYear()){
		  System.out.println("Auctions must be in the future.");
		  year = checkYear(user_input);
	  }
	  if (myAuction != null && year <= myAuction.getStartTime().getYear()) 
	  {
		  System.out.println("You already have an auction this year.");
		  year = checkYear(user_input);
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
   
  public Auction getAuction()
  {
	  return myAuction;
  }
  
  public boolean setAuction(Auction theAuction)
  {
	  myAuction = theAuction;
	  return false;
  }
  
  public Command getMovementCommand(Command theCurrentState) {
		User.Command answer = theCurrentState;
		switch (theCurrentState) 
		{
		case ADDAUCTION:
			break;
		case ADDITEM:
			break;
		case BID:
			break;
		case EDITAUCTION:
			break;
		case EDITBID:
			break;
		case EDITITEM:
			break;
		case GOBACK:
			User.Command moveState = this.goBackState(theCurrentState);
			 if (moveState != null) 
			 {
				 answer = moveState;
			 }			
			 else 
			 {
				 System.out.println("Cannot go Back.");
			 }
			break;
		case LOGIN:
			break;
		case VIEWAUCTIONS:
			break;
		case VIEWBIDS:
			break;
		case VIEWCALENDAR:
			break;
		case VIEWITEM:
			break;
		case VIEWMAINMENU:
			break;
		case VIEWMYAUCTION:
			break;
		case VIEWONEAUCTION:
			break;
		default:
			break;		
		}
		
		return answer;
	}
  
  public User.Command goBackState(User.Command theCurrentState) 
	{
	  User.Command answer = null;
		switch (theCurrentState)
		 {
		 	case VIEWCALENDAR:
		 		answer = User.Command.VIEWMAINMENU;
				break;
		 	case VIEWMYAUCTION:
		 		answer = User.Command.VIEWCALENDAR;
		 		System.out.println("Calendar Menu");
		 		break;
	 		case VIEWITEM:
	 			answer = User.Command.VIEWMYAUCTION;
	 			System.out.println("Auction Menu");
	 			break;						
	 		default:
	 			//System.out.println("Cannot Go Back");
	 			break;						 
		 }		
		return answer;
	}
  
//  public boolean hasAuction()
//  {
//	  
//	  return myExistingAuctionStatus;
//  }
  
}