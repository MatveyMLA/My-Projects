import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClient extends JFrame implements ActionListener 
{
	Socket socket;
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	
	JTextArea area;
	JTextField field;

	public ChatClient(String host, int port) 
	{
		super("Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try
		{
			this.socket = new Socket(host, port);
			
			this.outStream = new ObjectOutputStream(socket.getOutputStream());
			this.outStream.flush();
			this.inStream = new ObjectInputStream(socket.getInputStream());
						
			area = new JTextArea(20,60);
			field = new JTextField(20);
			field.addActionListener(this);
			
			setLayout(new BorderLayout());
			add(area, BorderLayout.CENTER);
			add(field, BorderLayout.SOUTH);
			
			setSize(400,400);
			setVisible(true);
			
		}
		
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
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
	
	public static void main(String[] args) 
	{
		ChatClient client = new ChatClient("localhost", 4000);
		client.listen();	
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
			//	System.out.println(e.getMessage());
			}
		}
	}
	
	private void addTextToArea(String text)
	{
		String areaText = this.area.getText();
		areaText = areaText + text + "\n";
		this.area.setText(areaText);
	}

}
