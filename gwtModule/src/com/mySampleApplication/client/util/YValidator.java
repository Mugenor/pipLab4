package com.mySampleApplication.client.util;

public class YValidator {
    public static Double checkAndReturnY(String str) {
        Double y;
        try{
            if (str.isEmpty()) throw new RuntimeException("Invalid y");
            y = Double.valueOf(str);
            if(y>=-5.0 && y<=5.0) return y;
                else throw new RuntimeException("Y must be from -5 to 5");
        }catch (NumberFormatException e){
            throw new RuntimeException();
        }
    }
}
