package app.lab03.dao;

import app.lab03.data.History;
import app.lab03.data.Point;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Slf4j
@Named
@SessionScoped
public class PointDao implements Serializable {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    History history;

    @Transactional
    public void addPointToDb(Point point){
        log.info("DAO called");
        entityManager.persist(point);
    }

    @Transactional
    public void restorePointsFromDB(){
        List<Point> all = entityManager.createQuery("SELECT p FROM Point p", Point.class).getResultList();
        Collections.reverse(all);
        history.setAllRequests(all);
    }
}
