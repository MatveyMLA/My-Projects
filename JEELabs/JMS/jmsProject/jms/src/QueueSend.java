import java.io.*;
import java.util.Hashtable;
import java.util.Date;
import javax.transaction.*;
import javax.naming.*;
import javax.jms.*;
import java.awt.*;
import java.awt.event.*;


public class QueueSend extends Frame implements ActionListener{  

  private QueueConnectionFactory qconFactory;
  private QueueConnection qcon;
  private QueueSession qsession;
  private QueueSender qsender;
  private Queue queue;
  private TextMessage msg;

  private TextField tf=new TextField();
  private Button send=new Button("Send");

  public QueueSend(){
	  super("Queue Sender");
      setLocation(150,50);
      setSize(200,100);
	  add(tf);
	  add(send,BorderLayout.SOUTH);
	  send.addActionListener(this);
	  send.setEnabled(false);
	  addWindowListener(new WindowAdapter(){
                          public void windowClosing(WindowEvent w){
							  send("quit");
							  close();
                              System.exit(0);
                          }
      });
	  setVisible(true);
	  init();
  }

  public void init(){
	try{
		//initialize all jms class members and
		//connect to the Queue - using the configured JNDI name
		//start connection
		//enter text here


		//the send button will become available when connction in made..
		send.setEnabled(true);
	}catch(NamingException e1){e1.printStackTrace();
	}catch(JMSException e2){e2.printStackTrace();}
  }


  public void actionPerformed(ActionEvent e){
	  String message=tf.getText();
	  tf.setText("");
	  if(message.length()>0)
		  send(message);
  }

  public void send(String message){
	try{
	    //send a message with the given string
		// enter text here    
	}catch(JMSException e){e.printStackTrace();}
  }
 
  public void close(){
	try{
	    qsender.close();
		qsession.close();
	    qcon.close();
	}catch(JMSException e1){e1.printStackTrace();
	}catch(NullPointerException e2){e2.printStackTrace();}
  }
  
  public static void main(String[] args){
       new QueueSend();    
  }  

  public  InitialContext getInitialContext(){
		Hashtable<String,String> h=new Hashtable<String,String>();
		h.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		h.put("java.naming.provider.url","localhost");
		try {
			return new InitialContext(h);
		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
   }
}

