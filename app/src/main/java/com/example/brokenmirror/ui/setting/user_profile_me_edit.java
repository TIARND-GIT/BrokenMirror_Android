package com.example.brokenmirror.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.brokenmirror.R;
import com.example.brokenmirror.bitmap.BitmapConverter;
import com.example.brokenmirror.data.UserDto;
import com.example.brokenmirror.retrofit.RetrofitService;
import com.example.brokenmirror.retrofit.UserApi;
import com.example.brokenmirror.sharedpref.UserSharedPref;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class user_profile_me_edit extends AppCompatActivity {
    ImageView editMyProfileImg;
    TextView editMyNameText;
    TextView editMyPhoneText;
    Button editMyNameBtn;
    ImageButton editMyProfileBtn;

    String changedName;

    UserDto user_info;
    // UserApi
    UserApi userApi = RetrofitService.getUserApi();
    // UserSharedPreference
    UserSharedPref user_pref;

    Bitmap bitmap = null;
    String image;

    BitmapConverter converter = new BitmapConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_me_edit);

        // userSharedPref
        user_pref = new UserSharedPref(this);
        user_info = user_pref.getUser();

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        Button closeMyProfileBtn = findViewById(R.id.close_edit_myProfile_btn);

        editMyProfileImg = findViewById(R.id.edit_my_profile_img);
        editMyProfileBtn = findViewById(R.id.edit_my_profile_img_btn);
        editMyNameText = findViewById(R.id.edit_my_name_text);
        editMyNameBtn = findViewById(R.id.edit_my_name_btn);
        editMyPhoneText = findViewById(R.id.my_phoneNumber_text);

        Button closeBtn = findViewById(R.id.cancel_btn);
        Button doneBtn = findViewById(R.id.done_btn);

        editMyNameText.setText(user_info.getUserName());
        editMyPhoneText.setText(user_info.getPhoneNum());

        if (user_info.getProfileImg() != null) {
            bitmap = converter.StringToBitmap(user_info.getProfileImg());
            loadGlideImage(editMyProfileImg);

        }
        closeMyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 2. 프로필 이미지 설정
        editMyProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                activityResultLauncher.launch(intent);
                Intent intent = new Intent(Intent.ACTION_PICK);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg"); // JPEG 형식만 선택 가능
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"}); // JPEG 및 PNG 형식만 선택 가능
//                activityResultLauncher.launch(intent);
                imagelauncher.launch(intent);
            }
        });

        editMyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                activityResultLauncher.launch(intent);
                Intent intent = new Intent(Intent.ACTION_PICK);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg"); // JPEG 형식만 선택 가능
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"}); // JPEG 및 PNG 형식만 선택 가능
//                activityResultLauncher.launch(intent);
                imagelauncher.launch(intent);
            }
        });

        // 3. 이름 설정
        editMyNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile_me_edit.this, user_profile_me_change_name.class);
                intent.putExtra("userName", editMyNameText.getText().toString());
                launcher.launch(intent);
            }
        });

//        editMyNameBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(user_profile_me_edit.this, user_profile_me_change_name.class);
//                startActivity(intent);
//            }
//        });

        // 4. 하단 버튼 [취소][확인]
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedName = editMyNameText.getText().toString().trim();
                Intent profile = new Intent();
                user_info.setUserName(changedName);
                user_pref.putUser(user_info);
                newUserName(user_info.getId(), changedName);
                newImage(user_info.getId(), image);
                user_info.setProfileImg(image);
                user_pref.putUser(user_info);
                Toast.makeText(getApplicationContext(), "프로필이 변경되었습니다.", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, profile);
                finish();
            }
        });

    } //onCreate

    // registerForActivityResult
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    String changeName = result.getData().getStringExtra("userName");
                    editMyNameText.setText(changeName);
                }
            }
        }
    });

    // imageActivityResult
    ActivityResultLauncher<Intent> imagelauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri imageUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        loadGlideImage(editMyProfileImg);
                        image = converter.BitmapToString(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    // newUserName (이름 변경)
    public void newUserName(String id, String userName) {
        userApi.newUserName(id, userName).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "이름 변경 성공");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
                Log.e("TAG", "Error: " + t.getMessage());
            }
        });
    }

    // newImage (이미지 변경)
    public void newImage(String id, String profileImg) {
        userApi.newImage(id, profileImg).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "이미지 변경 성공");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
                Log.e("TAG", "Error: " + t.getMessage());
            }
        });
    }

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