//Student Name: Wuang Shen, Student ID:716090
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.awt.Color;

public class AddWord {

	private JFrame frame;
	private JTextField textField;
	private JSONParser parser = new JSONParser();

	/**
	 * Create the application.
	 */
	public AddWord(BufferedWriter bufferWrite, BufferedReader bufferRead) {
		initialize(bufferWrite, bufferRead);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(BufferedWriter bufferWrite, BufferedReader bufferRead) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 300);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEnterMeanings = new JLabel("Enter Meanings: ");
		lblEnterMeanings.setBounds(23, 61, 120, 16);
		panel.add(lblEnterMeanings);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(252, 227, 117, 29);
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(8, 89, 434, 115);
		panel.add(textArea);
		textArea.setRows(20);
		textArea.setColumns(20);
		
		textField = new JTextField();
		textField.setBounds(146, 16, 241, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterANew = new JLabel("Enter a New Word: ");
		lblEnterANew.setForeground(Color.BLACK);
		lblEnterANew.setBounds(23, 21, 118, 16);
		panel.add(lblEnterANew);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(104, 227, 117, 29);
		panel.add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!textArea.getText().equals("")) {
						JSONObject text = new JSONObject();
						text.put("WORD", textField.getText());
						text.put("COMMAND", "ADD");
						text.put("MEANING",textArea.getText());
						bufferWrite.write(text.toJSONString()+"\n");
//						bufferWrite.write("ADD"+"$$$"+textField.getText()+"$$$"+textArea.getText()+"\n");
						bufferWrite.flush();
						try {
							JSONObject checkJSON = (JSONObject) parser.parse(bufferRead.readLine());
							System.out.println(checkJSON);
							String meaning = (String) checkJSON.get("CHECK");
							
							if (meaning.equals("YES")){
								new MessageUI("This word is existing in dictioanry, try another word");
							}else {
								new MessageUI("Successfully added a new word.");
								frame.dispose();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							new MessageUI("Server is not available ATM, please reconnet to the server");
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else if (textArea.getText().equals("")){
						new MessageUI("Please enter meanings of the word");
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					new MessageUI("Server is not available ATM, please reconnet to the server");
				}
			}
		});
		
		textArea.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER){
						try {
							if (!textArea.getText().equals("")) {
								JSONObject text = new JSONObject();
								text.put("WORD", textField.getText());
								text.put("COMMAND", "ADD");
								text.put("MEANING",textArea.getText());
								bufferWrite.write(text.toJSONString()+"\n");
//								bufferWrite.write("ADD"+"$$$"+textField.getText()+"$$$"+textArea.getText()+"\n");
								bufferWrite.flush();
								
								try {
									JSONObject checkJSON = (JSONObject) parser.parse(bufferRead.readLine());
//									System.out.println(checkJSON);
									String meaning = (String) checkJSON.get("CHECK");
									
									if (meaning.equals("YES")){
										new MessageUI("This word is existing in dictioanry, try another word");
									}else {
										new MessageUI("Successfully added a new word.");
										frame.dispose();
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									new MessageUI("Server is not available ATM, please reconnet to the server");
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}else if (textArea.getText().equals("")){
								new MessageUI("Please enter meanings of the word");
							}
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							new MessageUI("Server is not available ATM, please reconnet to the server");
						}
				    }
			  }
			});
		JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("addWordBack.jpg")));
		panel.add(imgLabel);
		imgLabel.setBounds(-206, -109, 731, 456);
		
		frame.setVisible(true);
	}
}
