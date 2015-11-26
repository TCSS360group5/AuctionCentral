package model;

/**
 * This class represents an Auction Central Employee and gives the
 *  functionality to move around the menus.
 */
public class EmployeeModel extends UserModel
{

  /**
 * @param theUsername
 * @param theUserType
 */
public EmployeeModel(String theUsername, UserType theUserType) {
		super(theUsername, theUserType);
	}

//  public ArrayList<Command> GetMenu(Command theCommand, Item theItem)
//  {
//		ArrayList<Command> answer = new ArrayList<Command>();
//		switch (theCommand) {
//		case VIEWAUCTIONDETAILS:
//			answer.add(User.Command.GOBACK);
//			break;
//		case VIEWCALENDAR:
//			answer.add(User.Command.GOBACK);
//			answer.add(User.Command.VIEWAUCTIONDETAILS);
//			break;
//		case VIEWMAINMENU:
//			answer.add(User.Command.VIEWCALENDAR);
//			break;
//		default:
//			System.out.println("Menu Not Recognized");
//			break;
//		}
//		return answer;
//  }
  
///* (non-Javadoc)
// * @see User#ExecuteCommand(User.Command, Calendar, Auction, Item)
// */
//public boolean ExecuteCommand(Command theCommand, Calendar theCalendar, Auction theAuction, Item theItem)
//  {
//	boolean answer = false;
//	switch (theCommand) {
//	default:
//		System.out.println("Command Not Recognized");
//		break;
//	}
//	return answer;
//  }

//	public User.Command goBackState(User.Command theCurrentState) 
//	{
//	  User.Command answer = theCurrentState;
//		switch (theCurrentState)
//		 {			
//			case VIEWAUCTIONDETAILS:
//				answer = User.Command.VIEWCALENDAR;
//				break;
//			case VIEWCALENDAR:
//				answer = User.Command.VIEWMAINMENU;
//				break;
//	 		default:
//	 			System.out.println("Cannot Go Back");
//	 			break;						 
//		 }		
//		return answer;
//	}  
}