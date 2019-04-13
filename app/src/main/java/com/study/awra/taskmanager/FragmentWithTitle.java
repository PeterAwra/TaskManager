package com.study.awra.taskmanager;

import android.support.v4.app.Fragment;

abstract class FragmentWithTitle extends Fragment {
     private String title;

     public String getTitle(){
         return title;
     }
     public FragmentWithTitle setTitle (String title){
         this.title =title;
         return this;
     }
 }
