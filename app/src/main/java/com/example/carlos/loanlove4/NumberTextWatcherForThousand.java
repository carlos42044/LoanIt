package com.example.carlos.loanlove4;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.StringTokenizer;

/**
 * Created by Shreekrishna Ban on 12/14/2015.
 * This class is used For thousand seperator to the editText 
 * This seperats the editText input with comma while user is entering the value
 
 * ----------------Extra features-----------------------------
 * Prevents wrinting that starts with "0" and adds "0." when "." pressed
 
 ----------------Method Lists-----------------------------
 >> beforeTextChanged 	=> 	@Override
 >> onTextChanged 		=> 	@Override
 >> afterTextChanged 	=> 	@Override
 
 >> getDecimalFormat 	=> 	gets decimal format of input string with comma as needed
 >> trimCommaOfString 	=> 	Trims comma of strings, Used by calling activity or fragment
 
 */
public class NumberTextWatcherForThousand implements TextWatcher {

    EditText editText;
    

    public NumberTextWatcherForThousand(EditText editText) {
        this.editText = editText;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try
        {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();


            if (value != null && !value.equals(""))
            {

                if(value.startsWith(".")){ //adds "0." when only "." is pressed on begining of writting
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){ 
                    editText.setText(""); //Prevents "0" while starting but not "0."

                }


                String str = editText.getText().toString().replaceAll(",", "");
                if (!value.equals(""))
//                    Double.valueOf(str).doubleValue();
                editText.setText(getDecimalFormat(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    public static String getDecimalFormat(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }


    //Trims all the comma of the string and returns
    public static Double trimCommaOfString(String string) {
//        String returnString;
        if(string.contains(",")){
            return Double.parseDouble(string.replace(",",""));
        }
        else {
            return Double.parseDouble(string);
        }

    }
}