package com.example.asuper.kjar5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.asuper.kjar5.LoginActivity.idInt;

public class Mypage_reserveActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    Button mypage_user_btn, mypage_nticket_btn, mypage_reserve_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_reserve);

        Button mypage_user_btn = (Button) findViewById(R.id.mypage_user_btn);
        Button mypage_nticket_btn = (Button) findViewById(R.id.mypage_nticket_btn);
        Button mypage_reserve_btn = (Button) findViewById(R.id.mypage_reserve_btn);
        findViewById(R.id.mypage_user_btn).setOnClickListener(userClickListener);
        findViewById(R.id.mypage_nticket_btn).setOnClickListener(nticketClickListener);
        findViewById(R.id.mypage_reserve_btn).setOnClickListener(reserveClickListener);

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> mypage_request_reservation = apiService.getMypage_request_reservation(idInt);
        mypage_request_reservation.enqueue(new Callback<ResponseBody>() {
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
                    new Intent(Mypage_reserveActivity.this, MypageActivity.class);
            finish();
            startActivity(intentSubActivity);
        }
    };

    Button.OnClickListener nticketClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentSubActivity =
                    new Intent(Mypage_reserveActivity.this, Mypage_nticketActivity.class);
            finish();
            startActivity(intentSubActivity);

        }
    };

    Button.OnClickListener reserveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentSubActivity =
                    new Intent(Mypage_reserveActivity.this, Mypage_reserveActivity.class);
            startActivity(intentSubActivity);
        }
    };

}
