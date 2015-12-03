package model;
/*
 * This class creates a Calendar that can be used by main to schedule and hold auctions.
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CalendarModel
{
	public Map<LocalDate, ArrayList<AuctionModel>> myAuctionByDateList;
  
	/**
	 * Creates a new Calendar with a Map to store Auctions.
	 */
	public CalendarModel()
	{
		myAuctionByDateList = new HashMap<LocalDate, ArrayList<AuctionModel>>();
	}
	
	/**
	 * Adds a given auction to the Calendar. Enforces all business rules regarding scheduling except for checking whether
	 * an NPO has already scheduled an auction for the year.
	 * 
	 * @param theAuction the Auction to be added.
	 * @return whether or not the auction was added.
	 */
	public void addAuction(AuctionModel theAuction) throws AuctionException
	{
		LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
		LocalDateTime auctionStart = theAuction.getStartTime();
		LocalDateTime auctionEnd = theAuction.getEndTime();
		
		boolean futureAuctions = checkFutureAuctions();
		boolean ninetyDays = checkNinetyDays(auctionStart);
		boolean weekCheck = checkWeek(auctionDate);
		boolean timeCheck = checkTimes(auctionStart, auctionEnd);
		
		// checking business rules
		if(futureAuctions && ninetyDays && weekCheck && timeCheck)
		{
			if(myAuctionByDateList.containsKey(auctionDate))
			{
				checkAuctionsForDay(theAuction);
			}
			else
			{
				ArrayList<AuctionModel> addList = new ArrayList<AuctionModel>();
				addList.add(theAuction);
				myAuctionByDateList.put(auctionDate, addList);
			}
		}
		else
		{
			determineError(futureAuctions, ninetyDays, weekCheck, timeCheck);
		}
		 
	}
	
	/**
	 * Determines which exception to throw.
	 * @param theFutureAuctions
	 * @param theNinetyDays
	 * @param theWeekCheck
	 * @param theTimeCheck
	 * @throws AuctionException
	 */
	private void determineError(boolean theFutureAuctions, boolean theNinetyDays, boolean theWeekCheck, boolean theTimeCheck) throws AuctionException
	{
		if(!theFutureAuctions)
		{
			throw new AuctionException("The number of future auctions is currently at capacity.");
		}
		else if(!theNinetyDays)
		{
			throw new AuctionException("Auctions may not be scheduled more than 90 days from the current date.");
		}
		else if(!theWeekCheck)
		{
			throw new AuctionException("There are already 5 auctions scheduled within the week of your auction.");
		}
		else if(!theTimeCheck)
		{
			throw new AuctionException("Auction end is before the beginning.");
		}
	}
	/**
	 * Checks to see if the auction starts and ends on the same calendar day (Not a business rule, but it's
	 * convenient for our program), as well as if the auction start is before the auction end.
	 * 
	 * @param theAuctionStart Auction start
	 * @param theAuctionEnd Auction end
	 * @return whether the time criteria mentioned in the description is met.
	 */
	private boolean checkTimes(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd)
	{
		return (theAuctionStart.toLocalDate().equals(theAuctionEnd.toLocalDate()) && theAuctionStart.isBefore(theAuctionEnd));
	}
	
	/**
	 * Checks the "No more than 25 future auctions" business rule.
	 * 
	 * @return whether or not there are already 25 auctions scheduled for the future.
	 */
	private boolean checkFutureAuctions()
	{
		int count = 0;
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : myAuctionByDateList.entrySet())
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
	
	/**
	 * Enforces the "An auction may not be scheduled more than 90 days from the current date" business rule.
	 * 
	 * @param theAuctionStart the Auction start.
	 * @return whether or not the Auction start is 90 days after the current date.
	 */
	private boolean checkNinetyDays(LocalDateTime theAuctionStart)
	{
		if(theAuctionStart.isAfter(LocalDateTime.now().plusDays(90)) || theAuctionStart.isBefore(LocalDateTime.now().minusDays(1)))
		{
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Checks the "no more than 5 auctions may be scheduled for any rolling 7 day period" business rule.
	 * 
	 * @param theAuctionDate the date of the Auction.
	 * @return whether or not the 5 Auction rule is exceeded.
	 */
	private boolean checkWeek(LocalDate theAuctionDate)
	{
		int count = 0;
		 
		if(myAuctionByDateList.containsKey(theAuctionDate))
		{
			ArrayList<AuctionModel> dayOfAuctions = myAuctionByDateList.get(theAuctionDate);
			count += dayOfAuctions.size();
		}	
		for(int i = 1; i <=3; i++)
		{
			if(myAuctionByDateList.containsKey(theAuctionDate.plusDays(i)))
			{
				ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(theAuctionDate.plusDays(i));
				System.out.println();
				count += dayAuctions.size();
			}
			if(myAuctionByDateList.containsKey(theAuctionDate.minusDays(i)))
			{
				ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(theAuctionDate.minusDays(i));
				count += dayAuctions.size();
			}
		}
		
		if(count >= 5)
			return false;
		else
			return true;
	}
	
	/**
	 * Enforces the "no more than 2 auctions can be scheduled on the same day, and the star time of the
	 * second can be no earlier than 2 hours after the end time of the first" business rule.
	 * 
	 * @param theAuction the Auction to be added
	 * @return whether or not the critera metioned in the above description are met.
	 */
	private void checkAuctionsForDay(AuctionModel theAuction) throws AuctionException
	{
		LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
		LocalDateTime auctionStart = theAuction.getStartTime();
		LocalDateTime auctionEnd = theAuction.getEndTime();
		  
		ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(auctionDate);
	
		if(dayAuctions.size() < 2)
		{
			AuctionModel firstAuction = dayAuctions.get(0);
			if(auctionStart.isBefore(firstAuction.getStartTime()))
			{
				// at least 2 hours between actions
				if(auctionEnd.plusHours(2).minusMinutes(1).isBefore(firstAuction.getStartTime()))
				{
					dayAuctions.add(theAuction);
					myAuctionByDateList.replace(auctionDate, dayAuctions);
				}	
				else
				{
					throw new AuctionException("Auctions must have at least 2 hours between them.");
				}
			}
			else
			{
				// at least 2 hours between actions
				if(auctionStart.isAfter(firstAuction.getEndTime().plusHours(2).minusMinutes(1)))
				{
					dayAuctions.add(theAuction);
					myAuctionByDateList.replace(auctionDate, dayAuctions);
				}
				else
				{
					throw new AuctionException("Auctions must have at least 2 hours between them.");
				}
			}
		}
		// 2 auctions already scheduled for the day
		else
		{
			throw new AuctionException("There are already 2 auctions scheduled for this day.");
		}
	}
	
	/**
	 * Changes the Auction start and end time of an Auction already in the Calendar.
	 * 
	 * @param theAuction the Auction to be changed
	 * @param month the new month.
	 * @param day the new day.
	 * @param year the new year.
	 * @param auctionHourStart the new Auction hour start.
	 * @param auctionMinuteStart the new Auction minute start.
	 * @param auctionHourEnd the new Auction hour end.		
	 * @param auctionMinuteEnd the new Auction minute end.
	 * @return whether or not the Auction was edited.
	 */
	/*
	public boolean editAuctionDateTime(AuctionModel theAuction,int month, int day, int year,
			int auctionHourStart, int auctionMinuteStart,
			int auctionHourEnd, int auctionMinuteEnd)
	{
		LocalDateTime newStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
		LocalDateTime newEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
		  
		LocalDateTime oldStart = theAuction.getStartTime();
		LocalDateTime oldEnd = theAuction.getEndTime();
	
		ArrayList<AuctionModel> oldList = myAuctionByDateList.get(oldStart.toLocalDate());
		ArrayList<AuctionModel> removeFrom = oldList;
		removeFrom.remove(theAuction);
		  
		myAuctionByDateList.replace(oldStart.toLocalDate(), removeFrom);
		  
		theAuction.setAuctionDate(newStart, newEnd);
		  
		if(addAuction(theAuction))
		{
			return true;
		}
		else
		{
			theAuction.setAuctionDate(oldStart, oldEnd);
			myAuctionByDateList.replace(oldStart.toLocalDate(), oldList);
			return false;
		}
		  
		  
	}*/
	  
	/**
	 * Returns a sorted (by date) map of auctions for the current month.
	 * 
	 * @return sorted by date map of auctions for the current month.
	 */
	public Map<LocalDate, ArrayList<AuctionModel>> displayCurrentMonth()
	{
		return displayChosenMonth(LocalDate.now());
	}
	
	/**
	 * Returns a sorted (by date) map of auctions for the entered month.
	 * 
	 * @param theMonth the month to get a map for.
	 * @return sorted by date map of auctions for the entered month.
	 */
	public Map<LocalDate, ArrayList<AuctionModel>> displayChosenMonth(LocalDate theMonth)
	{
		Map<LocalDate, ArrayList<AuctionModel>> returnMap = new TreeMap<LocalDate,ArrayList<AuctionModel>>();
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : myAuctionByDateList.entrySet())
		{
			if(entry.getKey().equals(theMonth))
			{
				returnMap.put(entry.getKey(), entry.getValue());
			}
		}
		  
		return returnMap;
		  
	}
	
	/**
	 * Gets a Map of all future Auctions.
	 * @return Map of all future Auctions.
	 */
	public Map<LocalDate, ArrayList<AuctionModel>> getAllFutureAuctions()
	{
		LocalDate yesterday = LocalDate.now().minusDays(1);
		Map<LocalDate, ArrayList<AuctionModel>> returnMap = new TreeMap<LocalDate,ArrayList<AuctionModel>>();
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : myAuctionByDateList.entrySet())
		{
			if(entry.getKey().isAfter(yesterday))
			{
				returnMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		return returnMap;
	}
	  
	/**
	 * Returns an auction with the given name and date.
	 * 
	 * @param auctionName the Auction name.
	 * @param auctionDate the Auction date.
	 * @return the chosen Auction.
	 */
	public AuctionModel viewAuction(String auctionName, LocalDate auctionDate)
	{
		ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(auctionDate);
		if(dayAuctions.size() == 0)
		{
			return dayAuctions.get(0);
		}
		else
		{
			for(int i = 0; i < dayAuctions.size(); i++)
			{
				AuctionModel auction = dayAuctions.get(i);
				if(auction.getAuctionName().equals(auctionName))
				{
					return auction;
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes the desired Auction. 
	 * 
	 * @param theAuction the Auction to be removed.
	 */
	public void removeAuction(AuctionModel theAuction) 
	{
		LocalDate date = theAuction.getStartTime().toLocalDate();
		ArrayList<AuctionModel> removeDay = myAuctionByDateList.get(date);
		  
		if(removeDay.size() == 1)
		{
			myAuctionByDateList.remove(date);
		}
		else
		{
			removeDay.remove(theAuction);
			myAuctionByDateList.replace(date, removeDay);
		}
	}
	  
	/**
	 * Returns a String of the auctions for the chosen month.
	 * 
	 * @param theDate the Date chosen (only the month matters)
	 * @return String representation for the chosen month.
	 */
	public String toString(LocalDate theDate) 
	{
		Map<LocalDate, ArrayList<AuctionModel>> displayMap = displayChosenMonth(theDate);
		StringBuilder answer = new StringBuilder();
		for (Map.Entry<LocalDate,ArrayList<AuctionModel>> entry : displayMap.entrySet()) 
		{
			answer.append(entry.getKey());
			ArrayList<AuctionModel> Auctions = entry.getValue();
			for (int i = 0; i < Auctions.size(); i++)
			{
				answer.append(i + " " + Auctions.get(i).toString());
			}
		}
		return answer.toString();
	}
	
	/**
	 * Returns a String of the auctions for the current month.
	 * 
	 * @return String representation for the current month.
	 */
	public String toString()
	{
		// returns a sorted treemap
		Map<LocalDate, ArrayList<AuctionModel>> displayMap = displayCurrentMonth();
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : displayMap.entrySet())
		{
			LocalDate date = entry.getKey();
			ArrayList<AuctionModel> dayAuctions = entry.getValue();
			sb.append(i + ": " + date.getMonth().toString() + " " + date.getDayOfMonth() + ", " + date.getYear() + "\n");
			  
			char title = 'a';
			for(int j = 0; j < dayAuctions.size(); j++)
			{
				sb.append(title + ") " + dayAuctions.get(j).getAuctionName() + "\n");
				title = 'b';
			}
			sb.append("\n");
			i++;
		}
		  
		return sb.toString();
	}
  
}