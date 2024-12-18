package com.projet.myapplication.broadcastreceiver;// ChargerReceiver.java
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.projet.myapplication.AppDatabase;
import com.projet.myapplication.model.PowerConnection;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PowerConnectionReceiver extends BroadcastReceiver {
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // Charger connected
            Log.d("ChargerReceiver", "Phone charger connected");
            executor.execute(() -> {
                AppDatabase appDatabase = AppDatabase.getInstance(context);
                appDatabase.powerConnectionDao().insertPowerConnection(new PowerConnection(System.currentTimeMillis()));
            });
        }
    }
}
