package app.lab03.process;

import app.lab03.dao.PointDao;
import app.lab03.data.History;
import app.lab03.data.Point;
import app.lab03.validation.ValidationService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.FacesException;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Named
@SessionScoped
public class RequestHandler implements Serializable {
    @Inject
    PointDao pointDao;

    @Inject
    private History history;
    @Named
    @Inject
    private ValidationService validationService;

    public void addPoint(Point point){
        log.info("addPoint called!!");
        try{
            pointDao.restorePointsFromDB();
            long startTime = System.nanoTime();
            ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.forLanguageTag("ru"));
            String currentTime = dateTimeFormatter.format(moscowTime);

            Point newPoint = new Point();
            newPoint.setX(point.getX());
            newPoint.setY(point.getY());
            newPoint.setR(point.getR());
            newPoint.setResult(new MathematicalCalculations().ifHits(point.getX(), point.getY(), point.getR()));
            newPoint.setCurrentTime(currentTime);
            newPoint.setExecutionTime(System.nanoTime() - startTime);

            history.add(newPoint);

            pointDao.addPointToDb(newPoint);
            PrimeFaces.current().ajax().addCallbackParam("point", newPoint);
        }
        catch (Exception e){
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            throw new FacesException(e.getMessage());
        }
    }
}
