/**
* <p>Title: SMSSenderFactory.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 2, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender;

import com.mysms.sender.my_sms_sender.email.EmailSMSSender;

/**
 * <p>Title: SMSSenderFactory</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 2, 2015
 */
public class SMSSenderFactory {
	
	private static EmailSMSSender emailSMSSender = new EmailSMSSender();
	
	public static SMSSender getEmailSMSSender(){
		return emailSMSSender;
	}

}
