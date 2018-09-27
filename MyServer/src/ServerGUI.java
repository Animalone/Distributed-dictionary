//Student Name: Wuang Shen, Student ID:716090
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;

public class ServerGUI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 278);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server Admin Application");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Adobe Gothic Std", Font.PLAIN, 26));
		lblNewLabel.setBounds(69, 53, 391, 88);
		panel.add(lblNewLabel);
		
		JButton btnOpenANew = new JButton("Open a Server");
		btnOpenANew.setBounds(270, 153, 167, 29);
		panel.add(btnOpenANew);
		
		textField = new JTextField();
		textField.setBounds(147, 153, 113, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Port Number: ");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(18, 150, 145, 29);
		panel.add(lblNewLabel_1);
		
		btnOpenANew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ThreadPooledServer server = new ThreadPooledServer(Integer.parseInt(textField.getText()));
					new Thread(server).start();
					new ServerManagementGUI(server);
					frame.dispose();
		    	}catch(Exception e2) {
					new MessageGUI("Please enter a valid port number");
		    	}
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(270, 202, 167, 29);
		panel.add(btnExit);
		
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		
		textField.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER && !textField.getText().equals("")){
				    	try {
							ThreadPooledServer server = new ThreadPooledServer(Integer.parseInt(textField.getText()));
							new Thread(server).start();
							new ServerManagementGUI(server);
							frame.dispose();
				    	}catch(Exception e2) {
							new MessageGUI("Please enter a valid port number");
				    	}
				    }
			  }
			});
		JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("adminPage.jpg")));
		panel.add(imgLabel);
		imgLabel.setBounds(-114, 0, 695, 411);
	}
}
