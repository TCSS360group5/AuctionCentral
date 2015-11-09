import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.text.DateFormatSymbols;

public class Calendar
{
  public Map<LocalDateTime, ArrayList<Auction>> myAuctionByDateList;
  public int futureAuctions;
  DateFormatSymbols symbols = new DateFormatSymbols();
  
  public Calendar()
  {
	  myAuctionByDateList = new HashMap<LocalDateTime, ArrayList<Auction>>();
	  futureAuctions = 0;
  }
  
  public boolean addAuction(String orgName, LocalDateTime Start, LocalDateTime End)
			{
				return addAuction(orgName, Start.getMonth().getValue(), Start.getDayOfMonth(), Start.getYear(), 
						Start.getHour(), Start.getMinute(), End.getHour(), End.getMinute());
			}
  
  public boolean addAuction(String orgName, int month, int day, int year,
		  					int auctionHourStart, int auctionMinuteStart,
		  					int auctionHourEnd, int auctionMinuteEnd)
  {
	  // create new dates
	  //LocalDate	auctionDate = LocalDate.of(year, month, day);
	  LocalDateTime auctionStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
	  LocalDateTime auctionEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
	  String auctionName = orgName.replace(' ', '-') + symbols.getMonths()[month-1] + "-" + day + "-" + year;
	  
	  // new auction to add
	  Auction auctionToAdd = new Auction(orgName, auctionName, auctionStart, auctionEnd);
	  
	  // checking business rules (does not check if the organization has already had an auction for the year)
	  // don't add if future auctions is already at capacity
	  if(futureAuctions == 25)
	  {
		  return false;
	  }
	  // do not allow past auctions to be added (not a business rule, but convenient)
	  else if(auctionStart.isAfter(LocalDateTime.now().plusDays(90)) || auctionStart.isBefore(LocalDateTime.now()))
	  {		   
		  return false;
	  }
	  // if there is already at least one auction scheduled on the day, check business rules
	  else if(myAuctionByDateList.containsKey(auctionStart))
	  {
		  ArrayList<Auction> dayAuctions = myAuctionByDateList.get(auctionStart);
		  
		  // no more than 2 auctions per day
		  if(dayAuctions.size() < 2)
		  {
			  Auction firstAuction = dayAuctions.get(0);
			  if(auctionStart.isBefore(firstAuction.getStartDate()))
			  {
				  // at least 2 hours between actions
				  if(auctionEnd.plusHours(2).isBefore(firstAuction.getStartDate()))
				  {
					  dayAuctions.add(auctionToAdd);
					  futureAuctions ++;
					  myAuctionByDateList.replace(auctionStart, dayAuctions);
					  return true;
				  }
				  else
				  {
					  return false;
				  }
			  }
			  else
			  {
				  // at least 2 hours between actions
				  if(auctionStart.isAfter(firstAuction.getEndDate().plusHours(2)))
				  {
					  dayAuctions.add(auctionToAdd);
					  futureAuctions ++;
					  myAuctionByDateList.replace(auctionStart, dayAuctions);
					  return true;
				  }
				  else
				  {
					  return false;
				  }
			  }
		  }
		  // 2 auctions already scheduled for the day
		  else
		  {
			  return false;
		  }
	  }
	  // go ahead and schdule if no auctions already scheduled for the day
	  else
	  {
		  ArrayList<Auction> newListToAdd = new ArrayList<Auction>();
		  newListToAdd.add(auctionToAdd);
		  futureAuctions ++;
		  myAuctionByDateList.put(auctionStart, newListToAdd);
		  return true;
	  }
	  
  }
  
  public void editAuctionOrg(Auction theAuction, String orgName)
  {
	  DateFormatSymbols symbols = new DateFormatSymbols();
	  int month = theAuction.getStartDate().getMonth().getValue();
	  int day = theAuction.getStartDate().getDayOfMonth();
	  int year = theAuction.getStartDate().getYear();
	  // naming format requirement
	  String auctionName = orgName.replace(' ', '-') + symbols.getMonths()[month-1] + "-" + day + "-" + year;
	  
	  // change name and auction name
	  theAuction.setAuctionOrg(orgName);
	  theAuction.setAuctionName(auctionName);
  }
  
  public void editAuctionDateTime(Auction theAuction,int month, int day, int year,
								int auctionHourStart, int auctionMinuteStart,
								int auctionHourEnd, int auctionMinuteEnd)
  {
	  String orgName = theAuction.getAuctionOrg();
	  LocalDateTime auctionStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
	  LocalDateTime auctionEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
	  // naming format requirement
	  String auctionName = orgName.replace(' ', '-') + symbols.getMonths()[month-1] + "-" + day + "-" + year;
	  
	  // change times and auction name
	  theAuction.setAuctionDate(auctionStart, auctionEnd);
	  theAuction.setAuctionName(auctionName);
  }
  
  // returns auctions for the current month
  public Map<LocalDateTime, ArrayList<Auction>> displayCurrentMonth()
  {
	  return displayChosenMonth(LocalDateTime.now().getMonth().getValue());
  }
  
  public Map<LocalDateTime, ArrayList<Auction>> displayChosenMonth(int month)
  {
	  Map<LocalDateTime, ArrayList<Auction>> returnMap = new HashMap<LocalDateTime,ArrayList<Auction>>();
	  Month chosenMonth = Month.of(month);
	  for(Entry<LocalDateTime, ArrayList<Auction>> entry : myAuctionByDateList.entrySet())
	  {
		  if(entry.getKey().getMonth() == chosenMonth)
		  {
			  returnMap.put(entry.getKey(), entry.getValue());
		  }
	  }
	  
	  return returnMap;
	  
  }
  
  public Auction viewAuction(String auctionName, LocalDateTime auctionDate)
  {
	  ArrayList<Auction> dayAuctions = myAuctionByDateList.get(auctionDate);
	  if(dayAuctions.size() == 0)
	  {
		  return dayAuctions.get(0);
	  }
	  else
	  {
		  for(int i = 0; i < dayAuctions.size(); i++)
		  {
			  Auction auction = dayAuctions.get(i);
			  if(auction.getAuctionName().equals(auctionName))
			  {
				  return auction;
			  }
		  }
	  }
	return null;
  }
  
}

