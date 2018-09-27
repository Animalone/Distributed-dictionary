//Student Name: Wuang Shen, Student ID:716090
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected Map dictionary;
    private List<String> logHistory;
    private String ip;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private JSONParser parser = new JSONParser();
    private Semaphore semaphore;

    public WorkerRunnable(Socket clientSocket, Map dictionary, List<String> logHistory, String ip, Semaphore semaphore) {
        this.clientSocket = clientSocket;
        this.dictionary = dictionary;
        this.logHistory = logHistory;
        this.ip = ip;
        this.semaphore = semaphore;
    }

    public void run() {
    	try {
            InputStream is = clientSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //Sending the response back to the client.
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            int i = 0;
            while(true) {
	            String inputRead = br.readLine();
	            if (inputRead != null) { 
	            	JSONObject messageJSON = (JSONObject) parser.parse(inputRead);
			        String message = (String) messageJSON.get("COMMAND"); 
			        String word = (String) messageJSON.get("WORD");
		            if (message.equals("ADD")) {
		            	semaphore.acquire();
		            	String check;
		            	if (dictionary.containsKey(word)) {
		            		check = "YES";
		            		logHistory.add("Client from " +ip + " Checked a word: "+ word+ " at "+ localDateTime);
		            	}else {
		            		check = "NO";
		            		dictionary.put(word, (String) messageJSON.get("MEANING"));
			            	logHistory.add("Client from " +ip + " Add a word: "+ word + " at "+ localDateTime);
		            	}
		            	
		            	String outputMessage = check;
						JSONObject text = new JSONObject();
						text.put("CHECK", outputMessage);
		            	bw.write(text.toJSONString()+"\n");
						bw.flush();
						semaphore.release();
		            }else if(message.equals("SEARCH")) {
//		            	System.out.println(messageJSON);
		            	String outputMessage = (String) dictionary.get(word);        	
						JSONObject text = new JSONObject();
						text.put("MEANING", outputMessage);
		            	bw.write(text.toJSONString()+"\n");
						bw.flush();
						logHistory.add("Client from " +ip + " Searched a word: "+ word+ " at "+ localDateTime);
		            }else if(message.equals("DELETE")) {
		            	semaphore.acquire();
		            	String check;
		            	
		            	if (dictionary.containsKey(word)) {
		            		dictionary.remove(word);
		            		check = "YES";
		            	}else {
		            		check = "NO";
		            	}
		            	String outputMessage = check;
						JSONObject text = new JSONObject();
						text.put("CHECK", outputMessage);
		            	bw.write(text.toJSONString()+"\n");
						bw.flush();
						logHistory.add("Client from " +ip + " Deleted a word: "+ word+ " at "+ localDateTime);
						semaphore.release();
		            }
	            }else{
	            	break;
	            }
            }
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}