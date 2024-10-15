package com.example.brokenmirror.ui.friend;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brokenmirror.R;
import com.example.brokenmirror.data.FriendDto;

import java.util.List;

// Data - Adapter - UI
// 이 클래스는 리사이클러뷰를 사용해서 친구목록을 표시하는 어댑터

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<FriendDto> friendList;

    // 생성자
    public FriendAdapter(List<FriendDto> friendList) {
        this.friendList = friendList;
    }

    // onCreateViewHolder()는 아이템뷰 생성
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_listview, parent, false);
        return new FriendViewHolder(view);
    }

    // onBindViewHolder()는 생성된 뷰에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        FriendDto friend = friendList.get(position);
        Log.d("친구 어댑터", "Binding friend: " + friend.getFriendName());
        holder.nameTextView.setText(friend.getFriendName());
        holder.phoneNumTextView.setText(friend.getFriendPhoneNum());
//        Glide.with(holder.itemView.getContext())
//                .load(friend.getFriendProfileUrl())
//                .into(holder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    // friendList.java → adapter.setFriends(friends)
    // setFriends()는 새로운 친구 목록을 설정하고, 데이터 변경을 어댑터에게 알린다
    // notifyDataSetChanged()는 데이터 변경을 어댑터에 알려 목록을 업데이트함
    public void setFriends(List<FriendDto> friendList) {
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        //private ImageView profileImageView;
        private TextView phoneNumTextView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.listView_user_name);
            //profileImageView = itemView.findViewById(R.id.listView_user_icon);
            phoneNumTextView = itemView.findViewById(R.id.listView_user_phone);
        }
    }
}
