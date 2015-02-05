/**
* <p>Title: SendSMSViaEmail.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Jan 31, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.email;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysms.sender.my_sms_sender.SMSSender;
import com.mysms.sender.my_sms_sender.domain.CellProviderType;
import com.mysms.sender.my_sms_sender.domain.EmailTemplate;
import com.mysms.sender.my_sms_sender.domain.SpringContextUtils;

/**
 * <p>Title: SendSMSViaEmail</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Jan 31, 2015
 */
public class EmailSMSSender implements SMSSender {

	private static Logger log = LoggerFactory.getLogger(EmailSMSSender.class);
	private EmailSender emailSender;

	/* (non-Javadoc)
	* <p>Title: sendSMS</p>
	* <p>Description: </p>
	* @param phoneNumber
	* @param provider
	* @param content
	* @see com.mysms.sender.my_sms_sender.SendSMS#sendSMS(java.lang.String, com.mysms.sender.my_sms_sender.domain.CellProviderType, java.lang.String)
	*/
	public void sendSMS(String phoneNumber, CellProviderType provider,
			String content) {
		emailSender = (EmailSender) SpringContextUtils.getBean("emailsender");
		try {
			emailSender.sendMailBySynchronousMode(new EmailTemplate(phoneNumber,provider,content));
		} catch (MessagingException e) {
			log.error(e.getMessage());
		}
		
	}
	
	public void stopEmailSMSSender(){
		emailSender.getTaskExecutor();
	}

}
