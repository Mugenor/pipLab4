package org.itmo.vt.restServices;

import org.itmo.vt.entities.Point;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/service")
public class JustService {
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String  service(){
        return "hello";
    }
    @GET
    @Path("/point/{x}")
    @Produces(MediaType.APPLICATION_JSON)
    public Point getPoint(double x){
        Point point = new Point();
        point.setR(3.0);
        point.setX(1.0);
        point.setY(2.0);
        point.setHitted(point.checkHitted());
        return point;
    }
}
