package com.study.awra.taskmanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class DialogPriority extends DialogFragment {
    Context mContext;
    ResultDialog mResultDialog;

    public void setResultDialog(ResultDialog resultDialog) {
        mResultDialog = resultDialog;
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
        TaskPriorityAdapter adapter = new TaskPriorityAdapter(mContext, R.layout.item_dialog, Task.PriorityTask.values());
        adapter.setClickPriorityItem(new ClickPriorityItem() {
            @Override
            public void onClick(Task.PriorityTask priorityTask) {
               if(mResultDialog!=null)
                   mResultDialog.onResult(priorityTask);
                DialogPriority.this.dismiss();
            }
        });
        listView.setAdapter(adapter);
        Button mCancel=view.findViewById(R.id.cancel_dialog);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPriority.this.dismiss();
            }
        });
        return view;

    }

    private class TaskPriorityAdapter extends BaseAdapter {
        private final Context mContext;
        private final int mResource;
        private final Task.PriorityTask[] mPriorities;

        public void setClickPriorityItem(ClickPriorityItem clickPriorityItem) {
            mClickPriorityItem = clickPriorityItem;
        }

        private ClickPriorityItem mClickPriorityItem;

        TaskPriorityAdapter(Context context, int resource, Task.PriorityTask[] priorities) {
            mContext = context;
            mResource = resource;
            mPriorities = priorities;
        }

        @Override
        public int getCount() {
            return mPriorities.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view= inflater.inflate(mResource, parent,false);
            int color = Color.TRANSPARENT;
            String text = "";
            switch (mPriorities[position]){
                case PRIORITY_1:
                    color=mContext.getResources().getColor(R.color.priority1);
                    text=mContext.getString(R.string.priority_1);
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
            view.findViewById(R.id.iv).setBackgroundColor(color);
            ((TextView) view.findViewById(R.id.tv_dialog)).setText(text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickPriorityItem.onClick(mPriorities[position]);
                }
            });
            return view;
        }
    }
}
interface ClickPriorityItem{
    public void onClick(Task.PriorityTask priorityTask);
}
interface ResultDialog{
    public void onResult(Task.PriorityTask priority);
}
