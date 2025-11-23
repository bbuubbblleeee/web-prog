package app.lab03.data;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Named
@Entity
@Table(name = "point")
@SessionScoped
public class Point implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "x")
    private float x;

    @Column(name = "y")
    private Float y;

    @Column(name = "r")
    private int r;

    @Column(name = "result")
    private boolean result;

    @Column(name = "creation_time")
    private String currentTime;

    @Column(name = "execution_time")
    private long executionTime;
}
