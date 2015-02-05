

import java.util.concurrent.TimeUnit;

import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;

import com.mysms.sender.my_sms_sender.SMSSender;
import com.mysms.sender.my_sms_sender.SMSSenderFactory;
import com.mysms.sender.my_sms_sender.domain.CellProviderType;
import com.mysms.sender.my_sms_sender.jsmpp.JsmppReceiver;
import com.mysms.sender.my_sms_sender.jsmpp.JsmppSMSSender;

/**
 * Hello world!
 *
 */
public class App 
{
	 
    public static void main( String[] args )
    {
        SMSSender smsSender = SMSSenderFactory.getEmailSMSSender();
        smsSender.sendSMS("9178554662", CellProviderType.ATT, "Pang,I Love You");
        
        //using jsmpp to send
        JsmppSMSSender sender = new JsmppSMSSender();
        sender.setTypeOfNumber(TypeOfNumber.INTERNATIONAL);
        sender.setNumberingPlanIndicator(NumberingPlanIndicator.UNKNOWN);
        sender.setAddressRange("^1616");
        sender.setTimeOut(15);
        sender.setServiceType("CMT");
        sender.setSourceAddressTon(TypeOfNumber.INTERNATIONAL);
        sender.setSourceAddressNpi(NumberingPlanIndicator.UNKNOWN);
        sender.setSourceAddress("1616");
        sender.setDestAddressTon(TypeOfNumber.INTERNATIONAL);
        sender.setDestAddressNpi(NumberingPlanIndicator.UNKNOWN);
        sender.setDestAddress("1717");
        sender.setRegisteredDelivery(new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT));
        sender.setAlphabet(Alphabet.ALPHA_DEFAULT);
        sender.setMessageClass(MessageClass.CLASS1);
        sender.startSender();
        for(int i = 0 ; i < 50; ++i)
        sender.sendSMS("9999", CellProviderType.ATT, "hello");
        JsmppReceiver receiver = new JsmppReceiver(TypeOfNumber.NATIONAL,NumberingPlanIndicator.NATIONAL,"^1616");
        receiver.startReceiver();
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sender.stopSender();
		receiver.startReceiver();
        
    }  
        
    
}
