package org.itmo.vt.ejb;

import org.itmo.vt.entities.Point;
import org.itmo.vt.entities.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;

@LocalBean
@Stateful
public class DAO {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("myUnit").createEntityManager();


    public void saveUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void addPointToUser(User user, Point point) {
        entityManager.getTransaction().begin();
        user = entityManager.find(User.class, user.getUsername());
        if (user.getPoints() == null) {
            user.setPoints(new ArrayList<>());
        }
        user.getPoints().add(point);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    public User findUserByUserName(String username) {
        System.out.println("Entity manager: " + entityManager.toString());
        return entityManager.find(User.class, username);
    }

    public boolean isExist(User user) {
        return entityManager.contains(user);
    }
}
