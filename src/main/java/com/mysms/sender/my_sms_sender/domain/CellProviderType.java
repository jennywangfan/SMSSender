/**
* <p>Title: CellProviderType.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Jan 31, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.domain;

/**
 * <p>Title: CellProviderType</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Jan 31, 2015
 */
public enum CellProviderType {
	ATT("@txt.att.net"),
	TMOBILE("@tmomail.net"),
	VERIZON("@vtext.com"),
	SPRINT("@messaging.sprintpcs.com "),
	VIRGIN("@vmobl.com"),
	TRACFONE("@mmst5.tracfone.com"),
	METROPCS("@mymetropcs.com"),
	BOOSTMOBILE("@myboostmobile.com"),
	CRICKET("@sms.mycricket.com"),
	NEXTEL("@messaging.nextel.com"),
	ALLTEL("@message.alltel.com"),
	PTEL("@ptel.com"),
	SUNCOM("@tms.suncom.com"),
	QWEST("@qwestmp.com"),
	USCELLULAR("@email.uscc.net");
	
	private String emailPostfix;
	private CellProviderType(String s){
		this.emailPostfix = s;
	}
	
	public String getEmailPostfix(){
		return emailPostfix;
	}

}
