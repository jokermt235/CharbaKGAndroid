package org.exesoft.charbakg.Modal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import org.exesoft.charbakg.Callback.Feed.OnUpdateDocument;
import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;
import org.exesoft.charbakg.View.HomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileSettings {
    private static String TAG = "ProfileSettingsDialog";
    private HomeActivity activity;
    private View dialogContent;
    private  AlertDialog.Builder settingDialog;
    private AlertDialog dialog;
    private EditText name;
    private View view;
    private ImageButton closeBtn;
    private Button saveBtn;
    private ProgressBar progressBar;

    public ProfileSettings(final HomeActivity activity){
        this.activity = activity;
        settingDialog = new AlertDialog.Builder(activity);
        view = getContent();
        settingDialog.setView(view);
        dialog = settingDialog.create();
        name = view.findViewById(R.id.settingsName);
        closeBtn = view.findViewById(R.id.settingsFormCloseBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close on clicked");
                dialog.cancel();
            }
        });
        saveBtn = view.findViewById(R.id.settingsFormSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> user = new HashMap<>();
                progressBar.setVisibility(View.VISIBLE);
                if(!name.getText().toString().isEmpty()){
                    SimpleLoader.filter("user",new OnSimpleLoaderResult(){
                        @Override
                        public void onResult(ArrayList<Map<String, Object>> items) {
                            super.onResult(items);
                            if(!items.isEmpty()){
                                items.get(0).put("name", name.getText().toString());
                                SimpleLoader.update("user",items.get(0).get("_ref").toString(),items.get(0), new OnUpdateDocument(){
                                    @Override
                                    public void updated(boolean status) {
                                        super.updated(status);
                                        if(status){
                                            progressBar.setVisibility(View.GONE);
                                            dialog.cancel();
                                        }
                                    }
                                });
                            }else{
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", name.getText().toString());
                                SimpleLoader.save("user",user,new OnSavedResult(){
                                    @Override
                                    public void onSave(boolean saved) {
                                        super.onSave(saved);
                                        if(saved){
                                            progressBar.setVisibility(View.GONE);
                                            dialog.cancel();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        progressBar = view.findViewById(R.id.settingsProgressBar);
        loadLocal();
    }
    private View getContent(){
        dialogContent = LayoutInflater.from(activity).inflate(R.layout.profile_settings_form,null);
        return dialogContent;
    }
    public void show(){
        dialog.show();
    }

    private void loadLocal(){
        SimpleLoader.filter("user",new OnSimpleLoaderResult(){
            @Override
            public void onResult(ArrayList<Map<String, Object>> items) {
                super.onResult(items);
                if(!items.isEmpty()){
                    name.setText(items.get(0).get("name").toString());
                }
            }
        });
    }
}
