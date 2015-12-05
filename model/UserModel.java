package model;

import java.io.Serializable;

/**
 * 
 *
 */
public class UserModel implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
 * 
 * This enum represents what kind of user the user is.
 */
public enum UserType {NPO, EMPLOYEE, BIDDER}
  
  private UserType myUserType;
  private String myUserName;

  
  /**
 * @param theUserName
 * @param theUserType
 */
public UserModel(String theUserName, UserType theUserType)
  {
	  myUserName = theUserName;
	  myUserType = theUserType;	  
  }
  
	  /**
	 * @return
	 */
	public UserType getUserType(){
		  return myUserType;
	  }
	  /**
	 * @return
	 */
	public String getUserName(){
		  return myUserName;
	  }
	  /**
	 * @param theUserType
	 */
	public void setUserType(UserType theUserType){
		  myUserType = theUserType;
	  }
	  /**
	 * @param theUsername
	 */
	public void setUserName(String theUsername){
		  myUserName = theUsername;
	  }
		
	  /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		  return myUserName + " " + myUserType.toString();
	} 
	
	@Override
	public boolean equals(Object theOther){
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
	
	@Override
	public int hashCode() {
		int answer = 0;
		answer += myUserName.hashCode();
		answer += myUserType.toString().hashCode();
		return answer;
	}
}
