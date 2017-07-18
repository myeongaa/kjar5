package com.example.asuper.kjar5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.asuper.kjar5.LoginActivity.emailStr;
import static com.example.asuper.kjar5.LoginActivity.idInt;

import static com.example.asuper.kjar5.LoginActivity.nameStr;
import static com.example.asuper.kjar5.LoginActivity.phoneStr;

public class MainActivity extends AppCompatActivity {
    ImageButton hospital, restaurant, hair, bank;
    Retrofit retrofit;
    ApiService apiService;
    MenuItem menu_login;
    public static Integer sort_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hospital = (ImageButton)findViewById(R.id.hospital);
        restaurant = (ImageButton)findViewById(R.id.restaurant);
        hair = (ImageButton)findViewById(R.id.hair);
        bank = (ImageButton)findViewById(R.id.bank);
        findViewById(R.id.hospital).setOnClickListener(hClickListener);
        findViewById(R.id.restaurant).setOnClickListener(hClickListener);
        findViewById(R.id.hair).setOnClickListener(hClickListener);
        findViewById(R.id.bank).setOnClickListener(hClickListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);
        if(emailStr.length()>1){
            menu.getItem(0).setEnabled(false);
        }
        else{
            menu.getItem(1).setEnabled(false);
            menu.getItem(2).setEnabled(false);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case R.id.menu_login:
                //Toast.makeText(getApplicationContext(), "edit selected", Toast.LENGTH_SHORT).show();
                Intent intentSubActivity =
                        new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentSubActivity);
                return true;
            case R.id.menu_logout:
                //Toast.makeText(getApplicationContext(), "edit selected", Toast.LENGTH_SHORT).show();
                // Intent i = new Intent(getApplicationContext(), PhotoFlashActivity.class);
                //startActivity(i);
                idInt=null;
                emailStr = "";
                nameStr = "";
                phoneStr ="";
                finish();
                startActivity(getIntent());
                return true;
            case R.id.menu_mypage:
                //Toast.makeText(getApplicationContext(), "edit selected", Toast.LENGTH_SHORT).show();
                // Intent i = new Intent(getApplicationContext(), PhotoFlashActivity.class);
                //startActivity(i);
                Intent intentActivity =
                        new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intentActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    ImageButton.OnClickListener hClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
            apiService = retrofit.create(ApiService.class);

            switch (v.getId()) {
                case R.id.hospital:
                    sort_id =1;
                    Log.d("OnClickListener", "hosipital click session button");

                    // 액티비티 실행
                    break;
                case R.id.restaurant:
                    sort_id =2;
                    Log.d("OnClickListener", "restaurant click session button");
                    // 액티비티 실행
                    break;
                case R.id.hair:
                    sort_id =3;
                    Log.d("OnClickListener", "hair click session button");
                    // 액티비티 실행
                    break;
                case R.id.bank:
                    sort_id =4;
                    Log.d("OnClickListener", "bank click session button");
                    // 액티비티 실행
                    break;
            }
            Intent intentSubActivity =
                    new Intent(MainActivity.this, SortActivity.class);

            startActivity(intentSubActivity);
            //get

            //Call<ResponseBody> sort_request = apiService.getSort_request(sort_id);
            //sort_request.enqueue(new Callback<ResponseBody>() {
            //    @Override
            //
            //    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            //        JSONArray jArray = null;
            //        try {
            //            String s = URLDecoder.decode(response.body().string());
            //            JSONObject sortj = new JSONObject(s);
            //            idStr = sortj.getString("id");
            //            Log.v("Test", s);
            //        } catch (JSONException e1) {
            //            e1.printStackTrace();
            //        } catch (IOException e) {
            //            e.printStackTrace();
            //        }
            //    }

             //   @Override
             //   public void onFailure(Call<ResponseBody> call, Throwable t) {

             //   }
            //});
     }
    };




}
