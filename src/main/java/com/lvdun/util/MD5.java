package com.lvdun.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * md5加密字符串
 */
public class MD5 {

	public static final String MD5(String s)
	{
		char hexDigits[] = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'a', 'b', 'c', 'd', 'e', 'f'
			};
		char str[] = null;
		byte strTemp[] = s.getBytes();
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return new String(str);
	}
	
	public static final String MD516(String s)
	{	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if(i<0) i+= 256;
			if(i<16)
			buf.append("0");
			buf.append(Integer.toHexString(i));
			}
			return new String(buf.toString().substring(8,24));
			} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			} 
			return null;
	}
	
	public static final String MD524(String s)
	{	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if(i<0) i+= 256;
			if(i<16)
			buf.append("0");
			buf.append(Integer.toHexString(i));
			}
			return new String(buf.toString().substring(8,32));
			} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			} 
			return null;
	}


}
