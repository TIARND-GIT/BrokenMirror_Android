/*
__author__ = 'Song Chae Young'
__date__ = 'Mar.05, 2024'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'login_find_id.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.setting;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.example.brokenmirror.R;
import com.example.brokenmirror.data.UserDto;

public class login_find_id extends AppCompatActivity {

    private boolean[] req = new boolean[6];
    private Button next_button;
    private Button email_num_button;
    private Button email_list_button;
    EditText name_editText;
    EditText birth_editText;
    EditText phone_editText;
    private EditText email_num_editText;
    private EditText email_editText;
    private TextView email_condition;
    private Toast toast;
    private String email_buttonText;
    private String email_num = "12345";
    private String email;
    private String current_email_input;
    private final String idPattern = "[a-zA-Z0-9._-]+";
    private final String namePattern = "[가-힣]*";
    private final String birthPattern = "\\d{8}";
    private final String phonePattern = "\\d{11}";
    private final String emailNumPattern = "\\d+";

    UserDto user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_find_id);

        user = new UserDto();

        int colorMain = ContextCompat.getColor(login_find_id.this, R.color.main);
        int colorDefault = ContextCompat.getColor(login_find_id.this, R.color.hint);
        final Button[] lastClickedButton = {null};

        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
        ConstraintLayout header_layout = findViewById(R.id.header);

        Button close_button = findViewById(R.id.find_id_close_Button);
        Button name_button = findViewById(R.id.find_id_name_btn);
        Button male_button = findViewById(R.id.find_id_male_button);
        Button female_button = findViewById(R.id.find_id_female_button);
        Button back_button = findViewById(R.id.back_button);
        email_num_button = findViewById(R.id.find_id_emailNum_btn);
        next_button = findViewById(R.id.find_id_next_button);
        email_list_button = findViewById(R.id.find_id_email_list_btn);

        name_editText = findViewById(R.id.find_id_name_editText);
        birth_editText = findViewById(R.id.find_id_birth_editText);
        phone_editText = findViewById(R.id.find_id_phone_editText);
        email_num_editText = findViewById(R.id.find_id_emailNum_editText);
        email_editText = findViewById(R.id.find_id_email_editText);

        TextView name_condition = findViewById(R.id.find_id_name_textView_condition);
        TextView birth_condition = findViewById(R.id.find_id_birth_textView_condition);
        TextView phone_condition = findViewById(R.id.find_id_phone_textView_condition);
        email_condition = findViewById(R.id.find_id_email_textView_condition);

        current_email_input = email_editText.getText().toString();

        // TextView : MARQUEE
        name_condition.setSelected(true);
        name_condition.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        birth_condition.setSelected(true);
        birth_condition.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        phone_condition.setSelected(true);
        phone_condition.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        email_condition.setSelected(true);
        email_condition.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        // Button : MARQUEE
        email_list_button.setSelected(true);
        email_list_button.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        email_num_button.setSelected(true);
        email_num_button.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        // Button
        // 닫기 버튼 이벤트 : 아이디 찾기 -> 메인 로그인 화면 전환
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // back_button : OnClickListener
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // find_id_name_btn / bottomSheet Dialog / nationality
        name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetNationDialog bottomSheetDialogFragment = new BottomSheetNationDialog();

                // BottomSheetNamdDialog -> login_find_id
                bottomSheetDialogFragment.setTextButtonClickListener(new BottomSheetNationDialog.TextButtonClickListener() {
                    @Override
                    public void onTextButtonClick(String buttonText) {
                        name_button.setText(buttonText);
                    }
                });

                // login_find_id -> BottomSheetNationDialog
                String buttonText = name_button.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("buttonText", buttonText);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "BottomSheetDialogFragment");
            }
        });

        // gender : male_button
        male_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastClickedButton[0] != male_button) {
                    if (lastClickedButton[0] != null) {
                        lastClickedButton[0].setBackgroundResource(R.drawable.login_main_edittext);
                        lastClickedButton[0].setTextColor(colorDefault);
                    }
                    male_button.setBackgroundResource(R.drawable.login_main_loginbtn);
                    male_button.setTextColor(colorMain);

                    lastClickedButton[0] = male_button;
                    req[2] = true;
                }

                updateNextButton(req);
            }
        });

        // gender : female_button
        female_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastClickedButton[0] != female_button) {
                    if (lastClickedButton[0] != null) {
                        lastClickedButton[0].setBackgroundResource(R.drawable.login_main_edittext);
                        lastClickedButton[0].setTextColor(colorDefault);
                    }

                    female_button.setBackgroundResource(R.drawable.login_main_loginbtn);
                    female_button.setTextColor(colorMain);

                    lastClickedButton[0] = female_button;
                    req[2] = true;
                }

                updateNextButton(req);
            }
        });

        // email : email_list_button
        email_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetEmailDialog bottomSheetDialogFragment = new BottomSheetEmailDialog();

                // BottomSheetEmailDialog -> login_find_id
                bottomSheetDialogFragment.setTextButtonClickListener(new BottomSheetEmailDialog.TextButtonClickListener() {
                    @Override
                    public void onTextButtonClick(String buttonText) {
                        Log.d("ghkrdls", "버튼 시작");
                        email_list_button.setText(buttonText);
                        email_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        if (!(current_email_input.isEmpty())) {
                            emailTextChange(current_email_input);
                            Log.d("ghkrdls", "버튼 끝");
                        }
                    }
                });

                // login_find_id -> BottomSheetEmailDialog
                email_buttonText = email_list_button.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("buttonText", email_buttonText);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "BottomSheetDialogFragment");
            }
        });

        // email : email_num_button
        email_num_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = email_editText.getText().toString();
                String current_email = email_list_button.getText().toString();

                if (current_email.equals(getString(R.string.find_id_email_list_0))) {
                    email = input;
                } else {
                    email = input + current_email;
                }

                if (input.isEmpty() || email_condition.getVisibility() == View.VISIBLE) {
                    toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_email_warning), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
                    toast.show();
                } else if (email_condition.getVisibility() == View.GONE) {
                    showPopupDialog_first(email);
                }

            }
        });


        // next_button
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFalse = true;

                // req 배열이 모두 false인지 확인
                for (boolean b : req) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_data_input_warning), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
                    toast.show();
                }
            }
        });


        // editText Focus
        // Focus : name_editText
        name_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String inputName = name_editText.getText().toString();
                if (!b) {       // focus out
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            next_button.setVisibility(View.VISIBLE);
//                        }
//                    }, 150);
                    if (inputName.matches(namePattern)) {      // focus out : 이름 입력 조건 부합
                        name_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        name_condition.setVisibility(View.GONE);
                    } else {        // focus out : 이름 입력 조건 미부합
                        name_editText.setBackgroundResource(R.drawable.textfield_invalid);
                        findViewById(R.id.find_id_name_textView_condition).setVisibility(View.VISIBLE);
                    }
                    if (inputName.isEmpty()) {      // focus out : 공백일 때
                        name_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        name_condition.setVisibility(View.GONE);
                    }

                } else {        // focus in
//                    next_button.setVisibility(View.GONE);
                    if (inputName.matches(namePattern)) {      // focus in : 이름 조건 부합
                        name_editText.setBackgroundResource(R.drawable.textfield_activate);
                        name_condition.setVisibility(View.GONE);
                    } else {        // focus in : 이름 조건 미부합
                        name_editText.setBackgroundResource(R.drawable.textfield_invalid);
                        findViewById(R.id.find_id_name_textView_condition).setVisibility(View.VISIBLE);
                    }
                    if (inputName.isEmpty()) {      // focus in : 공백일 때
                        name_editText.setBackgroundResource(R.drawable.textfield_activate);
                        name_condition.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Focus : birth_editText
        birth_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String inputBirth = birth_editText.getText().toString();

                if (!b) {        // focus out
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            next_button.setVisibility(View.VISIBLE);
//                        }
//                    }, 150);
                    if (inputBirth.matches(birthPattern)) {     // focus out : 생일 입력 조건 부합
                        birth_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        birth_condition.setVisibility(View.GONE);
                    } else {        // focus out : 생일 입력 조건 미부합
                        birth_editText.setBackgroundResource(R.drawable.textfield_invalid);
                        findViewById(R.id.find_id_birth_textView_condition).setVisibility(View.VISIBLE);
                    }
                    if (inputBirth.isEmpty()) {     // focus out : 공백일 때
                        birth_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        birth_condition.setVisibility(View.GONE);
                    }

                } else {        // focus in
//                    next_button.setVisibility(View.GONE);
                    if (inputBirth.matches(birthPattern)) {       // focus in : 생일 입력 조건 부합
                        birth_editText.setBackgroundResource(R.drawable.textfield_activate);
                        birth_condition.setVisibility(View.GONE);
                    } else {        // focus in : 생일 입력 조건 미부합
                        birth_editText.setBackgroundResource(R.drawable.textfield_invalid);
                        findViewById(R.id.find_id_birth_textView_condition).setVisibility(View.VISIBLE);
                    }
                    if (inputBirth.isEmpty()) {       // focus in : 공백일 때
                        birth_editText.setBackgroundResource(R.drawable.textfield_activate);
                        birth_condition.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Focus : phone_editText
        phone_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String inputPhone = phone_editText.getText().toString();

                if (!b) {        // focus out
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            next_button.setVisibility(View.VISIBLE);
//                        }
//                    }, 150);
                    if (inputPhone.matches(phonePattern)) {        // focus out : 번호 입력 조건 충족
                        phone_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        phone_condition.setVisibility(View.GONE);
                    } else {        // focus out : 번호 입력 조건 미충족
                        phone_editText.setBackgroundResource(R.drawable.textfield_invalid);
                        findViewById(R.id.find_id_phone_textView_condition).setVisibility(View.VISIBLE);
                    }
                    if (inputPhone.isEmpty()) {     // focus in : 공백일 때
                        phone_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        phone_condition.setVisibility(View.GONE);
                    }

                } else {      // focus in
//                    next_button.setVisibility(View.GONE);
                    if (inputPhone.matches(phonePattern)) {        // focus in : 번호 입력 조건 충족
                        phone_editText.setBackgroundResource(R.drawable.textfield_activate);
                        phone_condition.setVisibility(View.GONE);
                    } else {        // focus in : 번호 입력 조건 불충족
                        phone_editText.setBackgroundResource(R.drawable.textfield_invalid);
                        findViewById(R.id.find_id_phone_textView_condition).setVisibility(View.VISIBLE);
                    }
                    if (inputPhone.isEmpty()) {     // focus in : 공백일 때
                        phone_editText.setBackgroundResource(R.drawable.textfield_activate);
                        phone_condition.setVisibility(View.GONE);
                    }
                }

            }
        });

        // Focus : email_editText
        email_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {       // focus out
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            next_button.setVisibility(View.VISIBLE);
//                        }
//                    }, 150);
                    if (email_list_button.getText().toString().equals(getString(R.string.find_id_email_list_0))) {     // focus out : 직접 입력일 때
                        if (Patterns.EMAIL_ADDRESS.matcher(current_email_input).matches()) {      //  focus out : 직접 입력 - 이메일 형식이 맞을 경우
                            email_editText.setBackgroundResource(R.drawable.login_main_edittext);
                            email_condition.setVisibility(View.GONE);
                        } else {         // focus out : 직접 입력 - 공백이 아닌, 조건에 부합하지 않을 때
                            email_editText.setBackgroundResource(R.drawable.textfield_invalid);
                            findViewById(R.id.find_id_email_textView_condition).setVisibility(View.VISIBLE);
                        }

                    } else {      // focus out : 직접 입력이 아닐 때 (이메일 4종 조건)
                        if (current_email_input.matches(idPattern)) {        // focus out : 이메일 선택 - 아이디 조건에 부합할 때
                            email_editText.setBackgroundResource(R.drawable.login_main_edittext);
                            email_condition.setVisibility(View.GONE);
                        } else {        // focus out : 이메일 선택 - 아이디 조건에 부합하지 않고, 공백도 아닐 때
                            email_editText.setBackgroundResource(R.drawable.textfield_invalid);
                            findViewById(R.id.find_id_email_textView_condition).setVisibility(View.VISIBLE);
                        }
                    }

                    if (current_email_input.isEmpty()) {     // focus out : 공백일 때
                        email_editText.setBackgroundResource(R.drawable.login_main_edittext);
                        email_condition.setVisibility(View.GONE);
                    }

                } else {      // focus in 일 때
//                    next_button.setVisibility(View.GONE);
                    if (email_list_button.getText().toString().equals(getString(R.string.find_id_email_list_0))) {     // focus in : 직접 입력일 때
                        if (Patterns.EMAIL_ADDRESS.matcher(current_email_input).matches()) {      //  focus in : 직접 입력 - 이메일 형식이 맞을 경우
                            email_editText.setBackgroundResource(R.drawable.textfield_activate);
                            email_condition.setVisibility(View.GONE);
                        } else {        // focus in : 직접 입력 - 이메일 형식이 맞지 않은 경우 (공백 제외)
                            email_editText.setBackgroundResource(R.drawable.textfield_invalid);
                            findViewById(R.id.find_id_email_textView_condition).setVisibility(View.VISIBLE);
                        }

                    } else {        // focus in : 이메일 4종 일 때
                        if (current_email_input.matches(idPattern)) {       // focus in : 이메일 4종 - 조건에 부합할 때
                            email_editText.setBackgroundResource(R.drawable.textfield_activate);
                            email_condition.setVisibility(View.GONE);
                        } else {        // focus in : 이메일 4종 - 조건에 부합 하지 않고 공백도 아닐 때
                            email_editText.setBackgroundResource(R.drawable.textfield_invalid);
                            findViewById(R.id.find_id_email_textView_condition).setVisibility(View.VISIBLE);
                        }
                    }

                    if (current_email_input.isEmpty()) {        // focus in : 공백일 때
                        email_editText.setBackgroundResource(R.drawable.textfield_activate);
                        email_condition.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Focus : email_num_editText
        email_num_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String inputEmailNum = email_num_editText.getText().toString();

                if (!b) {       // focus out
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            next_button.setVisibility(View.VISIBLE);
//                        }
//                    }, 150);
                    if (inputEmailNum.matches(emailNumPattern)) {        // focus out : 인증 번호 조건 충족
                        email_num_editText.setBackgroundResource(R.drawable.login_main_edittext);
                    } else {        // focus out : 인증 번호 조건 미충족
                        email_num_editText.setBackgroundResource(R.drawable.textfield_invalid);
                    }
                    if (inputEmailNum.isEmpty()) {      // focus out : 공백일 때
                        email_num_editText.setBackgroundResource(R.drawable.login_main_edittext);
                    }

                } else {        // focus in
//                    next_button.setVisibility(View.GONE);
                    if (inputEmailNum.matches(emailNumPattern)) {        // focus in : 인증 번호 조건 충족
                        email_num_editText.setBackgroundResource(R.drawable.textfield_activate);
                    } else {        // focus in : 인증 번호 조건 미충족
                        email_num_editText.setBackgroundResource(R.drawable.textfield_invalid);
                    }
                    if (inputEmailNum.isEmpty()) {      // focus in : 공백일 때
                        email_num_editText.setBackgroundResource(R.drawable.textfield_activate);
                    }
                }
            }
        });

        // editText Change
        // Change : name_editText
        name_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInputName = charSequence.toString();

                if (userInputName.matches(namePattern)) {       // 이름 입력 조건 충족
                    name_editText.setBackgroundResource(R.drawable.textfield_activate);
                    name_condition.setVisibility(View.GONE);
                    req[0] = true;
                } else {        // 이름 조건 불충족
                    name_editText.setBackgroundResource(R.drawable.textfield_invalid);
                    findViewById(R.id.find_id_name_textView_condition).setVisibility(View.VISIBLE);
                    req[0] = false;
                }
                if (userInputName.isEmpty()) {       // 공백일 시
                    name_editText.setBackgroundResource(R.drawable.textfield_activate);
                    name_condition.setVisibility(View.GONE);
                    req[0] = false;
                }

                updateNextButton(req);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        // Change : birth_editText
        birth_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInputBirth = charSequence.toString();

                if (userInputBirth.matches(birthPattern)) {     // 생일 입력 조건 충족
                    birth_editText.setBackgroundResource(R.drawable.textfield_activate);
                    birth_condition.setVisibility(View.GONE);
                    req[1] = true;
                } else {        // 생일 입력 조건 불충족
                    birth_editText.setBackgroundResource(R.drawable.textfield_invalid);
                    findViewById(R.id.find_id_birth_textView_condition).setVisibility(View.VISIBLE);
                    req[1] = false;
                }
                if (userInputBirth.isEmpty()) {     // 공백일 시
                    birth_editText.setBackgroundResource(R.drawable.textfield_activate);
                    birth_condition.setVisibility(View.GONE);
                    req[1] = false;
                }

                updateNextButton(req);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Change : phone_editText
        phone_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInputPhone = charSequence.toString();

                if (userInputPhone.matches(phonePattern)) {        // 번호 입력 조건 충족
                    phone_editText.setBackgroundResource(R.drawable.textfield_activate);
                    phone_condition.setVisibility(View.GONE);
                    req[3] = true;
                } else {        // 번호 입력 조건 불충족
                    phone_editText.setBackgroundResource(R.drawable.textfield_invalid);
                    findViewById(R.id.find_id_phone_textView_condition).setVisibility(View.VISIBLE);
                    req[3] = false;
                }
                if (userInputPhone.isEmpty()) {     // 공백일 시
                    phone_editText.setBackgroundResource(R.drawable.textfield_activate);
                    phone_condition.setVisibility(View.GONE);
                    req[3] = false;
                }

                updateNextButton(req);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Change : email_editText
        email_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                current_email_input = charSequence.toString();
                emailTextChange(current_email_input);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Change : email_num_editText
        email_num_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInputEmailNum = charSequence.toString();

                if (userInputEmailNum.matches(emailNumPattern)) {        // 인증 번호 조건 충족
                    email_num_editText.setBackgroundResource(R.drawable.textfield_activate);
                    req[5] = true;
                } else {        // 인증 번호 조건 불충족
                    email_num_editText.setBackgroundResource(R.drawable.textfield_invalid);
                    req[5] = false;
                }
                if (userInputEmailNum.isEmpty()) {      // 공백일 시
                    email_num_editText.setBackgroundResource(R.drawable.textfield_activate);
                    req[5] = false;
                }

                updateNextButton(req);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                name_editText.clearFocus();
                birth_editText.clearFocus();
                phone_editText.clearFocus();
                email_editText.clearFocus();
                email_num_editText.clearFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                return false;
            }
        });

        header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_editText.clearFocus();
                birth_editText.clearFocus();
                phone_editText.clearFocus();
                email_editText.clearFocus();
                email_num_editText.clearFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }   // onCreate

    // Update next_btn status
    public void updateNextButton(boolean[] req) {

        // include false
        for (int j = 0; j < req.length; j++) {
            if (!req[j]) {
                Log.v("Test", "비활성화");
                next_button.setBackgroundResource(R.drawable.login_main_loginbtn);
                next_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
                next_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!req[0]) {
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_name_warning), Toast.LENGTH_SHORT);
                        } else if (!req[1]) {
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_birth_warning), Toast.LENGTH_SHORT);
                        } else if (!req[2]) {
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_gender_warning), Toast.LENGTH_SHORT);
                        } else if (!req[3]) {
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_phone_warning), Toast.LENGTH_SHORT);
                        } else if (!req[4]) {
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_email_warning), Toast.LENGTH_SHORT);
                        } else if (!req[5]) {
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.find_pw_email_num_warning), Toast.LENGTH_SHORT);
                        }

                        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
                        toast.show();
                    }
                });

                return;
            }
        }

        // all true
        next_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
        next_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        Log.v("Test", "활성화");

        // change layout & activity login_find_id -> login_find_id_result
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserName(name_editText.getText().toString());
                user.setBirth(birth_editText.getText().toString());
                user.setPhoneNum(phone_editText.getText().toString());
                String current_email_num = email_num_editText.getText().toString();
                if (current_email_num.equals(email_num)) {
                    Intent intent = new Intent(login_find_id.this, login_find_id_result.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                } else {
                    showPopupDialog_def();
                }
            }
        });
    }

    private void showPopupDialog_first(String email) {
        Dialog popupDialog = new Dialog(login_find_id.this);
        popupDialog.setContentView(R.layout.popup_dialog_email_certi);

        // Background color of the dialog to transparent
        Window window = popupDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView email_textView = popupDialog.findViewById(R.id.email_textView);
        Button confirm_button = popupDialog.findViewById(R.id.confirm_button);

        email_textView.setText(email);
        email_textView.setSelected(true);
        email_textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        // Button : OnClickListener
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();
                email_num_button.setText(getString(R.string.find_id_email_re_request));
            }
        });

        popupDialog.show();

    }

    private void showPopupDialog_def() {
        Dialog popupDialog = new Dialog(login_find_id.this);
        popupDialog.setContentView(R.layout.popup_dialog);

        // Background color of the dialog to transparent
        Window window = popupDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView content_textView = popupDialog.findViewById(R.id.popup_dialog_textView);
        Button confirm_button = popupDialog.findViewById(R.id.popup_dialog_confirm_button);

        content_textView.setText(getString(R.string.popup_dialog_wrong));

        // Button : OnClickListener
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();
            }
        });

        popupDialog.show();

    }

    private void emailTextChange(String input) {
        Log.d("ghkrdls", "current email input : " + current_email_input);

        if (email_list_button.getText().toString().equals(getString(R.string.find_id_email_list_0))) {      // 직접 입력
            if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) {     // 직접 입력 입력 조건 부합
                email_editText.setBackgroundResource(R.drawable.textfield_activate);
                email_condition.setVisibility(View.GONE);
                req[4] = true;
            } else {        // 직접 입력 조건 미부합
                email_editText.setBackgroundResource(R.drawable.textfield_invalid);
                findViewById(R.id.find_id_email_textView_condition).setVisibility(View.VISIBLE);
                req[4] = false;
            }
        } else {        // 이메일 4종
            if (input.matches(idPattern)) {        // 이메일 4종 조건 부합
                email_editText.setBackgroundResource(R.drawable.textfield_activate);
                email_condition.setVisibility(View.GONE);
                req[4] = true;
            } else {        // 이메일 4종 조건 미부합
                email_editText.setBackgroundResource(R.drawable.textfield_invalid);
                findViewById(R.id.find_id_email_textView_condition).setVisibility(View.VISIBLE);
                req[4] = false;
            }
        }
        if (input.isEmpty()) {     // 공백일 시
            email_editText.setBackgroundResource(R.drawable.textfield_activate);
            email_condition.setVisibility(View.GONE);
            req[4] = false;
        }

        updateNextButton(req);
        Log.d("ghkrdls", "메서드 끝");

    }
}   // login_find_id.java
