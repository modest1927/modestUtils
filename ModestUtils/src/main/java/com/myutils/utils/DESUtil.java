package com.myutils.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

//@RemoteProxy(name="DESUtil")
public class DESUtil {

	public static String encryStr(String str) {
		try {
			SecretKey deskey = null;
			InputStream fis  = null;
			GZIPInputStream gis = null;
			ObjectInputStream ois = null;
			FileOutputStream fos =null;
			GZIPOutputStream gos =null;
			ObjectOutputStream oos =null;
			try {
				fis = DESUtil.class.getResourceAsStream("/key.dat");
				gis = new GZIPInputStream(fis);
				ois = new ObjectInputStream(gis);
				deskey = (SecretKey) ois.readObject();
				
			} catch (Exception e) {
				KeyGenerator keygen = KeyGenerator.getInstance("DES");
				deskey = keygen.generateKey();
				fos = new FileOutputStream(DESUtil.class
						.getResource("/").getPath().toString().concat(
								"/key.dat"));
				gos = new GZIPOutputStream(fos);
				oos = new ObjectOutputStream(gos);
				oos.writeObject(deskey);				
			} finally{
				if(ois!=null){
					ois.close();
				}
				if(oos!=null){
					oos.close();
				}
				if(gos!= null){
					gos.close();
				}
				if(fos!= null){
					fos.close();
				}
				if(gis!= null){
					gis.close();
				}
				if(fis!= null){
					fis.close();
				}
				
			}
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] cipherByte = c1.doFinal(str.getBytes());
			BASE64Encoder en = new BASE64Encoder();
			String s = en.encode(cipherByte);
			str = s;
		} catch (Exception e) {
			e.printStackTrace();
			str = "";
		}
		return str;
	}

	public static String decryStr(String str) {
		InputStream fis =null;
		GZIPInputStream gis =null;
		ObjectInputStream ois =null;
		try {
			fis = DESUtil.class.getResourceAsStream("/key.dat");
			gis = new GZIPInputStream(fis);
			ois = new ObjectInputStream(gis);
			SecretKey deskey = (SecretKey) ois.readObject();
			//ois.close();
			BASE64Decoder de = new BASE64Decoder();
			byte[] cipherByte = de.decodeBuffer(str);
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] clearByte = c1.doFinal(cipherByte);
			String s = new String(clearByte);
			str = s;
		} catch (Exception e) {
			e.printStackTrace();
			str = "";
		} finally{
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			if(gis!= null){
				try {
					gis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fis!= null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return str;
	}


}