import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {

    public ArrayList<BigInteger> blokplain = new ArrayList<>();
    public ArrayList<BigInteger> blokcipher = new ArrayList<>();
    public BigInteger constef;
    public BigInteger constn;
    public BigInteger constp = new BigInteger("1267650600228229401496703205653");
    public BigInteger constq = new BigInteger("1427247692705959881058285969449495136382746771");
    public BigInteger kuncipublik;
    public BigInteger kunciprivat;



    public RSA(ArrayList<BigInteger> mplain, ArrayList<BigInteger> mcipher, BigInteger kpublik, BigInteger kprivat){
        blokplain = new ArrayList<BigInteger>(mplain);
        blokcipher = new ArrayList<BigInteger>(mcipher);
        constn = constp.multiply(constq);
        constef = constp.subtract(BigInteger.ONE).multiply(constq.subtract(BigInteger.ONE));
        kuncipublik = kpublik;
        kunciprivat = kprivat;
    }

    public void enkripsi(String alamat) throws IOException {
        blokcipher = new ArrayList<>();

        long startTime = System.nanoTime();
        for (int i = 0 ; i < blokplain.size() ; i++){
            blokcipher.add((blokplain.get(i).modPow(kuncipublik, constn).mod(BigInteger.valueOf(256))));
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Durasi Enkripsi = " + (double) totalTime/1000000 + " millisecond");

        byte[] outcipher = new byte[blokcipher.size()];

        for(int i = 0 ; i < blokcipher.size(); i++) {
            outcipher[i] = (byte) blokcipher.get(i).intValue();
        }

        FileOutputStream stream = new FileOutputStream(alamat);
        try {
            stream.write(outcipher);
        } finally {
            stream.close();
        }
    }

    public void dekripsi(String alamat) throws IOException {
        blokplain = new ArrayList<>();

        long startTime = System.nanoTime();
        for (int i = 0 ; i < blokcipher.size() ; i++){
            blokplain.add((blokcipher.get(i).modPow(kunciprivat, constn)).mod(BigInteger.valueOf(256)));
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Durasi Dekripsi = " + (double) totalTime/1000000 + " millisecond");

        byte[] outcipher = new byte[blokplain.size()];

        for(int i = 0 ; i < blokplain.size(); i++) {
            outcipher[i] = (byte) blokplain.get(i).intValue();
        }

        FileOutputStream stream = new FileOutputStream(alamat);
        try {
            stream.write(outcipher);
        } finally {
            stream.close();
        }
    }

    public void carikunciprivat() throws FileNotFoundException {
        kunciprivat = kuncipublik.modInverse(constef);
        try (PrintStream out = new PrintStream(new FileOutputStream("privat.pri"))) {
            out.print(kunciprivat.toString());
        }
    }
}