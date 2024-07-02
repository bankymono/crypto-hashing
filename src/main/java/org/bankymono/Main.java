package org.bankymono;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        String text = "The world does not revolve around you";

//        CaesarCipher caesarCipher = new CaesarCipher();
//        String cipherText = caesarCipher.encrypt(text, 3);
//        System.out.println(cipherText);
//        System.out.println(caesarCipher.decrypt(cipherText,3));
        DES des = new DES();

        String text = "Cryptography is important in cryptocurrency";
        String cipherText = des.encrypt(text);

        System.out.println("cipherText->"+cipherText);
        System.out.println("plainText->"+des.decrypt(cipherText));
    }
}