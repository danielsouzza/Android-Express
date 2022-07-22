 package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.edit_number);
        TextView txtResult = findViewById(R.id.textResult);
        Button btnGenerate = findViewById(R.id.btn_generate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                numberGenerator(text,txtResult);
            }
        });

    }

    private void numberGenerator(String text, TextView txtResult){
        if(!text.isEmpty()){
            int qtd = Integer.parseInt(text);

            // Validar o campo
            if(qtd >= 6 && qtd <= 15){

                HashSet<Integer> numbers = new HashSet<Integer>();
                Random random = new Random();

                while (!(numbers.size() == qtd)){
                    int number = random.nextInt(60);
                    numbers.add(number + 1);
                }

                String txt =
                        numbers.toString()
                                .replace(",","-")
                                .replace("[","")
                                .replace("]","");

                txtResult.setText(txt);

            }else{
                Toast.makeText(this,"Informe um número entre 6 e 15", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Informe um número entre 6 e 15", Toast.LENGTH_LONG).show();
        }
    }
}