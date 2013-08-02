package com.magician.TimeSheet.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridLayout;

public class HelpFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(125, 125, 400, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea);
		TimeSheetGUI.setTextAreaProperties(textArea);
		textArea.setText("Sorry, no helptext is available yet, but a manual is in development!" +
				"\nFor now please visit our wiki at http://www.sourceforge.net/p/magictimesheet/wiki/home" +
				"\nIf you can't find what you need on the wiki, please submit a ticket on our SourceForge project page " +
				"(souceforge.net/p/magictimesheet).");
	}

}
