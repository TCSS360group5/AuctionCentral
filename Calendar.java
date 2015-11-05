public class Calendar
{
  
  // public for now, maybe use a hashmap or something instead?
  // I thought it would be helpful to easily access the date
  public Map<Date, Auction> auctionList = new Map<Date, Auction>();
  
  // create calendar based of of existing auctions perhaps?
  public Calendar(File Auctions)
  {
  }
  
  // maybe needs more arguments of auction details?
  // returns whether or not a successful add
  public boolean addAuction(String orgName, Date auctionDate)
  {
  }
  
  // I was thinking we could have a string that represents what to edit instead
  // of having multiple methods for different edits
  // returns whether or not a successful edit
  public editAuction(Auction theAuction, String whatToEdit, String newEdit)
  {
  }
  
  // returns auctions for the current month
  public Map<Date, Auction> displayCurrentMonth()
  {
  }
  
  public Map<Date, Auction> displayChosenMonth(String month)
  {
  }
  
  // I was thinking the user could type in the auction name that was displayed to them and get the auction
  public Auction viewAuction(String auctionName)
  {
  }
}
