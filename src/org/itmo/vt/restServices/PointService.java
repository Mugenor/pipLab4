package org.itmo.vt.restServices;

import org.itmo.vt.entities.Point;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/points")
public class PointService {
   /* @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Point getAllPoints(){
        Point point = new Point();
        point.setX(1.0);
        point.setY(2.0);
        point.setR(3.0);
        point.setHitted(point.checkHitted());
        return point;
    }*/
   @GET
   @Path("/hello")
   @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(){
       System.out.println("Pidor");
       return "Hello world!";
   }
}
