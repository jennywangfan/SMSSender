/**
* <p>Title: JsmppReceiver.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 3, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.jsmpp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysms.sender.my_sms_sender.domain.JsmppUtils;

/**
 * <p>Title: JsmppReceiver</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 3, 2015
 */
public class JsmppReceiver{
	
	private TypeOfNumber typeOfNumber;
    private NumberingPlanIndicator numberingPlanIndicator;
    //should be a regular expression,mostly set to null since SMSC will not relinquish routing control to an ESME for preventing of mis-routing messages
    private String addressRange;
    private static Logger log = LoggerFactory.getLogger(JsmppReceiver.class);
    private Thread recThread;
    private boolean running = true;
    
    public JsmppReceiver(TypeOfNumber ton,NumberingPlanIndicator npi,String addr){
    	this.typeOfNumber = ton;
    	this.numberingPlanIndicator = npi;
    	this.addressRange = addr;
    }
    
    
    public void startReceiver(){
    	
    	recThread = new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				SMPPSession session = new SMPPSession();
				try {
				
				session.setMessageReceiverListener(new MessageListener());
				session.addSessionStateListener(new ReceiverSessionListener(typeOfNumber,numberingPlanIndicator,addressRange));
		    	 
					session.connectAndBind(JsmppUtils.getLocalHost(), JsmppUtils.getPort(), 
							new BindParameter(BindType.BIND_TRX,JsmppUtils.getSystemID(), JsmppUtils.getPassword(),JsmppUtils.getSystemType(),
									typeOfNumber, numberingPlanIndicator, addressRange));
					
				while(running && session != null && !session.getSessionState().equals(SessionState.CLOSED)){
					TimeUnit.SECONDS.sleep(60);
				}
					
				} catch (IOException e) {
					log.error("Failed connect and bind to host");
				} catch (InterruptedException e) {
					log.error("Thread is interuppted");
				}finally{
					session.unbindAndClose();
				}
			}
    		
    		
    	});
    	recThread.start();
    }

	
	
	
	public static void main(String[] args){
		JsmppReceiver jRec = new JsmppReceiver(TypeOfNumber.NATIONAL,NumberingPlanIndicator.NATIONAL,"^1616");
		jRec.startReceiver();
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jRec.stopReceiver();
	}


	/**
	* <p>Title: stopReceiver</p>
	* <p>Description: </p>
	*/
	private void stopReceiver() {
		// TODO Auto-generated method stub
		if(recThread != null)
			recThread.interrupt();
		
		running = false;
	}

}
