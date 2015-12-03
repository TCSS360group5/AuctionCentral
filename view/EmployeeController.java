package view;
import model.EmployeeModel;
import model.UserModel;



/**
 * This class represents an Auction Central Employee and gives the
 *  functionality to move around the menus.
 *  
 *  @author TCSS 360 Group 5
 */
public class EmployeeController extends UserController
{
	
	private EmployeeModel myEmployeeModel;

	/**
	 * Creates a new EmployeeController using the EmployeeModel passed in.
	 * 
	 * @param theModel the EmployeeModel to be used by the controller.
	 */
	public EmployeeController(UserModel theModel) {
		super("Employee");
		myEmployeeModel = (EmployeeModel) theModel;		
	}

	/**
	 * @param theCurrentState This is the menu the user is currently in
	 * @param theCurrentCommand This is the command the user just selected
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
	
	public EmployeeModel getEmployeeModel() {
		return myEmployeeModel;
	}
	
}