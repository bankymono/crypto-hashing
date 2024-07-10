package org.bankymono;

public class ModularInverse {
    public int calculate(int a, int m){
        for(int inv=0; inv < m;++inv)
            if((a*inv)%m == 1)
                return inv;

        return -1;
    }
}
