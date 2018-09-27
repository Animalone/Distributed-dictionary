//Student Name: Wuang Shen, Student ID:716090
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerManagementGUI {

	private JFrame frame;
	/**
	 * Create the application.
	 */
	public ServerManagementGUI(ThreadPooledServer server) {
		initialize(server);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ThreadPooledServer server) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 278);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		
		JButton btnTerminate = new JButton("Terminate the Server");
		btnTerminate.setBounds(137, 160, 166, 29);
		panel.add(btnTerminate);
		
		btnTerminate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				server.stop();
			}
		});
		
		JButton btnNewButton = new JButton("Log History");
		btnNewButton.setBounds(137, 100, 166, 29);
		panel.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new LogHistoryGUI(server);
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(137, 220, 166, 29);
		panel.add(btnExit);
		
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});

		JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("adminPage.jpg")));
		panel.add(imgLabel);
		imgLabel.setBounds(-114, 0, 695, 411);
		frame.setVisible(true);
	}
}
