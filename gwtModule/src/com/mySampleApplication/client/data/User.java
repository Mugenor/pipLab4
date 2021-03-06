package com.mySampleApplication.client.data;

import java.util.List;


public class User {
    private String username;
    private Integer password;

    private List<Point> points;

    public User(){}

    public User(String username, Integer password){
        this.username = username;
        this.password = password;
    }

    public List<Point> getPoints() {
        return points;
    }
    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
    public String toString(){
        StringBuilder str = new StringBuilder("{\"username\":\"" + username + "\",\"password\":" + password + ",\"points\": [");
        if(points!=null) {
            for(int i=0;i<points.size()-1;i++){
                str.append(points.get(i)).append(',');
            }
            str.append(points.get(points.size()-1));
        }
        str.append("]}");
        return str.toString();
    }
}
