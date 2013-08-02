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

package com.magician.TimeSheet.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.magician.TimeSheet.TimeSheet;
import com.magician.TimeSheet.TimeSheetEvent;
import com.magician.TimeSheet.TimeSheetIO;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.io.File;
import javax.swing.JPanel;

public class TimeSheetGUI {

	private JFrame frame;
	private JOptionPane saveOption = new JOptionPane("Would you like to save?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
	private JDialog saveDialog = saveOption.createDialog(frame, "Save?");
	private static final Object[] EXPORT_OPTIONS = {"Text file (.txt)", "Excel file (.xls)"};
	private ImageIcon icon = new ImageIcon((System.getProperty("user.home") + "/Documents/Source Code/TimeSheet/Icon.png"), "Icon");
	private JOptionPane exportOption = new JOptionPane("Please choose a format to export in:", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon, EXPORT_OPTIONS);
	private JDialog exportDialog = exportOption.createDialog(frame, "Export as...");
	private JFileChooser fc = new JFileChooser();
	private static String directory;
	protected static Font font;
	private static String system;
	private static boolean bTracking = false;
	private static boolean bSaved = false;
	private static String saveLocation;
	private TimeSheet ts = new TimeSheet("Default");
	private JButton btnTrack;
	private JTextArea txtrTag;
	private JTextArea txtrTime;
	private JTextArea txtrDates;
	private JTextArea txtrActivities;
	private JTextArea txtrTimeWorked;
	private String activity;
	private static int id;
	//	private JMenuItem mntmsource;
	//	private String source;
	
	//TODO icons and graphics

	protected static void doPlatformConfiguration(){
		if (System.getProperty("os.name").toLowerCase().contains("windows")){
			system = "windows";
		}
		else if (System.getProperty("os.name").toLowerCase().contains("mac os x")){
			system = "mac";
		}
		else if (System.getProperty("os.name").toLowerCase().contains("linux")){
			system = "ubuntu";
		}

		switch (system){
		case "windows":
			font = new Font("Segoe UI", Font.PLAIN, 14);
			directory = System.getProperty("user.home") + System.getProperty("file.separator" + 
						"My Documents" + System.getProperty("file.separator") + "MagicTS");
			break;
		case "mac":
			font = new Font("Lucida Grande", Font.PLAIN, 14);
			directory = System.getProperty("user.home") + System.getProperty("file.separator" + 
					"My Documents" + System.getProperty("file.separator") + "MagicTS");
			break;
		case "ubuntu":
			font = new Font("Ubuntu", Font.PLAIN, 14);
			break;
		default:
			font = new Font("Times New Roman", Font.PLAIN, 12);
			break;
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		doPlatformConfiguration();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeSheetGUI window = new TimeSheetGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimeSheetGUI() {
		initialize();
	}

	public static void setTextAreaProperties(JTextArea area){
		area.setFont(TimeSheetGUI.font);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		area.setOpaque(false);
	}

	public static void generateID(){
		id = 2013 + id + 1;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//TODO Ask if user wants to save before exit.
		frame.setAutoRequestFocus(true);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doNew(e);
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmLoad = new JMenuItem("Open...");
		mntmLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
					doOpen(e);
			}
		});
		mnFile.add(mntmLoad);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doSave(e);
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doSaveAs(e);
			}
		});
		mnFile.add(mntmSaveAs);

		JMenu mnExport = new JMenu("Export");
		menuBar.add(mnExport);

		JMenuItem mntmExportReportAs = new JMenuItem("Export Report As...");
		mntmExportReportAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doExportAs(e);
			}
		});
		mnExport.add(mntmExportReportAs);
		
		JMenuItem mntmExportOptions = new JMenuItem("Export Options");
		mntmExportOptions.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doExportOptions(e);
			}
		});
		mnExport.add(mntmExportOptions);

		Component glue = Box.createGlue();
		menuBar.add(glue);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doHelp(e);
			}
		});
		mnHelp.add(mntmHelp);

		JMenuItem mntmLegal = new JMenuItem("Legal");
		mntmLegal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doLegal(e);
			}
		});
		mnHelp.add(mntmLegal);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doAbout(e);
			}
		});
		mnHelp.add(mntmAbout);

		JTextArea txtrCurrentWeek = new JTextArea();
		txtrCurrentWeek.setText("Current Week:");
		setTextAreaProperties(txtrCurrentWeek);

		txtrTime = new JTextArea();
		txtrTime.setText(ts.totalTimeWorked() + " Minutes Worked");
		setTextAreaProperties(txtrTime);

		JTextArea txtrWeekOf = new JTextArea();
		txtrWeekOf.setText(ts.getFormattedStartWeekDate() + " to " + ts.getFormattedEndWeekDate());
		//TODO DEBUG
		setTextAreaProperties(txtrWeekOf);

		JTextArea txtrActivity = new JTextArea();
		txtrActivity.setText("Activity:");
		setTextAreaProperties(txtrActivity);

		txtrTag = new JTextArea();
		txtrTag.setText("Add tags, separated by commas");
		txtrTag.setFont(TimeSheetGUI.font);
		txtrTag.setLineWrap(true);
		txtrTag.setWrapStyleWord(true);
		txtrTag.setEditable(true);
		txtrTag.setOpaque(true);

		btnTrack = new JButton("Start Tracking");
		btnTrack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doTrack(e);
			}
		});
		
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtrActivity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtrTag, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED, 18, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnTrack))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtrCurrentWeek, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtrWeekOf, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
							.addComponent(txtrTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtrTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtrCurrentWeek, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtrWeekOf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtrActivity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtrTag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnTrack))
					.addContainerGap())
		);
		
		txtrDates = new JTextArea();
		setTextAreaProperties(txtrDates);
		txtrDates.setText("Dates");
		
		txtrActivities = new JTextArea();
		setTextAreaProperties(txtrActivities);
		txtrActivities.setText("Activities" + "\n" + "\n" + "\nNothing to show yet!");
		
		txtrTimeWorked = new JTextArea();
		setTextAreaProperties(txtrTimeWorked);
		txtrTimeWorked.setText("Time Worked");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.CENTER)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(txtrDates, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtrActivities, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtrTimeWorked, GroupLayout.PREFERRED_SIZE, 82, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.CENTER)
				.addGroup(gl_panel.createParallelGroup(Alignment.CENTER)
					.addComponent(txtrDates, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
					.addComponent(txtrActivities, GroupLayout.PREFERRED_SIZE, 162, Short.MAX_VALUE)
					.addComponent(txtrTimeWorked, GroupLayout.PREFERRED_SIZE, 162, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		frame.pack();
		frame.setSize(frame.getWidth(), (frame.getHeight() + 15));
	}

	private void doNew(ActionEvent e){
		Object selectedOption;
		int option;
		if(!bSaved && !ts.isClear()){
			saveDialog.setVisible(true);
			selectedOption = saveOption.getValue();
			option = ((Integer)selectedOption).intValue();
			if(option == JOptionPane.YES_OPTION){
				doSave(e);
				ts.clear();
			}
			else if(option == JOptionPane.NO_OPTION){
				ts.clear();
			}
		}
		else{
			ts.clear();
		}

	}

	private void doOpen(ActionEvent e) {
		Object selectedOption;
		int option;
		int returnVal;
		if(!ts.isClear()){
			if(bSaved){
				returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					TimeSheetIO.load(ts, file.getPath());
				}
			}
			else {
				saveDialog.setVisible(true);
				selectedOption = saveOption.getValue();
				option = ((Integer)selectedOption).intValue();
				if(option == JOptionPane.YES_OPTION){
					doSave(e);
					returnVal = fc.showOpenDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = fc.getSelectedFile();
						TimeSheetIO.load(ts, file.getPath());
					}
				}
				else if(option == JOptionPane.NO_OPTION){
					returnVal = fc.showOpenDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = fc.getSelectedFile();
						TimeSheetIO.load(ts, file.getPath());
					}
				}
			}
		}
		else{
			returnVal = fc.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				TimeSheetIO.load(ts, file.getPath());
			}
		}
	}

	protected void doSave(ActionEvent e){
		if (!bSaved){
			doSaveAs(e);
		}
		else{
			TimeSheetIO.save(saveLocation, ts.toString());
		}
		bSaved = true;
	}

	protected void doSaveAs(ActionEvent e){
		int returnVal;
		returnVal = fc.showSaveDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			TimeSheetIO.save(file.getPath(), ts.toString());
		}
		bSaved = true;
	}

	private void doExportAs(ActionEvent e){
		String option;
		option = (String)JOptionPane.showInputDialog(frame, "Please choose a format to export in:", "Export as...", JOptionPane.QUESTION_MESSAGE, null, EXPORT_OPTIONS, EXPORT_OPTIONS[0]);
		if (option == "Text file (.txt)"){
			System.out.println("txtfile");
			//TODO implement
		}
		else if (option == "Excel file (.xls)"){
			System.out.println("excelfile");
			//TODO implement
		}
	}
	
	private void doExportOptions(ActionEvent e){
		ExportOptionsPane exportOptions = new ExportOptionsPane();
		exportOptions.setVisible(true);
	}

	private void doHelp(ActionEvent e){
		HelpFrame help = new HelpFrame();
		help.setVisible(true);
	}

	private void doLegal(ActionEvent e){
		LegalFrame legal = new LegalFrame();
		legal.setVisible(true);
	}

	private void doAbout(ActionEvent e){
		AboutFrame about = new AboutFrame();
		about.setVisible(true);
	}

	private void doTrack(ActionEvent e){
		if (bTracking == false){
			btnTrack.setText("Stop Tracking");
			txtrTag.setEditable(false);
			txtrTag.setOpaque(false);
			activity = txtrTag.getText();
			ts.addEvent(new TimeSheetEvent(activity));
			ts.getLastEvent(activity).doInitialize();
			ts.getLastEvent(activity).setStartTime();
			bTracking = true;
			bSaved = false;
			updateUI();
		}
		else {
			btnTrack.setText("Start Tracking");
			txtrTag.setEditable(true);
			txtrTag.setOpaque(true);
			ts.getLastEvent(activity).setEndTime();
			bTracking = false;
			updateUI();
		}
	}

	private void updateUI(){
		txtrTime.setText(ts.totalTimeWorked() + " Minutes Worked");
		txtrDates.setText("Dates" + "\n" + TimeSheetIO.loadDates(ts));
		txtrActivities.setText("Activities" + "\n" + TimeSheetIO.loadActivities(ts));
		txtrTimeWorked.setText("Time Worked" + "\n" + TimeSheetIO.loadTimesWorked(ts));
		System.out.println("UPDATE UI-- " + ts.toString());
	}

	public JFrame getFrame(){
		return frame;
	}
}
