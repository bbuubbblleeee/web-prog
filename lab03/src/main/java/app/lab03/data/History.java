package app.lab03.data;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Named
@SessionScoped
public class History implements Serializable {
    private List<Point> allRequests = new LinkedList<>();

    public void add(Point point){
        allRequests.add(0, point);
    }
}
