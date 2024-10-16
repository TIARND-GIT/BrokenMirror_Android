package com.example.brokenmirror.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit 인스턴스 생성 => 클래스명을 RetrofitService로 해야 돼, RetrofitClient로 해야 돼?
public class RetrofitService {
//    private static String BASE_URL = "http://10.0.2.2:8081";
    private static String BASE_URL = "http://172.22.7.105:8081";
    private static Retrofit retrofit;

    // Retrofit 객체 생성
    public static FriendApi getFriendApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(FriendApi.class);
    }

//    public static Retrofit getRetrofit(String baseUrl) {
//        return retrofit;
//    }

//    // UserRetrofit 객체 생성
//    public static UserApi getUserApi(){
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(new NullOnEmptyConverterFactory())
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit.create(UserApi.class);
//    }

    // 날짜 형식 파싱
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // 서버에서 넘어오는 날짜 형식 설정
            .create();

    // 하나의 apiservice로 관리?
    private static Retrofit getInstance() {
//        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    // UserApi
    public static UserApi getUserApi() {
        return getInstance().create(UserApi.class);
    }

    // LoginInfoApi
    public static LoginInfoApi getLoginInfoApi() {
        return getInstance().create(LoginInfoApi.class);
    }
}
