package com.optimize.optimize.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by samwalker on 31/12/14.
 */
public class FastToast {

    private FastToast() {};

    public static void show(String message, Context context){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }

    public static void show(int messageRes, Context context){
        Toast.makeText(context, messageRes,Toast.LENGTH_SHORT).show();
    }
}
