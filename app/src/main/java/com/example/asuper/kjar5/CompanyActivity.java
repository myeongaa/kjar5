package com.example.asuper.kjar5;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import static android.R.attr.name;
import static com.example.asuper.kjar5.LoginActivity.emailStr;
import static com.example.asuper.kjar5.LoginActivity.idInt;


public class CompanyActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    Button nticket_btn,reserve_btn;
    public Integer companyidInt,companycsort_idInt;
    public String companynameStr="",companyc_postcodeStr="",companyc_addressStr="",companyc_daddressStr="",companytelStr="",companyopentimeStr="",companyclosetimeStr="";
    private static final int DIALOG_YES_NO_MESSAGE = 1;

   @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case DIALOG_YES_NO_MESSAGE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("번호표뽑기")
                        .setMessage("번호표를 뽑으시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Integer user_id = idInt, company_id = companyidInt;
                                        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                                        apiService = retrofit.create(ApiService.class);
                                        //get
                                        Call<ResponseBody> nticket_request = apiService.getNticket_request(user_id,company_id);
                                        nticket_request.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                Integer ticketnumber;
                                                JSONArray jArray = null;
                                                String s = null;
                                                try {
                                                    s = URLDecoder.decode(response.body().string());
                                                    JSONObject jObj = new JSONObject(s);
                                                    ticketnumber = jObj.getInt("ticketnumber");
                                                    Log.v("Test", s);
                                                    Log.v("Test", ticketnumber.toString());

                                                    //ticketnumber = jObj.getInt("ticketnumber");
                                                    //Log.v("Test", ticketnumber.toString());

                                                    Toast toast = Toast.makeText(getApplicationContext(),
                                                            ticketnumber.toString()+"번 번호표를 뽑았습니다.", Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();
                                                } catch (JSONException e1) {
                                                    e1.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();

                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                            }
                                        });


                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                return alert;
        }
        return null;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        nticket_btn = (Button) findViewById(R.id.nticket_btn);
        reserve_btn = (Button) findViewById(R.id.reserve_btn);
        // Intent get
        Intent intentSubActivity = getIntent();

        // getIntExtra("받는변수명", 기본값)
        String company_name  = intentSubActivity.getExtras().getString("VALUE", "");

       Log.v("Test", company_name);

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);

        //string get

        String name = company_name;
        Call<ResponseBody> company_requestStr= apiService.getCompany_requestStr(name);
        company_requestStr.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONArray jArray = null;

                try {
                    String s = URLDecoder.decode(response.body().string());
                    JSONObject jObj = new JSONObject(s);
                    companyidInt = jObj.getInt("id");
                    companynameStr = jObj.getString("name");
                    companyc_postcodeStr = jObj.getString("c_postcode");
                    companyc_addressStr = jObj.getString("c_address");
                    companyc_daddressStr = jObj.getString("c_daddress");
                    companytelStr = jObj.getString("tel");
                    companyopentimeStr = jObj.getString("opentime");
                    companyclosetimeStr = jObj.getString("closetime");
                    companycsort_idInt = jObj.getInt("csort_id");

                    Log.v("Test", companynameStr);
                    Log.v("Test", companyc_postcodeStr);

                    TextView company_name = (TextView)findViewById(R.id.company_name);
                    company_name.setText(companynameStr);
                    TextView company_add = (TextView)findViewById(R.id.company_add);
                    company_add.setText(companyc_postcodeStr+""+companyc_addressStr+""+companyc_daddressStr);
                    TextView company_time = (TextView)findViewById(R.id.company_time);
                    company_time.setText(companyopentimeStr+"~"+companyclosetimeStr);

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

        findViewById(R.id.nticket_btn).setOnClickListener(nticketClickListener);
        findViewById(R.id.reserve_btn).setOnClickListener(reserveClickListener);
    }



   Button.OnClickListener nticketClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showDialog(DIALOG_YES_NO_MESSAGE);
            /*Integer user_id = idInt, company_id = companyidInt;
            retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
            apiService = retrofit.create(ApiService.class);
            //get
            Call<ResponseBody> nticket_request = apiService.getNticket_request(user_id,company_id);
            nticket_request.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Integer ticketnumber;
                    JSONArray jArray = null;
                    String s = null;
                    try {
                        s = URLDecoder.decode(response.body().string());
                        JSONObject jObj = new JSONObject(s);
                        ticketnumber = jObj.getInt("ticketnumber");
                        Log.v("Test", s);
                        Log.v("Test", ticketnumber.toString());

                        //ticketnumber = jObj.getInt("ticketnumber");
                        //Log.v("Test", ticketnumber.toString());

                                                    Toast toast = Toast.makeText(getApplicationContext(),
                                                            ticketnumber.toString()+"번 번호표를 뽑았습니다.", Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });*/


        }
    };

    Button.OnClickListener reserveClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intentSubActivity =
                    new Intent(CompanyActivity.this, ReserveActivity.class);
            //HashMap<String, String> obj = ( HashMap<String, String>) mAdapter.getItem(position);
            intentSubActivity.putExtra("VALUE", companyidInt);
            startActivity(intentSubActivity);
        }

    };
}
