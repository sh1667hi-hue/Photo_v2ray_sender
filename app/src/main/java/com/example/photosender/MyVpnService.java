package com.example.photosender;

import android.net.VpnService;
import android.os.ParcelFileDescriptor;
import android.content.Intent;

public class MyVpnService extends VpnService {

    private ParcelFileDescriptor vpnInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Builder builder = new Builder();

        builder.setSession("PhotoSender VPN");

        builder.addAddress("10.0.0.2", 32);
        builder.addRoute("0.0.0.0", 0);

        vpnInterface = builder.establish();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            if (vpnInterface != null) {
                vpnInterface.close();
            }
        } catch (Exception ignored) {}

        super.onDestroy();
    }
}
