package com.mysms.sender.my_sms_sender.domain;

import java.io.Serializable;

/**
 * 
* <p>Title: EmailTemplate</p>
* <p>Description: Class for storing email information for sending</p>
* @author Fan Wang
* @date Jan 29, 2015
 */
public class EmailTemplate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String subject;
	private String to;
	private String content;


    public EmailTemplate(){
    	
    }
    


	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param phoneNumber
	* @param provider
	* @param content2
	*
	*/
	public EmailTemplate(String phoneNumber, CellProviderType provider,
			String content) {
		this.setTo(phoneNumber+provider.getEmailPostfix());
		this.setSubject("Text To Phone Number "+phoneNumber);
		this.setContent(content);
	}



	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String toString(){
		String space = "   ";
		StringBuilder sb = new StringBuilder();
		sb.append("To : " + this.to + space);
		sb.append("Subject : " + this.subject + space);
		sb.append("Content : " +this.content + space);
		return sb.toString();
	}

}
