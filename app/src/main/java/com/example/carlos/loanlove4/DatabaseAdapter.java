package com.example.carlos.loanlove4;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DatabaseAdapter {

    DatabaseHelper helper;
    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }
    public long insertData(double interest, double principle, double balance) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.INTEREST, interest);
        contentValues.put(DatabaseHelper.PRINCIPAL, principle);
        contentValues.put(DatabaseHelper.BALANCE, balance);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "loandatabase";
        private static final String TABLE_NAME = "LOANTABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "Payment";
        private static final String INTEREST = "Interest";
        private static final String PRINCIPAL = "Principal";
        private static final String BALANCE = "Balance";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTEREST + ", " + PRINCIPAL + ", " + BALANCE + ");";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "";
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Message.message(context, "constructor 'DatabaseHelper' called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE LOANTABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, Interest);
            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context, "onCreate called");
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Message.message(context, "onUpgrade called");
            } catch (SQLException e) {
                e.printStackTrace();
                Message.message(context, "" + e);

            }
        }
    }
}
