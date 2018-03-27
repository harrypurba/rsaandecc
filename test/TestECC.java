import org.junit.Test;

import util.CurveEquation;
import util.Point;
import util.Util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TestECC {
//    @Test
//    public void testPointAddition(){
//        try {
//            ECC ecc = new ECC();
//            Point p = new Point(-1.155,1.755);
//            Point x = ecc.addPoint(p,p);
//            Point xr = ecc.addPoint(p,x);
//            System.out.println(xr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testCountPoints(){
        try {
            CurveEquation curveEquation = new CurveEquation(new BigInteger("18"), new BigInteger("53"), new BigInteger("257"));
            System.out.println(curveEquation.getPointsCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPoints(){
        try {
            CurveEquation curveEquation = new CurveEquation(new BigInteger("18"), new BigInteger("53"), new BigInteger("257"));
            List<Point> points = curveEquation.getAllPossiblePoints();
            System.out.println(points);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pointAddition(){
        try {
            CurveEquation curveEquation = new CurveEquation(new BigInteger("9"), new BigInteger("7"), new BigInteger("2011"));
            Point r = curveEquation.addPoint(new Point(new BigInteger("2"), new BigInteger("523")),new Point(new BigInteger("2"), new BigInteger("523")));
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pointSubtraction(){
        try {
            CurveEquation curveEquation = new CurveEquation(new BigInteger("1"), new BigInteger("6"), new BigInteger("11"));
            Point r = curveEquation.subtractPoint(new Point(new BigInteger("8"),new BigInteger("8")),new Point(new BigInteger("2"),new BigInteger("4")));
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyPointTest(){
        try {
            CurveEquation curveEquation = new CurveEquation(new BigInteger("9"), new BigInteger("7"), new BigInteger("2011"));
            Point r = curveEquation.multiplyPoint(new Point(new BigInteger("2"), new BigInteger("523")),3);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeKeyTest(){
        try {
            ECC ecc = new ECC(new BigInteger("1"), new BigInteger("6"), new BigInteger("11"));
            ecc.generateKey();
            ecc.writeKey("key/ecc_key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readKeyTest(){
        try {
            ECC ecc = new ECC(new BigInteger("1"), new BigInteger("6"), new BigInteger("11"));
            ecc.readKey("key/ecc_key");
            ecc.readKey("key/ecc_key.pub");
            System.out.println(ecc.publicKey);
            System.out.println(ecc.privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptDecryptByte(){
        try {
            System.out.println("mulai");
            ECC ecc = new ECC(new BigInteger("18"), new BigInteger("53"), new BigInteger("257"));
            ecc.generateKey();
            System.out.println("key generated");
            byte[] plain = "abc".getBytes();
            System.out.println(Util.bytesToHex(plain));
            byte[] cipher = ecc.encrypt(plain);
            System.out.println(Util.bytesToHex(cipher));
            byte[] decripted = ecc.decrypt(cipher);
            System.out.println(Util.bytesToHex(decripted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptDecryptPoints(){
        try {
            ECC ecc = new ECC(new BigInteger("18"), new BigInteger("53"), new BigInteger("257"));
            ecc.generateKey();
            List<Point> plainPoints = new ArrayList<>();
            plainPoints.add(new Point(new BigInteger("1"),new BigInteger("103")));
            plainPoints.add(new Point(new BigInteger("87"),new BigInteger("114")));
            plainPoints.add(new Point(new BigInteger("256"),new BigInteger("88")));
            System.out.println(plainPoints);
            List<Point> enc = ecc.encryptPoints(plainPoints);
            System.out.println(enc);
            List<Point> decriptedPoints = ecc.decryptPoints(enc);
            System.out.println(decriptedPoints);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptDecryptPoint(){
        try {
            ECC ecc = new ECC(new BigInteger("1"),new BigInteger("6"),new BigInteger("1031"));
            ecc.generateKey();
            List<Point> enc = ecc.encryptPoint(new Point(new BigInteger("555"),new BigInteger("185")));
            System.out.println(enc);
            Point dec = ecc.decryptPoint(enc);
            System.out.println(dec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void inverseModTest(){
        System.out.println(new BigInteger("-3").modInverse(new BigInteger("11")));
    }

    @Test
    public void modTest(){
        BigInteger val = new BigInteger("-23");
        System.out.println(val.mod(new BigInteger("11")));
    }

//    @Test
//    public void get256Points(){
//        for(int a=1;a<100;a++){
//            for(int b=1;b<100;b++){
//                for(int i=0;i<Util.primes.length;i++){
//                    try{
//                        ECC ecc = new ECC(a,b,new BigInteger(String.valueOf(Util.primes[i])));
//                        int points = ecc.curveEquation.getPointsCount();
//                        if(points<256)
//                            continue;
//                        else if(points==256)
//                            System.out.println(String.format("a=%d b=%d p=%d",a,b,Util.primes[i]));
//                        else
//                            break;
//                    } catch (Exception e) {
//                        continue;
//                    }
//                }
//            }
//        }
//    }
}
