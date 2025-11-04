package app.lab03.process;

import app.lab03.data.Point;
import jakarta.inject.Inject;

public class MathematicalCalculations {
    @Inject
    Point point;
    public boolean ifHits(){
        float x = point.getX();
        float y = point.getY();
        int r = point.getR();
        if (x >= 0 && y >= 0){
            return x <= r && y <= (float) r/2;
        }
        else if (x >= 0 && y <= 0) {
            return x - r >= y;
        }
        else if (x < 0 && y >= 0){
            return x*x + y*y <= (float) (r * r) /4;
        }
        return false;
    }
}
