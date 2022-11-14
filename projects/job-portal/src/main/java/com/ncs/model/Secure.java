package com.ncs.model;

public class Secure {
	String enc(String s) {
		String encrypted = "";
		for(int i = 0; i < s.length(); i++) {
			int cp = s.codePointAt(i);
			encrypted = encrypted.concat(cp + "");
		}
		return encrypted;
	}
	
	void encrypt2(String s) {
		char c = 'c';
		Character ch = Character.valueOf(c);
	}
}
