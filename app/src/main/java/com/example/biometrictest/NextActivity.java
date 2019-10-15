package com.example.biometrictest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import java.util.concurrent.Executors;

public class NextActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, NextActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        biometricPrompt = new BiometricPrompt(this, Executors.newSingleThreadExecutor(), createCallback());
    }

    public void showBiometric(View view) {
        biometricPrompt.authenticate(new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Title")
                .setDeviceCredentialAllowed(true)
                .build());
    }

    private BiometricPrompt.AuthenticationCallback createCallback() {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(NextActivity.this, "OK", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(NextActivity.this, errString, Toast.LENGTH_LONG).show());
            }
        };
    }
}
