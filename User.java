public class User {
  String myUsername;
  String myUserType;
  
  public User(String theUsername, int theUserType) {
	  if(theUserType == 1) {
		  myUserType = "AuctionCentral";
	  } else if (theUserType == 2) {
		  myUserType = "NPO";
	  } else if (theUserType == 3) {
		  myUserType = "Bidder";
	  }
  }
  
  // ... a little confused about methods here...
}
