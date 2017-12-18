package com.mySampleApplication.client.util;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.mySampleApplication.client.data.Point;
import com.mySampleApplication.client.data.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UserParser {
    public static String encode(User user){
        return user.toString();
    }
    public static User decode(String json){
        User user = new User();
        List<Point> points = new ArrayList<>();
        JSONObject jsonObject = JSONParser.parseStrict(json).isObject();
        user.setUsername(jsonObject.get("username").isString().stringValue());
        user.setPassword((int)jsonObject.get("password").isNumber().doubleValue());
        JSONArray jsonArray = jsonObject.get("points").isArray();
        for(int i=0;i<jsonArray.size();i++){
            points.add(PointParser.decode(jsonArray.get(i).isObject()));
        }
        user.setPoints(points);
        return user;
    }
}
