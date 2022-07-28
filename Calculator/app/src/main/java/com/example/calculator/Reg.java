package com.example.calculator;

public class Reg {

    public static String regex1(){
        return "([-|.]?\\d+[.]?([.]\\d+)?([×]|[÷])[.]?\\d+([.]\\d+)?)";
    }

    public static String regex2(){
        return "([-|.]?\\d+[.]?([.]\\d+)?([+]|[-])[.]?\\d+([.]\\d+)?)";
    }

    public static String regex3(){
        return "([\\+|×|\\-|÷]?[.]?\\d+([.]\\d+)?)";
    }

    public static String regexLastOper(){
        return "(\\d+)?([+]|[-]|[×]|[÷])$";
    }
}
