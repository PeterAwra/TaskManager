package com.study.awra.taskmanager;

import android.support.v4.app.Fragment;

abstract class MyWithTitleFragment extends Fragment {
     String mTitle;

     public String getTitle(){
         return mTitle;
     }
     public MyWithTitleFragment setTitle (String title){
         mTitle=title;
         return this;
     }
 }
