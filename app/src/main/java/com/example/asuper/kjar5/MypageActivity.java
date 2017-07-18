package com.example.asuper.kjar5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.asuper.kjar5.LoginActivity.emailStr;
import static com.example.asuper.kjar5.LoginActivity.idInt;
import static com.example.asuper.kjar5.LoginActivity.nameStr;
import static com.example.asuper.kjar5.LoginActivity.phoneStr;

public class MypageActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    TextView mypage_name,mypage_email,mypage_phone;
    Button mypage_user_btn, mypage_nticket_btn, mypage_reserve_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);

        TextView mypage_name = (TextView) findViewById(R.id.mypage_name);
        mypage_name.setText(nameStr);
        TextView mypage_email = (TextView) findViewById(R.id.mypage_email);
        mypage_email.setText(emailStr);
        TextView mypage_phone = (TextView) findViewById(R.id.mypage_phone);
        mypage_name.setText(phoneStr);

        Button mypage_user_btn = (Button) findViewById(R.id.mypage_user_btn);
        Button mypage_nticket_btn = (Button) findViewById(R.id.mypage_nticket_btn);
        Button mypage_reserve_btn = (Button) findViewById(R.id.mypage_reserve_btn);
        findViewById(R.id.mypage_user_btn).setOnClickListener(userClickListener);
        findViewById(R.id.mypage_nticket_btn).setOnClickListener(nticketClickListener);
        findViewById(R.id.mypage_reserve_btn).setOnClickListener(reserveClickListener);


    }
        Button.OnClickListener userClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubActivity =
                        new Intent(MypageActivity.this, MypageActivity.class);
                finish();
                startActivity(intentSubActivity);

            }
        };

        Button.OnClickListener nticketClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubActivity =
                        new Intent(MypageActivity.this, Mypage_nticketActivity.class);
                finish();
                startActivity(intentSubActivity);

            }
        };

        Button.OnClickListener reserveClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubActivity =
                        new Intent(MypageActivity.this, Mypage_reserveActivity.class);
                startActivity(intentSubActivity);
            }
        };



}
