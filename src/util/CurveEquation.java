package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CurveEquation {
    // equation that follows y^2 = x^3 + ax + b mod p
    // where 4a^3+27b^2≠0
    public BigInteger a;
    public BigInteger b;
    public BigInteger p;

    public CurveEquation(BigInteger a, BigInteger b, BigInteger p) throws Exception {
//        if (4*Math.pow(a,3)+27*Math.pow(b,2)==0)
//            throw new Exception("a and b singular");
        if((a.pow(3).multiply( new BigInteger("4")).add(b.pow(2).multiply(new BigInteger("27")))).equals(new BigInteger("0"))){
            throw new Exception("a and b singular");
        }
        if (!p.isProbablePrime(1))
            throw new Exception("p is not prime");
        this.a = a;
        this.b = b;
        this.p = p;
    }

    public boolean isXSolvable(BigInteger x){
//        int mod = ((x*x*x)+a*x+b) % p;
//        // y^2 congruence with mod%p
//        for(int y=0;y<p;y++){
//            if((y*y%p)==mod)
//                return true;
//        }
//        return false;
        BigInteger mod = x.pow(3).add(x.multiply(a)).add(b).mod(p);
        // y^2 congruence with mod%p
        BigInteger y = new BigInteger("0");
        while (y.compareTo(p)<0){
            if(y.pow(2).mod(p).equals(mod))
                return true;
            y = y.add(new BigInteger("1"));
        }
        return false;
    }

    public List<Point> getAllPossiblePoints(){
        List<Point> points = new ArrayList<>();
        BigInteger x = new BigInteger("0");
        while (x.compareTo(p)<0){
            BigInteger mod = x.pow(3).add(a.multiply(x)).add(b).mod(p);
            // y^2 congruence with mod%p
            BigInteger y = new BigInteger("0");
            while(y.compareTo(p)<0){
                if(y.pow(2).mod(p).equals(mod)){
                    points.add(new Point(x,y));
                }
                y = y.add(new BigInteger("1"));
            }

            x = x.add(new BigInteger("1"));
        }
        return points;
    }

    public int getPointsCount(){
        return getAllPossiblePoints().size();
    }

    public boolean isInCurve(Point p){
//        int y2 = (p.x*p.x*p.x)+(a*p.x)+b;
        BigInteger t1 = p.x.pow(3);
        BigInteger t2 = p.x.multiply(a);
        BigInteger y2 = t1.add(t2).add(b);
        BigInteger mod = y2.mod(this.p);
        return ((p.y.pow(2)).mod(this.p).equals(mod));
    }

    public Point addPoint(Point p1, Point p2){
        if(p1.equals(p2)){
            if(p1.y.equals(new BigInteger("0")))
                return null;
            BigInteger divisor = p1.y.multiply(new BigInteger("2"));
            BigInteger invMod = divisor.modInverse(p);
//            int m = Math.floorMod((((3*p1.x*p1.x+a))*invMod),p);
            BigInteger m = p1.x.pow(2).multiply(new BigInteger("3")).add(a).multiply(invMod).mod(p);
//            int xr = (m*m-2*p1.x);
            BigInteger xr = m.pow(2).subtract(p1.x.multiply(new BigInteger("2")));
            xr = xr.mod(p);
//            Point ret = new Point(xr, Math.floorMod((m*(p1.x-xr)-p1.y),p));
            BigInteger yr = m.multiply(p1.x.subtract(xr)).subtract(p1.y).mod(p);
            Point ret = new Point(xr,yr);
            return (isInCurve(ret)?ret:null);
        }
//        int divisor = (p1.x-p2.x);
        BigInteger divisor = p1.x.subtract(p2.x);
//        int invMod = Util.modInverse(divisor,p);
        BigInteger invMod = divisor.modInverse(p);
//        int m = ((p1.y-p2.y)*invMod);
        BigInteger m = p1.y.subtract(p2.y).multiply(invMod);
        m = m.mod(p);
//        int xr = (int) (m*m-p1.x-p2.x); xr = Math.floorMod(xr,p);
        BigInteger xr = m.pow(2).subtract(p1.x).subtract(p2.x).mod(p);
//        Point ret = new Point(xr,(int)Math.floorMod((m*(p1.x-xr)-p1.y),p));
        Point ret = new Point(xr, m.multiply(p1.x.subtract(xr)).subtract(p1.y).mod(p));
        return (isInCurve(ret)?ret:null);
    }

    public Point multiplyPoint(Point p, int n){
        --n;
        if(n==0){
            return p;
        }
        Point r = addPoint(p,p);
        --n;
        for(int i=0;i<n;++i){
            r = addPoint(p,r);
        }
        return r;
    }

    public Point subtractPoint(Point p, Point q){
        Point negQ = new Point(q.x,q.y.multiply(new BigInteger("-1")).mod(this.p));
        return addPoint(p,negQ);
    }
}
