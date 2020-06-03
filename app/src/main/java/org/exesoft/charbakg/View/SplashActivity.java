package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Component.Auth;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(!Auth.isSigned()){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            SimpleLoader.filter("user",new OnSimpleLoaderResult(){
                @Override
                public void onResult(ArrayList<Map<String, Object>> items) {
                    super.onResult(items);
                    if(!items.isEmpty()){
                        if((boolean)items.get(0).get("status")){
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
                            finish();
                        }
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
