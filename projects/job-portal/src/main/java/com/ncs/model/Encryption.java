package com.ncs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Random;

public class Encryption {
	static public String encrypt(String s) {
		String encrypted = "";
		for(int i = 0; i < s.length(); i++) {
			int cp = s.codePointAt(i);
			encrypted = encrypted.concat(cp + "");
		}
		int length = min(encrypted.length(), 48);
		return encrypted.substring(0, length);
	}
	
	void encrypt2(String s) {
		char c = 'c';
		Character ch = Character.valueOf(c);
	}
	
	static int min (int n1, int n2) {
		if(n1 < n2) {
			return n1;
		}
		else {
			return n2;
		}
	}
	
	final static public String charString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	
	static public String generateSessionCode() {
		int charStringLength = charString.length();
		String sessionCode = "";
		LocalDate now = LocalDate.now();
		Long offset = now.toEpochSecond(LocalTime.now(), ZoneOffset.ofHours(8));
		Random rand = new Random(offset);
		for(int i = 0; i < 20; i++) {
			sessionCode = sessionCode.concat(charString.charAt(rand.nextInt(charStringLength)) + "");
		}
		return sessionCode;
	}
}
