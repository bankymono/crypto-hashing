package org.bankymono;

public class GCD {
    public int gcd_recursion(int a, int b) {
        if(a%b==0) return b;

        return gcd_recursion(b,a%b);
    }
}
