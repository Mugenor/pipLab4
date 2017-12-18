package com.mySampleApplication.client.util;

public class YValidator {
    public static Double checkAndReturnY(String str) {
        Double y;
        try{
            y = Double.valueOf(str);
            if(y>=-5.0 && y<=5.0) return y; else return null;
        }catch (NumberFormatException e){
            return null;
        }
    }
}
