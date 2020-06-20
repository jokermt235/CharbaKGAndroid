package org.exesoft.charbakg.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.exesoft.charbakg.Callback.Feed.OnUpdateDocument;
import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class SimpleLoader {
    public static String TAG = "SimpleLoader";
    public static void update(final  String collection,String documentStrRef, Map<String, Object> data, final OnUpdateDocument result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            data.put("phone",user.getPhoneNumber());
            //data.put("uid", UUID.randomUUID().toString());
            data.remove("_ref");
            db.collection(collection).document(documentStrRef)
                    .update(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            result.updated(true);
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
    public static void save(final  String collection, Map<String, Object> data, final OnSavedResult result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            data.put("phone",user.getPhoneNumber());
            data.put("uid", UUID.randomUUID().toString());
            data.put("added", new Date().getTime());
            db.collection(collection)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            result.onSave(true);
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
    public static void filter(final  String collection, long dateFrom, long dateTo, final OnSimpleLoaderResult result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            db.collection(collection).whereGreaterThanOrEqualTo("added",dateFrom).
                    whereLessThanOrEqualTo("added",dateTo).whereEqualTo("phone",user.getPhoneNumber()).orderBy("added", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG + "_" + collection, "Loaded success");
                        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            arrayList.add(document.getData());
                        }
                        result.onResult(arrayList);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }

    public static void filter(final  String collection,String key, Object value , final OnSimpleLoaderResult result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            db.collection(collection).whereEqualTo(key, value).whereEqualTo("phone",user.getPhoneNumber()).whereEqualTo("phone",user.getPhoneNumber()).orderBy("added", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG + "_" + collection, "Loaded success");
                        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Map<String, Object> item = document.getData();
                            item.put("_ref",document.getId());
                            arrayList.add(item);
                        }
                        result.onResult(arrayList);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }

    public static void filter(final  Map<String,Object> options, long dateFrom, long dateTo, final OnSimpleLoaderResult result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            db.collection(options.get("collection").toString()).whereGreaterThanOrEqualTo("added",dateFrom).
                    whereLessThanOrEqualTo("added",dateTo).whereEqualTo("phone",user.getPhoneNumber()).
                    whereEqualTo("owner",options.get("owner").toString()).orderBy("added", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG + "_" + options.get("collection").toString(), "Loaded success");
                        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            arrayList.add(document.getData());
                        }
                        result.onResult(arrayList);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }

    public static void filter(final  String collection,final OnSimpleLoaderResult result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            db.collection(collection).whereEqualTo("phone",user.getPhoneNumber()).orderBy("added", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG + "_" + collection, "Loaded success");
                        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Map<String, Object> item = document.getData();
                            item.put("_ref",document.getId());
                            arrayList.add(item);
                        }
                        result.onResult(arrayList);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }

    public static void filter(final  String collection,final OnSimpleLoaderResult result,boolean phone){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            db.collection(collection).orderBy("added", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG + "_" + collection, "Loaded success");
                        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Map<String, Object> item = document.getData();
                            item.put("_ref",document.getId());
                            arrayList.add(item);
                        }
                        result.onResult(arrayList);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }
    public static void uploadImages(String collection, ArrayList<Bitmap> images, String uid){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef1 = FirebaseStorage.getInstance().getReference().child(collection + "/" + uid);
        removeChilds(storageRef1);
        Log.d(TAG, collection + "/" + uid);
        Iterator it = images.iterator();
        while(it.hasNext())
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap  bitmap = (Bitmap)it.next();
            Log.d(TAG, "Start uploading");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            StorageReference storageRef = storage.getReference().child(collection + "/" + uid + "/" + UUID.randomUUID().toString() + ".jpeg");
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
                    Log.d(TAG, "Stop uploading");
                }
            });
        }
    }

    private static void removeChilds(StorageReference ref){
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                StorageReference iRef = null;
                for (StorageReference item : listResult.getItems()) {
                    if (item != null) {
                        iRef = item;
                        if (iRef != null) {
                            iRef.delete();
                        }
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
