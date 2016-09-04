import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

  
public class ChatServer extends JFrame implements ActionListener
{
	ServerSocket serverSocket;
	Socket socket;
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	
	JTextArea area;
	JTextField field;
	
	public ChatServer(int port) 
	{
		super("Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		area = new JTextArea(20,60);
		field = new JTextField(20);
		field.addActionListener(this);
			
		setLayout(new BorderLayout());
		add(area, BorderLayout.CENTER);
		add(field, BorderLayout.SOUTH);
		
		setSize(400,400);
		setVisible(true);		
	}
	
	public static void main(String[] args) 
	{
		ChatServer server = new ChatServer(4000);
		server.initCommunication();
		server.listen();
	
	}
	
	public void actionPerformed(ActionEvent event)
	{
		String fieldText = field.getText();
		addTextToArea(field.getText());
		field.setText("");
		try
		{
			this.outStream.writeObject(fieldText);
			this.outStream.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void listen()
	{
		while (true)
		{
			try
			{
				String text = (String) this.inStream.readObject();
				addTextToArea(text);
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void initCommunication()
	{
		try
		{
			this.serverSocket = new ServerSocket(4000);
			this.socket = this.serverSocket.accept();
			this.outStream = new ObjectOutputStream(socket.getOutputStream());
			this.outStream.flush();
			this.inStream = new ObjectInputStream(socket.getInputStream());			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void addTextToArea(String text)
	{
		String areaText = this.area.getText();
		areaText = areaText + text + "\n";
		this.area.setText(areaText);
	}

}
