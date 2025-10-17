package app.lab02.beans;


import jakarta.enterprise.context.RequestScoped;

import java.io.Serializable;


@RequestScoped
public class RequestBean implements Serializable {
    private float x;
    private float y;
    private float r;
    private boolean result;
    private String currentTime;
    private long executionTime;


    public RequestBean(){
        currentTime = "";
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }


    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }


    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

};
