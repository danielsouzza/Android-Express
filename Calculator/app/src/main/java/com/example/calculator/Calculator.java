package com.example.calculator;


import android.annotation.SuppressLint;

public class Calculator {
    private String expression;
    private String calc;
    private final Verify verify;
    private final Reg reg;

    public Calculator(){
        verify = new Verify();
        reg = new Reg();
        this.expression="";
        this.calc = "";
    }


    public String calculateExpression(){
        try {
            String expression = this.getExpression().replace(" ","");
            if(Verify.checkOperators(expression, reg.regexLastOper())){
                expression = expression.substring(0,expression.length()-1);
            }
            if(expression.charAt(0)=='-'){
                expression = "0"+expression;
            }
            // check if it's numeric
            while(!(verify.isNumeric(expression))){ // checks if it found the priority 1 regex pattern

                if(!verify.getFoundPart(expression, reg.regex1()).equals("")){
                    expression = calcPartOfTheExpression(expression, reg.regex1());
                }else if(!verify.getFoundPart(expression, reg.regex2()).equals("")){ // checks if it found the priority 2 regex pattern
                    expression = calcPartOfTheExpression(expression, reg.regex2());
                }
            }
            double numDB = Double.parseDouble(expression);
            return verify.checkIntOrDouble(expression) ? Integer.toString((int)numDB) : Double.toString(numDB);

        } catch(ArithmeticException e) {
            return ("Can't divide by zero");
        }
    }

    public String calcPartOfTheExpression(String expression, String regx){
        String calc = verify.getFoundPart(expression,regx); // Get the part of the pattern found
        String result = calculation(calc); //Calculate the part found
        return expression.replace(calc,result); // Returns the expression already as a result
    }

    // Algorithm to calculate the expression
    @SuppressLint("DefaultLocale")
    private String calculation(String calc) throws  ArithmeticException{
        String result="";
        String part_1 = verify.getFoundPart(calc, reg.regex3());
        String part_2 = verify.getFoundPart(calc.replaceFirst(part_1,""), reg.regex3());
        double num1 = 0;
        double num2 = 0;
        if(part_2.charAt(0) == '×'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("×","");
            num2 = Double.parseDouble(part_2);
            result = String.format("%f", (num1 * num2));
        }else if(part_2.charAt(0) == '÷'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("÷","");
            num2 = Double.parseDouble(part_2);
            if(num2 == 0.0){
                throw new ArithmeticException();
            }
            result = String.format("%f", (num1 / num2));
        }else if(part_2.charAt(0) == '+'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("+","");
            num2 = Double.parseDouble(part_2);
            result = String.format("%f", (num1 + num2));
        }else if(part_2.charAt(0) == '-'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("-","");
            num2 = Double.parseDouble(part_2);
            result = String.format("%f", (num1 - num2));
        }
        return result;
    }

    public void setCalc(String calc) {
        this.calc = calc;
    }

    public String getCalc() {
        return calc;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

}
