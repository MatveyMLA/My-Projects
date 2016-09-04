package logger;

import java.io.*;
import javax.swing.JOptionPane;
import com.sun.jmx.snmp.Timestamp;

import utilities.TimeUtilities;

public final class Logger {
	private static final String LOG_FILE = "C:\\JavaWorkspace\\Bank system\\LogFile.txt";
	private static final String EXCEPTION_LOG_FILE = "C:\\JavaWorkspace\\Bank system\\ExceptionLogFile.txt";

	private static Logger instance;

	private Logger() {
	}	

	public static synchronized Logger getInstance(){

		if(instance == null){

			instance = new Logger();
		}

		return instance;
	}

	public synchronized void log(long clientID, String description, float amount){
		Timestamp timestamp = TimeUtilities.getTimeStemp();
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))){

			String strLog = "Time stemp: " + timestamp.getDateTime() + "; ID: " + clientID + "; Description: " + description + "; Amount of money: " + amount;
			writer.write(strLog);
			writer.flush();
		}
		catch(IOException e){
			JOptionPane.showInternalMessageDialog(null, "Log hasn't been wrote");
			e.printStackTrace();

		}
	}	
	public synchronized void log( long clientID, String description){

		Timestamp timestamp = TimeUtilities.getTimeStemp();
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))){

			String strLog = "Time stemp: " + timestamp.getDate() + "; ID: " + clientID + "; Description: " + description+ ".";
			writer.write(strLog);
			writer.newLine();
			writer.flush();
		}
		catch(IOException e){
			JOptionPane.showInternalMessageDialog(null, "Log hasn't been wrote");
			e.printStackTrace();

		}
	}
	public synchronized void logExeption(String description){

		Timestamp timestamp = TimeUtilities.getTimeStemp();
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(EXCEPTION_LOG_FILE, true))){

			String strLog = "Exception -- Time stemp: " + timestamp.getDate() + "; Exception description: " + description + ".";
			writer.write(strLog);
			writer.newLine();
			writer.flush();
		}
		catch(IOException e){
			JOptionPane.showInternalMessageDialog(null, "Log hasn't been wrote");
			e.printStackTrace();

		}
	}
	public synchronized void printLogs() {

		try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))){

			while(reader.ready()){

				System.out.println(reader.readLine());
			}

		} catch (IOException e) {

			JOptionPane.showInternalMessageDialog(null, "Can't print the log file");

			e.printStackTrace();
		}

	}
}
