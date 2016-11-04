package com.example.spring.services.utils;

import java.security.*;
import java.io.*;

public class PasswordUtils {
	private String username, password, encryptedPassword;

	public PasswordUtils(String user, String pass) {
		username = user.trim();
		password = pass.trim();
		encryptedPassword = getEncryptedPassword(password);
	}

	public static String getEncryptedPassword(String str) {
		try {
			MessageDigest d = MessageDigest.getInstance("MD5");
			byte b[] = str.getBytes();
			d.update(b);
			return byteArrayToHexString(d.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String byteArrayToHexString(byte[] b) {
		String str = "";
		for (int i = 0; i < b.length; i++) {
			int value = b[i] & 0xFF;
			if (value < 16)
				str += "0";
			str += Integer.toHexString(value);
		}
		return str.toUpperCase();
	}

	private void writeToFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, true)));
			String toWrite = username + "=" + encryptedPassword;
			out.println(toWrite);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
