import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class main {

    public byte[] plainfile; // Byte-byte bacaan plaintext dari file input.txt
    public String skpublik; // String bacaan kunci publik dari file publik.pub
    public String skprivat; // String bacaan kunci privar dari file privat.pri

    public static void main(String[] args) throws IOException {

        main tes = new main();

        ArrayList<BigInteger> blokplain = new ArrayList<>();
        ArrayList<BigInteger> blokcipher = new ArrayList<>();

        blokplain.add(BigInteger.valueOf(726));
        blokplain.add(BigInteger.valueOf(582));
        blokplain.add(BigInteger.valueOf(733));
        blokplain.add(BigInteger.valueOf(273));
        blokplain.add(BigInteger.valueOf(787));
        blokplain.add(BigInteger.valueOf(003));

        tes.bacafile(); // Baca plaintext, k publik ,k privat

        RSA mrsa = new RSA(blokplain,blokcipher, new BigInteger(tes.skpublik));
        System.out.println("Kunci Publik = " + mrsa.kuncipublik.toString());

        mrsa.carikunciprivat();
        System.out.println("Kunci Privat = " + mrsa.kunciprivat.toString());

        mrsa.enkripsi();
        System.out.println("Hasil Enkripsi");
        tes.printblok(mrsa.blokcipher);


        mrsa.dekripsi();
        System.out.println("Hasil Dekripsi");
        tes.printblok(mrsa.blokplain);

    }

    public void bacafile() throws IOException {
        plainfile = Files.readAllBytes(Paths.get("input.txt"));

        // Plain file belum diolah


        byte[] bkpublik= Files.readAllBytes(Paths.get("publik.pub"));
        byte[] bkprivat= Files.readAllBytes(Paths.get("privat.pri"));

        skpublik = new String(bkpublik, "UTF-8");
        skprivat= new String(bkprivat, "UTF-8");
    }

    public void printblok(ArrayList<BigInteger> al){
        for (int i = 0; i < al.size(); i++) {
            System.out.print(al.get(i).toString());
            System.out.print(", ");
        }
        System.out.println();
    }
}
