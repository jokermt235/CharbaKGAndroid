package org.exesoft.charbakg.Callback;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.exesoft.charbakg.View.CodeConfirmActivity;
import org.exesoft.charbakg.Component.Auth;

public class OnVerificationCallback extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {
    private  AppCompatActivity activity;
    private  String verificationId;
    public OnVerificationCallback(AppCompatActivity activity){
        this.activity = activity;
    }
    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        Auth.signInWithPhoneCredentials(this.activity , phoneAuthCredential);
        //Toast.makeText(activity.getApplicationContext(),"Verification passed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
        Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCodeSent(@NonNull String verificationId,
                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
        Intent intent = new Intent(activity, CodeConfirmActivity.class);
        intent.putExtra("verificationId", verificationId);
        activity.startActivity(intent);
        this.verificationId = verificationId;
        activity.finish();

    }
}
