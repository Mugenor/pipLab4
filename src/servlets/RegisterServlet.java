package servlets;

import com.google.gson.Gson;
import ejb.DAO;
import entities.User;
import serverResoperses.Status;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {

    @EJB
    private DAO dao;
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String strPass = req.getParameter("password");
        if(username==null || username.trim().equals("") || strPass==null || strPass.trim().equals("")){
            sendMessage(resp.getWriter(), "haveNoInfo");
            return;
        }
        Integer password = strPass.hashCode();
        User newUser = new User(username, password);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        if(dao.isExist(newUser)){
            sendMessage(writer, "error");
        } else {
            dao.saveUser(newUser);
            sendMessage(writer, "successful");
        }
    }
    private void sendMessage(PrintWriter writer, String message){
        Status status = new Status(message);
        writer.print(gson.toJson(status));
    }
}
