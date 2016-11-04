package com.example.spring.services.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper() {
		return mapper == null ? new ObjectMapper() : mapper;
	}

	public static JsonNode get(JsonNode object, String key) {
		if (object != null) {
			return object.get(key);
		} else {
			return null;
		}
	}

	public static JsonNode jsonFromString(String data) {
		try {
			return mapper.readTree(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
