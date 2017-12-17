package com.mySampleApplication.client.data;

import java.util.Collection;

public class Data extends Status{
    private Collection<Point> points;

    public Data(){}
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
