package org.itmo.vt.serverResponses;

import org.itmo.vt.entities.Point;

public class UserPoint extends Point {
    private String username;
    private Integer password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }
}
