package com.example.bloodbank.ui.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.bloodbank.R;
import static com.example.bloodbank.utlies.Extension.showSnake;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int splashInterval = 1500;
        ConstraintLayout ll = findViewById(R.id.ll);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                assert cManager != null;
                NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                if (nInfo != null && nInfo.isConnected()) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    String msg = getResources().getString(R.string.NetWorkCheck);
                    showSnake(ll, msg);
                }
                this.finish();
            }

            private void finish() {


            }
        }, splashInterval);
    }
}