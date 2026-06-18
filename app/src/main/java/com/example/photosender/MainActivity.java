package com.example.photosender;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(v -> {
            Toast.makeText(this, "Connect clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
