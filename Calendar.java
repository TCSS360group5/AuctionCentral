import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.text.DateFormatSymbols;

public class Calendar
{
  public Map<LocalDate, ArrayList<Auction>> auctionList;
  public int futureAuctions;
  DateFormatSymbols symbols = new DateFormatSymbols();
  
  public Calendar()
  {
	  auctionList = new HashMap<LocalDate, ArrayList<Auction>>();
	  futureAuctions = 0;
  }
  
  public boolean addAuction(String orgName, int month, int day, int year,
		  					int auctionHourStart, int auctionMinuteStart,
		  					int auctionHourEnd, int auctionMinuteEnd)
  {
	  // create new dates
	  LocalDate	auctionDate = LocalDate.of(year, month, day);
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
	  else if(auctionDate.isAfter(LocalDate.now().plusDays(90)) || auctionDate.isBefore(LocalDate.now()))
	  {		   
		  return false;
	  }
	  // if there is already at least one auction scheduled on the day, check business rules
	  else if(auctionList.containsKey(auctionStart))
	  {
		  ArrayList<Auction> dayAuctions = auctionList.get(auctionStart);
		  
		  // no more than 2 auctions per day
		  if(dayAuctions.size() < 2)
		  {
			  Auction firstAuction = dayAuctions.get(0);
			  if(auctionStart.isBefore(firstAuction.getStartTime()))
			  {
				  // at least 2 hours between actions
				  if(auctionEnd.plusHours(2).isBefore(firstAuction.getStartTime()))
				  {
					  dayAuctions.add(auctionToAdd);
					  futureAuctions ++;
					  auctionList.replace(auctionDate, dayAuctions);
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
				  if(auctionStart.isAfter(firstAuction.getEndTime().plusHours(2)))
				  {
					  dayAuctions.add(auctionToAdd);
					  futureAuctions ++;
					  auctionList.replace(auctionDate, dayAuctions);
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
		  auctionList.put(auctionDate, newListToAdd);
		  return true;
	  }
	  
  }
  
  public void editAuctionOrg(Auction theAuction, String orgName)
  {
	  DateFormatSymbols symbols = new DateFormatSymbols();
	  int month = theAuction.getStartTime().getMonth().getValue();
	  int day = theAuction.getStartTime().getDayOfMonth();
	  int year = theAuction.getStartTime().getYear();
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
	  String orgName = theAuction.getOrgName();
	  LocalDateTime auctionStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
	  LocalDateTime auctionEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
	  // naming format requirement
	  String auctionName = orgName.replace(' ', '-') + symbols.getMonths()[month-1] + "-" + day + "-" + year;
	  
	  // change times and auction name
	  theAuction.setStartTime(auctionStart);
	  theAuction.setEndTime(auctionEnd);
	  theAuction.setAuctionName(auctionName);
  }
  
  // returns auctions for the current month
  public Map<LocalDate, ArrayList<Auction>> displayCurrentMonth()
  {
	  return displayChosenMonth(LocalDate.now().getMonth().getValue());
  }
  
  public Map<LocalDate, ArrayList<Auction>> displayChosenMonth(int month)
  {
	  Map<LocalDate,ArrayList<Auction>> returnMap = new HashMap<LocalDate,ArrayList<Auction>>();
	  Month chosenMonth = Month.of(month);
	  for(Entry<LocalDate, ArrayList<Auction>> entry : auctionList.entrySet())
	  {
		  if(entry.getKey().getMonth() == chosenMonth)
		  {
			  returnMap.put(entry.getKey(), entry.getValue());
		  }
	  }
	  
	  return returnMap;
	  
  }
  
  public Auction viewAuction(String auctionName, LocalDate auctionDate)
  {
	  ArrayList<Auction> dayAuctions = auctionList.get(auctionDate);
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
  }
  
}

