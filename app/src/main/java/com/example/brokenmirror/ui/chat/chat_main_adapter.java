/*
__author__ = 'Song Chae Young'
__date__ = 'Dec.15, 2023'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'chat_main_adapter.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.chat;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brokenmirror.R;

import java.util.ArrayList;

public class chat_main_adapter extends RecyclerView.Adapter<chat_main_adapter.ViewHolder> {

    private ArrayList<String> name;
    private ArrayList<String> message;
    private ArrayList<String> date;

    public chat_main_adapter(ArrayList<String> name, ArrayList<String> message, ArrayList<String> date) {
        this.name = name;
        this.message = message;
        this.date = date;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_main_adapter, parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String current_name = name.get(position);
        String current_message = message.get(position);
        String current_date = date.get(position);

        holder.name_textView.setText(current_name);
        holder.messate_textView.setText(current_message);
        holder.date_textView.setText(current_date);

    }

    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name_textView;
        private TextView messate_textView;
        private TextView date_textView;
        private ConstraintLayout chatItem;

        public ViewHolder(View view) {
            super(view);
            name_textView = view.findViewById(R.id.chat_main_adapter_name);
            messate_textView = view.findViewById(R.id.chat_main_adapter_message);
            date_textView = view.findViewById(R.id.chat_main_adapter_date);
            chatItem = view.findViewById(R.id.chatList);

            chatItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // 클릭된 아이템의 위치를 가져옴
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // 클릭된 아이템의 정보를 가져와서 로그로 출력하거나 원하는 작업을 수행할 수 있음
                String clickedName = name.get(position);
                String clickedMessage = message.get(position);
                String clickedDate = date.get(position);

                Log.d("RecyclerView Click", "Position : " + position + ", 이름 : " + name +
                        ", 메세지 : " + message + ", 날짜 : " + date);

                Intent intent = new Intent(view.getContext(), chat_room.class);
                intent.putExtra("name", clickedName);
                intent.putExtra("message_list", clickedMessage);
                intent.putExtra("date_list", clickedDate);
                intent.putExtra("key", 3);
                view.getContext().startActivity(intent);

            }
        }

    }


//    public void clear() {
//        name.clear();
//        message.clear();
//        date.clear();
//        notifyDataSetChanged();
//    }


}
