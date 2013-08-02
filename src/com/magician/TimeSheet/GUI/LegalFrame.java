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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.Font;

public class LegalFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public LegalFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAutoRequestFocus(true);
		setBounds(125, 125, 400, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		JTextArea textArea = new JTextArea();
		contentPane.add(textArea);
		TimeSheetGUI.setTextAreaProperties(textArea);
		textArea.setText("TimeSheet  Copyright (C) 2013  Daniel Bradner" +
				"\nThis program comes with ABSOLUTELY NO WARRANTY; for details see the included copy of the GPL." +
				"\nThis is free software, and you are welcome to redistribute it " +
				"under certain conditions; for details see the included copy of the GPL.");
	}

}
