package org.exesoft.charbakg.Component;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.exesoft.charbakg.Callback.OnVerificationCallback;
import org.exesoft.charbakg.View.HomeActivity;

import java.util.concurrent.TimeUnit;

public class Auth {
    public static void signIn(AppCompatActivity activity, String phoneNumber){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                new OnVerificationCallback(activity));        // OnVerificationStateChangedCallbacks
    }

    public static void signUp(PhoneAuthCredential credential ){

    }

    public  static  boolean isSigned(){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return true;
        }
        return false;

    }

    public static  void signInWithPhoneCredentials(final AppCompatActivity activity, PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = task.getResult().getUser();
                    Toast.makeText(activity.getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                    activity.finish();
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        });
    }
}
