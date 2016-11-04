package com.example.spring.services.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.services.api.FirebaseApi;
import com.example.spring.services.api.SendGridEmailApi;
import com.example.spring.services.utils.CookieUtils;
import com.example.spring.services.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@EnableAutoConfiguration
public class EmailController {

	private static final String COOKIE_NAME = "username";
	private static final String DEFAULT_EMAIL = "patelhumaira21@gmail.com";
	private static Logger LOGGER = LoggerFactory
			.getLogger(EmailController.class);
	private static final String FIREBASE_URL = "https://webproject-58f78.firebaseio.com";

	@RequestMapping(value = "/email", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity sendEmail(HttpServletRequest request,
			@RequestBody String body) throws UnsupportedEncodingException {
		String fromEmail = CookieUtils.getCookie(request.getCookies(),
				COOKIE_NAME);
		fromEmail = fromEmail == null ? DEFAULT_EMAIL : URLDecoder.decode(
				fromEmail, "UTF-8");

		JsonNode bodyNode = JsonUtils.jsonFromString(body);
		JsonNode name = JsonUtils.get(bodyNode, "name");
		JsonNode email = JsonUtils.get(bodyNode, "email");
		JsonNode message = JsonUtils.get(bodyNode, "message");

		SendGridEmailApi api = new SendGridEmailApi(email.asText(), fromEmail,
				name.asText(), message.asText());
		int statusCode = api.send();

		ObjectNode resultNode = JsonUtils.getMapper().createObjectNode();
		if (statusCode == 200 || statusCode == 201 || statusCode == 202) {
			LOGGER.info("Email sent successfully");
			ObjectNode messageNode = JsonUtils.getMapper().createObjectNode();
			messageNode.put("to", email.asText());
			messageNode.put("from", fromEmail);
			messageNode.put("subject", name.asText());
			messageNode.put("message", message.asText());
			FirebaseApi firebase = new FirebaseApi(FIREBASE_URL);
			firebase.saveInDataBase(messageNode);

			resultNode.put("status", "success");
			resultNode.put("message", "Email sent successfully.");
		} else {
			LOGGER.error("Error sending email");
			resultNode.put("status", "error");
			resultNode.put("message", "Email couldn't be sent.");
		}

		return ResponseEntity.ok().body(resultNode);
	}

}