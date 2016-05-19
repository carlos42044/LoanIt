package com.example.carlos.loanlove4;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;


public class CalculationScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    private double monthlyPayment, years, totalInterest, principal;
    private StringBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
        Intent intent = getIntent();
        //myDb = new DatabaseHelper(this);

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
        builder = new StringBuilder();
        builder.append("Month/\t\t\t\t\tInterest\t\t\t\tPrincipal Payment\t\t\t\t\tBalance\n");
        builder.append("Payment\n");

        totalInterest = 0;

        for (int i = 1; i <= years * 12; i++) {
            interest = monthlyInterestRate * balance; //finds the current interest due for that month
            totalInterest += interest;
            principalB = principalB - interest; //calculates the principle (paid on loan) after the interest is deducted
            balance = Math.abs(balance - principalB);

            builder.append(String.format("%d\t\t\t\t\t\t\t\t\t\t\t\t$%,.2f\t\t\t\t\t\t\t$%,.2f\t\t\t\t\t\t\t$%,.2f\n", i, interest, principalB, balance));
            final boolean b = myDb.insertData(interest, principalB, balance);
            principalB = monthlyPayment;
        }

        setCardviews();

    }

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

        // The view for the 3rd card. The loan schedule
        CardView cardLayout2 = (CardView) findViewById(R.id.card_view3);
        /*TextView textView = new TextView(this);
        textView.setTextSize(14);
        textView.setText(builder);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);
        cardLayout2.addView(scrollView);*/

        //code for the table
        TableLayout t1 = (TableLayout) findViewById(R.id.main_table);

        // create the table row header to hold the column headings
        TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // Adding data sections to the table row
        TextView label_payment_number = new TextView(this);
        label_payment_number.setId(20);
        label_payment_number.setText(R.string.payment_number_header);
        label_payment_number.setTextColor(Color.WHITE);
        label_payment_number.setPadding(5,5,5,5);
        // add the view to the header row
        tr_head.addView(label_payment_number);

        TextView label_interest = new TextView(this);
        label_interest.setId(21);
        label_interest.setText(R.string.interest_header);
        label_interest.setTextColor(Color.WHITE);
        label_interest.setPadding(5,5,5,5);
        // add the view to the header row
        tr_head.addView(label_interest);

        TextView label_principal = new TextView(this);
        label_principal.setId(22);
        label_principal.setText(R.string.principal_header);
        label_principal.setTextColor(Color.WHITE);
        label_principal.setPadding(5,5,5,5);
        // add the view to the header row
        tr_head.addView(label_principal);

        TextView label_balance = new TextView(this);
        label_balance.setId(23);
        label_balance.setText(R.string.balance_header);
        label_balance.setTextColor(Color.WHITE);
        label_balance.setPadding(5,5,5,5);
        // add the view to the header row
        tr_head.addView(label_balance);

        t1.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        Cursor result = myDb.getAllData();
        int count = 0;
        //Cursor res = DatabaseHelper.
        while (result.moveToNext()) {
            int paymentNumber = result.getInt(0);
            double interest = result.getDouble(1);
            double principle = result.getDouble(2);
            double balance = result.getDouble(3);

            //create tablerow
            TableRow tr = new TableRow(this);
            if (count % 2 != 0) {
                tr.setBackgroundColor(Color.GRAY);
            }
            tr.setId(100+count);
            tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // Create the rows to add to the table
            TextView labelPaymentNumber = new TextView(this);
            labelPaymentNumber.setId(200+count);
            labelPaymentNumber.setText(paymentNumber);
            tr.addView(labelPaymentNumber);
            t1.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            count++;

        }
    }

    //perhaps add a method to calculate all of the table and its row in a different method
}
