/*
__author__ = 'Song Chae Young'
__date__ = 'Feb.20, 2024'
__email__ = '0.0yeriel@gmail.com'
__fileName__ = 'setting_notice.java'
__github__ = 'SongChaeYoung98'
__status__ = 'Development'
*/

package com.example.brokenmirror.ui.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.brokenmirror.R;

public class setting_notice extends AppCompatActivity {
    private boolean opt_0 = true;
    private boolean opt_1 = false;
    private boolean opt_2 = true;
    private boolean opt_3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_notice);

        ConstraintLayout opt_0_layout = findViewById(R.id.setting_notice_opt_0_layout);
        ConstraintLayout opt_1_layout = findViewById(R.id.setting_notice_opt_1_layout);
        ConstraintLayout opt_2_layout = findViewById(R.id.setting_notice_opt_2_layout);
        ConstraintLayout opt_3_layout = findViewById(R.id.setting_notice_opt_3_layout);

        SwitchCompat opt_0_switch = findViewById(R.id.setting_notice_switch_0);
        SwitchCompat opt_1_switch = findViewById(R.id.setting_notice_switch_1);
        SwitchCompat opt_2_switch = findViewById(R.id.setting_notice_switch_2);
        SwitchCompat opt_3_switch = findViewById(R.id.setting_notice_switch_3);

        Button back_button = findViewById(R.id.setting_notice_back_button);
        Button close_button = findViewById(R.id.close_button);

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(150);

        // default setting
        opt_0_switch.setChecked(opt_0);
        opt_2_switch.setChecked(opt_2);
        opt_3_switch.setChecked(opt_3);

        Log.d("ghkrdls", "opt_0 : " + opt_0);
        Log.d("ghkrdls", "opt_1 : " + opt_1);
        Log.d("ghkrdls", "opt_2 : " + opt_2);
        Log.d("ghkrdls", "opt_3 : " + opt_3);
        Log.d("ghkrdls", "---------------------------");

        // FrameLayout
        // opt_0_layout : OnClickListener
        opt_0_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt_0_switch.performClick();
            }
        });

        // opt_1_layout : OnClickListener
        opt_1_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt_1_switch.performClick();
            }
        });

        // opt_2_layout : OnClickListener
        opt_2_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt_2_switch.performClick();
            }
        });

        // opt_3_layout : OnClickListener
        opt_3_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opt_3_switch.performClick();
            }
        });

        // Switch
        // opt_0_switch : OnCheckedChangeListener
        opt_0_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                opt_0 = false;
                opt_1 = false;
                opt_2 = false;
                opt_1_layout.setVisibility(View.GONE);
                opt_2_layout.setVisibility(View.GONE);
                opt_3_layout.setVisibility(View.GONE);
            } else {
                opt_0 = true;
                opt_1 = false;
                opt_2 = true;
                opt_3 = false;
                opt_1_switch.setChecked(opt_1);
                opt_2_switch.setChecked(opt_2);
                opt_3_switch.setChecked(opt_3);
                opt_1_layout.setVisibility(View.VISIBLE);
                opt_2_layout.setVisibility(View.VISIBLE);
                opt_3_layout.setVisibility(View.VISIBLE);
            }

            Log.d("ghkrdls", "opt_0 : " + opt_0);
            Log.d("ghkrdls", "opt_1 : " + opt_1);
            Log.d("ghkrdls", "opt_2 : " + opt_2);
            Log.d("ghkrdls", "opt_3 : " + opt_3);
            Log.d("ghkrdls", "---------------------------");
        });

        // opt_1_switch : OnCheckedChangeListener
        opt_1_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                opt_1 = false;
            } else {
                opt_0 = true;
                opt_1 = true;
                opt_0_switch.setChecked(opt_0);
            }

            Log.d("ghkrdls", "opt_0 : " + opt_0);
            Log.d("ghkrdls", "opt_1 : " + opt_1);
            Log.d("ghkrdls", "opt_2 : " + opt_2);
            Log.d("ghkrdls", "opt_3 : " + opt_3);
            Log.d("ghkrdls", "---------------------------");
        });

        // opt_2_switch : OnCheckedChangeListener
        opt_2_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                opt_2 = false;
            } else {
                opt_0 = true;
                opt_2 = true;
                opt_0_switch.setChecked(opt_0);
            }

            Log.d("ghkrdls", "opt_0 : " + opt_0);
            Log.d("ghkrdls", "opt_1 : " + opt_1);
            Log.d("ghkrdls", "opt_2 : " + opt_2);
            Log.d("ghkrdls", "opt_3 : " + opt_3);
            Log.d("ghkrdls", "---------------------------");
        });

        // opt_2_switch : OnCheckedChangeListener
        opt_3_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                opt_3 = false;
            } else {
                opt_0 = true;
                opt_3 = true;
                opt_0_switch.setChecked(opt_0);
            }

            Log.d("ghkrdls", "opt_0 : " + opt_0);
            Log.d("ghkrdls", "opt_1 : " + opt_1);
            Log.d("ghkrdls", "opt_2 : " + opt_2);
            Log.d("ghkrdls", "opt_3 : " + opt_3);
            Log.d("ghkrdls", "---------------------------");
        });

        // Button
        // back_button : OnClickListener
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // close_button : OnClickListener
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}