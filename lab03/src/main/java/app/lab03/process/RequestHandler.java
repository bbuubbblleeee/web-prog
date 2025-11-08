package app.lab03.process;

import app.lab03.data.History;
import app.lab03.data.Point;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.FacesException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
public class RequestHandler implements Serializable {
    private Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Inject
    private History history;
    @Named
    @Inject
    private ValidationService validationService;

    public void addPoint(Point point){
        try {
            logger.info("addPoint called!!");

            long startTime = System.nanoTime();
            Point newPoint = new Point();
            newPoint.setX(point.getX());
            newPoint.setY(point.getY());
            newPoint.setR(point.getR());
            newPoint.setResult(new MathematicalCalculations().ifHits(point.getX(), point.getY(), point.getR()));

            ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.forLanguageTag("ru"));
            String currentTime = dateTimeFormatter.format(moscowTime);

            newPoint.setCurrentTime(currentTime);
            newPoint.setExecutionTime(System.nanoTime() - startTime);
            history.add(newPoint);
            System.out.println(point.getX());

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
            Point newPoint = new Point();
            newPoint.setX(validationService.validateFloat(params.get("x"), "X"));
            newPoint.setY(validationService.validateFloat(params.get("y"), "Y"));
            newPoint.setR(validationService.validateInt(params.get("r")));
            addPoint(newPoint);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new FacesException(e.getMessage());
        }
    }
}
