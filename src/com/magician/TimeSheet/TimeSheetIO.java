/** COPYRIGHT 2013 DANIEL BRADNER
 * 
 *  This file is part of Magic Time Sheet.
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class TimeSheetIO extends TimeSheet {

	private static final String TIME_WORKED = "timeWorked";
	private static final String START_TIME = "startTime";
	private static final String END_TIME = "endTime";
	private static final String DAY_OF_MONTH = "dayofMonth";
	private static final String MONTH = "month";
	private static final String YEAR = "year";
	private static final String ACTIVITY = "activity";
	private static final String END_OF_LINE = "]";
	//private String fileVal = null;
	private static final String USER_HOME = System.getProperty("user.home");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String TEMP_SAVE = USER_HOME + "/Documents/Source Code/TimeSheet/temp.txt";
	//private static String SAVE_LOCATION = USER_HOME + FILE_SEPARATOR + "Documents" + FILE_SEPARATOR + "TimeSheet" + FILE_SEPARATOR;
	private static String SAVE_LOCATION;
	private static Properties defaultProperties;
	private static Properties appProperties = new Properties();

	public static void loadAndSetProperties(){
		try{
			File configFile = new File("config.properties");
			InputStream input = new FileInputStream(configFile);
			appProperties.load(input);
			SAVE_LOCATION = appProperties.getProperty("saveLocation", USER_HOME + FILE_SEPARATOR + "Documents" + FILE_SEPARATOR + "TimeSheet" + FILE_SEPARATOR);
		}
		catch(Exception e){
			generateErrorLog(e);
		}
	}

	public static void loadAndSetDefaultProperties(){
		try{
			defaultProperties = new Properties();
			File defaultConfigFile = new File("defaultconfig.properties");
			InputStream defaultInput = new FileInputStream(defaultConfigFile);
			defaultProperties.load(defaultInput);
			appProperties = defaultProperties;
			SAVE_LOCATION = appProperties.getProperty("saveLocation", USER_HOME + FILE_SEPARATOR + "Documents" + FILE_SEPARATOR + "TimeSheet" + FILE_SEPARATOR);
		}
		catch(Exception e){
			generateErrorLog(e);
		}
	}

	public static void setProperty(String key, String value){
		appProperties.setProperty(key, value);
	}

	//TODO remove before deployment, should not be accessible to end user.
	public static void setDefaultProperties(){
		defaultProperties = new Properties();

		try{
			defaultProperties.setProperty("isSet", "yes");
			//defaultProperties.setProperty("userHome", System.getProperty("user.home"));
			//defaultProperties.setProperty("fileSeparator", System.getProperty("file.separator"));
			defaultProperties.setProperty("saveLocation", USER_HOME + FILE_SEPARATOR + "Documents" + FILE_SEPARATOR + "TimeSheet" + FILE_SEPARATOR);

			defaultProperties.store(new FileOutputStream("defaultconfig.properties"), null);

			appProperties = defaultProperties;

			appProperties.store(new FileOutputStream("config.properties"), null);
		}

		catch(Exception e){
			TimeSheetIO.generateErrorLog(e);
		}
	}

	public static void storeProperties(){
		try{
			appProperties.store(new FileOutputStream("config.properties"), null);
		}
		catch(Exception e){
			TimeSheetIO.generateErrorLog(e);
		}
	}

	/** Checks if the specified file exists by pathname.
	 * @param fn - The pathname of the file.
	 * @return - true if the file exists, false otherwise.
	 */
	protected static boolean isFileCreated(String fn){
		try{
			File f = new File(fn);
			if(f.isFile()){
				return true;
			}
		}
		catch(Exception e){
			TimeSheetIO.generateErrorLog(e);
		}
		return false;
	}

	public static void setSaveLocation(String loc){
		SAVE_LOCATION = loc;
	}

	public static String getSaveLocation(){
		return SAVE_LOCATION;
	}

	/** Writes the specified string to the file specified by the pathname.
	 * @param fn - The pathname of the file.
	 * @param w - The string to write to the file.
	 */
	private static void writeToFile(String fn, String w){
		FileWriter output = null;
		BufferedWriter writer = null;

		try{
			output = new FileWriter(fn);
			writer = new BufferedWriter(output);
			writer.write(w);
		} 

		catch (Exception e){
			TimeSheetIO.generateErrorLog(e);
		} 

		finally{
			if (output != null){
				try{
					writer.close();
					output.close();
				}

				catch (Exception e) {
					TimeSheetIO.generateErrorLog(e);
				}
			}
		}

	}

	/** Reads a text file and returns a string with the output. The string is broken up by the same linebreaks as the file.
	 * @param fn - The pathname of the file.
	 * @return - A string containing all the text in the file.
	 */
	protected static String readFromFile(String fn){
		String returnValue = "";
		FileReader file = null;
		String line = "";
		BufferedReader reader = null;

		try{
			file = new FileReader(fn);
			reader = new BufferedReader(file);

			while ((line = reader.readLine()) != null) {
				returnValue += line + "\n";
			}
		} 

		catch (Exception e){
			TimeSheetIO.generateErrorLog(e);
		} 

		finally{
			if (file != null){
				try {
					file.close();
				} 

				catch (Exception e) {
					TimeSheetIO.generateErrorLog(e);
				}
			}
		}

		return returnValue;
	}

	/** Reads a textfile and loads the values into a timesheetevent. FOR TESTING PURPOSES ONLY.
	 * @param sheet - The event to fill with the values from the tempfile.
	 * @deprecated
	 */
	protected static void loadFromTempFile(TimeSheetEvent sheet){ 
		sheet.setTimeWorked(Integer.parseInt(loadValue(readFromFile(TEMP_SAVE), TIME_WORKED, START_TIME)));
		sheet.setCurrentDay(Integer.parseInt(loadValue(readFromFile(TEMP_SAVE), DAY_OF_MONTH , MONTH)));
		sheet.setCurrentDay(Integer.parseInt(loadValue(readFromFile(TEMP_SAVE), MONTH, YEAR)));
		sheet.setCurrentDay(Integer.parseInt(loadValue(readFromFile(TEMP_SAVE), YEAR, ACTIVITY)));
		sheet.setActivity((loadValueFromEnd(readFromFile(TEMP_SAVE), ACTIVITY )));
	}

	/** Loads values from a text file into a TimeSheet. Each separate line in the textfile is used to fill a new TimeSheetEvent.
	 * @param ts - The TimeSheet to load values into.
	 * @param loc - The pathname of the file that the values will be loaded from.
	 */
	public static void load(TimeSheet ts, String loc){
		String str = readFromFile(loc);
		while(str.length() > END_OF_LINE.length()){
			String act = loadValueFromEnd(str, ACTIVITY);
			ts.addEvent(new TimeSheetEvent(act));
			ts.getLastEvent(act).setTimeWorked(Integer.parseInt(loadValue(str, TIME_WORKED, START_TIME)));
			ts.getLastEvent(act).setStartTime(Double.parseDouble(loadValue(str, START_TIME, END_TIME)));
			ts.getLastEvent(act).setEndTime(Double.parseDouble(loadValue(str, END_TIME, DAY_OF_MONTH)));
			ts.getLastEvent(act).setCurrentDay(Integer.parseInt(loadValue(str, DAY_OF_MONTH , MONTH)));
			ts.getLastEvent(act).setCurrentDay(Integer.parseInt(loadValue(str, MONTH, YEAR)));
			ts.getLastEvent(act).setCurrentDay(Integer.parseInt(loadValue(str, YEAR, ACTIVITY)));
			str = str.substring(str.indexOf(END_OF_LINE) + 1);
		}
	}

	public static String loadDates(TimeSheet ts){
		String s = ts.toString();
		String result = "";
		while(s.length() > END_OF_LINE.length()){
			result = result + (loadValue(s, MONTH , YEAR) + "/" + loadValue(s, DAY_OF_MONTH , MONTH) + "/" + loadValue(s, YEAR, ACTIVITY) + "\n");
			s = s.substring(s.indexOf(END_OF_LINE) + 1);
		}
		return result;
	}

	public static String loadActivities(TimeSheet ts){
		String s = ts.toString();
		String result = "";
		while(s.length() > END_OF_LINE.length()){
			result = result + (loadValueFromEnd(s, ACTIVITY) + "\n");
			s = s.substring(s.indexOf(END_OF_LINE) + 1);
		}
		return result;
	}

	public static String loadTimesWorked(TimeSheet ts){
		String s = ts.toString();
		String result = "";
		while(s.length() > END_OF_LINE.length()){
			result = result + (loadValue(s, TIME_WORKED, START_TIME) + "\n");
			s = s.substring(s.indexOf(END_OF_LINE) + 1);
		}
		return result;
	}

	public static String loadAndFormatForExport(TimeSheet ts){
		String s = ts.toString();
		String result = "";
		while (s.length() > END_OF_LINE.length()){
			result = result + (loadValue(s, MONTH , YEAR) + "/" + loadValue(s, DAY_OF_MONTH , MONTH) + "/" + loadValue(s, YEAR, ACTIVITY) + "\t")
					+ (loadValueFromEnd(s, ACTIVITY)) + "\t" + (loadValue(s, TIME_WORKED, START_TIME) + "\n");
			s = s.substring(s.indexOf(END_OF_LINE) + 1);
		}
		return result;
	}

	/** Returns the substring of the parameter str between the beginning of the parameter var and the end of the string. To be used with toString method of TimeSheet or TimeSheetEvent.
	 * @param str - The full string.
	 * @param var - The value that should be loaded.
	 * @return - The substring between the beginning of var and the end of str.
	 * @see com.magician.TimeSheet.toString()
	 * @see com.magician.TimeSheetEvent.toString()
	 */
	private static String loadValueFromEnd(String str, String var){
		String result = "";
		int index = str.indexOf(var);
		index = index + var.length() + 1;
		result = str.substring(index, str.indexOf(END_OF_LINE));
		return result;
	}

	/** Returns the substring of the parameter str between varToGet and nextVar. To be used with toString method of TimeSheet or TimeSheetEvent.
	 * @param str - The full string.
	 * @param varToGet - The value to be loaded.
	 * @param nextVar - The next value in the string, indicates stop point.
	 * @return - The substring containing the value to be loaded.
	 * @see com.magician.TimeSheet.toString()
	 * @see com.magician.TimeSheetEvent.toString()
	 */
	private static String loadValue(String str, String varToGet, String nextVar){
		String result = "";
		int stop = str.indexOf(nextVar) - 2;
		int index = str.indexOf(varToGet);
		index = index + varToGet.length() + 1;
		result = str.substring(index, stop);
		return result;
	}

	public static void save(String fn, String w){
		writeToFile(fn, "");
		writeToFile(fn, w);
	}

	public static void generateErrorLog(Exception e){
		try {
			File file = new File("errorlog.txt");
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			e.printStackTrace(pw);
			pw.close();
		} 
		catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}
