package model;

import java.io.Serializable;

/**
 * This is the parent class for all users and stores each users name and type.
 * @author UWT Group 5
 */
public class UserModel implements Serializable {
	
	/**
	 * required for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This enum represents what kind of user the user is.
	 * 
	 * @author Quinn
	 */
	public enum UserType {
		NPO, EMPLOYEE, BIDDER
	}

	/**
	 * This is what type the user is.
	 */
	private UserType myUserType;
	/**
	 * This is the name of the user.
	 */
	private String myUserName;

	/**
	 * This constructor stores the user name and type.
	 * 
	 * @param theUserName This is the name of the new user.
	 * @param theUserType This is the type of the new user.
	 * @author Quinn
	 */
	public UserModel(String theUserName, UserType theUserType) {
		myUserName = theUserName;
		myUserType = theUserType;
	}

	/**
	 * This is the getter for the user type.
	 * 
	 * @return returns the type of user this is.
	 * @author Quinn
	 */
	public UserType getUserType() {
		return myUserType;
	}

	/**
	 * This is the getter for the user name.
	 * 
	 * @return returns the string representing the user name.
	 * @author Quinn
	 */
	public String getUserName() {
		return myUserName;
	}

	/**
	 * This is the setter for the user type.
	 * 
	 * @param theUserType This is the new user type that the user will be set to.
	 * @author Quinn
	 */
	public void setUserType(UserType theUserType) {
		myUserType = theUserType;
	}

	/**
	 * This is the setter for the user name
	 * 
	 * @param theUsername  This is the new name that the user will be set to.
	 * @author Quinn
	 */
	public void setUserName(String theUsername) {
		myUserName = theUsername;
	}

	/**
	 * This is the string that represents this user.  Used for testing
	 * 
	 * @return returns a string that represents the user.
	 * @author Quinn
	 */
	@Override
	public String toString() {
		return myUserName + " " + myUserType.toString();
	}

	/**
	 * Compares this object to another object.
	 * 
	 * @param theOther
	 *            this is the other object being compared.
	 * @return returns true if the objects have the same values in their fields
	 * @author Quinn
	 */
	@Override
	public boolean equals(Object theOther) {
		boolean answer = true;
		if (theOther instanceof UserModel) {
			UserModel theOtherModel = (UserModel) theOther;
			if (theOtherModel.myUserName.compareTo(myUserName) != 0) {
				answer = false;
			}
			if (theOtherModel.myUserType != myUserType) {
				answer = false;
			}
		} else {
			answer = false;
		}
		return answer;
	}

	/**
	 * This method produces the hashCode of this object.
	 * 
	 * @return returns the hashCode of this object.
	 * @author Quinn
	 */
	@Override
	public int hashCode() {
		int answer = 0;
		answer += myUserName.hashCode();
		answer += myUserType.toString().hashCode();
		return answer;
	}
}
