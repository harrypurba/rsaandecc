import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.CurveEquation;
import util.Point;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ECC extends BaseAsymetricCrypt{
    CurveEquation curveEquation;
    ObjectMapper mapper = new ObjectMapper();
    int privateKey = 5;
    Point publicKey,base;

    public ECC(int a, int b,int p) throws Exception {
        curveEquation = new CurveEquation(a,b,p);
    }

    @Override
    public byte[] encrypt(byte[] in){
        return null;
    }

    public List<Point> encryptPoint(Point in){
        Random rand = new Random();
        int k = rand.nextInt(curveEquation.p-2)+1;
        List<Point> ret = new ArrayList<>();
        ret.add(0,curveEquation.multiplyPoint(base,k));
        Point ptemp = curveEquation.multiplyPoint(publicKey,k);
        Point pres = curveEquation.addPoint(in,ptemp);
        ret.add(1,pres);
        return ret;
    }

    public Point decryptPoint(List<Point> in){
        Point ptemp = curveEquation.multiplyPoint(in.get(0),this.privateKey);
        Point pres = curveEquation.subtractPoint(in.get(1),ptemp);
        return pres;
    }

    @Override
    public byte[] decrypt(byte[] in) {
        return new byte[0];
    }

    @Override
    public void generateKey() {
        base = curveEquation.getAllPossiblePoints().get(0);
        publicKey = curveEquation.multiplyPoint(base,privateKey);
    }

    @Override
    public void parsePublicKey(String publicKey) {
        try {
            this.publicKey = mapper.readValue(publicKey,Point.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parsePrivateKey(String privateKey) {
        this.privateKey = Integer.parseInt(privateKey);
    }

    @Override
    public String getPublicString() {
        try {
            return mapper.writeValueAsString(publicKey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPrivateString() {
        try {
            return mapper.writeValueAsString(privateKey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
