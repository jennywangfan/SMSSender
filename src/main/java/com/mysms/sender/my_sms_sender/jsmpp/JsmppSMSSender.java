/**
* <p>Title: JsmppSMSSender.java</p>
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
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
 * <p>Title: JsmppSMSSender</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 3, 2015
 */
public class JsmppSMSSender implements SMSSender {
	
    
  
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
	private long timeOut;
	private RegisteredDelivery registeredDelivery;
	private Alphabet alphabet;
	private MessageClass messageClass;
    private static Logger log = LoggerFactory.getLogger(JsmppSMSSender.class);
    private SMPPSession smppSession;
    private ExecutorService taskExecutor;
   
    
    public void startSender(){
    	smppSession = new SMPPSession();
    	SenderSessionListner sessionListner = new SenderSessionListner();
    	smppSession.addSessionStateListener(sessionListner);
    	taskExecutor =  Executors.newFixedThreadPool(10);
    	try {
			smppSession.connectAndBind(JsmppUtils.getLocalHost(), JsmppUtils.getPort(), 
					new BindParameter(BindType.BIND_TX,JsmppUtils.getSystemID(), JsmppUtils.getPassword(),JsmppUtils.getSystemType(),
							typeOfNumber, numberingPlanIndicator, addressRange),timeOut);
		} catch (IOException e) {
			log.error("Failed connect and bind to host");
		}
	
    }
    
    public void stopSender(){
    	if(smppSession != null)
    		smppSession.unbindAndClose();
    	if(taskExecutor != null)
    		taskExecutor.shutdown();
    }
    
	/* (non-Javadoc)
	 * <p>Title: sendSMS</p>
	 * <p>Description: </p>
	 * @param phoneNumber
	 * @param provider
	 * @param content
	 * @see com.mysms.sender.my_sms_sender.SMSSender#sendSMS(java.lang.String, com.mysms.sender.my_sms_sender.domain.CellProviderType, java.lang.String)
	 */
    
	public void sendSMS(String phoneNumber, CellProviderType provider,
			final String content) {
		// TODO Auto-generated method stub
    	
		 taskExecutor.execute(new Runnable(){

			public void run() {
				try {
		    		 
					String messageId = smppSession.submitShortMessage(serviceType, sourceAddressTon, sourceAddressNpi, sourceAddress, 
							destAddressTon, destAddressNpi, destAddress,
							new ESMClass(), (byte)0, (byte)1,  JsmppUtils.getTimeFormatter().format(new Date()), null, 
							registeredDelivery, (byte)0,
							new GeneralDataCoding(alphabet, messageClass, false), (byte)0, content.getBytes());
					log.info("Message ID is  " + messageId);
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
		              
		          }
				
			}
			 
		 });
    	 

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

		public long getTimeOut() {
			return timeOut;
		}

		public void setTimeOut(long timeOut) {
			this.timeOut = timeOut;
		}

		public RegisteredDelivery getRegisteredDelivery() {
			return registeredDelivery;
		}

		public void setRegisteredDelivery(RegisteredDelivery registeredDelivery) {
			this.registeredDelivery = registeredDelivery;
		}

		public SMPPSession getSmppSession() {
			return smppSession;
		}

		public void setSmppSession(SMPPSession smppSession) {
			this.smppSession = smppSession;
		}

		public ExecutorService getTaskExecutor() {
			return taskExecutor;
		}

		public void setTaskExecutor(ExecutorService taskExecutor) {
			this.taskExecutor = taskExecutor;
		}

		public Alphabet getAlphabet() {
			return alphabet;
		}

		public void setAlphabet(Alphabet alphabet) {
			this.alphabet = alphabet;
		}

		public MessageClass getMessageClass() {
			return messageClass;
		}

		public void setMessageClass(MessageClass messageClass) {
			this.messageClass = messageClass;
		}
		
		

}
