package app.lab02.beans;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

abstract public class HistoryBean implements Serializable {
    protected List<RequestBean> allRequests;

    public HistoryBean(){
        allRequests = new LinkedList<>();
    }

    public void add(RequestBean requestBean){
        allRequests.add(0, requestBean);
    }

    public List<RequestBean> getAllRequests() {
        return allRequests;
    }
}
