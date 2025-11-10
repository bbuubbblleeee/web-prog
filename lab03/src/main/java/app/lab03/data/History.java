package app.lab03.data;

import com.google.gson.Gson;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Named
@SessionScoped
public class History implements Serializable {
    private List<Point> allRequests = new LinkedList<>();

    public void add(Point point){
        allRequests.add(0, point);
    }

    public void getAllRequestsAsJson(){
        Gson gson = new Gson();
        PrimeFaces.current().ajax().addCallbackParam("pointsHistory", gson.toJson(allRequests));
    }
}
