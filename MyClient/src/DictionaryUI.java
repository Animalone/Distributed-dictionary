//Student Name: Wuang Shen, Student ID:716090
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class DictionaryUI {

	private JFrame frame;
	private JTextField textField;
    private static BufferedReader bufferRead;
    private static BufferedWriter bufferWrite;
    private JSONParser parser = new JSONParser();
    private MyClient myclient;


	/**
	 * Create the application.
	 */
	public DictionaryUI(MyClient myclient) {
		this.myclient = myclient;
		initialize(myclient);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(MyClient myclient) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		bufferRead = myclient.getBufferReader();
		bufferWrite = myclient.getBufferWrite();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 380);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		textField = new JTextField();
		textField.setBounds(40, 24, 524, 20);
		textField.setColumns(70);
		panel.add(textField);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(UIManager.getColor("OptionPane.background"));
		textArea.setLineWrap(true);
//		textArea.setRows(12);
		textArea.setEditable(false);
//		textArea.setColumns(49);
		
//		JTextArea textArea = new JTextArea();
//		textArea.setEditable(false);
//		for (String entry:server.getLog()) {
//			textArea.append(entry+"\n");
//		}
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(6, 108, 588, 192);
		panel.add(scroll);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(61, 56, 117, 29);
		panel.add(btnSearch);
		
		textField.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER && !textField.getText().equals("")){
						textArea.removeAll();
//						System.out.println("Button clicked!");
						try {
							JSONObject text = new JSONObject();
							text.put("WORD", textField.getText());
							text.put("COMMAND", "SEARCH");
							bufferWrite.write(text.toJSONString()+"\n");
//							bufferWrite.write("SEARCH"+"$$$"+textField.getText()+"\n");
							bufferWrite.flush();
							try {
								textArea.setText(null);
								String inputMessage = bufferRead.readLine();
								if(inputMessage!= null) {
									JSONObject meaningJSON = (JSONObject) parser.parse(inputMessage);
	//								System.out.println(meaningJSON);
									String meaning;
									if (meaningJSON.get("MEANING") != null) {
										meaning = (String) meaningJSON.get("MEANING");
									}else {
										meaning = "null";
									}
									
									if (meaning.equals("null")){
										textArea.append("Can not find this word"+"\n");
									}else {
	//									System.out.println(bufferRead);
										textArea.append(meaning+"\n");
									}
								}else {
									System.out.println("check not ava");
								}
//								textArea.setText(null);
//								JSONObject meaningJSON = (JSONObject) parser.parse(bufferRead.readLine());
////								System.out.println(meaningJSON);
//								String meaning;
//								if (meaningJSON.get("MEANING") != null) {
//									meaning = (String) meaningJSON.get("MEANING");
//								}else {
//									meaning = "null";
//								}
//								
//								if (meaning.equals("null")){
//									textArea.append("Can not find this word"+"\n");
//								}else {
////									System.out.println(bufferRead);
//									textArea.append(meaning+"\n");
//								}
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								new MessageUI("Server is not available ATM, please reconnet to the server");
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							new MessageUI("Server is not available ATM, please reconnet to the server");
						}	

				    }
				  }
			});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textField.getText().equals("")) {
					textArea.removeAll();
//					System.out.println("Button clicked!");
					try {
							JSONObject text = new JSONObject();
							text.put("WORD", textField.getText());
							text.put("COMMAND", "SEARCH");
							bufferWrite.write(text.toJSONString()+"\n");
//							bufferWrite.write("SEARCH"+"$$$"+textField.getText()+"\n");
							bufferWrite.flush();
							try {
								textArea.setText(null);
								String inputMessage = bufferRead.readLine();
								if(inputMessage!= null) {
									JSONObject meaningJSON = (JSONObject) parser.parse(inputMessage);
	//								System.out.println(meaningJSON);
									String meaning;
									if (meaningJSON.get("MEANING") != null) {
										meaning = (String) meaningJSON.get("MEANING");
									}else {
										meaning = "null";
									}
									
									if (meaning.equals("null")){
										textArea.append("Can not find this word"+"\n");
									}else {
	//									System.out.println(bufferRead);
										textArea.append(meaning+"\n");
									}
								}else {
									System.out.println("check not ava");
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								new MessageUI("Server is not available ATM, please reconnet to the server");
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						new MessageUI("Server is not available ATM, please reconnet to the server");
					}
				}
			}
		});
		
		JButton btnAddANew = new JButton("Add a New Word ");
		btnAddANew.setBounds(224, 56, 151, 29);
		panel.add(btnAddANew);
		btnAddANew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddWord(bufferWrite, bufferRead);
			}
		});
		JButton btnDeleteAWord = new JButton("Delete a Word");
		btnDeleteAWord.setBounds(414, 56, 131, 29);
		panel.add(btnDeleteAWord);
		btnDeleteAWord.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeleteWord(bufferWrite, bufferRead);
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(245, 317, 117, 29);
		panel.add(btnExit);
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				myclient.close();
				frame.dispose();
				
			}
		});
		
		
		
		JLabel imgLabel = new JLabel(new javax.swing.ImageIcon(getClass().getResource("dictionaryBack.jpeg")));
		panel.add(imgLabel);
		imgLabel.setBounds(-33, -29, 695, 411);
		
		frame.setVisible(true);
		
	}
}
