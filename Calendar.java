import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.text.DateFormatSymbols;

import javax.swing.text.html.HTMLDocument.Iterator;

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

  // TODO: check the week requirement
  public boolean addAuction(Auction theAuction)
  {
	  LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
	  LocalDateTime auctionStart = theAuction.getStartTime();
	  LocalDateTime auctionEnd = theAuction.getEndTime();
	  
	  // checking business rules (does not check if the organization has already had an auction for the year)
	  // don't add if future auctions is already at capacity
	  if(checkFutureAuctions() && checkNinetyDays(auctionStart) && checkWeek(auctionDate) && checkTimes(auctionStart, auctionEnd))
	  {
		  if(myAuctionByDateList.containsKey(auctionDate))
		  {
			  return checkAuctionsForDay(theAuction);
		  }
		  else
		  {
			  ArrayList<Auction> addList = new ArrayList<Auction>();
			  myAuctionByDateList.put(auctionDate, addList);
			  return true;
		  }
	  }
	  else
	  {
		  return false;
	  }
	 
  }
  
  private boolean checkTimes(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd)
  {
	  return theAuctionStart.toLocalDate().equals(theAuctionEnd.toLocalDate());
  }
  
  private boolean checkFutureAuctions()
  {
	  int count = 0;
	  for(Entry<LocalDate, ArrayList<Auction>> entry : myAuctionByDateList.entrySet())
	  {
		  if(entry.getKey().isAfter(LocalDate.now()))
		  {
			  count++;
		  }
	  }
	  if(count >= 25)
	  {
		  return false;
	  }
	  else
		  return true;
  }
  
  private boolean checkNinetyDays(LocalDateTime theAuctionStart)
  {
	  if(theAuctionStart.isAfter(LocalDateTime.now().plusDays(90)) || theAuctionStart.isBefore(LocalDateTime.now()))
		  return false;
	  else
		  return true;
  }
  
  private boolean checkWeek(LocalDate theAuctionDate)
  {
	 int count = 0;
	 
	 ArrayList<Auction> dayOfAuctions = myAuctionByDateList.get(theAuctionDate);
	 count += dayOfAuctions.size();
	
	 for(int i = 1; i <=3; i++)
	 {
		 if(myAuctionByDateList.containsKey(theAuctionDate.plusDays(i)))
		{
			ArrayList<Auction> dayAuctions = myAuctionByDateList.get(theAuctionDate.plusDays(i));
			count += dayAuctions.size();
		}
		if(myAuctionByDateList.containsKey(theAuctionDate.minusDays(i)))
		{
			ArrayList<Auction> dayAuctions = myAuctionByDateList.get(theAuctionDate.minusDays(i));
			count += dayAuctions.size();
		}
	 }
	 
	 if(count >= 5)
		 return false;
	 else
		 return true;
  }
  
  private boolean checkAuctionsForDay(Auction theAuction)
  {
	  LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
	  LocalDateTime auctionStart = theAuction.getStartTime();
	  LocalDateTime auctionEnd = theAuction.getEndTime();
	  
	  ArrayList<Auction> dayAuctions = myAuctionByDateList.get(auctionDate);
	  
	  if(dayAuctions.size() < 2)
	  {
		  Auction firstAuction = dayAuctions.get(0);
		  if(auctionStart.isBefore(firstAuction.getStartTime()))
		  {
			  // at least 2 hours between actions
			  if(auctionEnd.plusHours(2).isBefore(firstAuction.getStartTime()))
			  {
				  dayAuctions.add(theAuction);
				  futureAuctions ++;
				  myAuctionByDateList.replace(auctionDate, dayAuctions);
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
				  dayAuctions.add(theAuction);
				  futureAuctions ++;
				  myAuctionByDateList.replace(auctionDate, dayAuctions);
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
  
  // TODO: check for time conflicts
  public boolean editAuctionDateTime(Auction theAuction,int month, int day, int year,
								int auctionHourStart, int auctionMinuteStart,
								int auctionHourEnd, int auctionMinuteEnd)
  {
	  LocalDateTime newStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
	  LocalDateTime newEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
	  
	  LocalDateTime oldStart = theAuction.getStartTime();
	  LocalDateTime oldEnd = theAuction.getEndTime();
	  String oldName = theAuction.getAuctionName();
	  
	  String orgName = theAuction.getAuctionOrg();
	  String auctionName = orgName.replace(' ', '-') + symbols.getMonths()[month-1] + "-" + day + "-" + year;

	  ArrayList<Auction> oldList = myAuctionByDateList.get(oldStart.toLocalDate());
	  ArrayList<Auction> removeFrom = oldList;
	  removeFrom.remove(theAuction);
	  
	  myAuctionByDateList.replace(oldStart.toLocalDate(), removeFrom);
	  
	  theAuction.setAuctionDate(newStart, newEnd);
	  theAuction.setAuctionName(auctionName);
	  
	  if(addAuction(theAuction))
	  {
		  return true;
	  }
	  else
	  {
		  theAuction.setAuctionDate(oldStart, oldEnd);
		  theAuction.setAuctionName(oldName);
		  myAuctionByDateList.replace(oldStart.toLocalDate(), oldList);
		  return false;
	  }
	  
	  
  }
  
  // returns auctions for the current month
  public Map<LocalDate, ArrayList<Auction>> displayCurrentMonth()
  {
	  return displayChosenMonth(LocalDate.now().getMonth().getValue());
  }
  
  public Map<LocalDate, ArrayList<Auction>> displayChosenMonth(int month)
  {
	  Map<LocalDate, ArrayList<Auction>> returnMap = new TreeMap<LocalDate,ArrayList<Auction>>();
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
  
  public void removeAuction(Auction theAuction) 
  {
	  LocalDate date = theAuction.getStartTime().toLocalDate();
	  ArrayList<Auction> removeDay = myAuctionByDateList.get(date);
	  
	  removeDay.remove(theAuction);
	  
	  myAuctionByDateList.replace(date, removeDay);
  }
  
  public String toString()
  {
	  // returns a sorted treemap
	  Map<LocalDate, ArrayList<Auction>> displayMap = displayCurrentMonth();
	  StringBuilder sb = new StringBuilder();
	  int i = 1;
	  for(Entry<LocalDate, ArrayList<Auction>> entry : displayMap.entrySet())
	  {
		  LocalDate date = entry.getKey();
		  ArrayList<Auction> dayAuctions = entry.getValue();
		  sb.append(i + ": " + date.getMonth().toString() + " " + date.getDayOfMonth() + ", " + date.getYear() + "\n");
		  
		  char title = 'a';
		  for(int j = 0; j < dayAuctions.size(); j++)
		  {
			  sb.append(title + ") " + dayAuctions.get(j).toString() + "\n");
		  }
		  sb.append("\n");
		  i++;
	  }
	  
	  return sb.toString();
  }
  
}

	

