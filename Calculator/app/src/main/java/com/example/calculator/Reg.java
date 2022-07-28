package com.example.calculator;

public class Reg {

    public String regex1(){
        return "([-]?\\d+[.]?([.]\\d+)?([×]|[÷])\\d+([.]\\d+)?)";
    }

    public String regex2(){
        return "([-]?\\d+[.]?([.]\\d+)?([+]|[-])\\d+([.]\\d+)?)";
    }

    public String regex3(){
        return "([\\+|×|\\-|÷]?\\d+([.]\\d+)?)";
    }

    public String regexLastOper(){
        return "(\\d+)?([+]|[-]|[×]|[÷])$";
    }
}
