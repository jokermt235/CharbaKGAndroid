package org.exesoft.charbakg.Modal;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.R;

import java.util.Calendar;
import java.util.Date;

public class DatePicker {
    private static String TAG = "DatePicker";
    public static AlertDialog dialog;
    private static  Date date;
    public static void pickupDate(AppCompatActivity activity, final OnDatePickerResult result){
        AlertDialog.Builder addDialog = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.calendar_view,null);
        addDialog.setView(dialogView);
        Button okButton = dialogView.findViewById(R.id.CVOkBtn);
        ImageButton closeButton = dialogView.findViewById(R.id.CVCloseBtn);
        CalendarView calendarView = dialogView.findViewById(R.id.CVCalendar);
        date = new Date();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(year, month,dayOfMonth,23,59);
                date = calendar.getTime();

            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.onDateCanceled(false);
                dialog.cancel();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.onDateChoosen(date);
                dialog.cancel();
            }
        });
        dialog = addDialog.create();
        dialog.show();
    }
}
