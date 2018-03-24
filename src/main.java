import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    ArrayList<BigInteger> blokplain = new ArrayList<>();
    ArrayList<BigInteger> blokcipher = new ArrayList<>();
    public byte[] plainfile; // Byte-byte bacaan plaintext dari file input.txt
    public byte[] cipherfile; // Byte-byte bacaan ciphertext dari file output.txt
    public String skpublik; // String bacaan kunci publik dari file publik.pub
    public String skprivat; // String bacaan kunci privar dari file privat.pri

    public static void main(String[] args) throws IOException {


        main tes = new main();

        System.out.println("RSA ENKRIPSI");
        tes.enkripsiRSA();

        System.out.println("=================================================================");
        System.out.println("RSA DEKRIPSI");
        tes.dekripsiiRSA();
    }

    public void enkripsiRSA() throws IOException {
        BigInteger kpub;
        int pilihaninputkunci = 1;

        if (pilihaninputkunci == 1){
            bacakuncipublik();
            kpub = new BigInteger(skpublik);
        }else{
            Scanner sc = new Scanner(System.in);
            String sin = sc.nextLine();
            kpub = new BigInteger(sin);
        }

        bacaplain(); // Baca plaintext

        RSA mrsa = new RSA(blokplain,new ArrayList<BigInteger>(), kpub, null);
        System.out.println("Kunci Publik = " + mrsa.kuncipublik.toString());

        mrsa.carikunciprivat();
        mrsa.enkripsi();
        System.out.println("Hasil Enkripsi");
        printblok(mrsa.blokcipher);
    }

    public void dekripsiiRSA() throws IOException {
        BigInteger kpri;
        int pilihaninputkunci = 1;

        if (pilihaninputkunci == 1){
            bacakunciprivat();
            kpri = new BigInteger(skprivat);
        }else{
            Scanner sc = new Scanner(System.in);
            String sin = sc.nextLine();
            kpri = new BigInteger(sin);
        }
        bacacipher(); // Baca ciphertext

        RSA mrsa = new RSA(new ArrayList<BigInteger>(), blokcipher, null, kpri);
        System.out.println("Kunci Privat = " + mrsa.kunciprivat.toString());

        mrsa.dekripsi();
        System.out.println("Hasil Dekripsi");
        printblok(mrsa.blokplain);
    }

    public void bacakuncipublik() throws IOException {
        byte[] bkpublik= Files.readAllBytes(Paths.get("publik.pub"));
        skpublik = new String(bkpublik, "UTF-8");
    }

    public void bacakunciprivat() throws IOException {
        byte[] bkprivat= Files.readAllBytes(Paths.get("privat.pri"));
        skprivat= new String(bkprivat, "UTF-8");
    }
    public void bacaplain() throws IOException {
        // Dengan ukuran tiap blok adalah 1 byte ( skala 0 sampai 255)
        plainfile = Files.readAllBytes(Paths.get("input.txt"));
        for (byte b : plainfile) {
            blokplain.add(BigInteger.valueOf(b));
//            System.out.print(String.format("%02X", b));
//            System.out.print(", ");
        }
//        System.out.println();
    }
    public void bacacipher() throws IOException {
        // Asumsi tidak ada blok dengan nilai 256 257 258
        cipherfile = Files.readAllBytes(Paths.get("output.txt"));
        for (byte b : cipherfile) {
            if(b < 0){
                blokcipher.add(BigInteger.valueOf(256).add(BigInteger.valueOf(b)));
            }else{
                blokcipher.add(BigInteger.valueOf(b));
            }
//            System.out.print(String.format("%02X", b));
//            System.out.print(", ");
        }
//        System.out.println();
    }

    public void printblok(ArrayList<BigInteger> al){
        for (int i = 0; i < al.size(); i++) {
//            System.out.print(al.get(i).toString());
//            System.out.print("(");
            System.out.print(String.format("%02X", al.get(i)));
            System.out.print(", ");
        }
        System.out.println();
    }

    public void list4faktor(){
        for (int i = 0 ; i < 1000 ; i++){
            if (hitungfaktor(i) == 4){
                System.out.println(i);
            }
        }
    }

    public int hitungfaktor(int a){
        int count = 0;
        for (int i = 1; i <= a; i++) {
            if (a%i == 0){
                count++;
            }
        }
        return count;
    }
}
