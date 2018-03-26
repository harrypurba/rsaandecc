import org.junit.Test;

import util.CurveEquation;
import util.Point;
import util.Util;

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
            CurveEquation curveEquation = new CurveEquation(1,6,11);
            System.out.println(curveEquation.getPointsCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPoints(){
        try {
            CurveEquation curveEquation = new CurveEquation(1,6,11);
            List<Point> points = curveEquation.getAllPossiblePoints();
            System.out.println(points);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pointAddition(){
        try {
            CurveEquation curveEquation = new CurveEquation(1,6,11);
            Point r = curveEquation.addPoint(new Point(2,4),new Point(5,9));
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
            CurveEquation curveEquation = new CurveEquation(1,6,11);
            Point r = curveEquation.multiplyPoint(new Point(10,9),13);
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
    public void encryptDecrypt(){
        try {
            ECC ecc = new ECC(1,6,11);
            ecc.generateKey();
            List<Point> enc = ecc.encryptPoint(new Point(2,7));
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
}
