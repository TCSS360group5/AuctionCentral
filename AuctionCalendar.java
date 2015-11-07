package user;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class AuctionCalendar {
	  
	  // public for now, maybe use a hashmap or something instead?
	  // I thought it would be helpful to easily access the date
	  //public Map<Date, Auction> auctionList = new ArrayMap<Date, Auction>();
	  
	  // create calendar based of of existing auctions perhaps?
	  public AuctionCalendar(File Auctions)
	  {
	  }
	  
	  // maybe needs more arguments of auction details?
	  // returns whether or not a successful add
	  public boolean addAuction(String orgName, Date auctionDate)
	  {
		return false;
	  }
	  
	  // returns whether or not a successful edit
	  public void editAuctionOrg(Auction theAuction, String orgName)
	  {
	  }
	  
	  // returns whether or not a successful edit
	  public void editAuctionDate(Auction theAuction, String auctionDate)
	  {
	  }
	  
	  // returns auctions for the current month
	  public Map<Date, Auction> displayCurrentMonth()
	  {
		return null;
	  }
	  
	  public Map<Date, Auction> displayChosenMonth(String month)
	  {
		return null;
	  }
	  
	  // I was thinking the user could type in the auction name that was displayed to them and get the auction
	  public Auction viewAuction(String auctionName)
	  {
		return null;
	  }

		public boolean addAuction(String myNPOName, LocalDateTime of) {
			return false;
			// TODO Auto-generated method stub
			
		}
	
	}
