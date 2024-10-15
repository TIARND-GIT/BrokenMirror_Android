package com.example.brokenmirror.retrofit;

import com.example.brokenmirror.data.FriendDto;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FriendApi {
//    @GET("/api/friendList/user/{userId}")
//    Call<List<Friend>> getFriendsByUserId(@Path("userId") String userId);

    @GET("/api/friend/user/{userId}")
    Call<List<FriendDto>> getFriendsByUserId(@Path("userId") String userId);

    @GET("/api/friend/all")
    Call<List<FriendDto>> getAllFriend();

}