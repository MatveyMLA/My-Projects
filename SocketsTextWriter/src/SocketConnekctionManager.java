import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class SocketConnekctionManager extends Thread {
	ServerSocket serverSocket; 
	Socket socket;
public SocketConnekctionManager(){
	
	try {
		serverSocket = new ServerSocket(47012);
		
	} catch (IOException e) {
		e.printStackTrace();
//		log
		JOptionPane.showMessageDialog(null, "Sorry, faild create connection. Port is beasy.");
	}
}
@Override
public void run() {
	while(true){
		System.out.println("Waiting For connection");
		try {
			socket = serverSocket.accept();		
			FileWritingThread fileWritingThread = new FileWritingThread(socket, "C://tmp/file.txt");
			Thread thread = new Thread(fileWritingThread);
			thread.start();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
}
