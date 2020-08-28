package fr.entasia.apis.socket;

import fr.entasia.apis.utils.TextUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class SocketSecurity {


	public final static SecureRandom rd = new SecureRandom();
	public static byte[] secret;

	public static String signMsg(String msg){ // JUSTE LA SIGNATURE, PAS LE MESSAGE
		byte[] salt = new byte[16];
		rd.nextBytes(salt);
		return signMsg(msg, TextUtils.bytesToHex(salt));
	}

	public static String signMsg(String msg, String hexSalt){
		MessageDigest SHA256;
		try {
			SHA256 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		SHA256.update(secret);
		SHA256.update(hexSalt.getBytes(StandardCharsets.UTF_8));
		SHA256.update(msg.getBytes(StandardCharsets.UTF_8));

		byte[] hash = SHA256.digest();

		return b64(hash)+";"+hexSalt;

	}

	public static boolean verifyMsg(String signed){
		String[] list = signed.split(" ");
		String signature = list[0];
		return verifyMsg(String.join(" ", Arrays.copyOfRange(list , 1, list.length)), signature);
	}

	public static boolean verifyMsg(String msg, String signature){
//		System.out.println(signature);
		String[] list = signature.split(";");
		return signature.equals(signMsg(msg, list[1]));
	}

//	public static void main(String[] fa){
//		String msg = "Salut !";
//		String signature = signMsg(msg);
//
//		System.out.println(signature);
//		if(verifyMsg(msg, signature)){
//			System.out.println("vérifié");
//		}else System.out.println("invalidé");
//	}

	private static String b64(byte[] hash) {
		return Base64.getEncoder().encodeToString(hash);
	}

}
