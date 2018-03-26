import util.CurveEquation;
import util.Point;

import java.math.BigInteger;
import java.util.ArrayList;

public class ECC {
    public ArrayList<BigInteger> blokplain = new ArrayList<>();
    public ArrayList<BigInteger> blokcipher = new ArrayList<>();
    CurveEquation curveEquation = new CurveEquation(1,6,11);


    public ECC() throws Exception {

    }

    public byte[] encrypt(byte[] in){
        return null;
    }

    public void dekripsi(){

    }
}
