/**
* <p>Title: SenderSessionListner.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 4, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.jsmpp;


import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SessionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: SenderSessionListner</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 4, 2015
 */
public class SenderSessionListner implements SessionStateListener {
	
	private static Logger log = LoggerFactory.getLogger(SenderSessionListner.class);

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
		log.info("State changes from" + oldState + " to " + newState + " source " + source.toString() );
		

	}

}
