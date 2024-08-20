package com.worldflower.utils;

import java.security.SecureRandom;

public class createVerificationCode {
	
	 private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	 
	 
	 public String createVerificationCodeGet(int codeLength) {
		 	SecureRandom random = new SecureRandom();
	        StringBuilder code = new StringBuilder(codeLength);

	        for (int i = 0; i < codeLength; i++) {
	            int index = random.nextInt(CHARACTERS.length());
	            code.append(CHARACTERS.charAt(index));
	        }

	        return code.toString();
	 }
	 

	  

}
