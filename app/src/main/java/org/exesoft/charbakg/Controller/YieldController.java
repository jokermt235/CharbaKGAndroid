package org.exesoft.charbakg.Controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.exesoft.charbakg.Adapter.YieldAdapter;
import org.exesoft.charbakg.Model.Yield;
import org.exesoft.charbakg.View.YieldActivity;

import java.util.ArrayList;

public class YieldController {
    private static String TAG = "YieldController";
    private static  double sum = 0.0;
    public static void save(Yield yield){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("yields").child(user.getPhoneNumber()).push().setValue(yield);
        }
    }
    public static ArrayList<Yield> yields(final YieldActivity activity){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<Yield> arrayList = new ArrayList<>();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Query data = mDatabase.child("yields").child(user.getPhoneNumber());
            sum = 0.0;
            data.limitToLast(300).orderByChild("owner").equalTo(activity.getOwner()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Yield item = dataSnapshot.getValue(Yield.class);
                    if(item != null) {
                        arrayList.add(item);
                        sum+= item.getAmount();
                        activity.getYieldSum().setText(Double.toString(sum));
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.d(TAG, "onChildChange method");
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return arrayList;
    }
}
