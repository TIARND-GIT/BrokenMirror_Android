/*
__author__ = 'Song Chae Young'
__date__ = 'Mar.15, 2024'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'setting_friend.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.setting;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.brokenmirror.R;

public class setting_friend extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_friend);

        Button back_button = findViewById(R.id.setting_friend_back_button);

        TextView block_layout = findViewById(R.id.setting_friend_block_layout);
        TextView sync_layout = findViewById(R.id.setting_friend_sync_layout);

        View loading = findViewById(R.id.loading);

        Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.test_need_server), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);

        // Button
        // back_button : OnClickListener
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // LinearLayout
        // block_layout : OnClickListener
//        block_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(setting_friend.this, setting_friend_block.class);
//                startActivity(intent);
//                onPause();
//            }
//        });

        // sync_layout : OnClickListener
        sync_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.GONE);
                    }
                }, 3000);
                toast.show();
            }
        });
    }
}
