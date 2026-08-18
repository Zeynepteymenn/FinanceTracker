package com.example.financetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewPayments;
    private Button btnAddPayment;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPayments = findViewById(R.id.listViewPayments);
        btnAddPayment = findViewById(R.id.btnAddPayment);

        paymentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, paymentList);
        listViewPayments.setAdapter(adapter);

        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentList.add("Yeni Ödeme - " + System.currentTimeMillis());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Ödeme Eklendi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}