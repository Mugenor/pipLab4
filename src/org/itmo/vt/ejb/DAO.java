package org.itmo.vt.ejb;

import org.itmo.vt.entities.Point;
import org.itmo.vt.entities.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@LocalBean
@Stateful
public class DAO {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;

    public void saveUser(User user){
        entityManager.persist(user);
    }

    public void addPointToUser(User user, Point point){
        user = entityManager.find(User.class, user.getUsername());
        user.getPoints().add(point);
    }

    public User findUserByUserName(String username) {
        System.out.println("Entity manager: " + entityManager.toString());
        return entityManager.find(User.class, username);
    }
    public boolean isExist(User user){
        return entityManager.contains(user);
    }
}
