import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {

    public ArrayList<BigInteger> blokplain = new ArrayList<>();
    public ArrayList<BigInteger> blokcipher = new ArrayList<>();
    public BigInteger constef = BigInteger.valueOf(216);
    public BigInteger constn = BigInteger.valueOf(259);
    public BigInteger constp = BigInteger.valueOf(7);
    public BigInteger constq = BigInteger.valueOf(37);
    public BigInteger kuncipublik;
    public BigInteger kunciprivat;



    public RSA(ArrayList<BigInteger> mplain, ArrayList<BigInteger> mcipher, BigInteger kpublik, BigInteger kprivat){
        blokplain = new ArrayList<BigInteger>(mplain);
        blokcipher = new ArrayList<BigInteger>(mcipher);
        kuncipublik = kpublik;
        kunciprivat = kprivat;
    }

    public void enkripsi() throws IOException {
        blokcipher = new ArrayList<>();

        long startTime = System.nanoTime();
        for (int i = 0 ; i < blokplain.size() ; i++){
            blokcipher.add(blokplain.get(i).modPow(kuncipublik, constn));
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Durasi Enkripsi = " + (double) totalTime/1000000 + " millisecond");

        byte[] outcipher = new byte[blokcipher.size()];

        for(int i = 0 ; i < blokcipher.size(); i++) {
            outcipher[i] = (byte) blokcipher.get(i).intValue();
        }

        FileOutputStream stream = new FileOutputStream("output.txt");
        try {
            stream.write(outcipher);
        } finally {
            stream.close();
        }
    }

    public void dekripsi(){
        blokplain = new ArrayList<>();

        long startTime = System.nanoTime();
        for (int i = 0 ; i < blokcipher.size() ; i++){
            blokplain.add(blokcipher.get(i).modPow(kunciprivat, constn));
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Durasi Dekripsi = " + (double) totalTime/1000000 + " millisecond");
    }

    public void carikunciprivat() throws FileNotFoundException {
        kunciprivat = kuncipublik.modInverse(constef);
        try (PrintStream out = new PrintStream(new FileOutputStream("privat.pri"))) {
            out.print(kunciprivat.toString());
        }
    }
}