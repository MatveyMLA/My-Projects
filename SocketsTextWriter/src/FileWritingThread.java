import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class FileWritingThread implements Runnable {
	ObjectInputStream objectInputStream;
	BufferedWriter writer;
	Socket socket;
	
	public FileWritingThread(Socket socket, String filePath) {
		try {
			
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			writer = new BufferedWriter(new FileWriter(filePath, true));
			this.socket = socket;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while (!socket.isClosed()){
				String str = (String) objectInputStream.readObject();
				writer.write(str);
				writer.newLine();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		finally{
			try {
				writer.close();
				objectInputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
