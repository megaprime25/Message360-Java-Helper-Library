package com.M360.api.example.phonenumber;

import com.M360.api.Message360Connector;
import com.M360.api.configuration.BasicM360Configuration;
import com.M360.api.configuration.M360Constants;
import com.M360.api.domain.Message360;
import com.M360.api.domain.enums.PhoneNumberType;
import com.M360.api.domain.phonenumber.Phone;
import com.M360.api.domain.responses.NumberMessage;
import com.M360.api.exception.Error;
import com.M360.api.exception.M360Exception;

public class AvailablePhoneNumber {
	public static void main(String[] args) {
		BasicM360Configuration conf = new BasicM360Configuration();
		conf.setSid(M360Constants.ACCOUNTSID); 
		conf.setAuthToken(M360Constants.AUTHTOKEN);
		Message360Connector conn = new Message360Connector(conf);
		try {
			if(M360Constants.JSONFORMAT){
				//800 areacode
				//1 is pagesize
				String jsonSMSResponse=conn.availableJsonNumber(null, PhoneNumberType.SMS, 1);
				System.out.println(jsonSMSResponse);
			}else{
				Message360<NumberMessage> availablePhoneNumberMessage = conn.availableNumber(null, null, null);
				if(availablePhoneNumberMessage.getMessage360().getErrors().getError().size()!=0){
					for(int x=0;x<availablePhoneNumberMessage.getMessage360().getErrors().getError().size();x++){
						Error error=availablePhoneNumberMessage.getMessage360().getErrors().getError().get(x);
							System.out.println("code :="+error.getCode()+".\nMessage:="+error.getMessage()+",\nMoreInfo"+error.getMoreInfo().toString());
					}
				}else{
					System.out.println("List of Available Phone number:");
					for(Phone curPhone:availablePhoneNumberMessage.getMessage360().getPhones().getPhone()){
						System.out.println("sid="+curPhone.getPhoneNumber()+",Voice Enabled:="+curPhone.getVoiceEnabled()+",SMS Enabled:="+curPhone.getSmsEnabled());
					}
				}
			}
		} catch (M360Exception e) {
			e.printStackTrace();
		}
	}
}
