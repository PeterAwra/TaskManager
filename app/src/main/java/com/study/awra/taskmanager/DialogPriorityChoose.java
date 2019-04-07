package com.study.awra.taskmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


public class DialogPriorityChoose extends DialogFragment {
    Context mContext;
    ResultDialogChoosePriority mResultDialogChoosePriority;

    public void setResultDialogChoosePriority(ResultDialogChoosePriority resultDialogChoosePriority) {
        mResultDialogChoosePriority = resultDialogChoosePriority;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog, container, false);
        ListView listView=view.findViewById(R.id.list_view_dialog);
        TaskPriorityAdapter adapter = new TaskPriorityAdapter(this, mContext, R.layout.item_dialog);
        adapter.setSelectedPriorityItemList(new SelectedPriorityItemList() {
            @Override
            public void onClick(int priorityTask) {
               if(mResultDialogChoosePriority !=null)
                   mResultDialogChoosePriority.onResult(priorityTask);
                DialogPriorityChoose.this.dismiss();
            }
        });
        listView.setAdapter(adapter);
        Button mCancel=view.findViewById(R.id.cancel_dialog);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPriorityChoose.this.dismiss();
            }
        });
        return view;

    }

}

