package com.example.carlos.loanlove4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private TextView t4;
    private TableLayout t1;
    private TableRow tr_head;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        e1 = (EditText) findViewById(R.id.editText);
        e1.addTextChangedListener(new NumberTextWatcher(e1));

        /*
        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        */

        //code to make the table
        t1 = (TableLayout) findViewById(R.id.main_table); //defining the table element

        //create the table row header to hold the column headings
        tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.parseColor("#777777"));
        tr_head.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        //Adding data sections to the table row
        TextView label_Payment_Number = new TextView(this);
        label_Payment_Number.setId(20);
        label_Payment_Number.setText(R.string.payment_number_header);
        label_Payment_Number.setTextColor(Color.WHITE);
        label_Payment_Number.setPadding(5,5,5,5);
        //label_Payment_Number.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tr_head.addView(label_Payment_Number); // add the column to the table row here

        TextView label_interest = new TextView(this);
        label_interest.setId(21); //define an id that must be unique
        label_interest.setText(R.string.interest_header); // set the text for the header
        label_interest.setTextColor(Color.WHITE); // set the color
        label_interest.setPadding(5,5,5,5); // set the padding
        //label_interest.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tr_head.addView(label_interest); // add the column to the table row here


        TextView label_principal_paid = new TextView(this);
        label_principal_paid.setId(22);
        label_principal_paid.setText(R.string.principal_header);
        label_principal_paid.setTextColor(Color.WHITE);
        label_principal_paid.setPadding(5,5,5,5);
        //label_principal_paid.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tr_head.addView(label_principal_paid);

        TextView label_balance = new TextView(this);
        label_balance.setId(23);
        label_balance.setText(R.string.balance_header);
        label_balance.setTextColor(Color.WHITE);
        label_balance.setPadding(5,5,5,5);
        //label_balance.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        tr_head.addView(label_balance);
    }


    public void onButtonClick(View v) {

        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        //t4 = (TextView) findViewById(R.id.textView4);



        // After adding the colums to the table row its time to add the table row to the main table layout that we fetched at the start
        try {
            t1.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {


            double principal = NumberTextWatcherForThousand.trimCommaOfString(e1.getText().toString()); //This is the loan amount
            double annualInterestRate = Double.parseDouble(e2.getText().toString()); //converts the interest rate to decimal
            double years = Double.parseDouble(e3.getText().toString()); //stores the number
            double monthlyInterestRate = annualInterestRate / 1200;
            double monthlyPayment = principal * monthlyInterestRate / (1 - 1 / Math.pow((1 + monthlyInterestRate), years * 12));

            double interest = 0;
            double principalB = monthlyPayment;
            double balance = principal;
            //StringBuilder builder = new StringBuilder();

            for (int i = 1; i <= years * 12; i++) {
                interest = monthlyInterestRate * balance; //finds the current interest due for that month
                principalB = principalB - interest; //calculates the principle (paid on loan) after the interest is deducted
                balance = Math.abs(balance - principalB);

                //builder.append(String.format("%d\t\t\t\tInterest: $%,.2f\t\t\t\tprinicpal payment: $%,.2f\t\t\t\tbalance: $%,.2f\n", i, interest, principalB, balance));
                //builder.append(interest + "\t" + principalB + " \t" + balance + "\n");
                principalB = monthlyPayment;


            }

            //t4.setText(builder.toString());

            //Following two lines hide the keyboard when the user chooses to hit calculate instead of done on the keyboard
            InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
