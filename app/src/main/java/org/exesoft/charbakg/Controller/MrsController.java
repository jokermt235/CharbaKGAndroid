package org.exesoft.charbakg.Controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.exesoft.charbakg.Adapter.MrsAdapter;
import org.exesoft.charbakg.View.MRSActivity;
import org.exesoft.charbakg.View.MRSFormActivity;
import java.util.ArrayList;
import java.util.Map;

public class MrsController {
    private static String TAG = "MrsController";
    private static  int sum = 0;

    public static void save(final MRSFormActivity activity, Map<String, Object> mrs){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            mrs.put("phone",user.getPhoneNumber());
            db.collection("mrs")
                    .add(mrs)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            activity.startActivity(new Intent(activity.getApplicationContext(), MRSActivity.class));
                            activity.finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    public static void mrs(final MRSActivity activity, long dateFrom, long dateTo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        activity.getMrsListView().setAdapter(null);
        activity.getMrsSum().setText(Integer.toString(sum));
        activity.getProgressBar().setVisibility(View.VISIBLE);
        sum = 0;
        if(user != null) {
            db.collection("mrs").whereGreaterThan("added",dateFrom).
                    whereLessThan("added",dateTo).whereEqualTo("phone",user.getPhoneNumber()).orderBy("added", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Loaded success");
                        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            arrayList.add(document.getData());
                            sum+= (long)document.getData().get("amount");
                         }
                        MrsAdapter livestockAdapter = new MrsAdapter(activity);
                        livestockAdapter.setItems(arrayList);
                        activity.getMrsListView().setAdapter(livestockAdapter);
                        activity.getProgressBar().setVisibility(View.GONE);
                        activity.getMrsSum().setText(Integer.toString(sum));
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }
}
