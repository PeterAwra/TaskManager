package com.study.awra.taskmanager.AddTask;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.study.awra.taskmanager.MainActivity;
import com.study.awra.taskmanager.R;

public class AddTaskFragment extends Fragment implements OnResultDialogListener {
  public static final String SAVE_PRIORITY = "save_priority";
  private Context context;
  private TextView priorityView;
  private int priority = 3;
  private FragmentManager fragmentManager;

  @Override public void onAttach(Context context) {
    this.context = context;
    super.onAttach(context);
    fragmentManager = ((MainActivity) context).getSupportFragmentManager();
  }

  @Override public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVE_PRIORITY, priority);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_main, container, false);
    final EditText inputTask = view.findViewById(R.id.et_input_task);
    final View buttonAddTask = view.findViewById(R.id.bt_add_task);
    priorityView = view.findViewById(R.id.tv_choose_priority);
    buttonAddTask.setVisibility(View.INVISIBLE);
    inputTask.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) {
          buttonAddTask.setVisibility(View.INVISIBLE);
        } else {
          buttonAddTask.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    if (savedInstanceState != null) {
      priority = savedInstanceState.getInt(SAVE_PRIORITY, 0);
    }
    setPriorityView(priority);
    priorityView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DialogPriorityChoose dialogPriority = new DialogPriorityChoose();
        dialogPriority.show(fragmentManager, "tag");
      }
    });
    buttonAddTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputTask.getWindowToken(),
            InputMethodManager.RESULT_UNCHANGED_SHOWN);
        fragmentManager.popBackStackImmediate();
      }
    });

    return view;
  }

  @Override public void onAttachFragment(Fragment childFragment) {
    super.onAttachFragment(childFragment);
  }

  private void setPriorityView(int priority) {
    this.priority = priority;
    int[] color = getResources().getIntArray(R.array.priority_color);
    String text = getResources().getString(R.string.priority_text);
    SpannableString spannableText = new SpannableString("  " + text + " " + (priority + 1));
    ImageSpan color_point = new ImageSpan(context, R.drawable.point, ImageSpan.ALIGN_BASELINE);
    GradientDrawable gradientDrawable = (GradientDrawable) color_point.getDrawable();
    gradientDrawable.setBounds(priorityView.getLeft(), priorityView.getTop(),
        priorityView.getRight(), priorityView.getBottom());
    gradientDrawable.setColor(color[priority]);
    spannableText.setSpan(color_point, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    priorityView.setText(spannableText);
    this.priority = priority;
  }

  @Override public void onResultDialog(int priority) {
    setPriorityView(priority);
  }
}
