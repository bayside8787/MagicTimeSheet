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

package com.magician.TimeSheet.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.magician.TimeSheet.TimeSheetIO;
import javax.swing.LayoutStyle.ComponentPlacement;

public class PreferencesPane extends JFrame {

	private JPanel contentPane;
	private JFileChooser fc = new JFileChooser();
	private JTextArea txtrPath;

	/**
	 * Create the frame.
	 */
	public PreferencesPane() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(125, 125, 400, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTextArea txtrCurrentPath = new JTextArea();
		TimeSheetGUI.setTextAreaProperties(txtrCurrentPath);
		txtrCurrentPath.setText("Current Path:");

		txtrPath = new JTextArea();
		TimeSheetGUI.setTextAreaProperties(txtrPath);
		txtrPath.setText(TimeSheetIO.getSaveLocation());

		JButton btnEditPath = new JButton("Edit");
		btnEditPath.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doEditPath(e);
			}
		});

		JButton btnRestoreDefaults = new JButton("Restore Defaults");
		btnEditPath.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doRestoreDefaults(e);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(txtrCurrentPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(txtrPath, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
										.addGap(18)
										.addComponent(btnEditPath)
										.addContainerGap())
										.addComponent(btnRestoreDefaults, Alignment.TRAILING)))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtrCurrentPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtrPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEditPath))
								.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
								.addComponent(btnRestoreDefaults))
				);
		contentPane.setLayout(gl_contentPane);
	}

	private void doEditPath(ActionEvent e){
		int returnVal;
		File file;
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("Edit Export Path");
		returnVal = fc.showDialog(contentPane, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			TimeSheetIO.setSaveLocation(file.getPath());
			TimeSheetIO.setProperty("saveLocation", file.getPath());
			txtrPath.setText(TimeSheetIO.getSaveLocation());
		}
	}

	private void doRestoreDefaults(ActionEvent e){
		TimeSheetIO.loadAndSetDefaultProperties();
	}
}
