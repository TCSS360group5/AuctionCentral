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
	case VIEWAUCTION:
		answer.add(User.Command.GOBACK);
		break;
	case VIEWCALENDAR:
		answer.add(User.Command.GOBACK);
		answer.add(User.Command.VIEWAUCTION);
		break;
	case VIEWMAINMENU:
		answer.add(User.Command.VIEWCALENDAR);
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
