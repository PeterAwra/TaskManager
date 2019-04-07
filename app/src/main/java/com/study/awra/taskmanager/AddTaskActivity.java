package com.study.awra.taskmanager;

import android.content.Context;
import android.content.Intent;
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
    private int mPriority=3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
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
                DialogPriorityChoose dialogPriority = new DialogPriorityChoose();
                dialogPriority.setResultDialogChoosePriority(new ResultDialogChoosePriority() {
                    @Override
                    public void onResult(int priority) {
                        setPriorityView(priority);
                    }
                });
                dialogPriority.show(getSupportFragmentManager(),"tag");
            }
        });
    }

    private void setPriorityView(int priority) {
        int[] color=getResources().getIntArray(R.array.priority_color);
        String text=getResources().getString(R.string.priority_text);
        SpannableString spannableText=new SpannableString("  "+text+" "+(priority+1));
        ImageSpan color_point = new ImageSpan(this, R.drawable.point, ImageSpan.ALIGN_BASELINE);
        GradientDrawable gradientDrawable= (GradientDrawable) color_point.getDrawable();
//       gradientDrawable.setBounds();
        gradientDrawable.setColor(color[priority]);
        spannableText.setSpan(color_point,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mPriorityView.setText(spannableText);
        mPriority=priority;
    }
}
