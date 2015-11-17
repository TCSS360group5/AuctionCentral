import java.util.ArrayList;

public class Employee extends User
{

  public Employee(String theUsername, UserType theUserType) {
		super(theUsername, theUserType);
	}


  public ArrayList<Command> GetMenu(Command theCommand)
  {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCommand) {
		case VIEWAUCTIONS:
			answer.add(User.Command.GOBACK);
			break;
		case VIEWCALENDAR:
			answer.add(User.Command.GOBACK);
			answer.add(User.Command.VIEWAUCTIONS);
			break;
		case VIEWMAINMENU:
			answer.add(User.Command.VIEWCALENDAR);
			break;
		default:
			break;
		}
		return answer;
  }
  
public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
  {
	ArrayList<Command> answer = new ArrayList<Command>();
	switch (theCommand) {
	case VIEWAUCTIONS:
		answer.add(User.Command.GOBACK);
		break;
	case VIEWCALENDAR:
		answer.add(User.Command.GOBACK);
		answer.add(User.Command.VIEWAUCTIONS);
		break;
	case VIEWMAINMENU:
		answer.add(User.Command.VIEWCALENDAR);
		break;
	default:
		System.out.println("Movement Command Not Recognized");
		break;
	}
	return answer;
  }

	public User.Command goBackState(User.Command theCurrentState) 
	{
	  User.Command answer = null;
		switch (theCurrentState)
		 {						
	 		default:
	 			System.out.println("Command Not Recognized");
	 			break;						 
		 }		
		return answer;
	}  
}