package panel;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
	private JLabel header;
	private JLabel version;
	private JLabel slogan;
	// private JLabel background;

	public HeaderPanel() {
		this.setBounds(100, 100, 491, 400);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		createUIComponents();
	}

	private void createUIComponents() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		version = new JLabel(
				"<html><span style=' color: blue;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Version 1.0<br>Acqusition,Processing & Export</html>");

		add(version);

		header = new JLabel("<html><span style='color: teal;'>UNLIMIT</span></html>");
		header.setFont(header.getFont().deriveFont(64.0F));
		// adding header to panel
		add(header);
		add(Box.createVerticalStrut(20));

		slogan = new JLabel("<html><span style=' color: blue;'>Excel Sheet<br>Application</html>");
		add(slogan);

	}
}