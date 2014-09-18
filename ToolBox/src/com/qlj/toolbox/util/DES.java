package com.qlj.toolbox.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密解密
 * 
 * @author qlj
 * @time 2014年9月18日下午2:37:08
 */
public class DES {

	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * 加密实现方法
	 * 
	 * @param encryptString
	 *            要加密的内容
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);

		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

		return Base64.encode(encryptedData);

	}

	/**
	 * 解密的方法
	 * 
	 * @param decryptString
	 *            要加密的内容
	 * @param decryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptDES(String decryptString, String decryptKey) throws Exception {

		byte[] byteMi = Base64.decode(decryptString);

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);

		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData);

	}

}
