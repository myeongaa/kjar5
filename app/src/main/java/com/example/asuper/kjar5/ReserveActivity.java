package com.example.asuper.kjar5;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Time;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.asuper.kjar5.LoginActivity.idInt;

public class ReserveActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    private int mYear,mMonth,mDay,mHour,mMinute;
    public int year, month, day, hour, minute;
    public int company_id,person_num2;
    public String reserve_time,reserve_date,requestmenu2="";
    TextView reserve_date_text;
    TextView reserve_time_text;
    EditText person_num,requestmenu;
    Button reserve_date_btn,reserve_time_btn,doreserve_btn;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ReserveActivity(){
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reserve);

        Intent intentSubActivity = getIntent();

        // getIntExtra("받는변수명", 기본값)
        Integer company_id  = intentSubActivity.getExtras().getInt("VALUE", 0);

        Log.v("Test", company_id.toString());

        reserve_date_text = (TextView)findViewById(R.id.reserve_date_text);
        reserve_time_text = (TextView)findViewById(R.id.reserve_time_text);
        reserve_date_btn = (Button) findViewById(R.id.reserve_date_btn);
        reserve_time_btn = (Button) findViewById(R.id.reserve_time_btn);
        doreserve_btn = (Button)findViewById(R.id.doreserve_btn);
        person_num = (EditText)findViewById(R.id.person_num);
        requestmenu = (EditText)findViewById(R.id.requestmenu);
        findViewById(R.id.reserve_date_btn).setOnClickListener(dateClickListener);
        findViewById(R.id.reserve_time_btn).setOnClickListener(timeClickListener);
        findViewById(R.id.doreserve_btn).setOnClickListener(reserveClickListener);


    }
    Button.OnClickListener reserveClickListener = new View.OnClickListener(){
        String requestmenu2 = "메롱";
        int person_num2 = 1;
        @Override
        public void onClick(View v){
            retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
            apiService = retrofit.create(ApiService.class);

            //string post
            Call<ResponseBody> Reserve_requestStr = apiService.getReserve_requestStr(idInt,company_id,reserve_time,reserve_date,requestmenu2,person_num2);
            Reserve_requestStr.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String s = null;
                    try {
                        s = URLDecoder.decode(response.body().string());
                        Log.v("Test", s);
                        Toast toast = Toast.makeText(getApplicationContext(),"예약되었습니다.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.v("Test","서버 에러내용:"+ t.getMessage());
                }
            });
        }
    };


    Button.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(DATE_DIALOG_ID);
        }
    };

    Button.OnClickListener timeClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            showDialog(TIME_DIALOG_ID);
        }
    };

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearSeleted, int monthOfYear, int dayOfMonth) {
            year = yearSeleted;
            month = monthOfYear;
            day = dayOfMonth;

            reserve_date = month+"/"+day+"/"+year;
            Toast.makeText(getApplicationContext(),"날짜:"+year+"-"+month+"-"+day,Toast.LENGTH_SHORT).show();
            reserve_date_text.setText("날짜:"+year+"-"+month+"-"+day);

        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {
            String AM_PM;
            if(hourOfDay < 12) {
                AM_PM = "am";
            } else {
                AM_PM = "pm";
            }
            hour = hourOfDay;
            minute = min;
            reserve_time =hour+":"+minute+AM_PM;
            Toast.makeText(getApplicationContext(),"시간:"+hour+"-"+minute,Toast.LENGTH_SHORT).show();
            reserve_time_text.setText("시간:"+AM_PM+hour+":"+minute);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id){
        getSupportActionBar().hide();
        switch (id){
            case DATE_DIALOG_ID:
                DatePickerDialog _date =   new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        if (year < mYear)
                            view.updateDate(mYear, mMonth, mDay);

                        if (monthOfYear < mMonth && year == mYear)
                            view.updateDate(mYear, mMonth, mDay);

                        if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                            view.updateDate(mYear, mMonth, mDay);

                    }
                };
                return _date;
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,false);
        }
        return null;
    }



}
