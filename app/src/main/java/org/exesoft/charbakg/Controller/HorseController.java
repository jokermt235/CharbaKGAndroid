package org.exesoft.charbakg.Controller;

import android.util.Log;
import android.view.View;

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

import org.exesoft.charbakg.Adapter.HorseAdapter;
import org.exesoft.charbakg.Model.Horse;
import org.exesoft.charbakg.View.HorseActivity;

import java.util.ArrayList;

public class HorseController {
    private static String TAG = "HorseController";
    private static int sum = 0;
    public static void save(Horse horse){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("horses").child(user.getPhoneNumber()).push().setValue(horse);
        }
    }

    public static void horses(final HorseActivity activity, long dateFrom , long dateTo){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<Horse> arrayList = new ArrayList<>();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, Long.toString(dateFrom) + " " + Long.toString(dateTo));
        if(user != null){
            final Query data = mDatabase.child("horses").child(user.getPhoneNumber());
            sum = 0;
            activity.getHorseListView().setAdapter(null);
            activity.getProgressBar().setVisibility(View.VISIBLE);
            activity.getSum().setText("0");
            data.orderByChild("added").startAt(dateFrom).endAt(dateTo).limitToLast(300).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Horse item = dataSnapshot.getValue(Horse.class);
                    arrayList.add(item);
                    sum++;
                    activity.getSum().setText(Integer.toString(sum));
                    activity.getProgressBar().setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    /*Livestock item = (Livestock)dataSnapshot.child(dataSnapshot.getValue().toString()).getValue();
                    arrayList.add(item);
                    Log.d(TAG, item.toString());*/
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

    }
}
