package org.itmo.vt.restServices;

import org.itmo.vt.ejb.DAO;
import org.itmo.vt.entities.Point;
import org.itmo.vt.entities.User;
import org.itmo.vt.serverResponses.Data;
import org.itmo.vt.serverResponses.Status;
import org.itmo.vt.serverResponses.UserPoint;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@LocalBean
@Stateless
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
    @EJB
    private DAO dao;

    @Path("/registration")
    @POST
    public Status registration(User user){
        if(user.getUsername()==null || user.getUsername().trim().equals("") || user.getPassword()==null){
            return new Status("refused"); //Введите корректные данные
        }
        if(dao.findUserByUserName(user.getUsername())!=null){
            return new Status("refused"); //Такой пользователь уже существует
        } else {
            dao.saveUser(user);
            return new Status("success"); //Пользователь зарегестрирован
        }
    }

    @Path("/login")
    @POST
    public Data login(User user, @Context HttpServletRequest request){
        System.out.println("DAO: " + dao);
        if(user == null ||user.getUsername()==null ||user.getUsername().trim().equals("") ||  user.getPassword()==null){
            return new Data("refused"); //Введите корректные данные
        }
        User userFromBD = dao.findUserByUserName(user.getUsername());
        if(userFromBD == null) {
            return new Data("refused"); //Нет такого пользователя
        }
        System.out.println("login.user: " + user.toString());
        System.out.println("login.userFromBD: " + userFromBD.toString());
        if(userFromBD.getPassword().equals(user.getPassword())){
            return new Data("success", userFromBD.getPoints());
        } else {
            return new Data("refused"); //Неправильный пароль
        }
    }

    @Path("/add")
    @POST
    public Status addPoint(UserPoint point, @Context HttpServletRequest request){
        if(point.getX() == null || point.getY() == null || point.getR()==null
                || point.getX()>4.0 || point.getX()<-4.0
                || point.getY()>5.0 || point.getY()<-5.0
                || point.getR()<=0.0 || point.getR()>4.0){
            return new Status("refused"); //Плохая точка
        }
        Point newPoint = new Point(point.getX(), point.getY(), point.getR());

        newPoint.setId(null);
        newPoint.setHitted(newPoint.checkHitted());
        User user =dao.findUserByUserName(point.getUsername());
        if(user == null || !user.getPassword().equals(point.getPassword())){
            return new Status("refused"); // Пользователь не вошёл в систему
        } else {
            dao.addPointToUser(user, newPoint);
            return new Status("success"); //Точка добавлена
        }
    }
/*
    @Path("/exit")
    @POST
    public Status exit(User user, @Context HttpServletRequest request){
        if(user == null || user.getUsername().trim().equals("") || user.getUsername()==null || user.getPassword()==null){
            System.out.println("Exit refused because of incorrect data!");
            return new Data("refused"); //Введите корректные данные
        }
        User serverUser = (User) request.getSession().getAttribute("user");
        if(serverUser == null){
            System.out.println("Exit refused because of there is no user like that on server!");
            return new Status("refused");
        } else {
            if(user.getUsername().equals(serverUser.getUsername()) && user.getPassword().equals(serverUser.getPassword())){
                System.out.println("Exit success!");
                request.getSession().removeAttribute("user");
                return new Status("success");
            } else {
                System.out.println("Exit refused because of wrong login/password");
                return new Status("refused");
            }
        }
    }*/
}
