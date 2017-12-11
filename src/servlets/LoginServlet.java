package servlets;

import com.google.gson.Gson;
import ejb.DAO;
import entities.Point;
import entities.User;
import serverResoperses.Data;
import serverResoperses.Status;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class LoginServlet extends HttpServlet {

    @EJB
    private DAO dao;
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        User userFromBD = dao.findUserByUserName(username);
        String strPass = req.getParameter("password");
        if(userFromBD == null) {
            sendMessage(resp.getWriter(), "noSuchUser");
            return;
        }
        Integer hashPass = strPass.hashCode();
        if(userFromBD.getUsername().equals(username) && userFromBD.getPassword().equals(hashPass)){
            sendMessage(resp.getWriter(), "successful", userFromBD.getPoints());
        } else {
            sendMessage(resp.getWriter(), "invalidPassword");
        }
    }
    private void sendMessage(PrintWriter writer, String message){
        Status status = new Status(message);
        writer.print(gson.toJson(status));
    }
    private void sendMessage(PrintWriter writer, String message, Collection<Point> points){
        Status status = new Data(message, points);
        writer.print(gson.toJson(status));
    }
}
