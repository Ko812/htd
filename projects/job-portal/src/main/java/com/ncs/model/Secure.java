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
	
	public static final String digits = "0123456789";
	public static final String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String smallLetters = "abcdefghijklmnopqrstuvwxyz";
	
	public static boolean validatePassword(String pwd) {
		if(pwd.length() > 24) {
			// Too long
			return false;
		}
		if(pwd.length() < 8) {
			// Too short
			return false;
		}
		if(hasNoDigit(pwd)) {
			// Does not contain at least 1 numeric character
			return false;
		}
		if(hasNoCapitalLetter(pwd)) {
			// Does not contain at least 1 capital letter
			return false;
		}
		if(hasNoSmallLetter(pwd)) {
			// Does not contain at least 1 small letter
			return false;
		}
		return true;
	}
	
	public static boolean hasNoDigit(String str) {
		for(int i =0;i < digits.length();i++) {
			if(str.contains(digits.charAt(i) + "")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasNoCapitalLetter(String str) {
		for(int i =0;i < capitalLetters.length();i++) {
			if(str.contains(capitalLetters.charAt(i) + "")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasNoSmallLetter(String str) {
		for(int i =0;i < smallLetters.length();i++) {
			if(str.contains(smallLetters.charAt(i) + "")) {
				return false;
			}
		}
		return true;
	}
}
