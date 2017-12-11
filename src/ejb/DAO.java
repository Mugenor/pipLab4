package ejb;

import entities.Point;
import entities.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@Stateful
public class DAO {
    @PersistenceUnit
    private EntityManager entityManager;

    public void saveUser(User user){
        entityManager.persist(user);
    }

    public void addPointToUser(User user, Point point){
        user = entityManager.find(User.class, user.getUsername());
        user.getPoints().add(point);
    }

    public User findUserByUserName(String username){
        return entityManager.find(User.class, username);
    }
    public boolean isExist(User user){
        return entityManager.contains(user);
    }
}
