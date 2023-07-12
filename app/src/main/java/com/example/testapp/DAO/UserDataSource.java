package com.example.testapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.SQLException;

import com.example.testapp.SQL.SQLHelper;
import com.example.testapp.DTO.UserAcc;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {
    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] allColumns = {SQLHelper.COLUMN_EMAIL,
            SQLHelper.COLUMN_NAME_USER};

    public UserDataSource(Context context) {
        dbHelper = new SQLHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public List<UserAcc> getAllPeople() {
        List<UserAcc> people = new ArrayList<UserAcc>();

        Cursor cursor = database.query(SQLHelper.TABLE_USER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserAcc person = cursorToUserAcc(cursor);
            people.add(person);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return people;
    }


    public void close() {
        dbHelper.close();
    }

    public int createUser(UserAcc userAcc) {
        ContentValues values = new ContentValues();

        values.put(SQLHelper.COLUMN_EMAIL, userAcc.getUsername());
        values.put(SQLHelper.COLUMN_NAME_USER, userAcc.getPassWord());
        long insertId = database.insert(SQLHelper.TABLE_USER, null,
                values);
        if(insertId <= 0 ){
            return -1;
        }

        Log.v("QYLOGG",userAcc.username);
        return 1;
    }
    public boolean checkUser(){
        String check = "Select * from "+SQLHelper.TABLE_USER;
        Cursor cursor = database.rawQuery(check,null);
        if (cursor.getCount() != 0 ){
            return true;
        }
        else {
            return false;
        }
    }

    public void deleteUser(UserAcc p) {
        long id = Long.parseLong(p.getUsername());
        Log.e("SQLite", "Person entry deleted with id: " + id);
        database.delete(SQLHelper.TABLE_USER, SQLHelper.COLUMN_EMAIL
                + " = " + id, null);
    }


    private UserAcc cursorToUserAcc(Cursor cursor) {
        UserAcc user = new UserAcc();
        user.setUsername(cursor.getString(0));
        user.setPassWord(cursor.getString(1));
        return user;
    }
    public int CheckLogin(String userName,String pw ){
        String checkUserName = "Select * from " + SQLHelper.TABLE_USER + " Where " + SQLHelper.COLUMN_EMAIL +" = '"+ userName +"'";
        Cursor cursor1 = database.rawQuery(checkUserName,null);
        if (cursor1.getCount() != 0 ){
            String checkPw = "Select * from " + SQLHelper.TABLE_USER + " Where " + SQLHelper.COLUMN_NAME_USER +" = '"+ pw +"'";
            Cursor cursor2 = database.rawQuery(checkPw,null);
            if(cursor2.getCount()!=0){
                return 1;
            }
            else {
                return 2;
            }
        }
        else {
            return -1;
        }
    }



}
