package org.exesoft.charbakg.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.exesoft.charbakg.Adapter.LivestockAdapter;
import org.exesoft.charbakg.View.LivestockActivity;
import org.exesoft.charbakg.View.LsformActivity;
import org.exesoft.charbakg.View.MRSActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class LivestockController {
    private static String TAG = "LivestockController";
    private static  int sum = 0;
    public static void save(final LsformActivity activity, Map<String,Object> krs, ArrayList<Bitmap> images) {
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(user != null) {
            krs.put("phone",user.getPhoneNumber());
            db.collection("krs")
                    .add(krs)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            activity.startActivity(new Intent(activity.getApplicationContext(), LivestockActivity.class));
                            activity.finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
            uploadImages(images, krs.get("uid").toString());
        }
    }

    private static void uploadImages(ArrayList<Bitmap> images,String uid){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (Bitmap bitmap:images) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            StorageReference storageRef = storage.getReference().child("krs/" + uid + "/" + UUID.randomUUID().toString() + ".jpeg");

            UploadTask uploadTask = storageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d(TAG, "Failed yo upload file to storage");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d(TAG, "Images saved on storage");
                }
            });
        }

    }

    public static void livestocks(final LivestockActivity activity, long dateFrom, long dateTo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            sum = 0;
            activity.getLivestockList().setAdapter(null);
            activity.getLivestockSum().setText(Integer.toString(sum));
            activity.getProgressBar().setVisibility(View.VISIBLE);
            db.collection("krs").whereGreaterThan("added",dateFrom).
                    whereLessThan("added",dateTo).whereEqualTo("phone",user.getPhoneNumber()).orderBy("added", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Loaded success");
                        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            arrayList.add(document.getData());
                        }
                        LivestockAdapter livestockAdapter = new LivestockAdapter(activity);
                        livestockAdapter.setItems(arrayList);
                        activity.getLivestockList().setAdapter(livestockAdapter);
                        activity.getProgressBar().setVisibility(View.GONE);
                        activity.getLivestockSum().setText(Integer.toString(querySnapshot.size()));
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }
}
