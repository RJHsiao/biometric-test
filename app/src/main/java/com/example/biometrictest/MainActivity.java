package com.example.biometrictest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        biometricPrompt = new BiometricPrompt(this, Executors.newSingleThreadExecutor(), createCallback());
    }

    public void goNext(View view) {
        NextActivity.launch(this);
    }

    public void showBiometricAndGoNext(View view) {
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
                NextActivity.launch(MainActivity.this);
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.this, errString, Toast.LENGTH_LONG).show());
            }
        };
    }
}
