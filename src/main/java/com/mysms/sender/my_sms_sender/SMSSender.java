/**
* <p>Title: SendSMS.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Jan 31, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender;

import com.mysms.sender.my_sms_sender.domain.CellProviderType;

/**
 * <p>Title: SendSMS</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Jan 31, 2015
 */
public interface SMSSender {
	
	public void sendSMS(String phoneNumber,CellProviderType provider,String content);
	
	

}
