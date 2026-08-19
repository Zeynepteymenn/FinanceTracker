package com.example.financetracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IncomeActivity extends AppCompatActivity {

    private EditText etIncomeAmount;
    private Button btnSaveIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        etIncomeAmount = findViewById(R.id.etIncomeAmount);
        btnSaveIncome = findViewById(R.id.btnSaveIncome);

        btnSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String incomeText = etIncomeAmount.getText().toString().trim();

                if (incomeText.isEmpty()) {
                    Toast.makeText(
                            IncomeActivity.this,
                            "Lütfen bir gelir tutarı girin.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                try {
                    double newIncome = Double.parseDouble(incomeText);

                    if (newIncome <= 0) {
                        Toast.makeText(
                                IncomeActivity.this,
                                "Gelir tutarı 0'dan büyük olmalıdır.",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    SharedPreferences preferences =
                            getSharedPreferences("FinanceData", MODE_PRIVATE);

                    float currentIncome =
                            preferences.getFloat("totalIncome", 0f);

                    float updatedIncome =
                            currentIncome + (float) newIncome;

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putFloat("totalIncome", updatedIncome);
                    editor.apply();

                    Toast.makeText(
                            IncomeActivity.this,
                            "Gelir kaydedildi: " + newIncome + " TL",
                            Toast.LENGTH_SHORT
                    ).show();

                    etIncomeAmount.setText("");

                } catch (NumberFormatException e) {
                    Toast.makeText(
                            IncomeActivity.this,
                            "Geçerli bir sayı girin.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}