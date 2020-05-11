package org.exesoft.charbakg.View;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.exesoft.charbakg.R;

public class AppToolbar {
    public static  void initSimpleToolbar(final AppCompatActivity activity, int toolbarId){
        Toolbar appToolbar = activity.findViewById(toolbarId);
        appToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        activity.setSupportActionBar(appToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity.getApplicationContext(), HomeActivity.class));
                activity.finish();
            }
        });
    }

}
