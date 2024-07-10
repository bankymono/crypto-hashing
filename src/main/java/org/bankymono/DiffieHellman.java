package org.bankymono;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
    private Random random;

    public DiffieHellman() {
        this.random = new Random();
    }

    public void generatePrivateKeys(BigInteger n, BigInteger g){
        int rand1 = random.nextInt(n.intValue()-2) + 1;
        BigInteger x = new BigInteger(Integer.toString(rand1));

        int rand2 = random.nextInt(n.intValue()-2) + 1;
        BigInteger y = new BigInteger(Integer.toString(rand2));
    }
}
