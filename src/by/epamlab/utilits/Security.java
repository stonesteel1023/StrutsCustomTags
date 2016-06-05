package by.epamlab.utilits;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
	public static String criptPass(String password) {
		if(password == null || password.equals("")) {
			return "";
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(md.digest(password.getBytes()));
	}
}
