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

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeSheetEvent {

	private int timeWorked = 0;
	private double startTime = 0;
	private double endTime = 0;
	private int dayofMonth = 0;
	private int month = 0;
	private int year = 0;
	private String activity;
	
	/** Constructs a TimeSheet with no activity or date.
	 * 
	 */
	public TimeSheetEvent(){
		activity = "";
	}
	
	/** Constructs a TimeSheet with an activity but no ID.
	 * 
	 */
	public TimeSheetEvent(String act){
		activity = act;
	}
	
	/** Constructs a TimeSheet with an activity but no ID.
	 * 
	 * @param act - the name of the activity.
	 * @param ID - the unique ID of the activity.
	 */
	public TimeSheetEvent(String act, int ID){
		activity = act;
	}
	
//	/** Constructs a TimeSheet with a date but no name.
//	 * 
//	 * @param dom - The number of the day of the month.
//	 * @param m - The number of the month.
//	 * @param y - The year.
//	 */
//	public TimeSheetEvent(int dom, int m, int y){
//		activity = "";
//		timeWorked = 0;
//		startTime = 0;
//		endTime = 0;
//		dayofMonth = dom;
//		month = m;
//		year = y;
//	}
//	
//	/** Constructs a TimeSheet with a name and date.
//	 * 
//	 * @param dom - The number of the day of the month.
//	 * @param m - The number of the month.
//	 * @param y - The year.
//	 * @param act - The activity for the event.
//	 */
//	public TimeSheetEvent(int dom, int m, int y, String act){
//		activity = "";
//		timeWorked = 0;
//		startTime = 0;
//		endTime = 0;
//		dayofMonth = dom;
//		month = m;
//		year = y;
//	}
	
	public void doInitialize(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US);
		setCurrentDay(cal);
		setCurrentMonth(cal);
		setCurrentYear(cal);
	}
	
	protected int calculateTimeWorked(){
		double tw = timeWorked + (((endTime - startTime) / 1000) / 60);
		timeWorked = (int)Math.round(tw);
		return timeWorked;
	}
	
	protected void setTimeWorked(int tw){
		timeWorked = tw;
	}
	
	protected void setActivity(String act){
		activity = act;
	}
	
	private long getCurrentTime(){
		return Calendar.getInstance(TimeZone.getTimeZone("America/New_York"), Locale.US).getTimeInMillis();
	}
	
	protected void setCurrentDay(Calendar cal){
		dayofMonth = cal.get(Calendar.DAY_OF_MONTH);
	}
	
	protected void setCurrentMonth(Calendar cal){
		month = cal.get(Calendar.MONTH) + 1;
	}
	
	protected void setCurrentYear(Calendar cal){
		year = cal.get(Calendar.YEAR);
	}
	
	protected void setCurrentDay(int dom){
		dayofMonth = dom;
	}
	
	protected void setCurrentMonth(int m){
		month = m;
	}
	
	protected void setCurrentYear(int y){
		year = y;
	}
	
	public int getDayofMonth(){
		return dayofMonth;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getActivity(){
		return activity;
	}
	
	public int getTimeWorked(){
		return timeWorked;
	}
	
	public void setStartTime(){
		startTime = getCurrentTime();
	}
	
	public void setEndTime(){
		endTime = getCurrentTime();
		calculateTimeWorked();
	}
	
	public void setStartTime(double time){
		startTime = time;
	}
	
	public void setEndTime(double time){
		endTime = time;
	}

	public String incompleteToString() {
		return "TimeSheetEvent [startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}

	@Override
	public String toString() {
		return "TimeSheetEvent [timeWorked=" + timeWorked + ", startTime="
				+ startTime + ", endTime=" + endTime + ", dayofMonth="
				+ dayofMonth + ", month=" + month + ", year=" + year
				+ ", activity=" + activity + "]";
	}
}
