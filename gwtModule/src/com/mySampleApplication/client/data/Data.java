package com.mySampleApplication.client.data;

import java.util.Collection;
import java.util.List;

public class Data extends Status{
    private List<Point> points;

    public Data(){}
    public Data(String status){
        super(status);
    }

    public Data(String status, List<Point> points){
        super(status);
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
