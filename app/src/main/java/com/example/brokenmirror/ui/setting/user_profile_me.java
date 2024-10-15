package com.example.brokenmirror.ui.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.brokenmirror.R;
import com.example.brokenmirror.bitmap.BitmapConverter;
import com.example.brokenmirror.data.UserDto;
import com.example.brokenmirror.sharedpref.UserSharedPref;

public class user_profile_me extends AppCompatActivity {

    ImageView myProfileImg;
    TextView myNameText;
    TextView myPhoneNumberText;

    private UserDto user_info;
    private UserSharedPref user_pref;

    Bitmap bitmap;

    BitmapConverter converter = new BitmapConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_me);

        // userSharedPref
        user_pref = new UserSharedPref(this);
        user_info = user_pref.getUser();

        Button closeMyProfileBtn = findViewById(R.id.close_myProfile_btn);
        myProfileImg = findViewById(R.id.my_profile_img);
        myNameText = findViewById(R.id.my_name_text);
        myPhoneNumberText = findViewById(R.id.my_phoneNumber_text);
        Button editProfileBtn = findViewById(R.id.edit_profile_btn);

        myNameText.setText(user_info.getUserName());
        myPhoneNumberText.setText(user_info.getPhoneNum());

        if (user_info.getProfileImg() != null) {
            bitmap = converter.StringToBitmap(user_info.getProfileImg());
            loadGlideImage(myProfileImg);
        }

        // 1. X버튼
        closeMyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent();
                setResult(RESULT_OK, profile);
                finish();
            }
        });

        // 2. 프로필 편집 버튼
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile_me.this, user_profile_me_edit.class);
                launcher.launch(intent);
                overridePendingTransition(0, 0);
            }
        });

    }  // onCreate

    // registerForActivityResult
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            user_info = user_pref.getUser();
            myNameText.setText(user_info.getUserName());
            if (user_info != null) {
                bitmap = converter.StringToBitmap(user_info.getProfileImg());
                loadGlideImage(myProfileImg);
            }
        }
    });

    // Glide (이미지 로드)
    public void loadGlideImage(ImageView profileImage) {
        Glide.with(this)
                .load(bitmap)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(profileImage);
    }
}