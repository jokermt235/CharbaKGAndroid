package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.exesoft.charbakg.Component.Auth;
import org.exesoft.charbakg.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(!Auth.isSigned()){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
