package app.lab02.process;

public class MathematicalCalculations {
    public boolean ifHits(float x, float y, float r){
        if (x >= 0 && y >= 0){
            return x * x + y * y <= r * r;
        }
        else if (x >= 0 && y <= 0) {
            return x <= r && y >= -r;
        }
        else if (x < 0 && y <= 0){
            return y >= -2 * x - r;
        }
        return false;
    }
}
