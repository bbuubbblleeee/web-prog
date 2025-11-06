package app.lab03.data;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Named
@SessionScoped
public class Point implements Serializable {
    private float x;
    private float y;
    private int r;
    private boolean result;
    private LocalDateTime currentTime;
    private long executionTime;
}
