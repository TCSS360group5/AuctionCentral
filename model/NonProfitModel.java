package model;
import java.time.LocalDate;

import exceptions.AuctionException;

public class NonProfitModel extends UserModel
{
  private LocalDate myLastAuctionDate;
  private AuctionModel myAuction; 
  private String myNPOName;
  
  public NonProfitModel(String theUserName, UserType theUserType, String theNPOName, LocalDate theLastAuctionDate){
	  super(theUserName, theUserType);
	  myNPOName = theNPOName;
	  myLastAuctionDate = theLastAuctionDate;
  }
  	
  public void checkYear(int theYear) throws AuctionException
  {

	  if (theYear < LocalDate.now().getYear() || theYear > LocalDate.now().plusYears(1).getYear())
	  {
		  throw new AuctionException("Auctions can only be sheduled this year and next year.");
	  }
  }
  
  public void check365(LocalDate theDate) throws AuctionException
  {
	  if(theDate.minusDays(365).isBefore(myLastAuctionDate))
	  {
		  throw new AuctionException("Your next auction must be 365 days after your last auction");
	  }
  }
  
  public String getNPOName()
  {
	  return myNPOName;
  }
  
  public void setNPOName(String theNewName)
  {
	  myNPOName = theNewName;
  }
  
  public LocalDate getCurrentAuctionDate() 
  {
	  if (myAuction != null)
	  {
		  return myAuction.getStartTime().toLocalDate();
	  } 
	  else 
	  {
		  return null;
	  }	  
  }
  
  public LocalDate getLastAuctionDate() {
	return myLastAuctionDate;
  }
  
  public void setLastAuctionDate(LocalDate myLastAuctionDate) {
	this.myLastAuctionDate = myLastAuctionDate;
  }

public AuctionModel getAuction()
  {
	  return myAuction;
  }
  
  public boolean setAuction(AuctionModel theAuction)
  {
	  myAuction = theAuction;
	  return false;
  }
  
  public boolean hasAuction()
  {
	  return myAuction != null;
  }
  
//  public boolean canAddAuction() {	
//	  return (myAuction == null);
//  }
}