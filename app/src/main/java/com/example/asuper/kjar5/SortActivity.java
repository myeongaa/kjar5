package com.example.asuper.kjar5;

import android.content.ClipData;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.asuper.kjar5.MainActivity.sort_id;

public class SortActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    ListView sort_list;
    String[] sort_comname;
    String[] sort_add;
    String[] sort_company_name;
    String[] sort_company_add;
    Integer[] sort_company_id;
    public static Integer companyname;

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    private Context t = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sort);
        sort_company_name = new String[10];
        sort_company_add = new String[10];
        sort_company_id = new Integer[10];
        mListView = (ListView) findViewById(R.id.sort_list);
        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> sort_request = apiService.getSort_request(sort_id);
        sort_request.enqueue(new Callback<ResponseBody>() {
            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resultStr = "", terms ="";
                String comnameStr ="",comaddStr="";
                Integer comidInt;
                try {
                    String s = URLDecoder.decode(response.body().string());
                    JSONArray jArr = new JSONArray(s);

                    for (int i = 0; i < jArr.length(); i++) {
                        JSONObject jObj = jArr.getJSONObject(i);

                        comnameStr = jObj.getString("name");
                        comaddStr = jObj.getString("c_address");
                        comidInt = jObj.getInt("id");
                        sort_company_name[i] = comnameStr;
                        sort_company_add[i] = comaddStr;
                        sort_company_id[i] = comidInt;
                        Log.v("Test", comnameStr);


                    }
                    mAdapter = new ListViewAdapter(t);
                    mListView.setAdapter(mAdapter);
                    sort_company_name.toString();
                    for(int i= 0; i<sort_company_name.length; i++){
                        //Log.v("Test", sort_company_name[i]);
                        //Log.v("Test", sort_company_add[i]);
                        mAdapter.addItem(
                                sort_company_name[i],
                                sort_company_add[i]);
                    }

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


        /*
        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        sort_company_name.toString();
        for(int i= 0; i<sort_company_name.length; i++){
            //Log.v("Test", sort_company_name[i]);
            //Log.v("Test", sort_company_add[i]);
            mAdapter.addItem(
                    sort_company_name[i],
                    "이명아바보메롱똥");
        }

*/

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private ArrayList<ListData> mListData = new ArrayList<ListData>();
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                //ListData mData = mAdapter.mListData.get(position);


                Intent intentSubActivity =
                        new Intent(SortActivity.this, CompanyActivity.class);
                String obj = mAdapter.getName(position);
                //HashMap<String, String> obj = ( HashMap<String, String>) mAdapter.getItem(position);
                intentSubActivity.putExtra("VALUE", obj);
                finish();
                startActivity(intentSubActivity);
            }
        });

    }

    private class ViewHolder {
        public TextView sort_comname;

        public TextView sort_comadd;
    }
    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.sortlistitem, null);

                holder.sort_comname = (TextView) convertView.findViewById(R.id.sort_comname);
                holder.sort_comadd = (TextView) convertView.findViewById(R.id.sort_comadd);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);


            holder.sort_comname.setText(mData.sort_comname);
            holder.sort_comadd.setText(mData.sort_comadd);

            return convertView;
        }

        public void addItem( String mTitle, String mDate){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.sort_comname = mTitle;
            addInfo.sort_comadd = mDate;

            mListData.add(addInfo);
        }

        public String getName(int position) {
            return mListData.get(position).sort_comname;
        }
        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }
}
