/**
 * The SendEmailExample program implements that application that 
 * validate the Sid,Authentication and email formate and send 
 * email to user else send exception.
 * @version v1b
 * @since 2015-11-12 12:12:13
 * 
 */

package com.M360.api.example.emails;


import com.M360.api.Message360Connector;
import com.M360.api.configuration.BasicM360Configuration;
import com.M360.api.configuration.M360Constants;
import com.M360.api.domain.Message360;
import com.M360.api.domain.responses.Message360Email;
import com.M360.api.domain.email.SendEmail;
import com.M360.api.domain.enums.EmailSendAs;
import com.M360.api.exception.M360Exception;
import com.M360.api.exception.Error;


public class SendEmails {
	public static void main(String[] args) {
		BasicM360Configuration conf = new BasicM360Configuration();
		conf.setSid(M360Constants.ACCOUNTSID);
		conf.setAuthToken(M360Constants.AUTHTOKEN);
		Message360Connector conn = new Message360Connector(conf);
		try {
			if(M360Constants.JSONFORMAT){
				String jsonEmailResponse=conn.sendJsonEmail("{to}", "{cc}", "{bcc}", "{subject}", EmailSendAs.TEXT, "{message}", null);
				System.out.println(jsonEmailResponse);
			}else{
				Message360<Message360Email<SendEmail>> smsMessage = conn.sendEmail("rizwan+ytel@df.vom", null, "shoaib+ytel.com", "Test Helper Lib Attachment", EmailSendAs.TEXT, "Test Email from JAva Helper Library 1",null);
				if(smsMessage.getMessage360().getErrors().getError().size()!=0){
					for(int x=0;x<smsMessage.getMessage360().getErrors().getError().size();x++){
						Error error=smsMessage.getMessage360().getErrors().getError().get(x);
							System.out.println("code :="+error.getCode()+",\nMessage:="+error.getMessage()+",\nMoreInfo"+error.getMoreInfo().toString());
					}
				}
				else{
					System.out.println("20:Sid:= "+smsMessage.getMessage360().getEmail().getEmailSid()+",Total Email Sent:="+smsMessage.getMessage360().getEmail().getTotalEmailSent()+","+smsMessage.getMessage360().getEmail().getCc()+","+smsMessage.getMessage360().getEmail().getBcc());
				}
			}	
		} catch (M360Exception e) {
			e.printStackTrace();
		}
	}
}
