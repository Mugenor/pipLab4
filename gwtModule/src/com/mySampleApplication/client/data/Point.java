package com.mySampleApplication.client.data;

public class Point {
    private Integer id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean isHitted;

    public Point(){}
    public Point(Double x, Double y, Double r){
        this.x = (double)Math.round(x* 1000.0d)/1000.0d;
        this.y = (double)Math.round(y* 1000.0d)/1000.0d;;
        this.r = (double)Math.round(r* 1000.0d)/1000.0d;;
        isHitted = checkHitted();
    }
    public boolean checkHitted(){
        return (x>=0.0 && y>=0.0 && (x*x+y*y)<=(r*r/4)) ||
                (x>=0.0 && y<=0.0 && x<=r && y>=-r) ||
                (x<=0.0 && y>=0.0 && y<=x+r/2.0);
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = (double)Math.round(x* 1000.0d)/1000.0d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return id.equals(point.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = (double)Math.round(y* 1000.0d)/1000.0d;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = (double)Math.round(r * 1000.0d)/1000.0d;
    }

    public Boolean getHitted() {
        return isHitted;
    }

    public void setHitted(Boolean hitted) {
        isHitted = hitted;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String toString(){
        return "{\"x\":" + x + ",\"y\":" + y + ",\"r\":" + r + ",\"isHitted\":" + isHitted + "}";
    }
    public boolean checkHitted(Double R){
        return (x>=0.0 && y>=0.0 && (x*x+y*y)<=(R*R/4)) ||
                (x>=0.0 && y<=0.0 && x<=R && y>=-R) ||
                (x<=0.0 && y>=0.0 && y<=x+R/2.0);

    }
}