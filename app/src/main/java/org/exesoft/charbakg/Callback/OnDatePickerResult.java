package org.exesoft.charbakg.Callback;

import java.util.Date;

public class OnDatePickerResult {
    private Date date;
    public void onDateChoosen(Date date){
        this.date = date;
    }

    public void onDateCanceled(boolean error){
        this.date  = null;
    }
}
