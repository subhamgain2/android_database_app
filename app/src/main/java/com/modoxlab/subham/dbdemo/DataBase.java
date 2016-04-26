package com.modoxlab.subham.dbdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MScor on 14-03-2016.
 */
public class DataBase extends SQLiteOpenHelper {
    private static final String DBNAME = "database.db";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "employees";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String ID = "_id";
    private static final String ADDRESS = "address";
    private static final String SALARY = "salary";

    private SQLiteDatabase sqLiteDatabase;

    public DataBase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getFirstName() {
        return FIRST_NAME;
    }

    public String getLastName() {
        return LAST_NAME;
    }

    public String getId() {
        return ID;
    }

    public String getAddress() {
        return ADDRESS;
    }

    public String getSalary() {
        return SALARY;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIRST_NAME + " TEXT NOT NULL, " +
                LAST_NAME + " TEXT NOT NULL, " +
                ADDRESS + " TEXT NOT NULL, " +
                SALARY + " REAL NOT NULL " +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB() {
        sqLiteDatabase = getWritableDatabase();
    }

    public void closeDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public long insert(String fName, String lName, String address, Double salary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, fName);
        contentValues.put(LAST_NAME, lName);
        contentValues.put(ADDRESS, address);
        contentValues.put(SALARY, salary);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public int update(int id, String fName, String lName, String address, Double salary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, fName);
        contentValues.put(LAST_NAME, lName);
        contentValues.put(ADDRESS, address);
        contentValues.put(SALARY, salary);
        String query = ID + " = " + id;
        return sqLiteDatabase.update(TABLE_NAME, contentValues, query, null);
    }

    public Cursor search(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        return sqLiteDatabase.rawQuery(query, null);
    }

    public int delete(int id) {
        String query = ID + " = " + id;
        return sqLiteDatabase.delete(TABLE_NAME, query, null);
    }

    public Cursor getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return sqLiteDatabase.rawQuery(query, null);
    }
}
