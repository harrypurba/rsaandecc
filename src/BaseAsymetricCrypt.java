import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseAsymetricCrypt {
    public abstract byte[] encrypt(byte[] in);
    public abstract byte[] decrypt(byte[] in);

    public abstract void generateKey();

    public abstract void parsePublicKey(String publicKey);
    public abstract void parsePrivateKey(String privateKey);

    public abstract String getPublicString();
    public abstract String getPrivateString();

    public void writeKey(String path){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path+".pub"))) {
            bw.write(getPublicString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(getPrivateString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readKey(String path) throws IOException {
        byte[] content= Files.readAllBytes(Paths.get(path));
        if(path.substring(path.length()-4).equals(".pub")){
            parsePublicKey(new String(content, "UTF-8"));
        } else {
            parsePrivateKey(new String(content,"UTF-8"));
        }
    }
}
