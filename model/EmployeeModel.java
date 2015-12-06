package model;

/**
 * This class represents an Auction Central Employee.
 * Since it doesn't do much, there is really no functionality
 * 
 */
public class EmployeeModel extends UserModel {

	/**
	 * required for serialization
	 */
	private static final long serialVersionUID = 2270770097531002818L;

	/**
	 * Creates an EmployeeModel with the specified name and type.
	 * 
	 * @param theUsername The user name of this user
	 * @param theUserType The type of this user
	 */
	public EmployeeModel(String theUsername, UserType theUserType) {
		super(theUsername, theUserType);
	}
}