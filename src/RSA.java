import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {

    public ArrayList<BigInteger> blokplain = new ArrayList<>();
    public ArrayList<BigInteger> blokcipher = new ArrayList<>();
    public BigInteger constef = BigInteger.valueOf(3220);
    public BigInteger constn = BigInteger.valueOf(3337);
    public BigInteger constp = BigInteger.valueOf(47);
    public BigInteger constq = BigInteger.valueOf(71);
    public BigInteger kuncipublik;
    public BigInteger kunciprivat;



    public RSA(ArrayList<BigInteger> mplain, ArrayList<BigInteger> mcipher, BigInteger kpublik){
        blokplain = new ArrayList<BigInteger>(mplain);
        blokcipher = new ArrayList<BigInteger>(mcipher);
        kuncipublik = kpublik;
    }

    public void enkripsi(){
        blokcipher = new ArrayList<>();
        for (int i = 0 ; i < blokplain.size() ; i++){
            blokcipher.add(blokplain.get(i).modPow(kuncipublik, constn));
        }
    }

    public void dekripsi(){
        blokplain = new ArrayList<>();
        for (int i = 0 ; i < blokcipher.size() ; i++){
            blokplain.add(blokcipher.get(i).modPow(kunciprivat, constn));
        }
    }

    public void carikunciprivat(){
        BigInteger clone = new BigInteger(kuncipublik.toString());

        BigInteger one = BigInteger.valueOf(1);
        BigInteger zero = BigInteger.valueOf(0);
        BigInteger k = BigInteger.valueOf(0);
        BigInteger[] res;

        res = one.add(k.multiply(constef)).divideAndRemainder(clone);

        while (res[1].compareTo(zero) != 0 ){
            k = k.add(one);
            res = one.add(k.multiply(constef)).divideAndRemainder(clone);
        }

        kunciprivat = new BigInteger(res[0].toString());
    }
}