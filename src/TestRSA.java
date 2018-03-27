import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TestRSA {

    ArrayList<BigInteger> blokplain = new ArrayList<>();
    ArrayList<BigInteger> blokcipher = new ArrayList<>();
    public String fileinput;
    public String fileoutput;
    public byte[] plainfile; // Byte-byte bacaan plaintext dari file input.txt
    public byte[] cipherfile; // Byte-byte bacaan ciphertext dari file output.txt
    public String skpublik; // String bacaan kunci publik dari file publik.pub
    public String skprivat; // String bacaan kunci privar dari file privat.pri

    public static void main(String[] args) throws IOException {
        TestRSA tes = new TestRSA();
        tes.mainprogram();
    }

    public void mainprogram() throws IOException {
        System.out.println("File yang akan dienkripsi/dekripsi :");
        Scanner sc = new Scanner(System.in);
        fileinput = sc.nextLine();
        System.out.println("File output setelah melakukan enkripsi / dekripsi:");
        sc = new Scanner(System.in);
        fileoutput= sc.nextLine();

        System.out.println("Pilihan Aksi :");
        System.out.println("1. Enkripsi");
        System.out.println("2. Dekripsi");
        sc = new Scanner(System.in);
        int pilihanaksi = sc.nextInt();
        if (pilihanaksi == 1){
            bacaplain();
            System.out.println("Pilihan Masukan Kunci Enkripsi :");
            System.out.println("1. Papan Ketik");
            System.out.println("2. File Eksternal");
            sc = new Scanner(System.in);
            int pilihanmasukan = sc.nextInt();
            BigInteger kpub;
            if(pilihanmasukan == 1){
                System.out.println("Kunci Enkripsi :");
                sc = new Scanner(System.in);
                skpublik = sc.nextLine();
                kpub = new BigInteger(skpublik);
            }else{
                System.out.println("File kunci publik :");
                sc = new Scanner(System.in);
                String alamat = sc.nextLine();
                bacakuncipublik(alamat);
                kpub = new BigInteger(skpublik);
            }

            RSA mrsa = new RSA(blokplain,new ArrayList<BigInteger>(), kpub, null);
            System.out.println("Kunci Publik = " + mrsa.kuncipublik.toString());

            mrsa.carikunciprivat();

            System.out.println("Kunci Privat = " + mrsa.kunciprivat.toString());
            System.out.println("Kunci Privat telah di simpan di file '" + fileoutput + "'");
            mrsa.enkripsi(fileoutput);
            System.out.println("Hasil Enkripsi :");
            printblok(mrsa.blokcipher);

        }else{
            bacacipher();
            System.out.println("Pilihan Masukan Kunci Dekripsi :");
            System.out.println("1. Papan Ketik");
            System.out.println("2. File Eksternal");
            sc = new Scanner(System.in);
            int pilihanmasukan = sc.nextInt();
            BigInteger kpri;
            if(pilihanmasukan == 1){
                System.out.println("Kunci Dekripsi:");
                sc = new Scanner(System.in);
                skprivat = sc.nextLine();
                kpri = new BigInteger(skprivat);
            }else{
                System.out.println("File kunci privat :");
                sc = new Scanner(System.in);
                String alamat = sc.nextLine();
                bacakunciprivat(alamat);
                kpri = new BigInteger(skprivat);
            }

            RSA mrsa = new RSA(new ArrayList<BigInteger>(), blokcipher, null, kpri);
            System.out.println("Kunci Privat = " + mrsa.kunciprivat.toString());
            mrsa.dekripsi(fileoutput);
            System.out.println("Hasil Dekripsi :");
            printblok(mrsa.blokplain);
        }
    }

    public void bacakuncipublik(String alamat) throws IOException {
        byte[] bkpublik= Files.readAllBytes(Paths.get(alamat));
        skpublik = new String(bkpublik, "UTF-8");
    }

    public void bacakunciprivat(String alamat) throws IOException {
        byte[] bkprivat= Files.readAllBytes(Paths.get(alamat));
        skprivat= new String(bkprivat, "UTF-8");
    }

    public void bacaplain() throws IOException {
        // Dengan ukuran tiap blok adalah 1 byte ( skala 0 sampai 255)
        plainfile = Files.readAllBytes(Paths.get(fileinput));
        for (byte b : plainfile) {
            blokplain.add(BigInteger.valueOf(b));
        }
    }

    public void bacacipher() throws IOException {
        // Asumsi tidak ada blok dengan nilai 256 257 258
        cipherfile = Files.readAllBytes(Paths.get(fileinput));
        for (byte b : cipherfile) {
            if(b < 0){
                blokcipher.add(BigInteger.valueOf(256).add(BigInteger.valueOf(b)));
            }else{
                blokcipher.add(BigInteger.valueOf(b));
            }
        }
    }

    public void printblok(ArrayList<BigInteger> al){
        for (int i = 0; i < al.size(); i++) {
            if(i % 30 == 0){
                System.out.println();
            }
            System.out.print(String.format("%02X", al.get(i)));
            System.out.print(" ");
        }
        System.out.println();
    }
}
