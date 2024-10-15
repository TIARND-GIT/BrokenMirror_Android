/*
__author__ = 'Song Chae Young'
__date__ = 'Jan.25, 2024'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'setting_account.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.setting;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.brokenmirror.R;
import com.example.brokenmirror.bitmap.BitmapConverter;
import com.example.brokenmirror.data.UserDto;
import com.example.brokenmirror.sharedpref.AutoSharedPref;
import com.example.brokenmirror.sharedpref.UserSharedPref;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class setting_account extends AppCompatActivity {

    TextView NameTextView;
    TextView userNameTextView;
    TextView userIdTextView;
    TextView userCreatedAtTextView;
    ImageView userProfileImg;

    private UserDto user_info;
    private UserSharedPref user_pref;
    private AutoSharedPref auto_pref;

    Bitmap bitmap;

    BitmapConverter converter = new BitmapConverter();

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy. MM. dd", Locale.KOREA);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_account);

        // userSharedPref
        user_pref = new UserSharedPref(this);
        user_info = user_pref.getUser();
        // AutoLogin
        auto_pref = new AutoSharedPref(this);

        Button back_button = findViewById(R.id.setting_account_back_button);
        Button close_button = findViewById(R.id.close_button);

        ConstraintLayout change_pw_layout = findViewById(R.id.setting_account_change_pw_layout);
        ConstraintLayout change_phone_layout = findViewById(R.id.setting_account_change_phone_layout);
        ConstraintLayout login_layout = findViewById(R.id.setting_account_login_layout);
        ConstraintLayout logout_layout = findViewById(R.id.setting_account_logout_layout);
        ConstraintLayout withdrawal_layout = findViewById(R.id.setting_account_withdrawal_layout);

        NameTextView = findViewById(R.id.name);
        userNameTextView = findViewById(R.id.user_name);
        userIdTextView = findViewById(R.id.user_id);
        userCreatedAtTextView = findViewById(R.id.user_created_at);
        userProfileImg = findViewById(R.id.profile_image);

        // 로그인 한 회원 조회
        NameTextView.setText(user_info.getUserName());
        userNameTextView.setText(user_info.getUserName());
        userIdTextView.setText(user_info.getId());
        userCreatedAtTextView.setText(dateformat.format(user_info.getCreatedAt()));

        if (user_info != null) {
            bitmap = converter.StringToBitmap(user_info.getProfileImg());
            Glide.with(this)
                    .load(bitmap)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(userProfileImg);
        }

        // Button
        // back_button : OnClickListener (setting_account.java -> setting_main.java)
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //close_button
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // ConstraintLayout
        // change_pw_layout : OnClickListener
        change_pw_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting_account.this, setting_account_pw.class);
                launcher.launch(intent);
                onPause();
            }
        });

        // change_phone_layout : OnClickListener
        change_phone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting_account.this, setting_account_phone.class);
                launcher.launch(intent);
                onPause();

            }
        });

        // login_layout : OnClickListener
        login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting_account.this, setting_account_login.class);
                startActivity(intent);
                onPause();
            }
        });

        // logout_layout : OnClickListener
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();

                // close all activities
                for (ActivityManager.AppTask appTask : appTasks) {
                    appTask.finishAndRemoveTask();
                }
                // switch to the 'login_main' activity
                Intent intent = new Intent(setting_account.this, login_main.class);
                user_pref.removeUser();
                auto_pref.removeAuto();
                startActivity(intent);
                finish();

            }
        });

        // withdrawal_layout : OnClickListener
        withdrawal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting_account.this, setting_account_withdrawal.class);
                startActivity(intent);
                onPause();
            }
        });
    }

    // registerForActivityResult
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            user_info = user_pref.getUser();
        }
    });
}
