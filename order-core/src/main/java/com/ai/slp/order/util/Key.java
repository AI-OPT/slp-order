package com.ai.slp.order.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.changhong.upp.crypto.rsa.KeyReader;
import com.changhong.upp.exception.UppException;

public class Key {
	
	private Map<KeyType, String> keyMap = new HashMap<KeyType, String>();
	
	public Key(String privateKeyPath, String privateKeyPwd, String publicKeyPath) throws UppException, IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		org.springframework.core.io.Resource resource = resourceLoader.getResource(privateKeyPath);
		String privateKey = KeyReader.readPrivateKey(resource.getFile().getPath(), privateKeyPwd);
		resource = resourceLoader.getResource(publicKeyPath);
		String publicKey = KeyReader.readPublicKey(resource.getFile().getPath());
		keyMap.put(KeyType.PRIVATE_KEY, privateKey);
		keyMap.put(KeyType.PUBLIC_KEY, publicKey);
	}
	
	public String getKey(KeyType keyType){
		return keyMap.get(keyType);
	}
}
