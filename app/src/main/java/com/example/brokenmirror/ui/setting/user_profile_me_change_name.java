package com.example.brokenmirror.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.brokenmirror.R;
import com.example.brokenmirror.data.UserDto;
import com.example.brokenmirror.sharedpref.UserSharedPref;

import java.util.Locale;

public class user_profile_me_change_name extends AppCompatActivity {

    String name;

    UserDto user_info;
    UserSharedPref user_pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_me_change_name);

        name = getIntent().getStringExtra("userName");

         // userSharedPref
        user_pref = new UserSharedPref(this);
        user_info = user_pref.getUser();

        ConstraintLayout context_layout = findViewById(R.id.context_layout);
        ConstraintLayout button_layout = findViewById(R.id.button_layout);
        ConstraintLayout header_layout = findViewById(R.id.header);

        Button changeNameBackBtn = findViewById(R.id.change_name_back_btn);

        EditText nameEditText = findViewById(R.id.name);
        Button deleteNameBtn = findViewById(R.id.delete_name);
        TextView countName = findViewById(R.id.count_name);

        Button changeNameCancelBtn = findViewById(R.id.cancel_btn);
        Button changeNameDoneBtn = findViewById(R.id.done_btn);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_profile_me_edit, null);
        TextView editMyNameText = view.findViewById(R.id.edit_my_name_text);

        // 상단 뒤로가기 버튼
        changeNameBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 이름 EditText
        nameEditText.requestFocus();

        // 이름 가져오기
        nameEditText.setText(name);

        nameEditText.setSelection(nameEditText.length());
        updateCountName(nameEditText.getText().length(), countName);

        //이름 EditText 텍스트 변경 감지 리스너
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // 글자수 변경
                updateCountName(charSequence.length(), countName);
                // 20글자 제한
//                if(charSequence.length() > 20) {
//                    String limText = charSequence.subSequence(0,20).toString();
//                    nameEditText.setText(limText);
//                    nameEditText.setSelection(20);
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 텍스트 삭제 버튼
        deleteNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.setText("");
                updateCountName(0, countName);
            }
        });

        // 하단 취소 버튼
        changeNameCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 하단 확인 버튼
        changeNameDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedName = nameEditText.getText().toString().trim();
                if (changedName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "이름을 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("userName", changedName);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        // 빈 레이아웃 클릭 시 키보드 감추기
//        parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nameEditText.clearFocus();
//                hideKeyboard();
//            }
//        });

        context_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEditText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

    }  // onCreate

    // 메서드(1) : 키보드 숨기기  (SongChaeYoung98 : 에러나서 쓰지 못함)
    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // 메서드(2) : 이름 글자 수 업데이트
    private void updateCountName(int curLength, TextView countText) {
        int maxLength = 20;
        countText.setText(String.format(Locale.getDefault(), "%d/%d", curLength, maxLength));
    }

    // 매서드(3) : 확인 버튼 상태 업데이트
//    private void updateDoneBtn(String newName, Button doneBtn) {
//        boolean sameName = newName.equals(editMyNameText.getText().toString());
//        doneBtn.setEnabled();
//    }

}
