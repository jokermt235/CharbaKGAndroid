package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.exesoft.charbakg.Component.Auth;
import org.exesoft.charbakg.R;

public class CodeConfirmActivity extends AppCompatActivity {

    private Button sendCodeBtn;
    private EditText codeConfirm;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_confirm);
        sendCodeBtn = findViewById(R.id.codeConfirmLogin);
        codeConfirm = findViewById(R.id.codeConfirmCode);

        verificationId = getIntent().getStringExtra("verificationId");

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,codeConfirm.getText().toString());
                Auth.signInWithPhoneCredentials(CodeConfirmActivity.this, phoneAuthCredential);
            }
        });
    }
}
