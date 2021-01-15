package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PermissionActivity extends AppCompatActivity {
    private static String TAG = "PermissionActivity";
    private Spinner farmSelect;
    private EditText nameInput;
    private Button reqPermissionBtn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        nameInput = findViewById(R.id.permissionname);
        reqPermissionBtn = findViewById(R.id.permissionREquestBtn);
        reqPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SimpleLoader.filter("permission","allowed",false, new OnSimpleLoaderResult(){
                    @Override
                    public void onResult(ArrayList<Map<String, Object>> items) {
                        super.onResult(items);
                        progressBar.setVisibility(View.GONE);
                        if(!items.isEmpty()){
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.permission_too_many_requested_permission),Toast.LENGTH_LONG).show();
                        }else {
                            Map<String , Object> perm = new HashMap<>();
                            perm.put("name",nameInput.getText().toString());
                            Map<String,Object> farm = (Map<String,Object>)farmSelect.getSelectedItem();
                            perm.put("farm", farm.get("name"));
                            perm.put("farm_id", farm.get("id"));
                            perm.put("farm_city", farm.get("city"));
                            perm.put("allowed",false);
                            SimpleLoader.save("permission",perm, new OnSavedResult(){
                                @Override
                                public void onSave(boolean saved) {
                                    super.onSave(saved);
                                    if(saved){
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.permission_requested_message),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        // Init Farm select spinner;
        farmSelect  = findViewById(R.id.permissonFarmSpinner);
        progressBar = findViewById(R.id.permissionProgressbar);
    }

    private void loadLocal(){
        farmSelect.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
        SimpleLoader.filter("farm",new OnSimpleLoaderResult(){
            @Override
            public void onResult(ArrayList<Map<String, Object>> items) {
                super.onResult(items);
                if(!items.isEmpty()){
                    SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), items, android.R.layout.simple_expandable_list_item_2,
                            new String[]{"name","city","id"},
                            new int[]{android.R.id.text1,android.R.id.text2, android.R.id.shareText});
                    farmSelect.setAdapter(simpleAdapter);
                }
                progressBar.setVisibility(View.GONE);
            }
        },false);
    }

    private void loadDefaults(){
        SimpleLoader.filter("user",new OnSimpleLoaderResult(){
            @Override
            public void onResult(ArrayList<Map<String, Object>> items) {
                super.onResult(items);
                if(!items.isEmpty()){
                    nameInput.setText(items.get(0).get("name").toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocal();
        loadDefaults();
    }
}
