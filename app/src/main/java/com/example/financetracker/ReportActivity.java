package com.example.financetracker;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    ListView lvReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // lvReports öğesi doğru şekilde bağlanmalı
        lvReports = findViewById(R.id.lvReports);

        // Burada raporlar verilerini listeleme işlemi yapılabilir
        // Örneğin, geçici bir liste veya veritabanı kullanarak listeyi doldurabilirsiniz
    }
}