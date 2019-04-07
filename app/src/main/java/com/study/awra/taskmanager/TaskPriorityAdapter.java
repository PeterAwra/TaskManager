package com.study.awra.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class TaskPriorityAdapter extends BaseAdapter {
    private DialogPriorityChoose mDialogPriorityChoose;
    private final Context mContext;
    private final int mResource;
    private SelectedPriorityItemList mSelectedPriorityItemList;
    private int[] color;

    public void setSelectedPriorityItemList(SelectedPriorityItemList selectedPriorityItemList) {
        mSelectedPriorityItemList = selectedPriorityItemList;
    }

    TaskPriorityAdapter(DialogPriorityChoose dialogPriorityChoose, Context context, int resource) {
        mDialogPriorityChoose = dialogPriorityChoose;
        mContext = context;
        mResource = resource;
        color= mDialogPriorityChoose.getResources().getIntArray(R.array.priority_color);
    }

    @Override
    public int getCount() {
        return color.length;
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
        String text= mDialogPriorityChoose.getResources().getString(R.string.priority_text);
        view.findViewById(R.id.iv).setBackgroundColor(color[position]);
        ((TextView) view.findViewById(R.id.tv_dialog)).setText(text+" "+(position+1));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPriorityItemList.onClick(position);
            }
        });
        return view;
    }
}
