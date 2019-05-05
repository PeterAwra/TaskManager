package com.study.awra.taskmanager.AddTask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.study.awra.taskmanager.MainActivity;
import com.study.awra.taskmanager.R;

interface OnResultDialogListener {
  void onResultDialog(int priority);
}

public class DialogPriorityChoose extends DialogFragment
    implements View.OnClickListener, OnChosenPriority {
  private OnResultDialogListener resultDialogListener;
  private Context context;
  private TaskPriorityAdapter adapter;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
    Fragment fragment = ((MainActivity) context).getSupportFragmentManager().getFragments().get(3);
    if (fragment instanceof OnResultDialogListener) {
      resultDialogListener = ((OnResultDialogListener) fragment);
    } else {
      throw new UnsupportedOperationException(
          fragment.getClass().getSimpleName() + " must implements OnResultDialogListener");
    }
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new TaskPriorityAdapter(context.getResources().getIntArray(R.array.priority_color));
    adapter.setOnChosenPriority(this);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog, container, false);

    RecyclerView recyclerView = view.findViewById(R.id.list_view_dialog);
    recyclerView.setLayoutManager(
        new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
    recyclerView.setAdapter(adapter);

    Button mCancel = view.findViewById(R.id.cancel_dialog);
    mCancel.setOnClickListener(this);
    return view;
  }

  @Override public void onClick(View v) {
    dismiss();
  }

  @Override public void chosenPriority(int priority) {
    if (resultDialogListener != null) {
      resultDialogListener.onResultDialog(priority);
    }
    dismiss();
  }
}
