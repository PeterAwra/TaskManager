package com.study.awra.taskmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText mInputTask;
    View mButtonAddTask;
    TextView mPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputTask=findViewById(R.id.et_input_task);
        mButtonAddTask =findViewById(R.id.bt_add_task);
        mPriority=findViewById(R.id.tv_choose_priority);
        mButtonAddTask.setVisibility(View.INVISIBLE);
        mInputTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) mButtonAddTask.setVisibility(View.INVISIBLE);
                else  mButtonAddTask.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        String mTextPrioriti="  "+getString(R.string.priority_4);
        SpannableString spannableText=new SpannableString(mTextPrioriti);
        BulletSpan bulletSpan = new BulletSpan(20, Color.BLUE);
        spannableText.setSpan(new ImageSpan(this,R.drawable.point,ImageSpan.ALIGN_BASELINE),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPriority.setText(spannableText);
    }
}
