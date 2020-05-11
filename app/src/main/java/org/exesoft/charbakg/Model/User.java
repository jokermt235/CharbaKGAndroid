package org.exesoft.charbakg.Model;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUserMetadata;

import java.util.UUID;


public class User{
    private String uid = UUID.randomUUID().toString();
    private  boolean anonymous = true;
    private String phoneNumber;
    @NonNull
    public String getUid() {
        return uid;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    @Nullable
    public String getDisplayName() {
        return null;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return null;
    }

    @Nullable
    public String getEmail() {
        return null;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEmailVerified() {
        return false;
    }

    @Nullable

    public FirebaseUserMetadata getMetadata() {
        return null;
    }

}
