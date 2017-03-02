package com.mvpretrofitrxjava.inter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import com.mvpretrofitrxjava.inter.bean.Book;
import com.mvpretrofitrxjava.inter.bean.User;

/**
 * Created by Administrator on 2017/2/15.
 */
public interface RetrofitService {

    @GET("userRegisterDo?")
    Call<Boolean> userRegister(@Query("userName") String userName,
                               @Query("password") String pwd,
                               @Query("trueName") String truName,
                               @Query("phone") String phone,
                               @Query("phoneCode") String code,
                               @Query("userType") int type);

    @GET("userLoginDo?")
    Call<JsonResult> userLogin(@Query("userName") String userName,
                            @Query("password") String pwd,
                            @Query("phoneCode") String code);


    @GET("userLoginDo?")
    Observable<JsonResult> userLogin1(@Query("userName") String userName,
                                     @Query("password") String pwd,
                                     @Query("phoneCode") String code);


    @GET("book/seach")
    Call<Book> getSeachBook(
            @Query("q") String name,
            @Query("tag") String tag,
            @Query("start") int start,
            @Query("count") int count);

    @GET("book/seach?q=name")
    Call<Book> getSeachBook();

    /*用于多个参数*/
    @GET("book/seach")
    Call<Book> getSeachBook(@QueryMap Map<String,String> options);

    /*用于替换url中个某个字段*/
    @GET("group/{id}/users")
    Call<Book> groupList(@Part("id") int groupId);

    /*指定一个对象做HTTP实体*/
    @POST("users/new")
    Call<User> create(@Body User user);

    /*表单请求*/
    @FormUrlEncoded
    @POST("user/edit")
    Call<User> update(@Field("frist_name") String first,
                      @Field("last_name") String last);


    /*添加头部请求*/
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization);

    @Headers("Cache-Contrl:max-age=640000")
    @GET("user")
    Call<User> getUser();

    @Headers({
            "Accept:application/vnd.github.v3.full+json",
            "user-Agent:Retrofit-Sample-App"
    })
    @GET("user")
    Call<User> getUsers();


}
