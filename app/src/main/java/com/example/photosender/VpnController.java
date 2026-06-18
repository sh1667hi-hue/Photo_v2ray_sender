package com.example.photosender;

import android.content.Context;
import android.content.Intent;
import android.net.VpnService;

public class VpnController {

    private Context context;

    public VpnController(Context context) {
        this.context = context;
    }

    public void startVpn() {
        Intent intent = VpnService.prepare(context);

        if (intent != null) {
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context)
                        .startActivityForResult(intent, 100);
            }
        } else {
            context.startService(new Intent(context, MyVpnService.class));
        }
    }

    public void stopVpn() {
        context.stopService(new Intent(context, MyVpnService.class));
    }
}
