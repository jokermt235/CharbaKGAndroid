package org.exesoft.charbakg.Modal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.exesoft.charbakg.Callback.Feed.OnUpdateDocument;
import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;
import org.exesoft.charbakg.View.FeedActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedReport {

    private static final String TAG = "FeedReport";
    private FeedActivity activity;
    private View dialogContent;
    private  AlertDialog.Builder addDialog;
    private AlertDialog dialog;
    private EditText amountInput;
    private Spinner feedTypeSelect;
    private ImageButton feedFormCloseBtn;

    public FeedReport(final FeedActivity activity){
        this.activity = activity;
        addDialog = new AlertDialog.Builder(activity);
        addDialog.setView(getContent());
        dialog = addDialog.create();
        setOkListener();
        //Init amountInput EditText
        amountInput = dialogContent.findViewById(R.id.feedFormAmount);
        //Init feedTypeSelect Spinner
        feedTypeSelect = dialogContent.findViewById(R.id.feedFormFeedType);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("feed_names")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        arrayList.add(document.getData());
                    }
                    SimpleAdapter adapter = new SimpleAdapter(activity, arrayList, android.R.layout.simple_list_item_2,
                            new String[]{"name","unit"},
                            new int[]{android.R.id.text1,android.R.id.text2});
                    feedTypeSelect.setAdapter(adapter);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        //Init feedFormCloseBtn ImageButton
        feedFormCloseBtn = dialogContent.findViewById(R.id.feedFormCloseBtn);
        feedFormCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void show(){
        dialog.show();
    }

    private View getContent(){
        dialogContent = LayoutInflater.from(activity).inflate(R.layout.feed_report_form,null);
        return dialogContent;
    }

    private void setOkListener(){
        dialogContent.findViewById(R.id.feedFormSaveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String,Object> feedType = (Map<String,Object>)feedTypeSelect.getSelectedItem();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Update data
                SimpleLoader.filter("feed_report","name",feedType.get("name").toString(), new OnSimpleLoaderResult(){
                    @Override
                    public void onResult(ArrayList<Map<String, Object>> items) {
                        super.onResult(items);
                        if(items.size() != 0) {
                            for (Map<String, Object> item : items) {
                                item.put("added",new Date().getTime());
                                item.put("amount",Integer.parseInt(amountInput.getText().toString().isEmpty() ? "0":amountInput.getText().toString()));
                                String owner = (String)item.get("owner");
                                if(owner.equals(activity.getIntent().getStringExtra("owner"))) {
                                    SimpleLoader.update("feed_report", item.get("_ref").toString(), item, new OnUpdateDocument() {
                                        @Override
                                        public void updated(boolean status) {
                                            super.updated(status);
                                            if (status) {
                                                activity.setDateTo(new Date());
                                                activity.loadLocal();
                                                dialog.cancel();
                                            }
                                        }
                                    });
                                }
                            }
                        }else{
                            Map<String, Object> feed = new HashMap<>();
                            feed.put("added",new Date().getTime());
                            feed.put("amount",Integer.parseInt(amountInput.getText().toString().isEmpty() ? "0":amountInput.getText().toString()));
                            feed.put("name", feedType.get("name"));
                            feed.put("unit",feedType.get("unit"));
                            feed.put("owner",activity.getIntent().getStringExtra("owner"));
                            SimpleLoader.save("feed_report",feed, new OnSavedResult(){
                                @Override
                                public void onSave(boolean saved) {
                                    super.onSave(saved);
                                    if(saved){
                                        activity.setDateTo(new Date());
                                        activity.loadLocal();
                                        dialog.cancel();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
