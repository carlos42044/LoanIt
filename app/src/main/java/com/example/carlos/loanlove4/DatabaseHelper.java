package com.example.carlos.loanlove4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carlos on 5/12/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Loan.db";
    public static final String TABLE_NAME = "amorization_table";
    public static final String COL_1 = "PAYMENT";
    public static final String COL_2 = "INTEREST";
    public static final String COL_3 = "PRINCIPAL";
    public static final String COL_4 = "BALANCE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (PAYMENT INTEGER PRIMARY KEY AUTOINCREMENT, INTEREST DOUBLE, PRINCIPAL DOUBLE, BALANCE DOUBLE) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // This method adds data to the table. It is adding interest principal balance
    public boolean insertData(Double interest, Double principal, Double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, interest);
        contentValues.put(COL_3, principal);
        contentValues.put(COL_4, balance);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}
