package com.example.testapp.SQL;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
public class UserOpenHelper {
    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] allColumns = {SQLHelper.COLUMN_EMAIL,
            SQLHelper.COLUMN_NAME_USER};

    public UserOpenHelper(Context context) {
        dbHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserAcc createUser(String pName) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_NAME_USER, pName);
        long insertId = database.insert(SQLHelper. TABLE_USER, null,
                values);
        Cursor cursor = database.query(SQLHelper. TABLE_USER,
                allColumns, SQLHelper.COLUMN_EMAIL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UserAcc newUser = cursorToUserAcc(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteUser(UserAcc p) {
        long id = Long.parseLong(p.getEmail());
        Log.e("SQLite", "Person entry deleted with id: " + id);
        database.delete(SQLHelper.TABLE_USER, SQLHelper.COLUMN_EMAIL
                + " = " + id, null);
    }

    public List<UserAcc> getAllPeople() {
        List<UserAcc> people = new ArrayList<UserAcc>();

        Cursor cursor = database.query(SQLHelper.TABLE_USER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserAcc user = cursorToUserAcc(cursor);
            people.add(user);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return people;
    }

    private UserAcc cursorToUserAcc(Cursor cursor) {
        UserAcc user = new UserAcc();
        user.setEmail(cursor.getString(0));
        user.setPassWord(cursor.getString(1));
        return user;
    }
}
