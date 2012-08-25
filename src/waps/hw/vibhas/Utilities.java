package waps.hw.vibhas;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.http.HttpSession;

public class Utilities {

	/*
	 * The code below is taken from
	 * http://stackoverflow.com/questions/415953/generate-md5-hash-in-java
	 */
	static public String getHash(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(input.getBytes());
			byte[] digest = messageDigest.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static public Boolean checkSessionForUser(HttpSession session)
	{
		Object id = session.getAttribute("id");
		String name = (String) session.getAttribute("name");

		if (id == null || name == null) {
			session.invalidate();
			return false;
		}
		
		else
			return true;
	}
}
