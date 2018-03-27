import org.junit.Test;

import util.CurveEquation;
import util.Point;
import util.Util;

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
            CurveEquation curveEquation = new CurveEquation(18,53,257);
            System.out.println(curveEquation.getPointsCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPoints(){
        try {
            CurveEquation curveEquation = new CurveEquation(18,53,257);
            List<Point> points = curveEquation.getAllPossiblePoints();
            System.out.println(points);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pointAddition(){
        try {
            CurveEquation curveEquation = new CurveEquation(9,7,2011);
            Point r = curveEquation.addPoint(new Point(2,523),new Point(2,523));
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pointSubtraction(){
        try {
            CurveEquation curveEquation = new CurveEquation(1,6,11);
            Point r = curveEquation.subtractPoint(new Point(8,8),new Point(2,4));
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyPointTest(){
        try {
            CurveEquation curveEquation = new CurveEquation(9,7,2011);
            Point r = curveEquation.multiplyPoint(new Point(2,523),3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeKeyTest(){
        try {
            ECC ecc = new ECC(1,6,11);
            ecc.generateKey();
            ecc.writeKey("key/ecc_key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readKeyTest(){
        try {
            ECC ecc = new ECC(1,6,11);
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
            ECC ecc = new ECC(18,53,257);
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
            ECC ecc = new ECC(18,53,257);
            ecc.generateKey();
            List<Point> plainPoints = new ArrayList<>();
            plainPoints.add(new Point(1,103));
            plainPoints.add(new Point(87,114));
            plainPoints.add(new Point(256,88));
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
            ECC ecc = new ECC(1,6,1031);
            ecc.generateKey();
            List<Point> enc = ecc.encryptPoint(new Point(555,185));
            System.out.println(enc);
            Point dec = ecc.decryptPoint(enc);
            System.out.println(dec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void inverseModTest(){
        System.out.println(Util.modInverse(-3,11));
    }

    @Test
    public void modTest(){
        System.out.println(Math.floorMod(-35,11));
    }

    @Test
    public void get256Points(){
        for(int a=1;a<100;a++){
            for(int b=1;b<100;b++){
                for(int i=0;i<Util.primes.length;i++){
                    try{
                        ECC ecc = new ECC(a,b,Util.primes[i]);
                        int points = ecc.curveEquation.getPointsCount();
                        if(points<256)
                            continue;
                        else if(points==256)
                            System.out.println(String.format("a=%d b=%d p=%d",a,b,Util.primes[i]));
                        else
                            break;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
    }
}
