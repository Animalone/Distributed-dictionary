//Student Name: Wuang Shen, Student ID:716090
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ThreadPooledServer implements Runnable{

    protected int          serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    private LocalDateTime localDateTime = LocalDateTime.now();
    protected ExecutorService threadPool =
        Executors.newFixedThreadPool(20);
    private JSONParser parser = new JSONParser();
    private Semaphore semaphore = new Semaphore(1);
    Map dictionary = new Hashtable();
    
    List<String> logHistory = new ArrayList<>();
    public ThreadPooledServer(int port){
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        this.loadDictionaryJson();
        this.openServerSocket();
        while(true){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                String ip= clientSocket.getRemoteSocketAddress().toString().replace("/","");
                logHistory.add("Client from " +ip + " is connected to the Server "+ "at "+ localDateTime);
                this.threadPool.execute(
                        new WorkerRunnable(clientSocket, dictionary, logHistory, ip, semaphore));
//                System.out.println("scoket is closed");
            } catch (IOException e) {
                if(isStopped()) {
//                    System.out.println("Server Stopped.") ;
                    break;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
        }
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port", e);
        }
    }
    
    public List<String> getLog(){
    	return this.logHistory;
    }
    
    private void loadDictionaryJson() {
    	try {
    		InputStreamReader jsonfile = new InputStreamReader(getClass().getResourceAsStream("dictionaryFile.json"));
    		if (jsonfile!= null) {
    			BufferedReader jsonfileBr = new BufferedReader(jsonfile);
    			String str = "";
    			String line = jsonfileBr.readLine();
    			while (line != null){
    			    str +=line;
    			    line = jsonfileBr.readLine();
    			}
				JSONObject jsonObect = (JSONObject) parser.parse(str);
				Set<String> keys =  jsonObect.keySet();
				for(String key : keys) {
					this.dictionary.put(key, jsonObect.get(key));
				}
    		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}