/**
 * Add email addresses to the Unsubscribe list.
 * @version v1b
 * @since 2015-11-12 12:12:13
 * @author Ytel Inc
 * 
 */
package com.M360.api.example.emails.unsubscribe;

import com.M360.api.Message360Connector;
import com.M360.api.configuration.BasicM360Configuration;
import com.M360.api.configuration.M360Constants;
import com.M360.api.domain.Message360;
import com.M360.api.domain.responses.Message360Email;
import com.M360.api.domain.email.EmailAddress;
import com.M360.api.domain.email.Unsubscribed;
import com.M360.api.exception.Error;
import com.M360.api.exception.M360Exception;

public class AddUnsubscribeEmail {
	public static void main(String[] args) {
		BasicM360Configuration conf = new BasicM360Configuration();
		conf.setSid(M360Constants.ACCOUNTSID);
		conf.setAuthToken(M360Constants.AUTHTOKEN);
		Message360Connector conn = new Message360Connector(conf);
		try {
			if(!M360Constants.JSONFORMAT){
				String jsonEmailResponse=conn.addJsonUnsubsribeEmailAddress("{emailsAddress}");
				System.out.println(jsonEmailResponse);
			}else{
				Message360<Message360Email<Unsubscribed>> addUnsubscribed = conn.addUnsubsribeEmailAddress("{emailsAddress}");
				if(addUnsubscribed.getMessage360().getErrors().getError().size()!=0){
					for(int x=0;x<addUnsubscribed.getMessage360().getErrors().getError().size();x++){
						Error error=addUnsubscribed.getMessage360().getErrors().getError().get(x);
							System.out.println("code :="+error.getCode()+",\nMessage:="+error.getMessage()+",\nMoreInfo"+error.getMoreInfo().toString());
					}
				}else{
					System.out.println("Add Unsubscribe Emails");
					EmailAddress unsubscribedEmailAddress=addUnsubscribed.getMessage360().getEmail().getUnsubscribedEmailAddress();
					System.out.println("39: Email "+unsubscribedEmailAddress.getEmail()+",Add status:"+unsubscribedEmailAddress.getAddStatus());
				}
			}
		} catch (M360Exception e) {
			e.printStackTrace();
		}
	}
}

