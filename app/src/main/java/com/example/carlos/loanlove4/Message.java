package com.example.carlos.loanlove4;

/**
 * Created by Carlos on 5/22/16.
 */

import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
