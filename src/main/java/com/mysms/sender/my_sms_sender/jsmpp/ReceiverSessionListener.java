/**
* <p>Title: SessionListener.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 4, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.jsmpp;


import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SessionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: SessionListener</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 4, 2015
 */
public class ReceiverSessionListener implements SessionStateListener {
	
	private TypeOfNumber ton;
	private NumberingPlanIndicator npi;
	private String addr;
	
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 *
	 */
	public ReceiverSessionListener(TypeOfNumber ton, NumberingPlanIndicator npi, String addr) {
		this.ton = ton;
		this.npi = npi;
		this.addr = addr;
	}
	
	private static Logger log = LoggerFactory.getLogger(ReceiverSessionListener.class);

	/* (non-Javadoc)
	 * <p>Title: onStateChange</p>
	 * <p>Description: </p>
	 * @param newState
	 * @param oldState
	 * @param source
	 * @see org.jsmpp.session.SessionStateListener#onStateChange(org.jsmpp.extra.SessionState, org.jsmpp.extra.SessionState, java.lang.Object)
	 */
	public void onStateChange(SessionState newState, SessionState oldState,
			Object source) {
		// TODO Auto-generated method stub
			if(newState.equals(SessionState.CLOSED)){
			log.warn("Session closed, Restart a new session to receive text");
			JsmppReceiver jRec = new JsmppReceiver(ton,npi,addr);
			jRec.startReceiver();
			
		}else{
			log.info("State changes from" + oldState + " to " + newState + " source " + source.toString() );
			
		}
		

	}

}
