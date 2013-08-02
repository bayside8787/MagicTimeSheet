package com.magician.TimeSheet.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.magician.TimeSheet.TimeSheetIO;

public class ExportOptionsPane extends JFrame {

	private JPanel contentPane;
	private JFileChooser fc = new JFileChooser();

	/**
	 * Create the frame.
	 */
	public ExportOptionsPane() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(125, 125, 400, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextArea txtrCurrentPath = new JTextArea();
		TimeSheetGUI.setTextAreaProperties(txtrCurrentPath);
		txtrCurrentPath.setText("Current Path:");
		
		JTextArea txtrPath = new JTextArea();
		TimeSheetGUI.setTextAreaProperties(txtrPath);
		txtrPath.setText("Path");
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doEdit(e);
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrCurrentPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txtrPath, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnEdit)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtrCurrentPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtrPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnEdit))
					.addContainerGap(95, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void doEdit(ActionEvent e){
		int returnVal;
		File file;
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("Edit Export Path");
		returnVal = fc.showDialog(contentPane, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getCurrentDirectory();
			file = fc.getSelectedFile();
			System.out.println("" + file.getPath());
			TimeSheetIO.setSaveLocation(file.getPath());
		}
	}
}
