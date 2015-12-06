package model;

import java.time.LocalDate;

import exceptions.AuctionException;

/**
 * This class represents the model of an Non Profit user with storage of 
 * required fields and checks for business rules.
 * 
 * @author UWT group 5
 */
public class NonProfitModel extends UserModel {

	/**
	 * required for serialization
	 */
	private static final long serialVersionUID = 6214725495197646748L;

	/**
	 * This is the last auction date of the user.  Used to determine if they
	 * can add a new auction or not.
	 */
	private LocalDate myLastAuctionDate;
	/**
	 * This is the current Auction of the user.
	 */
	private AuctionModel myAuction;
	/**
	 * This is the name of the user's NPO
	 */
	private String myNPOName;

	/**
	 * This constructor ensures that the NonProfit has an NPO name and date for last
	 * Auction as a LocalDate.
	 * 
	 * @param theUserName  This is the name of the user.
	 * @param theUserType  This is the user's type.
	 * @param theNPOName  This is the name of the user's NPO.
	 * @param theLastAuctionDate  This is the date of the Last Auction for this user.
	 * @author Quinn
	 */
	public NonProfitModel(String theUserName, UserType theUserType,
			String theNPOName, LocalDate theLastAuctionDate) {
		super(theUserName, theUserType);
		myNPOName = theNPOName;
		myLastAuctionDate = theLastAuctionDate;
	}

	/**
	 * This method checks that the year that the user would like an 
	 * auction to is valid.
	 * 
	 * @param theYear
	 *            This checks if the year of a new auction would be valid.
	 * @throws AuctionException
	 *             This lets the user know why the auction couldn't be added.
	 * @author Quinn
	 */
	public void checkYear(int theYear) throws AuctionException {
		if (theYear < LocalDate.now().getYear()
				|| theYear > LocalDate.now().plusYears(1).getYear()) {
			throw new AuctionException(
					"Auctions can only be sheduled this year and next year.");
		}
	}

	/**
	 * This checks the 365 day until you can add an auction business rule.
	 * 
	 * @param theDate
	 *            This is the date that the new auction would be on.
	 * @throws AuctionException
	 *             This lets the user know why the auction couldn't be added.
	 * @author Quinn
	 */
	public void check365(LocalDate theDate) throws AuctionException {
		if (theDate.minusDays(365).isBefore(myLastAuctionDate)) {
			throw new AuctionException(
					"Your next auction must be 365 days after your last auction");
		}
	}

	/**
	 * This is a getter for the users NPO name.
	 * 
	 * @return returns the NPO name as a string.
	 * @author Quinn
	 */
	public String getNPOName() {
		return myNPOName;
	}

	/**
	 * This is a getter for the user's current auction's date
	 * 
	 * @return returns the date of the current auction.
	 * @author Quinn
	 */
	public LocalDate getCurrentAuctionDate() {
		if (myAuction != null) {
			return myAuction.getStartTime().toLocalDate();
		} else {
			return null;
		}
	}

	/**
	 * This is a getter for the last auction date. Used to check if the user can
	 * add another auction.
	 * 
	 * @return returns a LocalDate of when the user's last auction was.
	 * @author Quinn
	 */
	public LocalDate getLastAuctionDate() {
		return myLastAuctionDate;
	}

	/**
	 * This method is a setter for the last auction date of the user.
	 * 
	 * @param myLastAuctionDate
	 *            This is the date that the user last had an auction
	 * @author Quinn
	 */
	public void setLastAuctionDate(LocalDate myLastAuctionDate) {
		this.myLastAuctionDate = myLastAuctionDate;
	}

	/**
	 * A getter for this users Auction
	 * 
	 * @return returns this users Auction
	 * @author Quinn
	 */
	public AuctionModel getAuction() {
		return myAuction;
	}

	/**
	 * Sets the Auction for this user to a new auction
	 * 
	 * @param theAuction
	 *            This is the auction that will be set as the users auction
	 * @author Quinn
	 */
	public void setAuction(AuctionModel theAuction) {
		myAuction = theAuction;
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
		boolean answer = super.equals(theOther);
		if (answer && theOther instanceof NonProfitModel) {
			NonProfitModel theOtherModel = (NonProfitModel) theOther;
			if (theOtherModel.myNPOName.compareTo(myNPOName) != 0) {
				answer = false;
			}
			if (!theOtherModel.myLastAuctionDate.equals(myLastAuctionDate)) {
				answer = false;
			}
			if (!theOtherModel.myAuction.equals(myAuction)) {
				answer = false;
			}
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
		answer += myNPOName.hashCode();
		answer += myLastAuctionDate.toString().hashCode();
		answer += myAuction.hashCode();
		return answer;
	}

}