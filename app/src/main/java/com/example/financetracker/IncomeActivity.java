package com.example.financetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IncomeActivity extends AppCompatActivity {

    EditText etIncomeAmount;
    Button btnSaveIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        etIncomeAmount = findViewById(R.id.etIncomeAmount);
        btnSaveIncome = findViewById(R.id.btnSaveIncome);

        btnSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String income = etIncomeAmount.getText().toString();
                if (!income.isEmpty()) {
                    Toast.makeText(IncomeActivity.this, "Gelir kaydedildi: " + income, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(IncomeActivity.this, "Lütfen bir değer girin.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}