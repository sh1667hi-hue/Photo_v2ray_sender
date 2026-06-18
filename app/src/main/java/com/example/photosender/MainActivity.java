package com.example.photosender;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int VPN_REQUEST_CODE = 100;

    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(v -> {

            Intent intent = VpnService.prepare(this);

            if (intent != null) {
                startActivityForResult(intent, VPN_REQUEST_CODE);
            } else {
                startService(new Intent(this, MyVpnService.class));
                Toast.makeText(this, "VPN Started", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VPN_REQUEST_CODE && resultCode == RESULT_OK) {
            startService(new Intent(this, MyVpnService.class));
            Toast.makeText(this, "VPN Permission Granted", Toast.LENGTH_SHORT).show();
        } else if (requestCode == VPN_REQUEST_CODE) {
            Toast.makeText(this, "VPN Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
