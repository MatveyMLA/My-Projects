import java.io.*;
import java.util.Hashtable;
import java.util.Date;
import javax.transaction.*;
import javax.naming.*;
import javax.jms.*;
import java.awt.*;
import java.awt.event.*;

public class QueueReceive extends Frame implements MessageListener{
  
  private QueueConnectionFactory qconFactory;
  private QueueConnection qcon;
  private QueueSession qsession;
  private QueueReceiver qreceiver;
  private Queue queue;
  private boolean quit = false;
  private TextArea tf=new TextArea();

  public QueueReceive(){
	  super("Queue Receiver");
      setLocation(400,50);
      setSize(400,200);
	  add(tf);	  
	  addWindowListener(new WindowAdapter(){
                          public void windowClosing(WindowEvent w){							  
							  close();
                              System.exit(0);
                          }
      });
	  setVisible(true);
	  init();
  }

  public void onMessage(Message msg)
  {
    try {
      //print message to the text-field
	  // enter text here     
    } catch (JMSException e) {
         e.printStackTrace();
    }
  }
  
  public void init(){
	try{
	    //initialize all jms class members and
		//connect to the Queue - using the configured JNDI name
		//start connection
	}catch(NamingException e1){e1.printStackTrace();
	}catch(JMSException e2){e2.printStackTrace();}
  }
  
   public void close(){
	try{
		synchronized(this) {
			 quit = true;
			 this.notifyAll(); // Notify main thread to quit
        }
	    qreceiver.close();
		qsession.close();
	    qcon.close();
	}catch(JMSException e1){e1.printStackTrace();
	}catch(NullPointerException e2){e2.printStackTrace();}
  }

  public static void main(String[] args){ 
	QueueReceive receiver=new QueueReceive();
    // Wait until a "quit" message has been received.
    synchronized(receiver) {
      while (! receiver.quit) {
        try {
          receiver.wait();
        } catch (InterruptedException ie) {}
      }
    }    
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




