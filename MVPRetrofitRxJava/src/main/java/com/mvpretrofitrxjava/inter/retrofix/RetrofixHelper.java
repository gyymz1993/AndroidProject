package com.mvpretrofitrxjava.inter.retrofix;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mvpretrofitrxjava.inter.AppUrl;
import com.mvpretrofitrxjava.inter.RetrofitService;

/**
 * Created by Administrator on 2017/2/15.
 */
public class RetrofixHelper {
    private static RetrofixHelper sRetrofixHelper;
    private Context mContext;
    private Retrofit mRetrofix;

    public static RetrofixHelper getInstance(Context context){
        if (sRetrofixHelper==null){
            synchronized (RetrofixHelper.class){
                if (sRetrofixHelper==null){
                    sRetrofixHelper=new RetrofixHelper(context);
                }
            }
        }
        return sRetrofixHelper;
    }

    private RetrofixHelper(Context context){
        this.mContext=context;
        initRetrofix();
    }


    private void initRetrofix() {
        mRetrofix=new Retrofit.Builder().baseUrl(AppUrl.baseUrl).
                addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                client(getOkHttpClient())
        .build();
    }

    public RetrofitService getRetrofixService(){
        return mRetrofix.create(RetrofitService.class);
    }

    /*请求路径打印*/
    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BASIC;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("TAG","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();

        /*添加拦截器和缓存*/
//        httpClientBuilder.addInterceptor(setInterceptor())
//                .cache(setCacheClinet()).build();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

//    /**
//    * 第一种类型（有网和没有网都是先读缓存）
//    * */
//    private static Interceptor setInterceptor(){
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                return setResponse(chain);
//            }
//        };
//        return interceptor;
//    }
//
//    /***
//     *
//     * 这种方法和第一种方法的区别是在设置的拦截器上，这里不能使用NetworkInterceptor，而是需要使用Interceptor，（不知道为什么用NetworkInterceptor就不行）
//     先讲一下步骤：
//     * 1、 首先，给OkHttp设置拦截器
//     * 2、然后，在拦截器内做Request拦截操作，在每个请求发出前，判断一下网络状况，如果没问题继续访问，如果有问题，则设置从本地缓存中读取
//     * 3、接下来是设置Response，先判断网络，网络好的时候，移除header后添加cache失效时间为0小时，网络未连接的情况下设置缓存时间为4周
//     *
//     * */
//
//    public Cache setCacheClinet(){
//        //设置缓存路径
//        File httpCacheDirectory = new File(FileUtil.getAvailableCacheDir(), "responses");
//        //设置缓存 10M
//        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
//        //创建OkHttpClient，并添加拦截器和缓存代码
//        return cache;
//    }
//
//    public static Response setResponse(Interceptor.Chain chain){
//        Response response = null;
//        Request request = chain.request();
//        if (!StringsUtil.isNetworkReachable(App.app)) {
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
//           // LogCat.i("no network");
//        }
//        try {
//            response = chain.proceed(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (StringsUtil.isNetworkReachable(App.app)) {
//            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
//           // LogCat.i("has network maxAge="+maxAge);
//            response.newBuilder()
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                    .build();
//        } else {
//           // LogCat.i("network error");
//            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
//          //  LogCat.i("has maxStale="+maxStale);
//            response.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .removeHeader("Pragma")
//                    .build();
//           // LogCat.i("response build maxStale="+maxStale);
//        }
//        return response;
//    }


}
