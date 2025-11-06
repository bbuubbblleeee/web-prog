package app.lab03.process;

import app.lab03.data.History;
import app.lab03.data.Point;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Named
@SessionScoped
public class RequestHandler implements Serializable {
    private Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Inject
    private History history;

    @Inject
    private Point point;

    public void addPoint(){
        try {
            logger.info("addPoint called!!");

            long startTime = System.nanoTime();
            Point newPoint = new Point();
            newPoint.setX(point.getX());
            newPoint.setY(point.getY());
            newPoint.setR(point.getR());
            newPoint.setResult(new MathematicalCalculations().ifHits(point.getX(), point.getY(), point.getR()));
            newPoint.setCurrentTime(LocalDateTime.now());
            newPoint.setExecutionTime(System.nanoTime() - startTime);
            history.add(newPoint);

            PrimeFaces.current().ajax().addCallbackParam("point", newPoint);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public void addPointFromGraph(){
        try {
            System.out.println("graph called!!");

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            point.setX(Float.parseFloat(params.get("x")));
            point.setY(Float.parseFloat(params.get("y")));
            addPoint();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
