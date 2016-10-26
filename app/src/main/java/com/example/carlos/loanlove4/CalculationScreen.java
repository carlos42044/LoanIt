package com.example.carlos.loanlove4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
//this is the initial branch

public class CalculationScreen extends AppCompatActivity {
    private double monthlyPayment, years, totalInterest, principal;
    private TableLayout t0, t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
        Intent intent = getIntent();

        // get the doubles from the bundle
        Bundle bundle = getIntent().getExtras();
        principal = bundle.getDouble("principal");
        double annualInterestRate = bundle.getDouble("annualInterestRate");
        years = bundle.getDouble("years");


        double monthlyInterestRate = annualInterestRate / 1200;
        monthlyPayment = principal * monthlyInterestRate / (1 - 1 / Math.pow((1 + monthlyInterestRate), years * 12));

        double interest = 0;
        double principalB = monthlyPayment;
        double balance = principal;

        totalInterest = 0;
        t1 = (TableLayout) findViewById(R.id.main_table);
        t0 = (TableLayout) findViewById(R.id.origin_table);

        int rowColor = 0;

        //code for the table
        TableRow tr_head = createTableRow(10, Color.parseColor("#3F51B5"), "s");

        // Adding data sections to the table header row
        textViewSetter(20, R.string.payment_number_header, Color.WHITE, tr_head);
        textViewSetter(21, R.string.interest_header, Color.WHITE, tr_head);
        textViewSetter(22, R.string.principal_header, Color.WHITE, tr_head);
        textViewSetter(23, R.string.balance_header, Color.WHITE, tr_head);

        // add the text to the table row.
        //t0.addView(tr_head);
        //t0.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // Loop for calculations, adds each row programatically.
        for (int i = 1; i <= years * 12; i++) {
            interest = monthlyInterestRate * balance; //finds the current interest due for that month
            totalInterest += interest;
            principalB = principalB - interest; //calculates the principle (paid on loan) after the interest is deducted
            balance = Math.abs(balance - principalB);

            if (i % 2 == 0) {
                rowColor = Color.parseColor("#e8eaf6");
            } else {
                rowColor = Color.WHITE;
            }

            TableRow row = createTableRow(i, rowColor);

            textViewSetter(i,"" + i,Color.BLACK, row);
            textViewSetter(i, formatNumber(interest),Color.BLACK, row);
            textViewSetter(i, formatNumber(principalB),Color.BLACK, row);
            textViewSetter(i, formatNumber(balance),Color.BLACK, row);

            t1.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            principalB = monthlyPayment;
        }

        setSummarryCardview();
    }

    // Sets the values for the Summarry card,
    public void setSummarryCardview() {
        // The first card view holding the RESULTS, monthly payment, total interest ect.

        TextView cardOneTitle = (TextView) findViewById(R.id.card_one_title);
        cardOneTitle.setTextSize(24);
        cardOneTitle.setText(R.string.summary_card_one_title);
        cardOneTitle.setGravity(Gravity.CENTER);

        TextView loanAmount = (TextView) findViewById(R.id.original_loan);
        loanAmount.setTextSize(14);
        loanAmount.setText(R.string.original_loan_amount);


        TextView loanAmountNumber = (TextView) findViewById(R.id.original_loan_amount);
        loanAmountNumber.setTextSize(14);
        loanAmountNumber.setGravity(Gravity.END);
        loanAmountNumber.setText(String.format("$%,.2f", principal));

        TextView monthlyHeader = (TextView) findViewById(R.id.monthly_payment);
        monthlyHeader.setTextSize(14);
        monthlyHeader.setText(R.string.monthly_payment_result);

        TextView monthlyCalculated = (TextView) findViewById(R.id.calculated_monthly_payment);
        monthlyCalculated.setTextSize(14);
        monthlyCalculated.setGravity(Gravity.END);
        monthlyCalculated.setText(String.format("$%,.2f", monthlyPayment));

        TextView interestHeader = (TextView) findViewById(R.id.total_interest);
        interestHeader.setTextSize(14);
        interestHeader.setText(R.string.total_interest_result);

        TextView interestCalculated = (TextView) findViewById(R.id.calculated_total_interest);
        interestCalculated.setTextSize(14);
        interestCalculated.setGravity(Gravity.END);
        interestCalculated.setText(String.format("$%,.2f", totalInterest));

        TextView totalHeader = (TextView) findViewById(R.id.total_paid);
        totalHeader.setTextSize(14);
        totalHeader.setText(R.string.total_paid_result);

        TextView totalCalculated = (TextView) findViewById(R.id.calculated_total_paid);
        totalCalculated.setTextSize(14);
        totalCalculated.setGravity(Gravity.END);
        totalCalculated.setText(String.format("$%,.2f", monthlyPayment * years * 12));
    }

    public void textViewSetter(int id, int text, int color, TableRow table_row){
        TextView textView = new TextView(this);
        textView.setId(id);
        textView.setText(text);
        textView.setTextColor(color);
        textView.setTextSize(16);
        //textView.setPadding(5,5,5,5);

        // Add the text view to the table row
        table_row.addView(textView);
    }

    public void textViewSetter(int id, String text, int color, TableRow table_row){
        TextView textView = new TextView(this);
        textView.setId(id);
        textView.setText(text);
        textView.setTextColor(color);
        //textView.setPadding(5,5,5,5);

        // Add the text view to the table row
        table_row.addView(textView);
    }

    // Creates a table Row porgramatically with a specific color
    public TableRow createTableRow(int id, int color) {
        // find the view
        TableRow tr = new TableRow(this);
        tr.setId(id);
        tr.setBackgroundColor(color);
        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return tr;
    }

    public TableRow createTableRow(int id, int color, String s) {
        // find the view
        TableRow tr = (TableRow) findViewById(R.id.header_row);
        //tr.setId(R.id.);
        tr.setBackgroundColor(color);
       // tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return tr;
    }
    // Formats a double to two decimal places
    public String formatNumber (double d){
        String formattedNumber = String.format("$%,.2f", d);
        return formattedNumber;
    }
}
