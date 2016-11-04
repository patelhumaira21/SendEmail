package com.example.spring.services.api;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring.services.utils.PasswordUtils;

public class AuthenticationApi {
	private static String encryptedPassword;
	private static final String PASSWORD_FILE = "passwords.dat";
	private String username;
	private String password;
	private static Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationApi.class);

	public AuthenticationApi() {
	}

	public AuthenticationApi(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String validateUser() {
		encryptedPassword = PasswordUtils.getEncryptedPassword(password);
		Vector<String> userData = new Vector<String>();
		ClassLoader classLoader = AuthenticationApi.class.getClassLoader();
		File file = new File(classLoader.getResource(PASSWORD_FILE).getFile());
		String data;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((data = br.readLine()) != null)
				userData.add(data);
			br.close();
		} catch (Exception e) {
			LOGGER.error("Exception caught while authentication");
			return "NO";
		}
		for (int i = 0; i < userData.size(); i++) {
			String tmp = userData.elementAt(i);
			StringTokenizer st = new StringTokenizer(tmp, "=", false);
			String fileUsername = st.nextToken();
			String filePassword = st.nextToken();
			if (fileUsername.equals(username)
					&& filePassword.equals(encryptedPassword)) {
				return "YES";
			}
		}
		return "NO";
	}

}
