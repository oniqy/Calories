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
    public static final String TABLE_ControlWeight = "weightChanges";
    //Tao thuoc tinh trong table
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME_USER= "name_user";

    public static final String COLUMN_UserInfo_idUser = "idUser";
    public static final String COLUMN_UserInfo_idEmail = "emailUserInfo";
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
    public static final String COLUMN_FoodMenu_soLuong = "SL";


    public static final String COLUMN_ExerciseMenu_idExercise = "idExercise";
    public static final String COLUMNE_ExerciseMenu_name = "nameExercise";
    public static final String COLUMN_ExerciseMenu_Reps = "Reps";
    public static final String COLUMN_ExerciseMenu_Mins = "Mins";
    public static final String COLUMN_CaloDaily_Id = "IdDaily";
    public static final String COLUMN_CaloDaily_IdDate = "IdDate";
    public static final String COLUMN_CaloDaily_idEmail = "emailCaloDaily";
    public static final String COLUMN_CaloDaily_idFood = "idFood";
    public static final String COLUMN_CaloDaily_NameFoodOfday = "foodDaily";
    public static final String COLUMN_CaloDaily_NameExerciseOfDay = "NameExercise";
    public static final String COLUMN_CaloDaily_TimeofDay = "TimeType";
    public static final String COLUMN_ControlWeight_id = "idControlWeight";
    public static final String COLUMN_ControlWeight_Date = "dayUp";
    public static final String COLUMN_ControlWeight_NewWeight = "newWeight";
    public static final String COLUMN_ControlWeight_EMAIL = "ControlWeightEMAIL";
    private static final String DATABASE_NAME = "healthyCare.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PATH="/data/data/com.example.testapp/databases/healthyCare/";

    // Câu lệnh khởi tạo Database.
    public SQLHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
//        database = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        String tbcalodaily = "create table "
                + TABLE_CaloDaily + "( " + COLUMN_CaloDaily_Id
                + " integer primary key autoincrement, "
                + COLUMN_CaloDaily_IdDate
                + " text , "
                +COLUMN_CaloDaily_idFood+" integer,"
                +COLUMN_CaloDaily_idEmail+" text ,"
                +COLUMN_CaloDaily_NameFoodOfday+" text,"
                +COLUMN_CaloDaily_TimeofDay+" text);";
        String tbUserAcc = "create table "
                + TABLE_USER + "( " + COLUMN_EMAIL
                + " text primary key , " + COLUMN_NAME_USER
                + " text not null);";
        String tbUpWeight = "create table "
                + TABLE_ControlWeight + "( " + COLUMN_ControlWeight_id
                + " integer primary key autoincrement, " + COLUMN_ControlWeight_Date
                + " text  , " + COLUMN_ControlWeight_NewWeight
                + " interger,"+COLUMN_ControlWeight_EMAIL+" text);";
        String tbfood = "create table "
                + TABLE_FoodMenu + "( " + COLUMN_FoodMenu_idFood
                + " integer primary key autoincrement, " + COLUMN_FoodMenu_name
                + " text ,"+COLUMN_FoodMenu_Calories+" integer ,"
                +COLUMN_FoodMenu_Fats+" integer,"
                +COLUMN_FoodMenu_Proteins+" integer,"
                +COLUMN_FoodMenu_Carbs+" integer,"
                +COLUMN_FoodMenu_soLuong+" text);";

        String tbExerciseMenu = "create table "
                + TABLE_ExerciseMenu + "( " + COLUMN_ExerciseMenu_idExercise
                + " integer primary key autoincrement, " + COLUMNE_ExerciseMenu_name+" text ,"
                +COLUMN_ExerciseMenu_Reps+" integer);";

        String tbUserInfo = "create table "
                + TABLE_UserInfo + "( " + COLUMN_UserInfo_idUser
                + " integer primary key autoincrement, "
                +COLUMN_UserInfo_UserHeight+" integer ,"
                +COLUMN_UserInfo_UserWeight+" integer ,"
                +COLUMN_UserInfo_idEmail+" text ,"
                +COLUMN_UserInfo_BirthDay+" integer ,"
                +COLUMN_UserInfo_Exercise+" text ,"
                +COLUMN_UserInfo_Gender+" text ,"
                +COLUMN_UserInfo_Target+" text);";

        database.execSQL(tbUserAcc);
        database.execSQL(tbUserInfo);
        database.execSQL(tbExerciseMenu);
        database.execSQL(tbfood);
        database.execSQL(tbcalodaily);
        database.execSQL(tbUpWeight);
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
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ControlWeight);
        onCreate(db);
    }
    public SQLiteDatabase open()  {
        return  this.getWritableDatabase();
    }
}
