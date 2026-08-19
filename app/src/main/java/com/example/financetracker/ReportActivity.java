package com.example.financetracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    private TextView tvTotalIncome;
    private TextView tvTotalExpense;
    private TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvBalance = findViewById(R.id.tvBalance);

        loadReportData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReportData();
    }

    private void loadReportData() {

        SharedPreferences preferences =
                getSharedPreferences("FinanceData", MODE_PRIVATE);

        float totalIncome =
                preferences.getFloat("totalIncome", 0f);

        float totalExpense =
                preferences.getFloat("totalExpense", 0f);

        float balance =
                totalIncome - totalExpense;

        tvTotalIncome.setText(
                String.format("Toplam Gelir: %.2f TL", totalIncome)
        );

        tvTotalExpense.setText(
                String.format("Toplam Gider: %.2f TL", totalExpense)
        );

        tvBalance.setText(
                String.format("Bakiye: %.2f TL", balance)
        );
    }
}