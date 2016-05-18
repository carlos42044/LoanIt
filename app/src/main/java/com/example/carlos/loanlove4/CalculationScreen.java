package com.example.carlos.loanlove4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalculationScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    private double monthlyPayment, years, totalInterest;
    private StringBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
        Intent intent = getIntent();
        //myDb = new DatabaseHelper(this);

        // get the doubles from the bundle
        Bundle bundle = getIntent().getExtras();
        double principal = bundle.getDouble("principal");
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
            //myDb.insertData(interest, principalB, balance);
            principalB = monthlyPayment;
        }

        setCardviews();

    }

    public void setCardviews() {
        // The first card view holding the RESULTS, monthly payment, total interest ect.

        TextView cardOneTitle = (TextView) findViewById(R.id.card_one_title) ;
        cardOneTitle.setTextSize(20);
        cardOneTitle.setText(R.string.summary_card_one_title);
        cardOneTitle.setGravity(Gravity.CENTER);

        TextView monthlyHeader = (TextView) findViewById(R.id.monthly_payment) ;
        monthlyHeader.setTextSize(18);
        monthlyHeader.setText(R.string.monthly_payment_result);

        TextView monthlyCalculated = (TextView) findViewById(R.id.calculated_monthly_payment) ;
        monthlyCalculated.setTextSize(18);
        monthlyCalculated.setGravity(Gravity.END);
        monthlyCalculated.setText(String.format("$%,.2f", monthlyPayment));

        TextView interestHeader = (TextView) findViewById(R.id.total_interest);
        interestHeader.setTextSize(18);
        interestHeader.setText(R.string.total_interest_result);

        TextView interestCalculated = (TextView) findViewById(R.id.calculated_total_interest) ;
        interestCalculated.setTextSize(18);
        interestCalculated.setGravity(Gravity.END);
        interestCalculated.setText(String.format("$%,.2f", totalInterest));

        TextView totalHeader = (TextView) findViewById(R.id.total_paid);
        totalHeader.setTextSize(18);
        totalHeader.setText(R.string.total_paid_result);

        TextView totalCalculated = (TextView) findViewById(R.id.calculated_total_paid) ;
        totalCalculated.setTextSize(18);
        totalCalculated.setGravity(Gravity.END);
        totalCalculated.setText(String.format("$%,.2f", monthlyPayment * years * 12));

        // The view for the 3rd card. The loan schedule
        CardView cardLayout2 = (CardView) findViewById(R.id.card_view3);
        TextView textView = new TextView(this);
        textView.setTextSize(14);
        textView.setText(builder);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);
        cardLayout2.addView(scrollView);

    }

    //perhaps add a method to calculate all of the table and its row in a different method
}
