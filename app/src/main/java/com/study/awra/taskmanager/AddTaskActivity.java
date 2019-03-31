package com.study.awra.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity {
    private View mButtonAddTask;
    private TextView mPriorityView;
    private Context mContext;
    private Task.PriorityTask mPriority= Task.PriorityTask.PRIORITY_4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        if(savedInstanceState!=null);
        final EditText inputTask = findViewById(R.id.et_input_task);
        mButtonAddTask =findViewById(R.id.bt_add_task);
        mPriorityView =findViewById(R.id.tv_choose_priority);
        setPriorityView(mPriority);
        mButtonAddTask.setVisibility(View.INVISIBLE);
        mButtonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra(AddTaskActivity.class.getName(),new Task(inputTask.getText().toString(),mPriority));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        inputTask.addTextChangedListener(new TextWatcher() {
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
        mPriorityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPriority dialogPriority = new DialogPriority();
                dialogPriority.setResultDialog(new ResultDialog() {
                    @Override
                    public void onResult(Task.PriorityTask priority) {
                        setPriorityView(priority);
                    }
                });
                dialogPriority.show(getSupportFragmentManager(),"sss");
            }
        });
    }

    private void setPriorityView(Task.PriorityTask priority) {
        int color=Color.TRANSPARENT;
        String text=" ";
        switch (priority){
            case PRIORITY_1:
                color=mContext.getResources().getColor(R.color.priority1);
                text = mContext.getString(R.string.priority_1);
                break;
            case PRIORITY_2:
                color=mContext.getResources().getColor(R.color.priority2);
                text=mContext.getString(R.string.priority_2);
                break;
            case PRIORITY_3:
                color=mContext.getResources().getColor(R.color.priority3);
                text=mContext.getString(R.string.priority_3);
                break;
            case PRIORITY_4:
                color=mContext.getResources().getColor(R.color.priority4);
                text=mContext.getString(R.string.priority_4);
                break;
        }
        String mTextPrioriti="  "+text;
        SpannableString spannableText=new SpannableString(mTextPrioriti);
        ImageSpan color_point = new ImageSpan(this, R.drawable.point, ImageSpan.ALIGN_BASELINE);
        GradientDrawable gradientDrawable= (GradientDrawable) color_point.getDrawable();
        gradientDrawable.setColor(color);
        spannableText.setSpan(color_point,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPriorityView.setText(spannableText);
        mPriority=priority;
    }
}
