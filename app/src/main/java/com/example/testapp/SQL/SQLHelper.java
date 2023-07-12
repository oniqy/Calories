package com.example.testapp.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper{
    //Tao table
    public static final String TABLE_USER = "user";
    public static final String TABLE_UserInfo = "userInfo";
    public static final String TABLE_FoodMenu = "foodMenu";
    public static final String TABLE_ExerciseMenu = "exescMenu";
    public static final String TABLE_CaloDaily = "caloDaily";
    //Tao thuoc tinh trong table
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME_USER= "name_user";

    public static final String TABLE_UserInfo_idUser = "idUser";
    public static final String TABLE_UserInfo_Name = "Name";
    public static final String TABLE_UserInfo_UserHeight = "UserHeight";
    public static final String TABLE_UserInfo_UserWeight = "UserWeight";
    public static final String TABLE_UserInfo_BirthDay = "BirthDay";
    public static final String TABLE_UserInfo_Exercise = "Exercise";
    public static final String TABLE_UserInfo_Gender = "Gender";
    public static final String TABLE_UserInfo_Target = "Target";

    public static final String TABLE_FoodMenu_idFood = "idFood";
    public static final String TABLE_FoodMenu_name= "nameFood";
    public static final String TABLE_FoodMenu_Calories= "Calories";
    public static final String TABLE_FoodMenu_Fats = "Fats";
    public static final String TABLE_FoodMenu_Proteins = "Proteins";
    public static final String TABLE_FoodMenu_Carbs = "Carbs";
    public static final String TABLE_FoodMenu_Grams = "Grams";


    public static final String TABLE_ExerciseMenu_idExercise = "idExercise";
    public static final String TABLE_ExerciseMenu_name = "nameExercise";
    public static final String TABLE_ExerciseMenu_Reps = "Reps";
    public static final String TABLE_ExerciseMenu_Mins = "Mins";

    public static final String TABLE_CaloDaily_IdDate = "IdDate";
    public static final String TABLE_CaloDaily_idUser = "idUser";
    public static final String TABLE_CaloDaily_idExercise = "idExercise";
    public static final String TABLE_CaloDaily_idFood = "idFood";
    public static final String TABLE_CaloDaily_TimeType = "TimeType";
    private static final String DATABASE_NAME = "appM_healthyCare.db";
    private static final int DATABASE_VERSION = 1;

    // Câu lệnh khởi tạo Database.

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String tbcalodaily = "create table "
                + TABLE_CaloDaily + "( " + TABLE_CaloDaily_IdDate
                + " integer primary key autoincrement, " + TABLE_CaloDaily_idUser
                + " text ,"+TABLE_CaloDaily_idExercise+"integer ,"
                +TABLE_CaloDaily_idFood+"integer,"
                +TABLE_CaloDaily_TimeType+"integer);";

        String tbUserAcc = "create table "
                + TABLE_USER + "( " + COLUMN_EMAIL
                + " text primary key , " + COLUMN_NAME_USER
                + " text not null);";

        String tbfood = "create table "
                + TABLE_FoodMenu + "( " + TABLE_FoodMenu_idFood
                + " integer primary key autoincrement, " + TABLE_FoodMenu_name
                + " text ,"+TABLE_FoodMenu_Calories+"integer ,"
                +TABLE_FoodMenu_Fats+"integer,"
                +TABLE_FoodMenu_Proteins+"integer,"
                +TABLE_FoodMenu_Carbs+"integer,"
                +TABLE_FoodMenu_Grams+"text);";

        String tbExerciseMenu = "create table "
                + TABLE_ExerciseMenu + "( " + TABLE_ExerciseMenu_idExercise
                + " integer primary key autoincrement, " + TABLE_ExerciseMenu_name
                + " text ,"+TABLE_ExerciseMenu_Reps+"integer ,"
                +TABLE_ExerciseMenu_Mins+"integer);";

        String tbUserInfo = "create table "
                + TABLE_UserInfo + "( " + TABLE_UserInfo_idUser
                + " integer primary key autoincrement, " + TABLE_UserInfo_Name
                + " text ,"+TABLE_UserInfo_UserHeight+"integer ,"
                +TABLE_UserInfo_UserWeight+"integer,"
                +TABLE_UserInfo_BirthDay+"integer,"
                +TABLE_UserInfo_Exercise+"text,"
                +TABLE_UserInfo_Gender+"text,"
                +TABLE_UserInfo_Target+"text);";

        database.execSQL(tbUserAcc);
        database.execSQL(tbUserInfo);
        database.execSQL(tbExerciseMenu);
        database.execSQL(tbfood);
        database.execSQL(tbcalodaily);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public SQLiteDatabase open()  {
        return  this.getWritableDatabase();
    }
}
