package application;

import panel.BodyPanel;
import panel.HeaderPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
	// setting the serialversion Uid for main class
	private static final long serialVersionUID = -2403986595768389766L;

	private JFrame mainFrame;
	private JPanel mainPanel;
    private JLabel background;
	private void prepareGUI() {
		mainFrame = new JFrame("File Upload");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		// mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		mainPanel.setLayout(new FlowLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// mainPanel.setBounds(0, 0, 491, 355);
		background = new JLabel(new ImageIcon("D:\\Sunny-Spring-Nature-Green-Field.jfif"));
        add(background);
        background.setLayout(new FlowLayout());
		// Add Panels
		mainPanel.add(new HeaderPanel());
		mainPanel.add(Box.createVerticalStrut(10));

		mainPanel.add(new BodyPanel());
		mainPanel.add(Box.createVerticalStrut(20));

		mainFrame.getContentPane().add(mainPanel);
		// controlPanel.setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// get 2/3 of the height, and 2/3 of the width
		int height = screenSize.height * 2 / 3;
		int width = screenSize.width * 2 / 3;

		// set the jframe height and width
		mainFrame.setPreferredSize(new Dimension(width, height));

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main excel = new Main();
				excel.prepareGUI();

			}
		});

	}
}
