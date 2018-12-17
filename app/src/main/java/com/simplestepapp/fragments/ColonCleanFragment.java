package com.simplestepapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Srinivas on 12/17/2018.
 */

public class ColonCleanFragment extends Fragment{

    private static ColonCleanFragment instance = null;

    public static ColonCleanFragment newInstance(String text){

        if(instance == null){
            // new instance
            instance = new ColonCleanFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {

            return instance;
        }

    }

}
