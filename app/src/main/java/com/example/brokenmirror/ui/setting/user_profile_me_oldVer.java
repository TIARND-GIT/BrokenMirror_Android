/*
__author__ = 'Song Chae Young'
__date__ = 'Nov.06, 2023'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'user_profile_me.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.setting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.brokenmirror.R;

import java.util.Locale;

public class user_profile_me_oldVer extends AppCompatActivity {

    // EditText 변수 선언
    private TextView myNameText;
    String key = "name";
    ImageView profileImageView;
    private Uri selectedImageUri;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_me);

//        // 상태 바를 넘어서 레이아웃을 그리기 위한 플래그 설정
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Button ok_btn = findViewById(R.id.ok_btn);
        Button cancel_btn =  findViewById(R.id.cancel_btn);
        profileImageView = findViewById(R.id.profile_img);
        myNameText = findViewById(R.id.my_name_text);
//        LinearLayout name_linearLayout = findViewById(R.id.my_name_linearlayout);
        Button name_edit_button = findViewById(R.id.my_name_edit_button);
        Button edit_profile_img_button = findViewById(R.id.change_profile_img_btn_me);

        // SharedPreferences 객체 가져오기
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        // 저장된 이름 불러오기, 기본값은 빈 문자열
        String savedName = sharedPreferences.getString(key, "");

        // 저장된 이미지 URI 불러오기
        String savedURI = sharedPreferences.getString("selectedURI", null);

        // 저장된 이름이 있다면 EditText에 설정
        if (!savedName.isEmpty()) {
            myNameText.setText(savedName);
        }

        // 저장된 이미지 URI가 있다면 ImageView에 설정
        if (savedURI != null) {
            selectedImageUri = Uri.parse(savedURI);
            profileImageView.setImageURI(selectedImageUri);
        }

        // 취소 버튼
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 확인 버튼
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedName = myNameText.getText().toString().trim();
                if (!changedName.isEmpty()) {
                    // 이름이 비어있지 않으면 결과 설정
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("changedName", changedName);
                    setResult(RESULT_OK, resultIntent);
                }

                // 프로필 이미지 변경 여부 확인
                if (selectedImageUri != null) {
                    // 선택한 이미지를 ImageView에 표시
                    ImageView profileImageView = findViewById(R.id.profile_img);
                    profileImageView.setImageURI(selectedImageUri);

                    // Glide를 사용하여 이미지를 원 모양으로 자르고 설정
                    Glide.with(user_profile_me_oldVer.this)
                            .load(selectedImageUri)
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(profileImageView);

                    // 바뀐 이미지를 setting_main의 user_profile 이미지뷰에도 적용하기 위해 결과에 이미지 URI 추가
                    Intent imageResultIntent = new Intent();
                    imageResultIntent.putExtra("selectedImageUri", selectedImageUri.toString());
                    setResult(RESULT_OK, imageResultIntent);
                }

                onBackPressed();
            }
        });

        // 프로필 이미지 변경 버튼
        edit_profile_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 액티비티 스택을 정리
                startActivityForResult(intent, 1);
            }
        });

        // 이름 수정 다이얼로그 호출
//        name_linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showChangeNameDialog();
//            }
//        });

//        myNameText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showChangeNameDialog();
//            }
//        });

        name_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showChangeNameDialog(); }
        });

    }  //onCreate


    // 소프트 키보드를 보여주는 메서드
    private void showSoftKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    // 소프트 키보드를 감추는 메서드
//    private void hideSoftKeyboard() {
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(myNameText.getWindowToken(), 0);
//    }

    // 이름 변경 다이얼로그의 linearLayout2 터치 시 소프트 키보드 감추기 (showChangeNameDialog()메서드 안으로 옮겨야 됨)
//        findViewById(android.R.id.linearLayout2).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                hideSoftKeyboard();
//                myNameText.clearFocus();
//                return false;
//            }
//        });


    // 글자 수 업데이트 메소드
    private void updateCharacterCount(int currentLength, TextView countTextView) {
        // 최대 글자 수
        int maxLength = 20;

        // 현재 글자 수 표시
        countTextView.setText(String.format(Locale.getDefault(), "%d/%d", currentLength, maxLength));
    }

    // 확인 버튼 상태 업데이트 메소드
    private void updateDoneButtonState(String newText, Button doneButton) {
        // 원래 있던 텍스트와 비교하여 변경이 있는지 확인
        boolean isTextChanged = !newText.equals(myNameText.getText().toString());

        // 텍스트가 없거나 변경이 없으면 버튼 비활성화
        doneButton.setEnabled(!(TextUtils.isEmpty(newText) || !isTextChanged));

        // 버튼 비활성화 시 텍스트 색상 회색으로 변경
        doneButton.setTextColor(doneButton.isEnabled() ? Color.WHITE : Color.GRAY);
    }



    private void showChangeNameDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Transparent_NoActionBar);
        dialog.setContentView(R.layout.dialog_change_name);
        dialog.setCancelable(true);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ConstraintLayout constraintLayout = dialog.findViewById(R.id.parent_layout);
        Button cancel_button = dialog.findViewById(R.id.cancel_button);
        Button confirm_button = dialog.findViewById(R.id.done_button);
        EditText name_editText = dialog.findViewById(R.id.name);
        Button clear_button = dialog.findViewById(R.id.delete_name);
        TextView len_textView = dialog.findViewById(R.id.count_name);

        // 다이얼로그가 전체 화면에 나타나도록 설정
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        // 다이얼로그가 표시되면서 포커스를 EditText로 이동
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                name_editText.requestFocus();
                showSoftKeyboard(name_editText);
                // 처음 다이얼로그가 열릴 때도 글자 수 업데이트
                updateCharacterCount(name_editText.getText().length(), len_textView);
                // 초기 상태에서 done 버튼 비활성화
                updateDoneButtonState(name_editText.getText().toString(), confirm_button);
            }
        });

        // user_profile_name.xml의 EditText에서 이름 가져와 설정
        name_editText.setText(myNameText.getText());

        // 커서를 맨 뒤로 이동
        name_editText.setSelection(name_editText.getText().length());

        // 다이얼로그가 표시되면서 소프트 키보드 표시
        name_editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftKeyboard(name_editText);
            }
        }, 100); // 딜레이를 주어 커서 이동 후에 키보드가 나타나도록 함

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_editText.setText("");
                // 글자 수 업데이트 호출
                updateCharacterCount(0, len_textView);
            }
        });

        confirm_button.setEnabled(false);
        confirm_button.setTextColor(Color.GRAY);

        // EditText의 텍스트 변경 감지 리스너
        name_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // 텍스트 변경 전에 호출됨
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 텍스트가 변경될 때 호출됨
                updateCharacterCount(charSequence.length(), len_textView);

                // 추가: 20글자가 넘어가면 입력 방지
                if (charSequence.length() > 20) {
                    String limitedText = charSequence.subSequence(0, 20).toString();
                    name_editText.setText(limitedText);
                    name_editText.setSelection(20);
                }

                updateDoneButtonState(name_editText.getText().toString(), confirm_button);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 텍스트 변경 후에 호출됨
            }
        });


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수정한 이름을 user_profile_name에 반영
                myNameText.setText(name_editText.getText().toString());
                dialog.dismiss();

                // 이름이 공백인지 확인
                String nameText = name_editText.getText().toString().trim();
                if (nameText.isEmpty()) {
                    // 경고창 표시
                    AlertDialog.Builder builder = new AlertDialog.Builder(user_profile_me_oldVer.this);
                    builder.setMessage(getString(R.string.no_blank));
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }
        });

        // Clear Focus
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                name_editText.clearFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                return false;
            }
        });

        dialog.show();

    }  // showChangeNameDialog


    // onPause()
    @Override
    protected void onPause() {
        super.onPause();

        // 액티비티가 일시정지될 때 수정한 이름을 SharedPreferences에 저장
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, myNameText.getText().toString());
        editor.apply();

        // 수정된 이름을 Intent에 담아서 현재 액티비티를 종료
        Intent resultIntent = new Intent();
        resultIntent.putExtra(key, myNameText.getText().toString());
        setResult(RESULT_OK, resultIntent);
        // finish();

        // 선택한 이미지 URI도 함께 저장
        if (selectedImageUri != null) {
            editor.putString("selectedImageUri", selectedImageUri.toString());
        }
        editor.apply();
    }


    // onResume()
    @Override
    protected void onResume() {
        super.onResume();

        // 이미지 URI 확인: 저장된 이미지 URI를 올바르게 가져오는지 확인
        String savedImageUri = sharedPreferences.getString("selectedImageUri", null);
        if (savedImageUri == null) {
            Log.e("DEBUG", "저장된 이미지 URI가 null입니다.");
        } else {
            Log.d("DEBUG", "저장된 이미지 URI: " + savedImageUri);
        }
        if (savedImageUri != null) {
            // URI를 Uri 객체로 파싱합니다.
            Uri imageUri = Uri.parse(savedImageUri);

            // 이미지를 설정할 ImageView를 찾습니다.
            ImageView profileImageView = findViewById(R.id.profile_img);

            // Glide를 사용하여 이미지를 로드하고 표시합니다.
            Glide.with(this)
                    .load(imageUri)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())) // 원 모양으로 자르기
                    .into(profileImageView);
        }
    }


    // onActivityResult()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // 선택한 이미지의 Uri를 가져옴
            selectedImageUri = data.getData();

            // 선택한 이미지를 ImageView에 표시
            ImageView profileImageView = findViewById(R.id.profile_img);
            profileImageView.setImageURI(selectedImageUri);

            // Glide를 사용하여 이미지를 원 모양으로 자르고 설정
            Glide.with(this)
                    .load(selectedImageUri)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(profileImageView);

            // 바뀐 이미지를 setting_main의 상단 프로필 부분 사진 필드에 적용
            Intent resultIntent = new Intent();
            // 선택한 이미지의 Uri를 Intent에 추가
            resultIntent.putExtra("selectedImageUri", selectedImageUri.toString());
            // setResult 메서드를 통해 결과 설정
            setResult(RESULT_OK, resultIntent);

            // 선택한 이미지의 URI를 SharedPreferences에 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedImageUri", selectedImageUri.toString());
            editor.apply();

            //선택한 이미지를 로컬에 저장 (예: SharedPreferences를 사용)
            //saveImageToLocalStorage(selectedImageUri);
        }
    }


    // 선택한 이미지를 로컬에 저장하는 메소드
//    private void saveImageToLocalStorage(Uri selectedImageUri) {
//
//        try {
//            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//            // 파일 이름을 지정하여 저장 (예: "profile_image.jpg")
//            String fileName = "profile_image.jpg";
//            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            outputStream.close();
//
//            // 저장된 파일 경로를 SharedPreferences에 저장 (추후 불러올 때 사용)
//            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("profile_image_path", getFilesDir() + "/" + fileName);
//            editor.apply();
//        } catch (IOException e) { e.printStackTrace(); }
//
//    }

}