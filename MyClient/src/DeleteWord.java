//Student Name: Wuang Shen, Student ID:716090
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class DeleteWord {

	private JFrame frame;
	private JTextField textField;
	private JSONParser parser = new JSONParser();

	/**
	 * Create the application.
	 */
	public DeleteWord(BufferedWriter bufferWrite, BufferedReader bufferRead) {
		initialize(bufferWrite, bufferRead);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(BufferedWriter bufferWrite, BufferedReader bufferRead) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 300, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 300, 150);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(43, 42, 218, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblDeleteAWord = new JLabel("Delete a Word");
		lblDeleteAWord.setBounds(91, 22, 122, 16);
		panel.add(lblDeleteAWord);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(165, 80, 117, 29);
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(24, 80, 117, 29);
		panel.add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!textField.getText().equals("")) {
						
						
						JSONObject text = new JSONObject();
						text.put("WORD", textField.getText());
						text.put("COMMAND", "DELETE");
						bufferWrite.write(text.toJSONString()+"\n");
//						bufferWrite.write("DELETE"+"$$$"+textField.getText()+"\n");
						bufferWrite.flush();
						
						try {
							JSONObject meaningJSON = (JSONObject) parser.parse(bufferRead.readLine());
							String check = (String) meaningJSON.get("CHECK");
//							String check = bufferRead.readLine();
							System.out.println(check);
							if (check.equals("YES")) {
								new MessageUI("Successfully deleted");
								frame.dispose();
								//warning 
							}else {
								new MessageUI("This word is not in the dictionary");
							}
							
							// check if pass on check, but change the text later
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							new MessageUI("Server is not available ATM, please reconnet to the server");
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						new MessageUI("Please enter a word");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					new MessageUI("Server is not available ATM, please reconnet to the server");
				}
				
			}
		});
		
		textField.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER){
						try {
							if (!textField.getText().equals("")) {
								JSONObject text = new JSONObject();
								text.put("WORD", textField.getText());
								text.put("COMMAND", "DELETE");
								bufferWrite.write(text.toJSONString()+"\n");
//								bufferWrite.write("DELETE"+"$$$"+textField.getText()+"\n");
//								bufferWrite.write()
								bufferWrite.flush();
								
								try {
									JSONObject meaningJSON = (JSONObject) parser.parse(bufferRead.readLine());
									String check = (String) meaningJSON.get("CHECK");
//									String check = bufferRead.readLine();
//									System.out.println(bufferRead);
									if (check.equals("YES")) {
										new MessageUI("Successfully deleted");
										frame.dispose();
										//warning 
									}else {
										new MessageUI("This word is not in the dictionary");
									}
									
									// check if pass on check, but change the text later
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									new MessageUI("Server is not available ATM, please reconnet to the server");
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}else{
								new MessageUI("Please enter a word");
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
		imgLabel.setBounds(-206, -129, 731, 456);
		frame.setVisible(true);
	}
}
