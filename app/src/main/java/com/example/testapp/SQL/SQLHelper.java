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

    public static final String COLUMN_UserInfo_idUser = "idUser";
    public static final String COLUMN_UserInfo_UserHeight = "UserHeight";
    public static final String COLUMN_UserInfo_UserWeight = "UserWeight";
    public static final String COLUMN_UserInfo_BirthDay = "BirthDay";
    public static final String COLUMN_UserInfo_Exercise = "Exercise";
    public static final String COLUMN_UserInfo_Gender = "Gender";
    public static final String COLUMN_UserInfo_Target = "Target";

    public static final String COLUMN_FoodMenu_idFood = "idFood";
    public static final String COLUMN_FoodMenu_name= "nameFood";
    public static final String COLUMN_FoodMenu_Calories= "Calories";
    public static final String COLUMN_FoodMenu_Fats = "Fats";
    public static final String COLUMN_FoodMenu_Proteins = "Proteins";
    public static final String COLUMN_FoodMenu_Carbs = "Carbs";
    public static final String COLUMN_FoodMenu_Grams = "Grams";


    public static final String COLUMN_ExerciseMenu_idExercise = "idExercise";
    public static final String COLUMNE_ExerciseMenu_name = "nameExercise";
    public static final String COLUMN_ExerciseMenu_Reps = "Reps";
    public static final String COLUMN_ExerciseMenu_Mins = "Mins";

    public static final String COLUMN_CaloDaily_IdDate = "IdDate";
    public static final String COLUMN_CaloDaily_idUser = "idUser";
    public static final String COLUMN_CaloDaily_idExercise = "idExercise";
    public static final String COLUMN_CaloDaily_idFood = "idFood";
    public static final String COLUMN_CaloDaily_TimeType = "TimeType";
    private static final String DATABASE_NAME = "healthyCare.db";
    private static final int DATABASE_VERSION = 1;

    // Câu lệnh khởi tạo Database.

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String tbcalodaily = "create table "
                + TABLE_CaloDaily + "( " + COLUMN_CaloDaily_IdDate
                + " integer primary key autoincrement, " + COLUMN_CaloDaily_idUser
                + " text ,"+COLUMN_CaloDaily_idExercise+" integer ,"
                +COLUMN_CaloDaily_idFood+" integer,"
                +COLUMN_CaloDaily_TimeType+" integer);";

        String tbUserAcc = "create table "
                + TABLE_USER + "( " + COLUMN_EMAIL
                + " text primary key , " + COLUMN_NAME_USER
                + " text not null);";

        String tbfood = "create table "
                + TABLE_FoodMenu + "( " + COLUMN_FoodMenu_idFood
                + " integer primary key autoincrement, " + COLUMN_FoodMenu_name
                + " text ,"+COLUMN_FoodMenu_Calories+" integer ,"
                +COLUMN_FoodMenu_Fats+" integer,"
                +COLUMN_FoodMenu_Proteins+" integer,"
                +COLUMN_FoodMenu_Carbs+" integer,"
                +COLUMN_FoodMenu_Grams+" text);";

        String tbExerciseMenu = "create table "
                + TABLE_ExerciseMenu + "( " + COLUMN_ExerciseMenu_idExercise
                + " integer primary key autoincrement, " + COLUMNE_ExerciseMenu_name
                + " text ,"+COLUMN_ExerciseMenu_Reps+" integer ,"
                +COLUMN_ExerciseMenu_Mins+" integer);";

        String tbUserInfo = "create table "
                + TABLE_UserInfo + "( " + COLUMN_UserInfo_idUser
                + " integer primary key autoincrement, "
                +COLUMN_UserInfo_UserHeight+" integer ,"
                +COLUMN_UserInfo_UserWeight+" integer ,"
                +COLUMN_UserInfo_BirthDay+" integer ,"
                +COLUMN_UserInfo_Exercise+" text ,"
                +COLUMN_UserInfo_Gender+" text ,"
                +COLUMN_UserInfo_Target+" text);";

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
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UserInfo);
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FoodMenu);
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ExerciseMenu);
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CaloDaily);
        onCreate(db);
    }
    public SQLiteDatabase open()  {
        return  this.getWritableDatabase();
    }
}
