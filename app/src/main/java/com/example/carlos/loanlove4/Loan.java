package com.example.carlos.loanlove4;

import java.util.Date;

/**
 * Created by Carlos on 11/11/16.
 * This is the class for Loan objects
 *
 */

public class Loan {
    private double loanAmount;
    private double numberOfYears;
    private double annualInterestRate;
    private Date loanDate;
    private int loanId;
    public static int loanNumber = 1;

    public Loan() {
        loanAmount = 0.0;
        numberOfYears = 0.0;
        annualInterestRate = 0.0;
        loanDate = new Date();
        loanId = loanNumber;
        loanNumber++;
    }

    public Loan(double loanAmount, double numberOfYears, double annualInterestRate, Date loanDate) {
        this.loanAmount = loanAmount;
        this.numberOfYears = numberOfYears;
        this.annualInterestRate = annualInterestRate;
        this.loanDate = loanDate;
        loanId = loanNumber;
        loanNumber++;
    }

    public Loan(double loanAmount, double numberOfYears, double annualInterestRate) {
        this.loanAmount = loanAmount;
        this.numberOfYears = numberOfYears;
        this.annualInterestRate = annualInterestRate;
        loanDate = new Date();
        loanId = loanNumber;
        loanNumber++;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getNumberOfYears() {
        return numberOfYears;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setNumberOfYears(double numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public String toString() {
        return "Loan ID: " + loanId + " Loan ammount: " + loanAmount + " Loan Term(in years): " + numberOfYears
                + " Annual Interest Rate: " + annualInterestRate + " Date created: " + loanDate;
    }
}

