package com.projet.myapplication.service;

// ForegroundService.java
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Random;

// ForegroundService.java
import android.os.Handler;

import com.projet.myapplication.AppDatabase;
import com.projet.myapplication.model.FreeMemory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ForegroundService extends Service {

    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String TAG = "ForegroundService";
    private static final int NOTIFICATION_ID = 1;

    private Random random = new Random();
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());

        // Initialize the handler to run a task every 1 hour
        handler = new Handler();
        handler.postDelayed(runnable, 3600);
    }

    private final Executor executor = Executors.newSingleThreadExecutor();

    // Runnable to be executed periodically
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long freeMemory = Runtime.getRuntime().freeMemory();
            executor.execute(() -> {
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                appDatabase.freeMemoryDao().insertFreeMemory(new FreeMemory(freeMemory, System.currentTimeMillis()));
                Log.d(TAG, "ForegroundService is running" + appDatabase.freeMemoryDao().getLastFreeMemory().getValue());
            });
            handler.postDelayed(runnable, 3600 * 1000);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Return START_STICKY to keep the service running
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the scheduled callbacks when the service is destroyed
        handler.removeCallbacks(runnable);
        Log.d(TAG, "ForegroundService destroyed");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Running...")
                .build();
    }
}


