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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class AboutFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAutoRequestFocus(true);
		setBounds(125, 125, 400, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{438, 0};
		gbl_contentPane.rowHeights = new int[]{261, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		contentPane.add(textArea, gbc_textArea);
		TimeSheetGUI.setTextAreaProperties(textArea);
		textArea.setText("Magic Time Sheet" + "\nVersion 0.10" + "\nCopyright (c) 2013 Daniel Bradner");
	}

}
