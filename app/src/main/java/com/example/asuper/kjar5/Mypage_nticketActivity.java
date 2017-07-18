package com.example.asuper.kjar5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.asuper.kjar5.LoginActivity.idInt;

public class Mypage_nticketActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    Button mypage_user_btn, mypage_nticket_btn, mypage_reserve_btn;

    private Context t = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_nticket);

        Button mypage_user_btn = (Button) findViewById(R.id.mypage_user_btn);
        Button mypage_nticket_btn = (Button) findViewById(R.id.mypage_nticket_btn);
        Button mypage_reserve_btn = (Button) findViewById(R.id.mypage_reserve_btn);
        findViewById(R.id.mypage_user_btn).setOnClickListener(userClickListener);
        findViewById(R.id.mypage_nticket_btn).setOnClickListener(nticketClickListener);
        findViewById(R.id.mypage_reserve_btn).setOnClickListener(reserveClickListener);
        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> mypage_request_nticket = apiService.getMypage_request_nticket(idInt);
        mypage_request_nticket.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.v("Test", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

        Button.OnClickListener userClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubActivity =
                        new Intent(Mypage_nticketActivity.this, MypageActivity.class);
                finish();
                startActivity(intentSubActivity);
            }
        };

        Button.OnClickListener nticketClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubActivity =
                        new Intent(Mypage_nticketActivity.this, Mypage_nticketActivity.class);
                finish();
                startActivity(intentSubActivity);

            }
        };

        Button.OnClickListener reserveClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubActivity =
                        new Intent(Mypage_nticketActivity.this, Mypage_reserveActivity.class);
                startActivity(intentSubActivity);
            }
        };



}
