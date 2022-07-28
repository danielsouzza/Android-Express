package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // Views
    private TextView viewCalc;
    private TextView enterView;
    private Calculator calculator;
    private Verify verify;
    private Reg reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Classes
        calculator = new Calculator();
        verify = new Verify();
        reg = new Reg();

        // Views
        viewCalc = findViewById(R.id.view_calc);
        enterView = findViewById(R.id.enterView);

        // Numeric Buttons
        Button clearAll = findViewById(R.id.button_clearAll);
        ImageButton clear = findViewById(R.id.button_clear);
        Button one = findViewById(R.id.button_1);
        Button two = findViewById(R.id.button_2);
        Button three = findViewById(R.id.button_3);
        Button four = findViewById(R.id.button_4);
        Button five = findViewById(R.id.button_5);
        Button six = findViewById(R.id.button_6);
        Button seven = findViewById(R.id.button_7);
        Button eight = findViewById(R.id.button_8);
        Button nine = findViewById(R.id.button_9);
        Button zero = findViewById(R.id.button_0);
        Button colt = findViewById(R.id.button_colt);

        // Operator Buttons
        Button plus = findViewById(R.id.button_plus);
        Button minus = findViewById(R.id.button_minus);
        Button multiply = findViewById(R.id.button_multiply);
        Button divide = findViewById(R.id.button_divide);
        Button iqual = findViewById(R.id.button_igual);

        // Start Setting
        viewCalc.setText("0");
        changeTextSize(25f,40f,getResources().getColor(R.color.calcView),getResources().getColor(R.color.black));



        // Logical Part
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("1");
                calculationView();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("2");
                calculationView();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("3");
                calculationView();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("4");
                calculationView();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("5");
                calculationView();
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("6");
                calculationView();
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("7");
                calculationView();
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("8");
                calculationView();
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView("9");
                calculationView();
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calculator.getExpression().equals("")){
                    return;
                }
                inputView("0");
                calculationView();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView("+");

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView("-");

            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView("×");

            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView("÷");

            }
        });

        colt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calculator.getExpression().equals("")){
                    inputView("0.");
                    calculationView();
                }else{
                    inputView(".");
                }

            }
        });


        iqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = calculationView();
                if(verify.isNumeric(result)){
                    calculator.setCalc(result);
                    calculator.setExpression(result);
                }else {
                    calculator.setCalc("");
                    calculator.setExpression("");
                }

                viewCalc.setTextColor(getResources().getColor(R.color.black));
                changeTextSize(25f,40f,getResources().getColor(R.color.calcView),getResources().getColor(R.color.black));
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextSize(25f,40f,getResources().getColor(R.color.calcView),getResources().getColor(R.color.black));
                enterView.setText("");
                viewCalc.setText("0");
                calculator.setCalc("");
                calculator.setExpression("");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEnterView();
            }
        });
    }

    public void changeTextSize(float enterSize, float calcSize, int enterColo, int calcColor){
        enterView.setTextSize(TypedValue.COMPLEX_UNIT_SP,enterSize);
        enterView.setTextColor(enterColo);
        viewCalc.setTextSize(TypedValue.COMPLEX_UNIT_SP,calcSize );
        viewCalc.setTextColor(calcColor);
    }

    @SuppressLint("SetTextI18n")
    public void inputView(String input) {
        changeTextSize(40f,25f,getResources().getColor(R.color.black),getResources().getColor(R.color.calcView));
        String expression = calculator.getExpression().replace(" ","");
        if(expression.equals(""))
            if(Verify.checkOperators(input, reg.regexLastOper())||enterView.getText().toString().equals("Can't divide by zero")){
                expression = "0";
                viewCalc.setText("0");
            }
        if(!calculator.getCalc().equals("")){
            calculator.setCalc("");
            if(verify.isNumeric(input)) expression = "";
        }

        if (Verify.checkOperators(input, reg.regexLastOper()))
            if (!verify.getFoundPart(expression, reg.regexLastOper()).equals("")) {
                expression = expression.substring(0, expression.length() - 1);
            }
        enterView.setText(expression + input);
        calculator.setExpression(expression + input);
    }

    public String calculationView() {
        String result = "";
        if(!calculator.getExpression().equals("")){
            result = calculator.calculateExpression();
            viewCalc.setText("="+result);
            return result;
        }
    return result;
    }

    public void clearEnterView(){
        String exp = calculator.getExpression();
        if(!calculator.getCalc().equals("")){
            return;
        }else if(exp.length() <= 1){
            changeTextSize(25f,40f,getResources().getColor(R.color.calcView),getResources().getColor(R.color.black));
            enterView.setText("");
            viewCalc.setText("0");
            calculator.setExpression("");
            calculator.setCalc("");
            return;
        }
        calculator.setExpression(exp.substring(0,exp.length()-1));
        enterView.setText(calculator.getExpression());
        calculationView();
    }
}
