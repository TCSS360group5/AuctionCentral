package model;

/**
 * This class represents an Auction Central Employee and gives the
 *  functionality to move around the menus.
 */
public class EmployeeModel extends UserModel
{

  /**
	 * 
	 */
	private static final long serialVersionUID = 2270770097531002818L;

/**
 * @param theUsername
 * @param theUserType
 */
public EmployeeModel(String theUsername, UserType theUserType) {
		super(theUsername, theUserType);
	}
	//uses the default user methods and nothing more.
}