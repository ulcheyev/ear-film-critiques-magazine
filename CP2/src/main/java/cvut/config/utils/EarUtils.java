package cvut.config.utils;

public class EarUtils {

    public static double floorNumber(int scale,double n){
        double sc = Math.pow(10, scale);
        return Math.floor(n*sc)/sc;
    }
}
