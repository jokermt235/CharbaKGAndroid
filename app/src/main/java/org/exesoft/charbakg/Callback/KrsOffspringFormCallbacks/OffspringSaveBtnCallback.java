package org.exesoft.charbakg.Callback.KrsOffspringFormCallbacks;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.View.KrsOffspringActivity;
import org.exesoft.charbakg.View.KrsOffspringFormActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OffspringSaveBtnCallback implements View.OnClickListener {
    private String sex;
    private static String TAG = "OffspringSaveBtnCallback";
    private KrsOffspringFormActivity activity;
    public  OffspringSaveBtnCallback(KrsOffspringFormActivity activity){
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        String serial = activity.getSerialInput().getText().toString();
        String serialParent = activity.getSerialParentInput().getText().toString();
        int ageYear = Integer.parseInt(activity.getAgeYearInput().getText().toString().isEmpty() ? "0":activity.getAgeYearInput().getText().toString());
        int ageMonth = Integer.parseInt(activity.getAgeMonthInput().getText().toString().isEmpty() ? "0":activity.getAgeMonthInput().getText().toString());
        RadioGroup radioGroup = activity.getRadioGroup();
        sex  = "Жен.";
        Date added = new Date();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = activity.findViewById(checkedId);
                sex = checked.getText().toString();
            }
        });
        Map<String,Object> krsOffspring = new HashMap<>();
        krsOffspring.put("serial",serial);
        krsOffspring.put("serialParent",serialParent);
        krsOffspring.put("ageYear",ageYear);
        krsOffspring.put("ageMonth",ageMonth);
        krsOffspring.put("sex",sex);
        krsOffspring.put("added",added.getTime());
        SimpleLoader.save("krs_offsprings",krsOffspring, new OnSavedResult(){
            @Override
            public void onSave(boolean saved) {
                super.onSave(saved);
                if(saved){
                    activity.startActivity(new Intent(activity, KrsOffspringActivity.class));
                    activity.finish();
                }
            }
        });
    }
}
