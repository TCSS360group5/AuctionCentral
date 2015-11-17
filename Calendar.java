/*
 * This class creates a Calendar that can be used by main to schedule and hold auctions.
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Calendar
{
	public Map<LocalDate, ArrayList<Auction>> myAuctionByDateList;
  
	/**
	 * Creates a new Calendar with a Map to store Auctions.
	 */
	public Calendar()
	{
		myAuctionByDateList = new HashMap<LocalDate, ArrayList<Auction>>();
	}
	
	/**
	 * Adds a given auction to the Calendar. Enforces all business rules regarding scheduling except for checking whether
	 * an NPO has already scheduled an auction for the year.
	 * 
	 * @param theAuction the Auction to be added.
	 * @return whether or not the auction was added.
	 */
	public boolean addAuction(Auction theAuction)
	{
		LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
		LocalDateTime auctionStart = theAuction.getStartTime();
		LocalDateTime auctionEnd = theAuction.getEndTime();
		
		// checking business rules
		if(checkFutureAuctions() && checkNinetyDays(auctionStart) && checkWeek(auctionDate) && checkTimes(auctionStart, auctionEnd))
		{
			if(myAuctionByDateList.containsKey(auctionDate))
			{
				return checkAuctionsForDay(theAuction);
			}
			else
			{
				ArrayList<Auction> addList = new ArrayList<Auction>();
				addList.add(theAuction);
				myAuctionByDateList.put(auctionDate, addList);
				return true;
			}
		}
		else
		{
			return false;
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
			ArrayList<Auction> dayOfAuctions = myAuctionByDateList.get(theAuctionDate);
			count += dayOfAuctions.size();
		}	
		for(int i = 1; i <=3; i++)
		{
			if(myAuctionByDateList.containsKey(theAuctionDate.plusDays(i)))
			{
				ArrayList<Auction> dayAuctions = myAuctionByDateList.get(theAuctionDate.plusDays(i));
				System.out.println();
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
	
	/**
	 * Enforces the "no more than 2 auctions can be scheduled on the same day, and the star time of the
	 * second can be no earlier than 2 hours after the end time of the first" business rule.
	 * 
	 * @param theAuction the Auction to be added
	 * @return whether or not the critera metioned in the above description are met.
	 */
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
				if(auctionEnd.plusHours(2).minusMinutes(1).isBefore(firstAuction.getStartTime()))
				{
					dayAuctions.add(theAuction);
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
				if(auctionStart.isAfter(firstAuction.getEndTime().plusHours(2).minusMinutes(1)))
				{
					dayAuctions.add(theAuction);
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
	public boolean editAuctionDateTime(Auction theAuction,int month, int day, int year,
			int auctionHourStart, int auctionMinuteStart,
			int auctionHourEnd, int auctionMinuteEnd)
	{
		LocalDateTime newStart = LocalDateTime.of(year, month, day, auctionHourStart, auctionMinuteStart);
		LocalDateTime newEnd = LocalDateTime.of(year, month, day, auctionHourEnd, auctionMinuteEnd);
		  
		LocalDateTime oldStart = theAuction.getStartTime();
		LocalDateTime oldEnd = theAuction.getEndTime();
	
		ArrayList<Auction> oldList = myAuctionByDateList.get(oldStart.toLocalDate());
		ArrayList<Auction> removeFrom = oldList;
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
		  
		  
	}
	  
	/**
	 * Returns a sorted (by date) map of auctions for the current month.
	 * 
	 * @return sorted by date map of auctions for the current month.
	 */
	public Map<LocalDate, ArrayList<Auction>> displayCurrentMonth()
	{
		return displayChosenMonth(LocalDate.now().getMonth().getValue());
	}
	
	/**
	 * Returns a sorted (by date) map of auctions for the entered month.
	 * 
	 * @param theMonth the month to get a map for.
	 * @return sorted by date map of auctions for the entered month.
	 */
	public Map<LocalDate, ArrayList<Auction>> displayChosenMonth(int theMonth)
	{
		Map<LocalDate, ArrayList<Auction>> returnMap = new TreeMap<LocalDate,ArrayList<Auction>>();
		Month chosenMonth = Month.of(theMonth);
		for(Entry<LocalDate, ArrayList<Auction>> entry : myAuctionByDateList.entrySet())
		{
			if(entry.getKey().getMonth() == chosenMonth)
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
	public Auction viewAuction(String auctionName, LocalDate auctionDate)
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
	
	/**
	 * Removes the desired Auction. 
	 * 
	 * @param theAuction the Auction to be removed.
	 */
	public void removeAuction(Auction theAuction) 
	{
		LocalDate date = theAuction.getStartTime().toLocalDate();
		ArrayList<Auction> removeDay = myAuctionByDateList.get(date);
		  
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
		Map<LocalDate, ArrayList<Auction>> displayMap = displayChosenMonth(theDate.getMonthValue());
		StringBuilder answer = new StringBuilder();
		for (Map.Entry<LocalDate,ArrayList<Auction>> entry : displayMap.entrySet()) 
		{
			answer.append(entry.getKey());
			ArrayList<Auction> Auctions = entry.getValue();
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
				sb.append(title + ") " + dayAuctions.get(j).getAuctionName() + "\n");
				title = 'b';
			}
			sb.append("\n");
			i++;
		}
		  
		return sb.toString();
	}
  
}

	

