package panel;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
	private JLabel header;
	private JLabel version;
	private JLabel slogan;

	public HeaderPanel() {
		this.setBounds(0, 0, 491, 400);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		createUIComponents();
	}

	private void createUIComponents() {
		header = new JLabel("<html><span style='color: teal;'>UnLimit</span></html>");
		header.setFont(header.getFont().deriveFont(64.0F));
		// adding header to panel
		add(header);
		add(Box.createVerticalStrut(10));

		this.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		version = new JLabel(
				"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Version 1.0<br>Acqusition,Processing & Export</html>");
		add(version);

		slogan = new JLabel("<html>Excel Sheet<br>Application</html>");
		add(slogan);

		add(Box.createVerticalStrut(20));

	}
}