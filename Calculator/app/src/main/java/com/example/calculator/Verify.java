package com.example.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verify {
    public static String getFoundPart(String input, String OPERATOR_REGEX ){
        String result = "";
        Pattern pattern = Pattern.compile(OPERATOR_REGEX);
        Matcher matcher = pattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            if(count != 1){
                result = input.substring(matcher.start(), matcher.end());
                count++;
            }
        }
        return result;
    }

    public static boolean isNumeric(String expression){
        try {
            Double.parseDouble(expression);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean checkIntOrDouble(String exp){
        double result = Double.parseDouble(exp);
        return (int) result == result;
    }

    public static boolean checkOperators(String input, String OPERATOR_REGEX){
        Pattern pattern = Pattern.compile(OPERATOR_REGEX);
        return pattern.matcher(input).matches();
    }

}
