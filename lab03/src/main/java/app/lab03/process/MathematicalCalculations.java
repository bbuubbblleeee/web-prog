package app.lab03.process;

public class MathematicalCalculations {
    public boolean ifHits(float x, float y, int r){
        if (x >= 0 && y >= 0){
            return x <= r && y <= (float) r /2;
        }
        else if (x >= 0 && y <= 0) {
            return y >= x - r;
        }
        else if (x < 0 && y >= 0){
            return x*x + y*y <= (float) (r * r) /4;
        }
        return false;
    }
}
