package org.exesoft.charbakg.Controller;

import android.graphics.Bitmap;
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
import org.exesoft.charbakg.Adapter.OffspringAdapter;
import org.exesoft.charbakg.Model.Offspring;
import org.exesoft.charbakg.View.OffspringActivity;

import java.util.ArrayList;

public class OffspringController {
    private static String TAG = "OffspringController";
    private static int sum  = 0;

    public static void save(Offspring offspring) {
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if( user != null) {
            Log.d(TAG, " Save mothod says " + offspring.getOwner());
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("offsprings").child(user.getPhoneNumber()).push().setValue(offspring);
        }
    }

    public static void save(Offspring offspring, ArrayList<Bitmap> images) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("offsprings").push().setValue(offspring);
        //uploadImages(images,livestock.getUid());
    }

    public static void offsprings(final OffspringActivity activity, long dateFrom, long dateTo){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<Offspring> arrayList = new ArrayList<>();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            final String owner = activity.getOwner();
            final Query data = mDatabase.child("offsprings").child(user.getPhoneNumber());
            sum = 0;
            activity.getOffspringList().setAdapter(null);
            activity.getSum().setText("0");
            activity.getProgressBar().setVisibility(View.VISIBLE);
            data.orderByChild("added").startAt(dateFrom).endAt(dateTo).limitToLast(300).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Offspring item = dataSnapshot.getValue(Offspring.class);
                    if(item.getOwner().equals(owner)) {
                        arrayList.add(item);
                        OffspringAdapter offspringAdapter = new OffspringAdapter(activity);
                        offspringAdapter.setItems(arrayList);
                        activity.getOffspringList().setAdapter(offspringAdapter);
                        sum++;
                        activity.getSum().setText(Integer.toString(sum));
                        Log.d(TAG, "Filter worked");
                        activity.getProgressBar().setVisibility(View.INVISIBLE);
                    }
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
