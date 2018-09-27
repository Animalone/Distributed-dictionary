//Student Name: Wuang Shen, Student ID:716090
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class LogHistoryGUI {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public LogHistoryGUI(ThreadPooledServer server) {
		initialize(server);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ThreadPooledServer server) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 700, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 700, 278);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		for (String entry:server.getLog()) {
			textArea.append(entry+"\n");
		}
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(39, 47, 622, 203);
		panel.add(scroll);
		

		JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("adminPage.jpg")));
		imgLabel.setBounds(0, 0, 738, 411);
		panel.add(imgLabel);
		frame.setVisible(true);
	}
}
