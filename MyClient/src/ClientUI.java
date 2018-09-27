//Student Name: Wuang Shen, Student ID:716090
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;


import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;



public class ClientUI {

	private JFrame frame;
	private JPanel p1 = new JPanel();
	private JButton b1 = new JButton("Connect");
	private JLabel lblNewLabel = new JLabel("Server IP Address");
	private JLabel lblNewLabel2 = new JLabel("Server Port Number");
	private JTextField textField = new JTextField();
	private JTextField textField2 = new JTextField();
	private final JLabel lblWelcomeToMydictionary = new JLabel("Welcome to MyDictionary");
    private static BufferedReader bufferRead;
    private static BufferedWriter bufferWrite;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public ClientUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 700, 333);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		p1.setBounds(0, 0, 700, 400);
		p1.setLayout(null);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(30, 83, 150, 20);
		lblNewLabel2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel2.setForeground(Color.WHITE);
		lblNewLabel2.setBounds(30,136,160,20);
		lblWelcomeToMydictionary.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblWelcomeToMydictionary.setForeground(Color.WHITE);
		lblWelcomeToMydictionary.setBounds(30, 18, 350, 29);
		
		p1.add(lblWelcomeToMydictionary);
		p1.add(lblNewLabel);
		p1.add(lblNewLabel2);
		textField.setBounds(192, 84, 130, 20);
		textField2.setBounds(192, 136, 130, 20);
		textField.setColumns(10);
		textField2.setColumns(10);
		
		b1.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		b1.setBounds(94, 182, 100, 29);
		b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					MyClient myClient = new MyClient(textField.getText(), textField2.getText());
					myClient.buildBufferRead();
					myClient.buildBufferWrite();
					if (myClient.getReady()) {
						new DictionaryUI(myClient);
						frame.dispose();
					}
			}
		});
		p1.add(textField);
		p1.add(textField2);
		p1.add(b1);
		frame.getContentPane().add(p1);
		
		textField2.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER && !textField.getText().equals("") && !textField2.getText().equals("")){
							MyClient myClient = new MyClient(textField.getText(), textField2.getText());
							myClient.buildBufferRead();
							myClient.buildBufferWrite();
							if (myClient.getReady()) {
								new DictionaryUI(myClient);
								frame.dispose();
							}
				    }
				  }
			});
		
		JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("words.jpg")));
		imgLabel.setBounds(0, -47, 700, 400);
		p1.add(imgLabel);
	}

}
