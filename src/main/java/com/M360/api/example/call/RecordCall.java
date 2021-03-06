package com.M360.api.example.call;

import com.M360.api.Message360Connector;
import com.M360.api.configuration.BasicM360Configuration;
import com.M360.api.configuration.M360Constants;
import com.M360.api.domain.Message360;
import com.M360.api.domain.call.Call;
import com.M360.api.domain.enums.AudioFormat;
import com.M360.api.domain.enums.Direction;
import com.M360.api.domain.responses.CallMessages;
import com.M360.api.exception.Error;
import com.M360.api.exception.M360Exception;

public class RecordCall {
	public static void main(String[] args) {
		BasicM360Configuration conf = new BasicM360Configuration();
		conf.setSid(M360Constants.ACCOUNTSID); 
		conf.setAuthToken(M360Constants.AUTHTOKEN);
		Message360Connector conn = new Message360Connector(conf);
		try {
			Long timeout=null;
			if(M360Constants.JSONFORMAT){
				String jsonCallResponse=conn.recordJsonCall("{CallSid}", Direction.OUT, true, timeout, null, AudioFormat.MP3);
				System.out.println(jsonCallResponse);
			}else{
				Message360<CallMessages> recordCall = conn.recordCall("{CallSid}", Direction.OUT, true, timeout, null, AudioFormat.MP3);
				if(recordCall.getMessage360().getErrors().getError().size()!=0){
					for(int x=0;x<recordCall.getMessage360().getErrors().getError().size();x++){
						Error error=recordCall.getMessage360().getErrors().getError().get(x);
							System.out.println("code :="+error.getCode()+".\nMessage:="+error.getMessage());
					}
				}else{
					System.out.println("Record Call");
					for(Call curCall:recordCall.getMessage360().getCall()){
						System.out.println("Account Sid:"+curCall.getAccountSid()+",Callsid:"+curCall.getCallSid());
					}
				}
			}
		} catch (M360Exception e) {
			e.printStackTrace();
		}
	}
}
