package ras.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptDecrypt {
	/*
		g	0x67
		r	0x72
		i	0x69
		d	0x64
		s	0x73
		t	0x74
		a	0x61
		t	0x74
		1	0x31
		2	0x32
		5	0x35
		@	0x40
		R	0x52
		A	0x41
		S	0x53
		#	0x23
	*/
	
	private static byte[] key = {
		0x67, 0x72, 0x69, 0x64, 0x73, 0x74, 0x61, 0x74, 0x31, 0x32, 0x35, 0x40, 0x52, 0x41, 0x53, 0x23
	};
	
	// build the initialization vector.  This example is all zeros, but it 
    // could be any value or generated using a random number generator.
    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    IvParameterSpec ivspec = new IvParameterSpec(iv);
	
	public String encryptMsg(String message){
		try{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			final String encryptedMsg = DatatypeConverter.printBase64Binary(cipher.doFinal(message.getBytes()));
			
			return encryptedMsg;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String decryptMsg(String message){
		try{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			final String decryptedMsg = new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(message)));
			
			return decryptedMsg;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void main(String[] args){
		/*EncryptDecrypt ede = new EncryptDecrypt();
		String message = ede.encryptMsg("gridStat#125@RAS");
		System.out.println(message);
		message = ede.decryptMsg(message);
		System.out.println(message);*/
	}
}
