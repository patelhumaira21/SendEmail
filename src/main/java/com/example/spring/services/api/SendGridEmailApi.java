package com.example.spring.services.api;

import com.example.spring.services.rest.EmailController;
import com.example.spring.services.utils.JsonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sendgrid.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendGridEmailApi {
	
	private static final String SENDGRID_KEY = "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";
	private static final String ENDPOINT = "mail/send";
	private String to;
	private String from;
	private String subject;
	private String message;
	private static Logger LOGGER = LoggerFactory
			.getLogger(SendGridEmailApi.class);

	public SendGridEmailApi() {
	}

	public SendGridEmailApi(String to, String from, String subject, String message){
		this.to = to;
		this.from = from;
		this.message = message;
		this.subject = subject;
	}
	
	public int send(){
		Email from = new Email(this.from);
        Email to = new Email(this.to);
        Content content = new Content("text/plain", this.message);
        Mail mail = new Mail(from, this.subject, to, content);
        SendGrid sg = new SendGrid(SENDGRID_KEY);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = ENDPOINT;
            request.body = mail.build();
            Response response = sg.api(request);
            LOGGER.info("Email sent successfully");
            return response.statusCode;
        } catch (IOException ex) {
        	LOGGER.error("Exception caught while sending email");
        	return 500;
        }
	}
	
	
	
}
