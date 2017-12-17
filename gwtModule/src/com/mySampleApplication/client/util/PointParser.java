package com.mySampleApplication.client.util;

import com.google.gwt.json.client.JSONObject;
import com.mySampleApplication.client.data.Point;

public class PointParser {
    public static Point decode(JSONObject jsonObject){
        Point point = new Point();
        point.setX(jsonObject.get("x").isNumber().doubleValue());
        point.setY(jsonObject.get("y").isNumber().doubleValue());
        point.setR(jsonObject.get("r").isNumber().doubleValue());
        point.setHitted(point.checkHitted());
        point.setId((int)jsonObject.get("id").isNumber().doubleValue());
        return point;
    }
}
