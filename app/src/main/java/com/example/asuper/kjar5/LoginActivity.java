package com.example.asuper.kjar5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    public static String emailStr="",nameStr="",phoneStr="",nologinStr="";
    public static Integer idInt;

    Retrofit retrofit;
    ApiService apiService;
    Button btn_login2,btn_signup;
    EditText email, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        btn_login2 = (Button)findViewById(R.id.btn_login2);
         btn_signup = (Button)findViewById(R.id.btn_signup);
        email = (EditText)findViewById(R.id.email);
        pw = (EditText)findViewById(R.id.pw);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        findViewById(R.id.btn_login2).setOnClickListener(m2ClickListener);
    findViewById(R.id.btn_signup).setOnClickListener(m3ClickListener);
}

    Button.OnClickListener m2ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
/*
            final String nologin = getText(0).toString();
*/
            String email2 = email.getText().toString();
            String pw2 = pw.getText().toString();
            retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
            apiService = retrofit.create(ApiService.class);
            //string get


            Call<ResponseBody> login_requestStr= apiService.getLogin_requestStr(email2,pw2);
            login_requestStr.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    JSONArray jArray = null;
                    try {
                        String s = URLDecoder.decode(response.body().string());
                        JSONObject jObj = new JSONObject(s);
/*
                        nologinStr = jObj.getString("");
*/

                            idInt = jObj.getInt("id");
                            emailStr = jObj.getString("email");
                            nameStr = jObj.getString("name");
                            phoneStr = jObj.getString("phone");


                            Log.v("Test", emailStr);
                            Log.v("Test", nameStr);
                            Log.v("Test", phoneStr);

                          /*  if(emailStr != null) {

                                Log.v("Test","gg");
                                Intent intentSubActivity =
                                        new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                startActivity(intentSubActivity);
                            }*/


                        Intent intentSubActivity =
                                new Intent(LoginActivity.this, MainActivity.class);
                        finish();
                        startActivity(intentSubActivity);



                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }
    };

    Button.OnClickListener m3ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intentSubActivity =
                    new Intent(LoginActivity.this, SignupActivity.class);
            finish();
            startActivity(intentSubActivity);
        }


    };
}
