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

import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Callback.OnVerificationCallback;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;
import org.exesoft.charbakg.View.HomeActivity;
import org.exesoft.charbakg.View.PermissionActivity;

import java.util.ArrayList;
import java.util.Map;
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

    public  static  void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public static  void signInWithPhoneCredentials(final AppCompatActivity activity, PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = task.getResult().getUser();
                    SimpleLoader.filter("user","status",true, new OnSimpleLoaderResult(){
                        @Override
                        public void onResult(ArrayList<Map<String, Object>> items) {
                            super.onResult(items);
                            if(!items.isEmpty()){
                                activity.startActivity(new Intent(activity, HomeActivity.class));
                                activity.finish();
                            }else{
                                activity.startActivity(new Intent(activity, PermissionActivity.class));
                                activity.finish();
                            }
                        }
                    });
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.login_error_message),  Toast.LENGTH_LONG).show();
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        });
    }
}
