package com.example.spring.services.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.services.api.AuthenticationApi;
import com.example.spring.services.api.SendGridEmailApi;
import com.example.spring.services.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@EnableAutoConfiguration
public class LoginController {
	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity validateLogin(HttpServletRequest request,
			@RequestBody String body) {
		JsonNode bodyNode = JsonUtils.jsonFromString(body);
		JsonNode user = JsonUtils.get(bodyNode, "username");
		JsonNode password = JsonUtils.get(bodyNode, "password");

		AuthenticationApi login = new AuthenticationApi(user.asText(),
				password.asText());
		String result = login.validateUser();
		LOGGER.info("Result returned after authentication");
		ObjectNode resultNode = JsonUtils.getMapper().createObjectNode();
		resultNode.put("status", result);
		return ResponseEntity.ok().body(resultNode);
	}

}