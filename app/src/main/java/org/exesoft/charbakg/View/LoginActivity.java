package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.exesoft.charbakg.Component.Auth;
import org.exesoft.charbakg.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton ;

    EditText  phoneNumber;

    String  errorMessage;

    private ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);

        phoneNumber = findViewById(R.id.loginPhone);

        loginProgress = findViewById(R.id.loginProgressBar);
        errorMessage = getString(R.string.error_empty_phone);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(phoneNumber.getText())) {
                    String phone = phoneNumber.getText().toString();
                    phone.replace("\\s+","");
                    loginProgress.setVisibility(View.VISIBLE);
                    Auth.signIn(LoginActivity.this, phone );
                }else{
                    phoneNumber.setError(errorMessage);
                }

            }
        });
    }

    public ProgressBar getLoginProgress(){
        return  loginProgress;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
