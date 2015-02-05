/**
* <p>Title: JsmppUtils.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 3, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.domain;

import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;

/**
 * <p>Title: JsmppUtils</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 3, 2015
 */
public class JsmppUtils {
	
    
    private static String systemID  ="smppclient1";
    private static String password = "password";
    private static String systemType = "cp";
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
    private static String localHost = "localhost";
    private static int  port = 2775;
    
	
	
	public static String getSystemID() {
		return systemID;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static String getSystemType() {
		return systemType;
	}
	
	public static TimeFormatter getTimeFormatter() {
		return timeFormatter;
	}

	public static String getLocalHost() {
		return localHost;
	}

	public static int getPort() {
		return port;
	}

	
	

}
