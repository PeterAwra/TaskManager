package com.study.awra.taskmanager;

import android.support.v4.app.Fragment;

abstract class FragmentWithTitle extends Fragment {
     String mTitle;

     public String getTitle(){
         return mTitle;
     }
     public FragmentWithTitle setTitle (String title){
         mTitle=title;
         return this;
     }
 }
