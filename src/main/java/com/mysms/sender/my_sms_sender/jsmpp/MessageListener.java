/**
* <p>Title: MessageListener.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>All Right Reserved</p>
* @author Fan Wang
* @date Feb 3, 2015
* @version 1.0
*/
package com.mysms.sender.my_sms_sender.jsmpp;

import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: MessageListener</p>
 * <p>Description: </p>
 * <p>All Right Reserved</p> 
 * @author Fan Wang
 * @date Feb 3, 2015
 */
public class MessageListener implements MessageReceiverListener {
	
	private static Logger log = LoggerFactory.getLogger(MessageListener.class);

	/* (non-Javadoc)
	 * <p>Title: onAcceptDataSm</p>
	 * <p>Description: </p>
	 * @param dataSm
	 * @param source
	 * @return
	 * @throws ProcessRequestException
	 * @see org.jsmpp.session.GenericMessageReceiverListener#onAcceptDataSm(org.jsmpp.bean.DataSm, org.jsmpp.session.Session)
	 */
	public DataSmResult onAcceptDataSm(DataSm dataSm, Session source)
			throws ProcessRequestException {
		log.info("Receive short data message" + dataSm );
		return null;
		//DataSmResult result = new DataSmResult(messageId, optionalParameters)
	}

	/* (non-Javadoc)
	 * <p>Title: onAcceptDeliverSm</p>
	 * <p>Description: </p>
	 * @param deliverSm
	 * @throws ProcessRequestException
	 * @see org.jsmpp.session.MessageReceiverListener#onAcceptDeliverSm(org.jsmpp.bean.DeliverSm)
	 */
	public void onAcceptDeliverSm(DeliverSm deliverSm)
			throws ProcessRequestException {
		if(MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())){
			try {
				DeliveryReceipt deliveryReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
				String messageID = deliveryReceipt.getId();
				messageID =Long.toString(Long.parseLong(messageID) & 0xffffffff,16).toUpperCase();
				
				//TODO Add your own code to handle the delivery receipt
				log.info("Receive delivery receipt for message " + messageID + "  from "+ deliverSm.getSourceAddr() + " to " + deliverSm.getDestAddress()
						+ " : " + deliveryReceipt);
			} catch (InvalidDeliveryReceiptException e) {
				log.error("Failed getting delivery receipt");
			}
			
		}
		else{
			// regular short message
			String message = new String(deliverSm.getShortMessage());
			log.info("Regular message " + message);
			parseMessage(message);
		}

	}

	/**
	* <p>Title: parseMessage</p>
	* <p>Description: </p>
	* @param shortMessage
	*/
	private void parseMessage(String shortMessage) {
		// TODO parse message and handle it accordingly
		
	}

	/* (non-Javadoc)
	 * <p>Title: onAcceptAlertNotification</p>
	 * <p>Description: </p>
	 * @param alertNotification
	 * @see org.jsmpp.session.MessageReceiverListener#onAcceptAlertNotification(org.jsmpp.bean.AlertNotification)
	 */
	public void onAcceptAlertNotification(AlertNotification alertNotification) {
		// TODO Auto-generated method stub
		
		log.info("Get AlertNotification "+"  from "+ alertNotification.getSourceAddr() + " to " + alertNotification.getEsmeAddr());

	}

}
