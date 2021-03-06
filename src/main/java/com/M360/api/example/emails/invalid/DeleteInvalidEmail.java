/**
 * This endpoint allows you to delete entries in the Invalid Emails list.
 * @version v1b
 * @since 2015-11-12 12:12:13
 * @author Ytel Inc
 * 
 */
package com.M360.api.example.emails.invalid;


import com.M360.api.Message360Connector;
import com.M360.api.configuration.BasicM360Configuration;
import com.M360.api.configuration.M360Constants;
import com.M360.api.domain.Message360;
import com.M360.api.domain.email.EmailAddress;
import com.M360.api.domain.email.Invalid;
import com.M360.api.domain.responses.Message360Email;
import com.M360.api.exception.Error;
import com.M360.api.exception.M360Exception;

public class DeleteInvalidEmail {
	public static void main(String[] args) {
		BasicM360Configuration conf = new BasicM360Configuration();
		conf.setSid(M360Constants.ACCOUNTSID);
		conf.setAuthToken(M360Constants.AUTHTOKEN);
		Message360Connector conn = new Message360Connector(conf);
		try {
			if(M360Constants.JSONFORMAT){
				String jsonEmailResponse=conn.deleteJsonInvalidEmail("{emailsAddress}");
				System.out.println(jsonEmailResponse);
			}else{
				Message360<Message360Email<Invalid>> deletedInvalidEmails = conn.deleteInValidEmail("{emailsAddress}");
				if(deletedInvalidEmails.getMessage360().getErrors().getError().size()!=0){
					for(int x=0;x<deletedInvalidEmails.getMessage360().getErrors().getError().size();x++){
						Error error=deletedInvalidEmails.getMessage360().getErrors().getError().get(x);
							System.out.println("code :="+error.getCode()+",\nMessage:="+error.getMessage()+",\nMoreInfo"+error.getMoreInfo().toString());
					}
				}else{
					System.out.println("Delete Invalid Emails");
					EmailAddress deleteInvalid=deletedInvalidEmails.getMessage360().getEmail().getInvalidEmailAddress();
					System.out.println("Deleted InValid Emails "+deleteInvalid.getEmail()+",Status :="+deleteInvalid.getDeleteStatus()+",Reason:="+deleteInvalid.getReason());	
				}
			}
		} catch (M360Exception e) {
			e.printStackTrace();
		}
	}
}

/*
shoaib@ytel.comin	Reason:Mail domain mentioned in email address is unknown
shoaib@ytel.coin	Reason:Mail domain mentioned in email address is unknown
seema+test11@ytel.coin	Reason:Mail domain mentioned in email address is unknown
*/