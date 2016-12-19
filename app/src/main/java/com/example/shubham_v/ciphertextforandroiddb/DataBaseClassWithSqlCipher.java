package com.example.shubham_v.ciphertextforandroiddb;


import android.content.ContentValues;
import android.content.Context;

import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by shubham_v on 15-12-2016.
 */

public class DataBaseClassWithSqlCipher extends SQLiteOpenHelper {
    private static DataBaseClassWithSqlCipher instance;

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Quantum.db";


    public static final String TABLE_NAME = "employee";
    public static final String COLUMN_NAME_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_EMPLOYEE_NAME = "employee_name";
    public static final String COLUMN_DEPARTMENT_NAME = "employee_department";


    private static final String TEXT_TYPE = " TEXT ";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_EMPLOYEE_ID + TEXT_TYPE +"PRIMARY KEY," + COLUMN_EMPLOYEE_NAME + TEXT_TYPE + "," + COLUMN_DEPARTMENT_NAME + TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DataBaseClassWithSqlCipher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    static public synchronized DataBaseClassWithSqlCipher getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseClassWithSqlCipher(context);
        }
        return instance;
    }



    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public long InsertDataDB(String id,String employee_name,String employee_department )
    {
        long rowInserted = -1;
        SQLiteDatabase db = this.getWritableDatabase("most");
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COLUMN_NAME_EMPLOYEE_ID, id);
            contentValues.put(COLUMN_EMPLOYEE_NAME, employee_name);
            contentValues.put(COLUMN_DEPARTMENT_NAME, employee_department);
            rowInserted =  db.insert(TABLE_NAME, null, contentValues);
            
        }

        catch (Exception e)
        {
            e.getMessage();
        }

        return rowInserted;
    }

    public Cursor GetData(String Employeeid ) {
        SQLiteDatabase db = this.getReadableDatabase("most");

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+ COLUMN_NAME_EMPLOYEE_ID +" =?", new String[] {Employeeid + ""});
        return res;
    }

    public long UpdateEmployeeDetail (String Employee_Id , String Update_Employee_name, String Update_Employee_Department) {
            SQLiteDatabase db = this.getWritableDatabase("rocklay");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPLOYEE_NAME, Update_Employee_name);
        contentValues.put(COLUMN_DEPARTMENT_NAME, Update_Employee_Department);;
         long updatestatus =  db.update(TABLE_NAME, contentValues, ""+COLUMN_NAME_EMPLOYEE_ID+" = ? ", new String[]{Employee_Id});
        return updatestatus;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase("rocklay");
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }
}