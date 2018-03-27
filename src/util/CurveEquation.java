package util;

import java.util.ArrayList;
import java.util.List;

public class CurveEquation {
    // equation that follows y^2 = x^3 + ax + b mod p
    // where 4a^3+27b^2≠0
    public int a;
    public int b;
    public int p;

    public CurveEquation(int a, int b, int p) throws Exception {
        if (4*Math.pow(a,3)+27*Math.pow(b,2)==0)
            throw new Exception("a and b singular");
        if (!Util.isPrime(p))
            throw new Exception("p is not prime");
        this.a = a;
        this.b = b;
        this.p = p;
    }

    public boolean isXSolvable(int x){
        int mod = ((x*x*x)+a*x+b) % p;
        // y^2 congruence with mod%p
        for(int y=0;y<p;y++){
            if((y*y%p)==mod)
                return true;
        }
        return false;
    }

    public List<Point> getAllPossiblePoints(){
        List<Point> points = new ArrayList<>();
        for(int x=0;x<p;x++){
            int mod = ((x*x*x)+a*x+b) % p;
            // y^2 congruence with mod%p
            for(int y=0;y<p;y++){
                if((y*y%p)==mod)
                    points.add(new Point(x,y));
            }
        }
        return points;
    }

    public int getPointsCount(){
        return getAllPossiblePoints().size();
    }

    public boolean isInCurve(Point p){
        int y2 = (p.x*p.x*p.x)+(a*p.x)+b;
        int mod = Math.floorMod(y2,this.p);
        return (Math.floorMod(p.y*p.y,this.p)==mod);
    }

    public Point addPoint(Point p1, Point p2){
        if(p1.equals(p2)){
            if(p1.y==0)
                return null;
            int divisor = Math.abs(2*p1.y);
            int invMod = Util.modInverse(divisor,p);
            int m = Math.floorMod((((3*p1.x*p1.x+a))*invMod),p);
            int xr = (m*m-2*p1.x);
            xr = Math.floorMod(xr,this.p);
            Point ret = new Point(xr, Math.floorMod((m*(p1.x-xr)-p1.y),p));
            return (isInCurve(ret)?ret:null);
        }
        int divisor = (p1.x-p2.x);
        int invMod = Util.modInverse(divisor,p);
        int m = ((p1.y-p2.y)*invMod);
        m = Math.floorMod(m,p);
        int xr = (int) (m*m-p1.x-p2.x); xr = Math.floorMod(xr,p);
        Point ret = new Point(xr,(int)Math.floorMod((m*(p1.x-xr)-p1.y),p));
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
        Point negQ = new Point(q.x,Math.floorMod(-1*q.y,this.p));
        return addPoint(p,negQ);
    }
}
