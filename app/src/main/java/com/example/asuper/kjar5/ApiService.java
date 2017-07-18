package com.example.asuper.kjar5;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by super on 2016-11-20.
 */

public interface ApiService {
    public static final String API_URL = "https://kjar-new-myeongaa.c9users.io/";
    //int
    @GET("androidrequest/sort_request")
    Call<ResponseBody>getSort_request(@Query("sort_id") int sort_id);

    @GET("androidrequest/mypage_request_nticket")
    Call<ResponseBody>getMypage_request_nticket(@Query("user_id") int user_id);
    @GET("androidrequest/mypage_request_reservation")
    Call<ResponseBody>getMypage_request_reservation(@Query("user_id") int user_id);

    @GET("androidrequest/nticket_request")
    Call<ResponseBody>getNticket_request(@Query("user_id") int user_id, @Query("company_id") int company_id);


    //@POST("home/mypage")
    //Call<ResponseBody>getPostUser(@Query("userId")int userId);
    //string
    @GET("androidrequest/login_request")
    Call<ResponseBody> getLogin_requestStr(@Query("email") String email, @Query("pw") String pw);

    @GET("androidrequest/company_request")
    Call<ResponseBody> getCompany_requestStr(@Query("name") String name);

    @GET("androidrequest/reserve_request")
    Call<ResponseBody> getReserve_requestStr(@Query("user_id") int user_id, @Query("company_id") int company_id, @Query("reserve_time") String reserve_time,@Query("reserve_date") String reserve_date
            ,@Query("requestmenu") String requestmenu,@Query("person_num") int person_num);

    //@FormUrlEncoded
   // @POST("androidrequest/reserve_request")
   // Call<ResponseBody>getPostReserve_requestStr(@Query("user_id") int user_id, @Query("company_id") int company_id, @Field("reserve_time") String reserve_time,@Field("reserve_date") String reserve_date
   // ,@Field("requestmenu") String requestmenu,@Query("person_num") int person_num);
}
