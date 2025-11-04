package app.lab03.process;

import app.lab03.data.History;
import app.lab03.data.Point;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;

@Named
@SessionScoped
public class RequestHandler implements Serializable {

    @Inject
    private History history;

    @Inject
    private Point point;

    public void addPoint(){
//        long startTime = System.nanoTime();
        System.out.println("addPoint called!!");
//        point.setR(point.getR() + 2);
//        Point newPoint = new Point();
//        newPoint.setX(point.getX());
//        newPoint.setY(point.getY());
//        newPoint.setR(point.getR());
//        newPoint.setResult(new MathematicalCalculations().ifHits());
//        newPoint.setCurrentTime(LocalDateTime.now());
//        newPoint.setExecutionTime(System.nanoTime() - startTime);
//        history.add(newPoint);
    }
}
