package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.edit_number);
        TextView txtResult = findViewById(R.id.textResult);
        Button btnGenerate = findViewById(R.id.btn_generate);

        // Banco de dados
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE);
        String lastResult = prefs.getString("lastResult",null);
        if(lastResult != null){
            txtResult.setText("Ultima aposta: " + lastResult);
        }

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                numberGenerator(text, txtResult);
            }
        });

    }

    private void numberGenerator(String text, TextView txtResult) {
        // Vadidar conteúdo
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show();
            return;
        }

        int qtd = Integer.parseInt(text);

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show();
            return;
        }


        HashSet<Integer> numbers = new HashSet<Integer>();
        Random random = new Random();

        while (!(numbers.size() == qtd)) {
            int number = random.nextInt(60);
            numbers.add(number + 1);
        }

        String txt =
                numbers.toString()
                        .replace(",", "-")
                        .replace("[", "")
                        .replace("]", "");

        txtResult.setText(txt);

        SharedPreferences.Editor editor =
                 prefs.edit()
                .putString("lastResult",txtResult.getText().toString());
        editor.apply(); // assicrona

    }
}