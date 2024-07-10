package org.bankymono;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import java.util.HexFormat;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        String text = "The world does not revolve around you";

//        CaesarCipher caesarCipher = new CaesarCipher();
//        String cipherText = caesarCipher.encrypt(text, 3);
//        System.out.println(cipherText);
//        System.out.println(caesarCipher.decrypt(cipherText,3));
//        DES des = new DES();

//        AES aes = new AES();
//        String text = "Cryptography is important in cryptocurrency";
//        String cipherText = des.encrypt(text);
//        String cipherText = aes.encrypt(text);

//        System.out.println("cipherText->"+cipherText);
//        System.out.println("plainText->"+aes.decrypt(cipherText));

//        GCD gcd = new GCD();
//        System.out.println(gcd.gcd_recursion(21,11));

//        ModularInverse algorithm = new ModularInverse();

        RSA rsa = new RSA();
        rsa.generateKeys(1024);

//        BigInteger cipher = rsa.encryptMessage(text);
//        System.out.println(cipher);
//        System.out.println(rsa.decryptMessage(cipher));
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

//        System.out.println("keyPriv->"+privateKey);
//        System.out.println("keyPub->"+publicKey);

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey sessionKey = keyGenerator.generateKey();

        System.out.println(HexFormat.of().formatHex(sessionKey.getEncoded()));

        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        AES aesEncrypt = new AES(sessionKey,iv);

        String cipherText = aesEncrypt.encrypt(text);
        System.out.println("cipher ->"+cipherText);
//        System.out.println(algorithm.calculate(12,31));

        Cipher encryptionCipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
        OAEPParameterSpec spec = new OAEPParameterSpec("SHA-256","MGF1",
                MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        encryptionCipher.init(Cipher.ENCRYPT_MODE,publicKey,spec);

        byte[] encryptedSessionKey = encryptionCipher.doFinal(sessionKey.getEncoded());
        System.out.println(HexFormat.of().formatHex(encryptedSessionKey));

        Cipher decryptionCipher = Cipher
                .getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
        OAEPParameterSpec spec2 = new OAEPParameterSpec("SHA-256","MGF1",
                MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);

        decryptionCipher.init(Cipher.DECRYPT_MODE,privateKey,spec2);

        byte [] decryptedKey = decryptionCipher.doFinal(encryptedSessionKey);
        System.out.println(HexFormat.of().formatHex(decryptedKey));

        SecretKey decryptedSessionKey = new SecretKeySpec(decryptedKey,0,decryptedKey.length,"AES");
        AES decryptAES = new AES(decryptedSessionKey, iv);

        System.out.println(decryptAES.decrypt(cipherText));

    }
}