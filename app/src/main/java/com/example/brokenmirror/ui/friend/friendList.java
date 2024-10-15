package com.example.brokenmirror.ui.friend;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brokenmirror.R;
import com.example.brokenmirror.data.FriendDto;
import com.example.brokenmirror.retrofit.FriendApi;
import com.example.brokenmirror.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class friendList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;
    private List<FriendDto> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstandceState) {
        super.onCreate(savedInstandceState);
        setContentView(R.layout.activity_friend);

        recyclerView = findViewById(R.id.ver_friend_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 설정
        friendAdapter = new FriendAdapter(friendList);
        recyclerView.setAdapter(friendAdapter);

        // retrofit으로 서버에서 친구 목록 가져오기
        FriendApi friendApi = RetrofitService.getFriendApi();
        friendApi.getFriendsByUserId("testmail@tia.net").enqueue(new Callback<List<FriendDto>>() {
            @Override
            public void onResponse(Call<List<FriendDto>> call, Response<List<FriendDto>> response) {
                // onResponse에는 어떤 타입으로 응답을 받을 것인지를 적어둔다
                // 그럼 서버 쪽에서 받아온 FriendDto 객체가 서버와 통신을 성공했을 때 호출되는 OnResponse에서 response로 넘어온다
                if (response.isSuccessful() && response.body() != null) {
                    friendList.addAll(response.body());
                    friendAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(friendList.this, "서버에서 데이터 못 가져옴", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FriendDto>> call, Throwable t) {
                Log.e("FriendList", "Error: " + t.getMessage());
                Toast.makeText(friendList.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
