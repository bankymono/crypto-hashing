package org.bankymono;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger n;
    private SecureRandom random;

    public RSA(){
        this.random = new SecureRandom();
    }

    public void generateKeys(int keyDigits){
        BigInteger p = BigInteger.probablePrime(keyDigits,random);
        BigInteger q = BigInteger.probablePrime(keyDigits,random);

        n = p.multiply(q);

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = generatePublicFactor(phi);

        BigInteger d = e.modInverse(phi);
        this.privateKey = d;
        this.publicKey = e;

    }

    private BigInteger generatePublicFactor(BigInteger phi) {
        BigInteger e = new BigInteger(phi.bitLength(), random);

        while (!e.gcd(phi).equals(BigInteger.ONE))
            e = new BigInteger(phi.bitLength(),random);

        return e;
    }

    public BigInteger encryptMessage(String message){
        return encrypt(publicKey,n,message);
    }

    public String decryptMessage(BigInteger message){
        return decrypt(privateKey,n,message);
    }

    private BigInteger encrypt(BigInteger e,BigInteger n, String message){
        byte[] messageByte = message.getBytes();
        BigInteger messageInt = new BigInteger(messageByte);
        return messageInt.modPow(e,n);
    }

    private String decrypt(BigInteger privateKey, BigInteger n, BigInteger cipherText){
        BigInteger messageInt = cipherText.modPow(privateKey,n);
        return new String(messageInt.toByteArray());
    }




}
