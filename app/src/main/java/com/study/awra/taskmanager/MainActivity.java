package com.study.awra.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mTextTask;
    private View mBtnADD;
    private TextView mPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextTask=findViewById(R.id.et_task);
        mBtnADD=findViewById(R.id.bt_add_task);
        mPriority=findViewById(R.id.choose_priority);
        mBtnADD.setVisibility(View.INVISIBLE);
        mTextTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty())
                    mBtnADD.setVisibility(View.INVISIBLE);
                else
                    mBtnADD.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Spannable spannable_text=new SpannableString("   Приоритет 4");
        spannable_text.setSpan(new ImageSpan(this,R.drawable.point_orange_red, ImageSpan.ALIGN_BASELINE),0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPriority.setText(spannable_text);

    }
}
