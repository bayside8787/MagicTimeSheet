/** COPYRIGHT 2013 DANIEL BRADNER
 * 
 *  This file is part of TimeSheet (Name not final).
 *  
 *  TimeSheet is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TimeSheet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with TimeSheet.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Daniel Bradner
 */

package com.magician.TimeSheet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeSheet {

	private ArrayList<TimeSheetEvent> timeSheet;
	private String name;
	private int startWeekDate = 0;
	private int endWeekDate = 0;

	public TimeSheet(){
		timeSheet = new ArrayList<TimeSheetEvent>();
	}

	public TimeSheet(String nm){
		timeSheet = new ArrayList<TimeSheetEvent>();
		name = nm;
	}

	private void setStartWeekDate(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
		while (cal.get(Calendar.DAY_OF_WEEK) != cal.getFirstDayOfWeek()) {
			cal.add(Calendar.DATE, -1);
		}
		startWeekDate = cal.get(Calendar.DAY_OF_MONTH);
	}

	protected void setEndWeekDate(){
		setStartWeekDate();
		GregorianCalendar gregcal = new GregorianCalendar(TimeZone.getTimeZone("America/New_York"), Locale.US);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
		if(cal.get(Calendar.MONTH) == Calendar.FEBRUARY && 
				gregcal.isLeapYear(cal.get(Calendar.YEAR))){
			endWeekDate = (startWeekDate + 7) % 29;
		}
		else if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY){
			endWeekDate = (startWeekDate + 7) % 28;
		}
		else if (cal.get(Calendar.MONTH) % 2 != 0){
			endWeekDate = (startWeekDate + 7) % 31;
		}
		else{
			endWeekDate = (startWeekDate + 7) % 30;
		}
	}

	public int getStartWeekDate(){
		this.setStartWeekDate();
		return startWeekDate;
	}

	public int getEndWeekDate(){
		this.setEndWeekDate();
		return endWeekDate;
	}

	public String getFormattedStartWeekDate(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
		if(this.getStartWeekDate() > cal.get(Calendar.DATE)){
			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) - 1));
		}
		if(this.getStartWeekDate() < (cal.get(Calendar.DAY_OF_MONTH) - 7)){
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			return (cal.get(Calendar.MONTH) + "-" + getStartWeekDate());
		}
		return ((cal.get(Calendar.MONTH) + 1) + "-" + getStartWeekDate());
		
		//TODO DEBUG
	}

	public String getFormattedEndWeekDate(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
		if(this.getEndWeekDate() < (cal.get(Calendar.DAY_OF_MONTH) + 7)){
			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + 1));
			return ((cal.get(Calendar.MONTH)) + "-" + getEndWeekDate());
		}
		return ((cal.get(Calendar.MONTH) + 2) + "-" + getEndWeekDate());
		
		//TODO DEBUG
	}

	public void addEvent(TimeSheetEvent event){
		timeSheet.add(event);	
	}

	public void removeEvent(TimeSheetEvent event){
		timeSheet.remove(event);
	}

	public TimeSheetEvent getLastEvent(String act){
		for(int i = timeSheet.size() - 1; i >= 0; i--){
			if(timeSheet.get(i).getActivity().equals(act)){
				return timeSheet.get(i);
			}
		}
		return null;
	}

	public TimeSheetEvent getFirstEvent(String act){
		for(int i = 0; i < timeSheet.size(); i++){
			if(timeSheet.get(i).equals(act)){
				return timeSheet.get(i);
			}
		}
		return null;
	}

	public int totalTimeWorked(){
		int total = 0;
		for(TimeSheetEvent tse : timeSheet){
			total = total + tse.getTimeWorked();
		}
		return total;
	}
	
//	public String getDates(){
//		String dates = "";
//		for(TimeSheetEvent tse:timeSheet){
//			dates = TimeSheetIO.loadDates(timeSheet);
//		}
//		return dates;
//	}

	@Override
	public String toString(){
		String result = "";
		for(TimeSheetEvent tse : timeSheet){
			result = result + tse.toString() + "\n";
		}
		return result;
	}
	
	public void clear(){
		timeSheet.clear();
	}
	
	public boolean isClear(){
		return timeSheet.isEmpty();
	}
	
}
