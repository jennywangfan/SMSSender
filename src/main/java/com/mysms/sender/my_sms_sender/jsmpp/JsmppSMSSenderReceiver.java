/**
* <p>Title: JsmppSMSSenderReceiver.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 3, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.jsmpp;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysms.sender.my_sms_sender.SMSSender;
import com.mysms.sender.my_sms_sender.domain.CellProviderType;
import com.mysms.sender.my_sms_sender.domain.JsmppUtils;

/**
 * <p>Title: JsmppSMSSenderReceiver</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 3, 2015
 */
public class JsmppSMSSenderReceiver implements SMSSender {

	private TypeOfNumber typeOfNumber;
    private NumberingPlanIndicator numberingPlanIndicator;
    //should be a regular expression,mostly set to null since SMSC will not relinquish routing control to an ESME for preventing of mis-routing messages
    private String addressRange;
    private String serviceType;
    private TypeOfNumber sourceAddressTon;
    private NumberingPlanIndicator sourceAddressNpi;
    private String sourceAddress;
    private TypeOfNumber destAddressTon;
    private NumberingPlanIndicator destAddressNpi;
    private String destAddress; 
	private String textMessage;
    
	public JsmppSMSSenderReceiver(){
		this.typeOfNumber = TypeOfNumber.NATIONAL;
		this.numberingPlanIndicator = NumberingPlanIndicator.NATIONAL;
		this.serviceType = "CMT";
		this.sourceAddress = "1616";
		this.sourceAddressTon = TypeOfNumber.NATIONAL;
		this.sourceAddressNpi = NumberingPlanIndicator.UNKNOWN;
		this.destAddress = "1717";
		this.destAddressTon = TypeOfNumber.NATIONAL;
		this.destAddressNpi = NumberingPlanIndicator.UNKNOWN;
		
		
	}
	
