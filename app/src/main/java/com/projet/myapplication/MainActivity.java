package com.projet.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.projet.myapplication.broadcastreceiver.PowerConnectionReceiver;
import com.projet.myapplication.fragment.InputFragment;
import com.projet.myapplication.service.ForegroundService;

public class MainActivity extends AppCompatActivity {
    private PowerConnectionReceiver powerConnectionReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerConnectionReceiver = new PowerConnectionReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(powerConnectionReceiver, filter);

        // Start the service
        startService(new Intent(this, ForegroundService.class));

        InputFragment fragmentA = new InputFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentA)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (powerConnectionReceiver != null) {
            unregisterReceiver(powerConnectionReceiver);
        }
    }
}