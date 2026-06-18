package com.example.photosender;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int VPN_REQUEST_CODE = 100;

    private Button btnConnect;

    // کانفیگ‌ها (فقط host)
    private final String[] HOSTS = {
            "bot.shopver.ir",
            "1.1.1.1"
    };

    private final int PORT = 995;

    private String bestHost = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(v -> {
            findBestConfig();
        });
    }

    // ===================== انتخاب بهترین کانفیگ =====================
    private void findBestConfig() {

        new Thread(() -> {

            int bestTime = Integer.MAX_VALUE;
            bestHost = "";

            for (String host : HOSTS) {

                int time = ConfigTester.testLatency(host, PORT);

                if (time > 0 && time < bestTime) {
                    bestTime = time;
                    bestHost = host;
                }
            }

            runOnUiThread(() -> {

                if (bestHost.isEmpty()) {
                    Toast.makeText(this,
                            "هیچ سروری وصل نشد",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this,
                        "Best Config: " + bestHost,
                        Toast.LENGTH_SHORT).show();

                startVpn();
            });

        }).start();
    }

    // ===================== شروع VPN =====================
    private void startVpn() {

        Intent intent = VpnService.prepare(this);

        if (intent != null) {
            startActivityForResult(intent, VPN_REQUEST_CODE);
        } else {
            startService(new Intent(this, MyVpnService.class));
            Toast.makeText(this, "VPN Started", Toast.LENGTH_SHORT).show();
        }
    }

    // ===================== نتیجه اجازه VPN =====================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VPN_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                startService(new Intent(this, MyVpnService.class));

                Toast.makeText(this,
                        "VPN Connected → " + bestHost,
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,
                        "VPN Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
