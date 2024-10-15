/*
__author__ = 'Song Chae Young'
__date__ = 'Feb.20, 2024'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'join_agreement.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.setting;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.brokenmirror.GlobalVariables;
import com.example.brokenmirror.R;

import java.util.Arrays;
import java.util.List;

public class join_agreement extends AppCompatActivity {
    static boolean[] JOIN_AGREEMENT_LIST_STATE = GlobalVariables.JOIN_AGREEMENT_LIST_STATE;
    Button agree_button;
    int contextCode;
    Intent intent;
    Intent intent_login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_agreement);

        intent = new Intent(join_agreement.this, join_agreement_detail.class);
        intent_login = new Intent(join_agreement.this, login_main.class);

        Button back_button = findViewById(R.id.back_button);
        Button close_button = findViewById(R.id.close_button);
        agree_button = findViewById(R.id.agree_button);

        ConstraintLayout list_0 = findViewById(R.id.list_0);
        ConstraintLayout list_1 = findViewById(R.id.list_1);
        ConstraintLayout list_2 = findViewById(R.id.list_2);
        ConstraintLayout list_3 = findViewById(R.id.list_3);
        ConstraintLayout list_4 = findViewById(R.id.list_4);

        // getIntent
        Intent get_intent = getIntent();
        if (get_intent != null) {
            int stateCode = get_intent.getIntExtra("stateCode", -1);
            Log.d("ghkrdls", "stateCode : " + stateCode);

            if (stateCode == 100) {
                JOIN_AGREEMENT_LIST_STATE[0] = true;
            } else if (stateCode == 200) {
                JOIN_AGREEMENT_LIST_STATE[1] = true;
            } else if (stateCode == 300) {
                JOIN_AGREEMENT_LIST_STATE[2] = true;
            } else if (stateCode == 400) {
                JOIN_AGREEMENT_LIST_STATE[3] = true;
            } else if (stateCode == 500) {
                JOIN_AGREEMENT_LIST_STATE[4] = true;
            }
            Log.d("ghkrdls", "JOIN_AGREEMENT_LIST_STATE : " + Arrays.toString(JOIN_AGREEMENT_LIST_STATE));
            updateViewBackground();
        }

        // Button
        // back_button : OnClickListener
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_login);
                finish();
            }
        });

        // close_button : OnClickListener
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_login);
                finish();
            }
        });

        agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (JOIN_AGREEMENT_LIST_STATE[0] && JOIN_AGREEMENT_LIST_STATE[1] && JOIN_AGREEMENT_LIST_STATE[2] && JOIN_AGREEMENT_LIST_STATE[3]) {
                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();

                    // close all activities
                    for (ActivityManager.AppTask appTask : appTasks) {
                        appTask.finishAndRemoveTask();
                    }

                    Intent intent1 = new Intent(join_agreement.this, join_member.class);
                    startActivity(intent1);
                    finish();

                } else {
                    for (int i = 0; i < JOIN_AGREEMENT_LIST_STATE.length - 1; i++) {
                        if (!JOIN_AGREEMENT_LIST_STATE[i]) {
                            contextCode = 100 + (i * 100);
                            intent.putExtra("contextCode", contextCode);
                            agreeButtonClick();
                            break;
                        }
                    }
                }
            }
        });

        list_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateCheck(0);
            }
        });

        list_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateCheck(1);
            }
        });

        list_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateCheck(2);
            }
        });

        list_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateCheck(3);
            }
        });
        list_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle
                JOIN_AGREEMENT_LIST_STATE[4] = !JOIN_AGREEMENT_LIST_STATE[4];
                updateViewBackground();
                Log.d("ghkrdls", "JOIN_AGREEMENT_LIST_STATE : " + Arrays.toString(JOIN_AGREEMENT_LIST_STATE));
            }
        });

    }   // onCreate

    private void agreeButtonClick() {
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void updateViewBackground() {
        for (int i = 0; i < JOIN_AGREEMENT_LIST_STATE.length; i++) {
            int currentViewId = getResources().getIdentifier("view_" + i, "id", getPackageName());
            View currentView = findViewById(currentViewId);

            // show checkbox state
            if (JOIN_AGREEMENT_LIST_STATE[i]) {
                currentView.setBackgroundResource(R.drawable.checkbox_activate);
            } else {
                currentView.setBackgroundResource(R.drawable.checkbox_deactivate);
            }
        }
    }

    private void stateCheck(int num){
        if (JOIN_AGREEMENT_LIST_STATE[num]) {
            JOIN_AGREEMENT_LIST_STATE[num] = false;
            updateViewBackground();
        } else {
            contextCode = (num + 1) * 100;
            intent.putExtra("contextCode", contextCode);
            agreeButtonClick();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(intent_login);
        // 기본 동작 (앱 종료)
        super.onBackPressed();
        finish();
    }

}   // join_agreement.java
