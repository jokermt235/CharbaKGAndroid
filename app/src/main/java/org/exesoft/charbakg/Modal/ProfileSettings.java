package org.exesoft.charbakg.Modal;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import org.exesoft.charbakg.R;
import org.exesoft.charbakg.View.HomeActivity;

public class ProfileSettings {
    private static String TAG = "ProfileSettingsDialog";
    private HomeActivity activity;
    private View dialogContent;
    private  AlertDialog.Builder settingDialog;
    private AlertDialog dialog;
    private EditText nameInput;

    public ProfileSettings(final HomeActivity activity){
        this.activity = activity;
        settingDialog = new AlertDialog.Builder(activity);
        settingDialog.setView(getContent());
        dialog = settingDialog.create();
    }
    private View getContent(){
        dialogContent = LayoutInflater.from(activity).inflate(R.layout.profile_settings_form,null);
        return dialogContent;
    }

    public void show(){
        settingDialog.show();
    }
}
