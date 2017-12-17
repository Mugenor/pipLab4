package org.itmo.vt.serverResponses;

import org.itmo.vt.entities.Point;

import java.util.Collection;

public class Data extends Status{
    private Collection<Point> points;

    public Data(String status){
        super(status);
    }

    public Data(String status, Collection<Point> points){
        super(status);
        this.points = points;
    }

    public Collection<Point> getPoints() {
        return points;
    }

    public void setPoints(Collection<Point> points) {
        this.points = points;
    }
}
