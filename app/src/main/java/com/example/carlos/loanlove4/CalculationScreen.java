package com.example.carlos.loanlove4;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class CalculationScreen extends AppCompatActivity {
    private double monthlyPayment, years, totalInterest, principal;
    private StringBuilder builder;
    private TableLayout t1;
    DatabaseAdapter databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
        Intent intent = getIntent();
        //databaseHelper = new DatabaseAdapter(this);

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
        //builder = new StringBuilder();
        //builder.append("Month/\t\t\t\t\tInterest\t\t\t\tPrincipal Payment\t\t\t\t\tBalance\n");
        //5builder.append("Payment\n");

        totalInterest = 0;
        t1 = (TableLayout) findViewById(R.id.main_table);
        int rowColor = 0;
        setCardviews();

        for (int i = 1; i <= years * 12; i++) {
            interest = monthlyInterestRate * balance; //finds the current interest due for that month
            totalInterest += interest;
            principalB = principalB - interest; //calculates the principle (paid on loan) after the interest is deducted
            balance = Math.abs(balance - principalB);

            //builder.append(String.format("%d\t\t\t\t\t\t\t\t\t\t\t\t$%,.2f\t\t\t\t\t\t\t$%,.2f\t\t\t\t\t\t\t$%,.2f\n", i, interest, principalB, balance));
            //final boolean b = myDb.insertData(interest, principalB, balance);
            //myDb.insertData(interest, principalB, balance);

            principalB = monthlyPayment;

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
        }

//        test message to see if the for loop was doing all of the calculations (used to debug)
//        Message.message(this, Integer.toString(count));

        TextView interestCalculated = (TextView) findViewById(R.id.calculated_total_interest);
        interestCalculated.setTextSize(14);
        interestCalculated.setGravity(Gravity.END);
        interestCalculated.setText(String.format("$%,.2f", totalInterest));
    }

    // method to dynamically set text views... look up differene between .setText and setId...
    public void setCardviews() {
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

        // had to move the total interest to below the

        TextView totalHeader = (TextView) findViewById(R.id.total_paid);
        totalHeader.setTextSize(14);
        totalHeader.setText(R.string.total_paid_result);

        TextView totalCalculated = (TextView) findViewById(R.id.calculated_total_paid);
        totalCalculated.setTextSize(14);
        totalCalculated.setGravity(Gravity.END);
        totalCalculated.setText(String.format("$%,.2f", monthlyPayment * years * 12));

        //code for the table

        TableRow tr_head = createTableRow(10, Color.parseColor("#3F51B5"));

        // Adding data sections to the table row
        textViewSetter(20, R.string.payment_number_header, Color.WHITE, tr_head, Typeface.BOLD);
        textViewSetter(21, R.string.interest_header, Color.WHITE, tr_head, Typeface.BOLD);
        textViewSetter(22, R.string.principal_header, Color.WHITE, tr_head, Typeface.BOLD);
        textViewSetter(23, R.string.balance_header, Color.WHITE, tr_head, Typeface.BOLD);

        // add the text to the table row.
        t1.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    }
    public void textViewSetter(int id, int text, int color, TableRow table_row, int typeface){
        TextView name = new TextView(this);
        name.setId(id);
        name.setText(text);
        name.setTextColor(color);
       // name.setTypeface(null, typeface);
        name.setTextSize(16);
        name.setPadding(5,5,5,5);
        //name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        table_row.addView(name);
    }

    public void textViewSetter(int id, String text, int color, TableRow table_row){
        TextView name = new TextView(this);
        name.setId(id);
        name.setText(text);
        name.setTextColor(color);
        name.setPadding(5,5,5,5);

        //name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        table_row.addView(name);
    }
    public TableRow createTableRow(int id, int color) {
        TableRow tr = new TableRow(this);
        tr.setId(id);
        tr.setBackgroundColor(color);
        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return tr;
    }

    public String formatNumber (double d){
        String formattedNumber = String.format("$%,.2f", d);
        return formattedNumber;
    }
    //perhaps add a method to calculate all of the table and its row in a different method
}
