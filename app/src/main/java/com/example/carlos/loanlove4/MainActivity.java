package com.example.carlos.loanlove4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private EditText e1, e2, e3;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Restore any saved state
        super.onCreate(savedInstanceState);
        // Set content view
        setContentView(R.layout.activity_main1);

        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        calculateButton = (Button) findViewById(R.id.button);

        // find more efficient text watcher
        e1.addTextChangedListener(new NumberTextWatcher(e1));
        addData();

    }

    public void addData(){
        calculateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard(v);
                        try {
                            Intent intent = new Intent(v.getContext(), CalculationScreen.class);

                            double principal = NumberTextWatcherForThousand.trimCommaOfString(e1.getText().toString()); //This is the loan amount
                            double annualInterestRate = Double.parseDouble(e2.getText().toString()); //converts the interest rate to decimal
                            double years = Double.parseDouble(e3.getText().toString()); //stores the number


                            Bundle bundle = new Bundle();

                            // add the data to the bundle
                            bundle.putDouble("principal", principal);
                            bundle.putDouble("annualInterestRate", annualInterestRate);
                            bundle.putDouble("years", years);
                            intent.putExtras(bundle);
                            startActivity(intent);

                            //Following two lines hide the keyboard when the user chooses to hit calculate instead of done on the keyboard
                            //InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                            //inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
