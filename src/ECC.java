import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.CurveEquation;
import util.Point;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ECC extends BaseAsymetricCrypt{
    CurveEquation curveEquation;
    ObjectMapper mapper = new ObjectMapper();
    int dictK = 5;
    int privateKey = 5;
    Point publicKey,base;
    List<Point> listPoints;
    HashMap<Point,Byte> mapPoints;

    public ECC(BigInteger a, BigInteger b,BigInteger p) throws Exception {
        curveEquation = new CurveEquation(a,b,p);
        List<Point> points = curveEquation.getAllPossiblePoints();
        if(points.size()<256){
            throw new Exception("Possible points less than 256");
        }
        buildDictionary(points);
    }

    private void buildDictionary(List<Point> points){
//        listPoints = new ArrayList<>();
        mapPoints = new HashMap<>();
//        HashMap<Integer,List<Integer>> temp = new HashMap<>();
//        for(Point p:points){
//            if(temp.containsKey(p.x)){
//                temp.get(p.x).add(p.y);
//            } else{
//                List<Integer> l = new ArrayList<>();
//                l.add(p.y);
//                temp.put(p.x,l);
//            }
//        }
//        for(int i=0;i<256;++i){
//            int x = i*dictK+1;
//            while(!temp.containsKey(x)){
//                ++x;
//                if(x>temp.size()){
//                    x=0;
//                }
//            }
//            Point p = new Point(x,temp.get(x).get(0));
//            listPoints.add(i,p);
//            mapPoints.put(p, (byte) i);
//        }
        listPoints = new ArrayList<>(points);
        for(int i=0;i<256;i++){
            mapPoints.put(listPoints.get(i), (byte) i);
        }
    }

    @Override
    public byte[] encrypt(byte[] in){
        List<Point> plainPoints = new ArrayList<>();
        for(byte b:in){
            plainPoints.add(listPoints.get(b));
        }
        List<Point> encrypted = encryptPoints(plainPoints);
        byte[] ret = new byte[encrypted.size()];
        for(int i=0;i<ret.length;i++){
            ret[i] = mapPoints.get(encrypted.get(i));
        }
        return ret;
    }

    public List<Point> encryptPoints(List<Point> points){
        Random rand = new Random();
        int k = rand.nextInt(1)+1;
        List<Point> ret = new ArrayList<>();
        ret.add(curveEquation.multiplyPoint(base,k));
        for(Point p : points){
            Point ptemp = curveEquation.multiplyPoint(publicKey,k);
            Point pres = curveEquation.addPoint(p,ptemp);
            ret.add(pres);
        }
        return ret;
    }

    public List<Point> encryptPoint(Point in){
        Random rand = new Random();
        int k = rand.nextInt(curveEquation.p.intValue()/10-2);
        List<Point> ret = new ArrayList<>();
        ret.add(0,curveEquation.multiplyPoint(base,k));
        Point ptemp = curveEquation.multiplyPoint(publicKey,k);
        Point pres = curveEquation.addPoint(in,ptemp);
        ret.add(1,pres);
        return ret;
    }

    @Override
    public byte[] decrypt(byte[] in) {
        List<Point> cipherPoints = new ArrayList<>();
        for(int i=0;i<in.length;i++){
            System.out.println(String.format("%d %d",i,in[i]));
            cipherPoints.add(i,listPoints.get((in[i])&0xFF));
        }
        List<Point> decripted = decryptPoints(cipherPoints);
        byte[] ret = new byte[decripted.size()];
        for(int i=0;i<decripted.size();++i){
            ret[i] = mapPoints.get(decripted.get(i));
        }
        return ret;
    }

    public List<Point> decryptPoints(List<Point> in){
        Point k = curveEquation.multiplyPoint(in.get(0),this.privateKey);
        List<Point> pres = new ArrayList<>();
        for(int i=1;i<in.size();i++){
            pres.add(curveEquation.subtractPoint(in.get(i),k));
        }
        return pres;
    }

    public Point decryptPoint(List<Point> in){
        Point ptemp = curveEquation.multiplyPoint(in.get(0),this.privateKey);
        Point pres = curveEquation.subtractPoint(in.get(1),ptemp);
        return pres;
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
