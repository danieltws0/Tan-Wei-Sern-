package com.example.asus.diceit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class dicedata {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_RANDOMNUM };

    public dicedata (Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public dice generatenum (String randnum){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_RANDOMNUM, randnum);
        long insertId = database.insert(MySQLiteHelper.TABLE_RANDNUM, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RANDNUM,
                allColumns, MySQLiteHelper.COLUMN_ID+" = "+ insertId, null, null, null, null);
        cursor.moveToFirst();
        dice newnumber = cursorToNum(cursor);
        cursor.close();
        return newnumber;
    }

    public void deleteNum (dice randnum){
        long id = randnum.getId();
        System.out.println("Comment deleted with id:"+id);
        database.delete(MySQLiteHelper.TABLE_RANDNUM, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<dice> getRandomNum() {
        List<dice> rnum = new ArrayList<dice>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RANDNUM, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dice randnum = cursorToNum(cursor);
            rnum.add(randnum);
            cursor.moveToNext();
        }

        cursor.close();
        return rnum;
    }
    private dice cursorToNum(Cursor cursor){
        dice randnum = new dice();
        randnum.setId(cursor.getLong(0));
        randnum.setNumber(cursor.getString(1));
        return randnum;

    }
}
