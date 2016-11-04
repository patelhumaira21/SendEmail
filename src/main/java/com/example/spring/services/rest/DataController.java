package com.example.spring.services.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.services.api.FirebaseApi;
import com.example.spring.services.api.SendGridEmailApi;
import com.example.spring.services.utils.CookieUtils;
import com.example.spring.services.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

@RestController
@EnableAutoConfiguration
public class DataController {
	
	private static final String COOKIE_NAME = "username";
	private static final String DEFAULT_EMAIL = "patelhumaira21@gmail.com";
	private static Logger LOGGER = LoggerFactory.getLogger(DataController.class);
	private static final String FIREBASE_URL = "https://webproject-58f78.firebaseio.com/Messages";

	@RequestMapping(value="/messages", method=RequestMethod.GET,produces="application/json")
	public ResponseEntity sendEmail (HttpServletRequest request) throws UnsupportedEncodingException {
		String fromEmail = CookieUtils.getCookie(request.getCookies(), COOKIE_NAME);
		fromEmail = fromEmail == null ? DEFAULT_EMAIL : URLDecoder.decode(fromEmail, "UTF-8");
		FirebaseApi api = new FirebaseApi(FIREBASE_URL);
		ArrayNode result = api.getData(fromEmail);
		LOGGER.info("Records retrieved from firebase");
		return ResponseEntity.ok().body(result);	
	}
}
