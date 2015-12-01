package model;

/**
 * 
 *
 */
public class UserModel
{
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
	public String toString() {
		  return myUserName + " " + myUserType.toString();
	  } 
}
