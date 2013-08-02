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

import java.util.Scanner;

public class TimeSheetCUI {

	public static void main(String[] args) {

		try{
			Scanner reader = new Scanner(System.in);
			byte quitter = 1;
			byte menu = 0;
			byte stoptracking = 0;
			byte choice = 0;
			TimeSheetEvent tse1 = new TimeSheetEvent("Jello");
			TimeSheetEvent tse2 = new TimeSheetEvent("Hello");
			TimeSheet ts = new TimeSheet();
			String userhome = System.getProperty("user.home");
			String tempsave = userhome + "/Documents/Source Code/TimeSheet/temp.txt";

			System.out.println("Hello, welcome to the TimeSheet!");
			System.out.println("Please enter 0 at any time to quit.");
			
			if(TimeSheetIO.isFileCreated(tempsave)){
				System.out.println("Would you like to restore the TimeSheet from last session? [1]");
				choice = reader.nextByte();
				if(choice == 1){
					TimeSheetIO.load(ts, tempsave);
					TimeSheetIO.loadFromTempFile(tse1);
				}
			}

			do{
				System.out.println("MAIN MENU");
				System.out.println("Start tracking [1], Output tracked time [2], Export tracked time to file [3]");
				menu = reader.nextByte();
				quitter = menu;

				switch(menu){

				case 1:
					System.out.println("Time tracking started.");
					tse1.setStartTime();
					tse2.setStartTime();

					do{
						System.out.println("Stop tracking [1]");
						stoptracking = reader.nextByte();
						quitter = stoptracking;

						if(stoptracking == 1){
							tse1.setEndTime();
							tse2.setEndTime();
							System.out.println("Your entry was accepted. Tracking has stopped.");
						}
						else{
							System.out.println("Your entry was invalid. Tracking has continued.");
						}
					}
					while(stoptracking != 1);

					break;
				case 2:
					System.out.println(tse1.incompleteToString() + " " + tse1.calculateTimeWorked());
					System.out.println(tse1.toString());
					//TODO-Implement proper formatting and output.
					break;
				case 3:
					//TODO-Implement output to file.
					break;
				default:
					break;
				}
			}
			while(quitter != 0);
			ts.addEvent(tse1);
			ts.addEvent(tse2);
			TimeSheetIO.save((tempsave), ts.toString());
			reader.close();
			System.exit(0);
		}
		catch(Exception e){
			Scanner reader = new Scanner(System.in);
			byte menu = 0;
			byte quitter = 0;

			System.out.println("An unexpected error has occured.");
			do{
				System.out.println("Quit immediately [0], Recieve a printout of the error [1].");
				menu = reader.nextByte();
				quitter = menu;

				switch(menu){

				case 0:
					reader.close();
					System.exit(0);
					break;
				case 1:
					reader.close();
					e.printStackTrace();
					System.exit(0);
					break;
				default:
					System.out.println("You didn't enter a valid option, returning to menu...");
					break;
				}	
			}
			while(quitter != 0 && quitter != 1);
		}

	}
}
