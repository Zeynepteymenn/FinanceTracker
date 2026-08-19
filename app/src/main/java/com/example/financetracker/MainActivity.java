package com.example.financetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listViewPayments;

    private Button btnAddIncome;
    private Button btnAddPayment;
    private Button btnReports;
    private Button btnRegister;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> paymentList;

    private static final String PREF_NAME = "FinanceData";
    private static final String PAYMENT_HISTORY_KEY = "paymentHistory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ekrandaki bileşenleri bağla
        listViewPayments = findViewById(R.id.listViewPayments);

        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnAddPayment = findViewById(R.id.btnAddPayment);
        btnReports = findViewById(R.id.btnReports);
        btnRegister = findViewById(R.id.btnRegister);

        // Ödeme geçmişini hazırla
        paymentList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                paymentList
        );

        listViewPayments.setAdapter(adapter);

        // Daha önce kaydedilen ödemeleri getir
        loadPaymentHistory();

        // GELİR EKLE
        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        new Intent(MainActivity.this, IncomeActivity.class);

                startActivity(intent);
            }
        });

        // ÖDEME / GİDER EKLE
        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showExpenseDialog();
            }
        });

        // RAPORLAR
        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        new Intent(MainActivity.this, ReportActivity.class);

                startActivity(intent);
            }
        });

        // KAYIT OL
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        new Intent(MainActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });
    }

    // Ödeme tutarını soran pencere
    private void showExpenseDialog() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Ödeme Ekle");
        builder.setMessage("Ödeme tutarını girin:");

        final EditText input = new EditText(MainActivity.this);

        input.setHint("Örn: 750");
        input.setInputType(
                InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        builder.setView(input);

        builder.setPositiveButton(
                "Kaydet",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                        String expenseText =
                                input.getText()
                                        .toString()
                                        .trim();

                        if (expenseText.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Lütfen ödeme tutarı girin.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        try {

                            double expense =
                                    Double.parseDouble(expenseText);

                            if (expense <= 0) {

                                Toast.makeText(
                                        MainActivity.this,
                                        "Ödeme tutarı 0'dan büyük olmalıdır.",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            saveExpense(expense);

                        } catch (NumberFormatException e) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Geçerli bir tutar girin.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }
        );

        builder.setNegativeButton(
                "İptal",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                        dialog.cancel();
                    }
                }
        );

        builder.show();
    }

    // Gideri kaydet
    private void saveExpense(double expense) {

        SharedPreferences preferences =
                getSharedPreferences(
                        PREF_NAME,
                        MODE_PRIVATE
                );

        float currentExpense =
                preferences.getFloat(
                        "totalExpense",
                        0f
                );

        float updatedExpense =
                currentExpense + (float) expense;

        preferences.edit()
                .putFloat(
                        "totalExpense",
                        updatedExpense
                )
                .apply();

        // Tarih ve saat
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "dd.MM.yyyy HH:mm",
                        Locale.getDefault()
                );

        String currentDate =
                dateFormat.format(new Date());

        String payment =
                String.format(
                        Locale.getDefault(),
                        "%.2f TL - %s",
                        expense,
                        currentDate
                );

        paymentList.add(0, payment);

        adapter.notifyDataSetChanged();

        savePaymentHistory();

        Toast.makeText(
                MainActivity.this,
                "Ödeme kaydedildi: "
                        + expense
                        + " TL",
                Toast.LENGTH_SHORT
        ).show();
    }

    // Ödeme listesini cihazda sakla
    private void savePaymentHistory() {

        SharedPreferences preferences =
                getSharedPreferences(
                        PREF_NAME,
                        MODE_PRIVATE
                );

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0;
             i < paymentList.size();
             i++) {

            builder.append(paymentList.get(i));

            if (i < paymentList.size() - 1) {
                builder.append("###");
            }
        }

        preferences.edit()
                .putString(
                        PAYMENT_HISTORY_KEY,
                        builder.toString()
                )
                .apply();
    }

    // Eski ödemeleri tekrar yükle
    private void loadPaymentHistory() {

        SharedPreferences preferences =
                getSharedPreferences(
                        PREF_NAME,
                        MODE_PRIVATE
                );

        String history =
                preferences.getString(
                        PAYMENT_HISTORY_KEY,
                        ""
                );

        if (history != null &&
                !history.isEmpty()) {

            String[] payments =
                    history.split("###");

            for (String payment : payments) {

                paymentList.add(payment);
            }

            adapter.notifyDataSetChanged();
        }
    }
}