package view;
import java.util.ArrayList;

import model.EmployeeModel;
import model.ItemModel;
import model.UserModel;

/**
 * This class represents an Auction Central Employee and gives the
 *  functionality to move around the menus.
 *  
 *  @author TCSS 360 Group 5
 */
public class EmployeeController extends UserController
{
	/**
	 * This is the EmployeeModel associated with this class.
	 */
	private EmployeeModel myEmployeeModel;

	/**
	 * Creates a new EmployeeController using the EmployeeModel passed in.
	 * 
	 * @param theModel the EmployeeModel to be used by the controller.
	 * @author Quinn
	 */
	public EmployeeController(UserModel theModel) {
		super("Employee");
		myEmployeeModel = (EmployeeModel) theModel;		
	}

	/**
	 * This method returns the current state of which menu the user
	 * is in if and only if the current command is an option to view
	 * another valid menu for this user.
	 * 
	 * @param theCurrentState This is the menu the user is currently in
	 * @param theCurrentCommand This is the command the user just selected
	 * @author Quinn
	 * 
	 * This method returns the current state of which menu the user is in if and only 
	 * if the current command is an option to view another valid menu for this user.
	 */
	@Override
	public UserController.Command goForwardState(UserController.Command theCurrentState, UserController.Command theCurrentCommand)
	{
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.VIEWCALENDAR)
		 {
			answer = UserController.Command.VIEWCALENDAR;
		 } 
		 else if (theCurrentCommand == UserController.Command.VIEWMAINMENU)
		 {
			 answer = UserController.Command.VIEWMAINMENU;
		 } 		 
		return answer;
	}
	
	/**
	 * This method simply determines whether to call the go back method or go forward method
	 * 
	 * @param theCurrentState This is what menu the user is currently in
	 * @param theCurrentCommand  This is the action the user would like to perform
	 * @return returns a new menu/view state if the user command required it
	 * @author Quinn
	 */
	@Override
	public UserController.Command getNextState(
			UserController.Command theCurrentState,
			UserController.Command theCurrentCommand) {
		UserController.Command answer = theCurrentState;
		if (theCurrentCommand == UserController.Command.GOBACK) {
			answer = goBackState(theCurrentState);
		} else {
			answer = goForwardState(theCurrentState, theCurrentCommand);
		}
		return answer;
	}

	/**
	 * This method determines the previous state for the user and returns it.  If there is no previous state
	 * from the current state, it displays a message to the user and then returns the unchanged current state.
	 * 
	 * @param theCurrentState This is what menu the user is currently in.
	 * @return returns the previous menu if possible and returns the current state otherwise
	 * @author Quinn
	 */
	@Override
	public UserController.Command goBackState(
			UserController.Command theCurrentState) {
		UserController.Command answer = theCurrentState;
		switch (theCurrentState) {
		case VIEWAUCTIONDETAILS:
			answer = UserController.Command.VIEWCALENDAR;
			break;
		default:
			System.out.println("Cannot Go Back");
			break;
		}
		return answer;
	}

	/**
	 * This method accepts a menu as a user command and returns all the menu options
	 * for that menu for this user if applicable.  If there are no menu options available
	 * for that menu for that user, then it returns an empty list.
	 * 
	 * @param theCommand This is the menu that needs menu options
	 * @param theItem This is used in the bidder class for menu selection
	 * @param theUser 
	 * @return returns a list of all the commands that will be put into the next user menu
	 * @author Quinn
	 */
	public ArrayList<Command> GetMenu(Command theCommand, ItemModel theItem,
			UserModel theUser) {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCommand) {
		case VIEWAUCTIONDETAILS:
			answer.add(UserController.Command.GOBACK);
			break;
		// CALENDAR has its own menu
		case VIEWMAINMENU:
			answer.add(UserController.Command.VIEWCALENDAR);
			break;
		default:
			System.out.println("Menu Not Recognized");
			break;
		}
		return answer;
	}
	
	/**
	 * This method returns the model of the class.
	 * (used for testing purposes)
	 * 
	 * @return returns the model stored in this class
	 * @author Shannon
	 */
	public EmployeeModel getEmployeeModel() {
		return myEmployeeModel;
	}
	
}