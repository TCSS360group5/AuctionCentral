import java.util.ArrayList;

public class Employee extends User
{

  public Employee(String theUsername, UserType theUserType) {
		super(theUsername, theUserType);
	}

public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	ArrayList<Command> answer = new ArrayList<Command>();
	switch (theCommand) {
	case VIEWMAINAUCTIONS:
		answer.add(User.Command.GOBACK);
		break;
	case VIEWCALENDAR:
		answer.add(User.Command.GOBACK);
		answer.add(User.Command.VIEWMAINAUCTIONS);
		break;
	case VIEWMAINMENU:
		answer.add(User.Command.VIEWCALENDAR);
		//answer.add(User.Command.VIEWAUCTION);
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
		 	case VIEWONEAUCTION:
		 		answer = User.Command.VIEWCALENDAR;
		 		break;
	 		case VIEWITEM:
	 			answer = User.Command.VIEWONEAUCTION;
	 			break;						
	 		default:
	 			break;						 
		 }		
		return answer;
	}

  // public void viewCurrentCalendarMonth()
//   {
//   }
  
  // public void viewSelectedCalendarMonth(String Month)
//   {
//   }
  
  // public void viewAuctionDetails(Date auction)
//   {
//   }
  
}