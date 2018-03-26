package util;

public class Util {
    public static boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    public static int modInverse(int a, int m)
    {
        a = a % m;
        for (int x = 1; x < m; x++)
            if (Math.floorMod((a * x) , m) == 1)
                return x;
        return 1;
    }
}
