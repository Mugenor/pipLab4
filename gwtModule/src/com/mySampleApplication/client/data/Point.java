package com.mySampleApplication.client.data;



public class Point {
    private Integer id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean isHitted;

    public Point(){}
    public Point(Double x, Double y, Double r){
        this.x = x;
        this.y = y;
        this.r = r;
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
        this.x = x;
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
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
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
}
