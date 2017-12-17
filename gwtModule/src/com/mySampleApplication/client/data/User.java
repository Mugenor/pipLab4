package com.mySampleApplication.client.data;

import java.util.Collection;



public class User {
    private String username;
    private Integer password;

    private Collection<Point> points;

    public User(){}

    public User(String username, Integer password){
        this.username = username;
        this.password = password;
    }

    public Collection<Point> getPoints() {
        return points;
    }
    public void setPoints(Collection<Point> points) {
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
        if(points!=null) points.forEach(str::append);
        str.append("]}");
        return str.toString();
    }
}