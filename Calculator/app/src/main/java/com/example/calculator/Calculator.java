package com.example.calculator;


import android.annotation.SuppressLint;

public class Calculator {
    private String expression;
    private String calc;

    public Calculator(){
        this.expression= "";
        this.calc = "";
    }


    public String calculateExpression(String expression){
        try {
            expression = expression.replace(" ","");
            if(!Verify.getFoundPart(expression, Reg.regexLastOper()).equals(""))
                expression = expression.substring(0, expression.length() - 1);

            // check if it's numeric
            while(!(Verify.isNumeric(expression))){ // checks if it found the priority 1 regex pattern
                if(!Verify.getFoundPart(expression, Reg.regex0()).equals("")) {
                    String priority = Verify.getFoundPart(expression, Reg.regex0());
                    String recur = calculateExpression(priority.replace("(", "").replace(")", ""));
                    expression = expression.replace(priority, recur);

                }else if(!Verify.getFoundPart(expression, Reg.regex1()).equals("")){

                    expression = calcPartOfTheExpression(expression, Reg.regex1()).replace(",","."); // Takes and calculates the part found

                }else if(!Verify.getFoundPart(expression, Reg.regex2()).equals("")) { // checks if it found the priority 2 regex pattern

                    expression = calcPartOfTheExpression(expression, Reg.regex2()).replace(",", "."); // Takes and calculates the part found
                }else{
                    throw new NumberFormatException();
                }
            }

            /*
                In this step it will be possible to transform to double,
                as it will only exit the while if it is a number
            */
            double numDB = Double.parseDouble(expression);
            return Verify.checkIntOrDouble(expression) ? Integer.toString((int)numDB) : Double.toString(numDB); // Convert to int be possible

        } catch(ArithmeticException e) {
            return ("Can't divide by zero");
        }catch (NumberFormatException e){
            return ("Erro");
        }
    }

    public String calcPartOfTheExpression(String expression, String regx){
        String calc = Verify.getFoundPart(expression,regx); // Get the part of the pattern found
        String result = calculation(calc); //Calculate the part found
        return expression.replace(calc,result); // Return the expression already as a result and put in place of the calculated part
    }

    // Algorithm to calculate the expression
    @SuppressLint("DefaultLocale")
    private String calculation(String calc) throws  ArithmeticException{
        String result="";

        //split the string into two parts to be able to calculate
        String part_1 = Verify.getFoundPart(calc, Reg.regex3());
        String part_2 = Verify.getFoundPart(calc.replaceFirst(part_1,""), Reg.regex3());
        double num1 = 0;
        double num2 = 0;

        // check the type of operation
        if(part_2.charAt(0) == '×'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("×",""); // Remove operator from string to perform calculation
            num2 = Double.parseDouble(part_2);
            result = String.format("%f", (num1 * num2));
        }else if(part_2.charAt(0) == '÷'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("÷",""); // Remove operator from string to perform calculation
            num2 = Double.parseDouble(part_2);
            if(num2 == 0.0){
                throw new ArithmeticException();
            }
            result = String.format("%f", (num1 / num2));
        }else if(part_2.charAt(0) == '+'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replace("+",""); // Remove operator from string to perform calculation
            num2 = Double.parseDouble(part_2);
            result = String.format("%f", (num1 + num2));
        }else if(part_2.charAt(0) == '-'){
            num1 = Double.parseDouble(part_1);
            part_2 = part_2.replaceFirst("-",""); // Remove operator from string to perform calculation
            num2 = Double.parseDouble(part_2);
            result = String.format("%f", (num1 - num2)); // Put the result in (result)
        }
        return result; // Returns (result)
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
