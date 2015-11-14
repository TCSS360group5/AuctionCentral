import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.text.DateFormatSymbols;

public class Calendar
{
  public Map<LocalDate, ArrayList<Auction>> myAuctionByDateList;
  public int futureAuctions;
  DateFormatSymbols symbols = new DateFormatSymbols();
  
  // maybe new constructor to pass in a Map of previous auctions stored in text file from main? and the number of future auctoins
  
  
  public Calendar()
  {
	  myAuctionByDateList = new HashMap<LocalDate, ArrayList<Auction>>();
	  futureAuctions = 0;
  }
  
  /** I changed these constructors to accept an Auction to add. This has been working with main so far. 
    	Didnt want to delete in case you wanted to change it
    	-Shannon 11/13/15 */
  
  
//  public boolean addAuction(String userName, String orgName, LocalDateTime Start, LocalDateTime End)
//			{
//				return addAuction(userName, orgName, Start.getMonth().getValue(), Start.getDayOfMonth(), Start.getYear(), 
//						Start.getHour(), Start.getMinute(), End.getHour(), End.getMinute());
//			}
  public boolean addAuction(String userName, Auction theAuction, LocalDateTime Start, LocalDateTime End) 
	{
		return addAuction(userName, theAuction, Start.getMonth().getValue(), Start.getDayOfMonth(), Start.getYear(), 
				Start.getHour(), Start.getMinute(), End.getHour(), End.getMinute());
	}
  
  // TODO: check the week requirement
 
//  public boolean addAuction(String userName, String orgName, int month, int day, int year,
//		  					int auctionHourStart, int auctionMinuteStart,
//		  					int auctionHourEnd, int auctionMinuteEnd)
//  {
  	public boolean addAuction(String userName, Auction theAuctionToAdd, int month, int day, int year,
			int auctionHourStart, int auctionMinuteStart,
			int auctionHourEnd, int auctionMinuteEnd)
  	{	
	  
	  boolean result = false;
	  
	  // create new dates
	  LocalDate auctionDate = LocalDate.of(year, month, day);
	  LocalDateTime auctionStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
	  LocalDateTime auctionEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
	  String auctionName = theAuctionToAdd.myOrgName.replace(' ', '-') + "-" + symbols.getMonths()[month-1] + "-" + day + "-" + year;
	  
	  // new auction to add
//	  Auction auctionToAdd = new Auction(orgName, auctionStart, auctionEnd);
	  theAuctionToAdd.setAuctionName(auctionName);
	  theAuctionToAdd.setUserName(userName);
	  
	  // checking business rules (does not check if the organization has already had an auction for the year)
	  // don't add if future auctions is already at capacity
	  if(futureAuctions == 25)
	  {
		  result = false;
	  }
	  // do not allow past auctions to be added (not a business rule, but convenient)
	  else if(auctionStart.isAfter(LocalDateTime.now().plusDays(90)) || auctionStart.isBefore(LocalDateTime.now()))
	  {		   
		  result = false;
	  }
	  // if there is already at least one auction scheduled on the day, check business rules
	  else if(myAuctionByDateList.containsKey(auctionStart))
	  {
		  ArrayList<Auction> dayAuctions = myAuctionByDateList.get(auctionStart);
		  
		  // no more than 2 auctions per day
		  if(dayAuctions.size() < 2)
		  {
			  Auction firstAuction = dayAuctions.get(0);
			  if(auctionStart.isBefore(firstAuction.getStartTime()))
			  {
				  // at least 2 hours between actions
				  if(auctionEnd.plusHours(2).isBefore(firstAuction.getStartTime()))
				  {
					  dayAuctions.add(theAuctionToAdd);
					  futureAuctions ++;
					  myAuctionByDateList.replace(auctionDate, dayAuctions);
					  result = true;
				  }
				  else
				  {
					  result = false;
				  }
			  }
			  else
			  {
				  // at least 2 hours between actions
				  if(auctionStart.isAfter(firstAuction.getEndTime().plusHours(2)))
				  {
					  dayAuctions.add(theAuctionToAdd);
					  futureAuctions ++;
					  myAuctionByDateList.replace(auctionDate, dayAuctions);
					  result = true;
				  }
				  else
				  {
					  result = false;
				  }
			  }
		  }
		  // 2 auctions already scheduled for the day
		  else
		  {
			  result = false;
		  }
	  }
	  // go ahead and schdule if no auctions already scheduled for the day
	  else
	  {
		  ArrayList<Auction> newListToAdd = new ArrayList<Auction>();
		  newListToAdd.add(theAuctionToAdd);
		  futureAuctions ++;
		  myAuctionByDateList.put(auctionDate, newListToAdd);
		  result = true;
	  }
	  
//	  System.out.println("Auctions: " + myAuctionByDateList.entrySet());
	  return result;
  }
  
  // probably not needed
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
  
  // TODO: check for time conflicts
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
  public Map<LocalDate, ArrayList<Auction>> displayCurrentMonth()
  {
	  return displayChosenMonth(LocalDate.now().getMonth().getValue());
  }
  
  public Map<LocalDate, ArrayList<Auction>> displayChosenMonth(int month)
  {
	  Map<LocalDate, ArrayList<Auction>> returnMap = new HashMap<LocalDate,ArrayList<Auction>>();
	  Month chosenMonth = Month.of(month);
	  for(Entry<LocalDate, ArrayList<Auction>> entry : myAuctionByDateList.entrySet())
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
  
  
  public void removeAuction(Auction theAuction) {
	  Collection<ArrayList<Auction>> auctions = myAuctionByDateList.values();
		Iterator <ArrayList<Auction>> it = auctions.iterator();
		while(it.hasNext()) {
			ArrayList<Auction> a = (ArrayList<Auction>) it.next();
			for(int j = 0; j < a.size(); j++) {
				Auction oldAuction = a.get(j);
					if(oldAuction.myAuctionName.equals(theAuction.myAuctionName)) {
						System.out.println("old auction " + oldAuction.myAuctionName);
						a.remove(oldAuction);
					}
			}
		}
	}


	public String toString(LocalDate theDate) 
	{
		StringBuilder answer = new StringBuilder();
		if (futureAuctions > 0)
		{
			for (Map.Entry<LocalDate,ArrayList<Auction>> entry :myAuctionByDateList.entrySet()) 
			{
				answer.append(entry.getKey());
				ArrayList<Auction> Auctions = entry.getValue();
				for (int i = 0; i < Auctions.size(); i++)
				{
					answer.append(i + " " + Auctions.get(i));
				}
			}
		}
		else
		{
			answer.append("No Current Auctions for " + theDate.getMonth().name());
		}
		return answer.toString();
	}
}

// TODO: add toString method

