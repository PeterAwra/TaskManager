package com.study.awra.taskmanager.AddTask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.study.awra.taskmanager.R;

public class AddTaskFragment extends Fragment {
    private View mButtonAddTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = inflater.getContext();
        View view = inflater.inflate(R.layout.activity_main, container, false);
        EditText inputTask = view.findViewById(R.id.et_input_task);
        mButtonAddTask = view.findViewById(R.id.bt_add_task);
        TextView priority = view.findViewById(R.id.tv_choose_priority);
        mButtonAddTask.setVisibility(View.INVISIBLE);
        inputTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) mButtonAddTask.setVisibility(View.INVISIBLE);
                else mButtonAddTask.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        String mTextPrioriti = "  " + getString(R.string.priority_text);
        SpannableString spannableText = new SpannableString("  " + mTextPrioriti + " " + 4);
        spannableText.setSpan(new ImageSpan(context, R.drawable.point, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        priority.setText(spannableText);
        return view;
    }
}
