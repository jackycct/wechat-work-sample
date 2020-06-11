package com.example.wechatwork.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AESKeyGenerator {
    private String ALGORITHM = "AES";
    private final byte[] keyValue;

    public AESKeyGenerator(String algorithm, String key) {
        ALGORITHM = algorithm;
        keyValue = Base64.decodeBase64(key.getBytes());
        System.out.println("base64 decoded key: " + keyValue);
    }

    public String encrypt(String valueToEnc) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);

        System.out.println("valueToEnc.getBytes().length "+valueToEnc.getBytes().length);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        System.out.println("encValue length" + encValue.length);
        byte[] encryptedByteValue = new Base64().encode(encValue);
        String encryptedValue = encryptedByteValue.toString();
        System.out.println("encryptedValue " + encryptedValue);

        return encryptedValue;
    }

    public String decrypt(String encryptedValue) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);

        byte[] decodedValue = new Base64().decode(encryptedValue);
        byte[] decryptedVal = c.doFinal(decodedValue);
        System.out.println("decryptedVal len " + decryptedVal.length);

        return new String(decryptedVal);
    }

    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }
}
