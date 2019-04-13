package com.study.awra.taskmanager.AddTask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.study.awra.taskmanager.R;

public class DialogPriorityChoose extends DialogFragment
    implements OnClickPriorityItemListListener {
  ChosenPriorityListener chosenPriorityListener;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ChosenPriorityListener) {
      chosenPriorityListener = (ChosenPriorityListener) context;
    } else {
      throw new UnsupportedOperationException(
          "Activity must implement interface ChosenPriorityListener");
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Context context = inflater.getContext();
    View view = inflater.inflate(R.layout.dialog, container, false);

    TaskPriorityAdapter adapter =
        new TaskPriorityAdapter(context.getResources().getIntArray(R.array.priority_color));
    adapter.setOnClickPriorityItemListListener(this);

    RecyclerView recyclerView = view.findViewById(R.id.list_view_dialog);
    recyclerView.setLayoutManager(
        new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
    recyclerView.setAdapter(adapter);

    Button mCancel = view.findViewById(R.id.cancel_dialog);
    mCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DialogPriorityChoose.this.dismiss();
      }
    });
    return view;
  }

  @Override
  public void onClick(int priorityTask) {
    if (chosenPriorityListener != null) {
      chosenPriorityListener.onResult(priorityTask);
    }
    DialogPriorityChoose.this.dismiss();
  }
}

