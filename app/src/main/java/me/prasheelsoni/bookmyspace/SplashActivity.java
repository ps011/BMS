package me.prasheelsoni.bookmyspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import me.prasheelsoni.bookmyspace.utils.SharedPrefUtil;

public class SplashActivity extends AppCompatActivity {
SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPrefUtil.retrieveString("loggedin","0").equals("1")){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
            }
        },3000);

    }
    //TODO : Extend WelcomeActivity and edit splash screen.
}
