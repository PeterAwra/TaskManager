package com.study.awra.taskmanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskFragment extends Fragment {
    private EditText mInputTask;
    private View mButtonAddTask;
    private TextView mPriority;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=container.getContext();
        View view=inflater.inflate(R.layout.activity_main,container,false);
        mInputTask=view.findViewById(R.id.et_input_task);
        mButtonAddTask =view.findViewById(R.id.bt_add_task);
        mPriority=view.findViewById(R.id.tv_choose_priority);
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
        spannableText.setSpan(new ImageSpan(getContext(),R.drawable.point,ImageSpan.ALIGN_BASELINE),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPriority.setText(spannableText);
        return view;
    }
}