    private static Logger log = LoggerFactory.getLogger(JsmppSMSSender.class);
	/* (non-Javadoc)
	 * <p>Title: sendSMS</p>
	 * <p>Description: </p>
	 * @param phoneNumber
	 * @param provider
	 * @param content
	 * @see com.mysms.sender.my_sms_sender.SMSSender#sendSMS(java.lang.String, com.mysms.sender.my_sms_sender.domain.CellProviderType, java.lang.String)
	 */
	public void sendSMS(String phoneNumber, CellProviderType provider,
			String content) {
		// TODO Auto-generated method stub
    	
		SMPPSession session = new SMPPSession();
		session.setMessageReceiverListener(new MessageListener());
    	 try {
			session.connectAndBind(JsmppUtils.getLocalHost(), JsmppUtils.getPort(), 
					new BindParameter(BindType.BIND_TRX,JsmppUtils.getSystemID(), JsmppUtils.getPassword(),JsmppUtils.getSystemType(),
							typeOfNumber, numberingPlanIndicator, addressRange));
		} catch (IOException e) {
			log.error("Failed connect and bind to host");
		}
    	 try {
			String messageId = session.submitShortMessage(serviceType, sourceAddressTon, sourceAddressNpi, sourceAddress, 
					destAddressTon, destAddressNpi, destAddress,
					new ESMClass(), (byte)0, (byte)1,  JsmppUtils.getTimeFormatter().format(new Date()), null, 
					new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), (byte)0,
					new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte)0, content.getBytes());
			log.info("Message ID is  " + messageId);
			TimeUnit.SECONDS.sleep(30);
		} catch (IllegalArgumentException e) {
			//Illegal arguments
			log.error("Illegal arguments when submit short message");
		  } catch (PDUException e) {
              // Invalid PDU parameter
              log.error("Invalid PDU parameter");
              
          } catch (ResponseTimeoutException e) {
              // Response timeout
              log.error("Response timeout");
              
          } catch (InvalidResponseException e) {
              // Invalid response
              log.error("Receive invalid respose");
             
          } catch (NegativeResponseException e) {
              // Receiving negative response (non-zero command_status)
              log.error("Receive negative response");
              
          } catch (IOException e) {
              log.error("IO error occur");
              
          } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
        	  session.unbindAndClose();
          }
    	 
    	 
    	 
         

	}
	
	  /**
		 * @return the typeOfNumber
		 */
		public TypeOfNumber getTypeOfNumber() {
			return typeOfNumber;
		}
		/**
		 * @param typeOfNumber the typeOfNumber to set
		 */
		public void setTypeOfNumber(TypeOfNumber typeOfNumber) {
			this.typeOfNumber = typeOfNumber;
		}
		/**
		 * @return the numberingPlanIndicator
		 */
		public NumberingPlanIndicator getNumberingPlanIndicator() {
			return numberingPlanIndicator;
		}
		/**
		 * @param numberingPlanIndicator the numberingPlanIndicator to set
		 */
		public void setNumberingPlanIndicator(
				NumberingPlanIndicator numberingPlanIndicator) {
			this.numberingPlanIndicator = numberingPlanIndicator;
		}
		/**
		 * @return the addressRange
		 */
		public String getAddressRange() {
			return addressRange;
		}
		/**
		 * @param addressRange the addressRange to set
		 */
		public void setAddressRange(String addressRange) {
			this.addressRange = addressRange;
		}
		/**
		 * @return the serviceType
		 */
		public String getServiceType() {
			return serviceType;
		}
		/**
		 * @param serviceType the serviceType to set
		 */
		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}
		/**
		 * @return the sourceAddressTon
		 */
		public TypeOfNumber getSourceAddressTon() {
			return sourceAddressTon;
		}
		/**
		 * @param sourceAddressTon the sourceAddressTon to set
		 */
		public void setSourceAddressTon(TypeOfNumber sourceAddressTon) {
			this.sourceAddressTon = sourceAddressTon;
		}
		/**
		 * @return the sourceAddressNpi
		 */
		public NumberingPlanIndicator getSourceAddressNpi() {
			return sourceAddressNpi;
		}
		/**
		 * @param sourceAddressNpi the sourceAddressNpi to set
		 */
		public void setSourceAddressNpi(NumberingPlanIndicator sourceAddressNpi) {
			this.sourceAddressNpi = sourceAddressNpi;
		}
		/**
		 * @return the sourceAddress
		 */
		public String getSourceAddress() {
			return sourceAddress;
		}
		/**
		 * @param sourceAddress the sourceAddress to set
		 */
		public void setSourceAddress(String sourceAddress) {
			this.sourceAddress = sourceAddress;
		}
		/**
		 * @return the destAddressTon
		 */
		public TypeOfNumber getDestAddressTon() {
			return destAddressTon;
		}
		/**
		 * @param destAddressTon the destAddressTon to set
		 */
		public void setDestAddressTon(TypeOfNumber destAddressTon) {
			this.destAddressTon = destAddressTon;
		}
		/**
		 * @return the destAddressNpi
		 */
		public NumberingPlanIndicator getDestAddressNpi() {
			return destAddressNpi;
		}
		/**
		 * @param destAddressNpi the destAddressNpi to set
		 */
		public void setDestAddressNpi(NumberingPlanIndicator destAddressNpi) {
			this.destAddressNpi = destAddressNpi;
		}
		/**
		 * @return the destAddress
		 */
		public String getDestAddress() {
			return destAddress;
		}
		/**
		 * @param destAddress the destAddress to set
		 */
		public void setDestAddress(String destAddress) {
			this.destAddress = destAddress;
		}

		/**
		 * @return the textMessage
		 */
		public String getTextMessage() {
			return textMessage;
		}

		/**
		 * @param textMessage the textMessage to set
		 */
		public void setTextMessage(String textMessage) {
			this.textMessage = textMessage;
		}
		

		public static void main(String[] args){
			
				JsmppSMSSenderReceiver rec = new JsmppSMSSenderReceiver();
				rec.setAddressRange("^1616");
				rec.sendSMS("9178555737", CellProviderType.ATT, "Hello");
				try {
					TimeUnit.SECONDS.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
}
