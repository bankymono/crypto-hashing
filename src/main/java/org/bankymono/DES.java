package org.bankymono;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DES {
    private final SecretKey secretKey;
    private final SecureRandom random;
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;
    private IvParameterSpec ivParams;

    DES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        secretKey = KeyGenerator.getInstance("DES").generateKey();
        random = new SecureRandom();
        encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        byte[] initializationVector = new byte[encryptCipher.getBlockSize()];
        random.nextBytes(initializationVector);
        ivParams = new IvParameterSpec(initializationVector);
        encryptCipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParams);
        decryptCipher.init(Cipher.DECRYPT_MODE,secretKey,ivParams);
    }

    public String encrypt(String plainText) throws IllegalBlockSizeException, BadPaddingException {
        byte[] bytes = plainText.getBytes();
        byte[] cipherText = null;
        cipherText = encryptCipher.doFinal(bytes);

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String cipherText) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] plainText = null;
        plainText = decryptCipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
        return new String(plainText, StandardCharsets.UTF_8);
    }
}
