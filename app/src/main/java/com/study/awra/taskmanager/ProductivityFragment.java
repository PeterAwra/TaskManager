package com.study.awra.taskmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductivityFragment extends FragmentWithTitle{
    private Context context;
    private TextView mTextView;
    Graph mGraph;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
       View view=inflater.inflate(R.layout.productivity_fragment_layout,container,false);
       mTextView=view.findViewById(R.id.tv_completed_task);
       mGraph=view.findViewById(R.id.graph);
        final SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
       refresh();
        return view;
    }
    void refresh(){
        int task_completed = context.getSharedPreferences(TaskAdapter.SAVE_COMPLETED_TASK, Context.MODE_PRIVATE).getInt(TaskAdapter.COMPLETED_TASK, 0);
        mGraph.setData(2,3,1,4,2,1,3);
        mTextView.setText(task_completed+"  "+getString(R.string.completed_task));
    }

}
