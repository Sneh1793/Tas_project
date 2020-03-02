package panel;

import javax.swing.*;

import dao.ProcessFile;
import dao.ProcessFileImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BodyPanel extends JPanel {

	private static final long serialVersionUID = -720148068509698904L;
	
	private String filename;
    private String fname;
    private JButton startButton;
    private JTextField textField;
    private JLabel lblFilename;
    private JButton btnNewButton;
    private JButton btnCancel;
    private ProcessFile processFile;

    public BodyPanel()
    {
        this.setLayout(new GridLayout(3,2));
        createUIComponents();
    }
    
    private void createUIComponents() {
       
    	// Browse Button
        btnNewButton = new JButton("IMPORT");
        btnNewButton.setBounds(200, 26, 205, 31);
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                JFileChooser filedilg = new JFileChooser();
                filedilg.showOpenDialog(filedilg);
                filename = filedilg.getSelectedFile().getAbsolutePath();
                textField.setText(filename);

                File file1 = new File(filename);
                fname = file1.getName();
                System.out.println("THE FILE NAME IS " + fname);
            }
        });
        this.add(btnNewButton, BorderLayout.CENTER);
        add(Box.createVerticalStrut(40));

        // label
        lblFilename = new JLabel("FileName");
        lblFilename.setVisible(true);
        lblFilename.setBounds(100, 80, 90, 31);
        lblFilename.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        this.add(lblFilename);

        // file location feild
        textField = new JTextField();
        textField.setBounds(100, 80,90 ,31);
        this.add(textField);
        textField.setColumns(30);
        
         // Send button
        startButton = new JButton("Process");
        
         startButton.setBounds(300, 90, 305, 91);
        startButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

     
                System.out.println("file reading from POI" + filename);
                processFile= new ProcessFileImpl();
                try {
					processFile.process(filename);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
     JOptionPane.showMessageDialog(getRootPane(), "File uploded for processing...!!!");
            }
        });

        this.add(startButton, BorderLayout.CENTER);

        // Close button
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(205, 82, 200, 31);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        this.add(btnCancel, BorderLayout.SOUTH);
    }
}
