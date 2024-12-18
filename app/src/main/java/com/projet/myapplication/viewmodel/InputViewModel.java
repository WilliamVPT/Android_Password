package com.projet.myapplication.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.projet.myapplication.AppDatabase;
import com.projet.myapplication.R;
import com.projet.myapplication.fragment.InputFragment;
import com.projet.myapplication.fragment.OutputFragment;
import com.projet.myapplication.localisation;
import com.projet.myapplication.model.FreeMemory;
import com.projet.myapplication.model.PowerConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class InputViewModel extends ViewModel {
    public ObservableField<String> user_word = new ObservableField<>();
    private InputFragment inputFragment;
    private final Executor executor = Executors.newSingleThreadExecutor();


    public InputViewModel(InputFragment inputFragment) {
        this.inputFragment = inputFragment;
    }

    public void change_fragment() {
        // Create a Bundle with a message
        Bundle bundle = new Bundle();
        bundle.putString("key_message", generatePassword());

        // Set the fragment result
        inputFragment.getParentFragmentManager().setFragmentResult("request_key", bundle);

        // Replace FragmentA with FragmentB
        OutputFragment fragmentB = new OutputFragment();
        inputFragment.getParentFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentB)
                .addToBackStack(null)
                .commit();
    }

    private String generatePassword() {
        Double[] weather = {0.0, 0.0, 0.0};
        try {
            weather = getWeather().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long[] lastPowerConnectionTimestamp = {0};
        long[] lastFreeMemoryValue = {0};

        CountDownLatch latch = new CountDownLatch(1);
        executor.execute(() -> {
            try {
                AppDatabase appDatabase = AppDatabase.getInstance(inputFragment.getContext());
                PowerConnection lastPowerConnection = appDatabase.powerConnectionDao().getLastPowerConnection();
                if (lastPowerConnection != null) {
                    lastPowerConnectionTimestamp[0] = lastPowerConnection.getTimestamp();
                }
                FreeMemory lastFreeMemory = appDatabase.freeMemoryDao().getLastFreeMemory();
                if (lastFreeMemory != null) {
                    lastFreeMemoryValue[0] = lastFreeMemory.getValue();
                }
            } finally {
                // Count down the latch to signal completion
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }

        StringBuilder passwordBuilder = new StringBuilder();
        passwordBuilder.append(weather[0]);
        passwordBuilder.append(weather[1]);
        passwordBuilder.append(weather[2]);
        passwordBuilder.append(lastPowerConnectionTimestamp[0]);
        passwordBuilder.append(lastFreeMemoryValue[0]);
        passwordBuilder.append(user_word.get());

        return passwordBuilder.toString();
    }

    private CompletableFuture<Double[]> getWeather() {
        CompletableFuture<Double[]> temperatureFuture = new CompletableFuture<>();

        localisation loca = new localisation((AppCompatActivity) inputFragment.requireActivity());

        String meteo = "https://api.open-meteo.com/v1/forecast?latitude=" + loca.getLongitude() + "&longitude=" + loca.getLatitude() + "&current=temperature_2m,precipitation,wind_speed_10m";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(meteo)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                temperatureFuture.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        try {
                            final String json = responseBody.string();
                            JSONObject jsonObject = new JSONObject(json);

                            // Retrieve the temperature from the JSON data
                            double temperature = jsonObject.getJSONObject("current").getDouble("temperature_2m");
                            double precipitation = jsonObject.getJSONObject("current").getDouble("precipitation");
                            double windSpeed = jsonObject.getJSONObject("current").getDouble("wind_speed_10m");

                            Double[] vals = {temperature, precipitation, windSpeed};
                            // Complete the future with the temperature value
                            temperatureFuture.complete(vals);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            temperatureFuture.completeExceptionally(e);
                        }
                    }
                } else {
                    temperatureFuture.completeExceptionally(new RuntimeException("HTTP request failed with code " + response.code()));
                }
            }
        });

        return temperatureFuture;
    }
}
