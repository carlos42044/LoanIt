package com.example.carlos.loanlove4;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by CarlosF on 8/23/2017.
 */

public abstract class TextValidator implements TextWatcher{
    private TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // not used
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // not used
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // not used
        String text = textView.getText().toString();
        validate(textView, text);
    }
}
