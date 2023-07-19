package com.example.testapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.SQLException;
import android.widget.Toast;

import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.SQL.SQLHelper;
import com.example.testapp.DTO.UserAcc;
import com.example.testapp.ui.notifications.BMR_page_Fragment;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {
    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] allColumns = {SQLHelper.COLUMN_EMAIL,
            SQLHelper.COLUMN_NAME_USER};
    private String[] colFood = {SQLHelper.COLUMN_FoodMenu_idFood,
            SQLHelper.COLUMN_FoodMenu_name, SQLHelper.COLUMN_FoodMenu_soLuong};

    public UserDataSource(Context context) {
        dbHelper = new SQLHelper(context.getApplicationContext(), "healthyCare",null,1);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public List<String> getAllPeople() {
        List<String> people = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from " + SQLHelper.TABLE_USER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserAcc foodMenu = new UserAcc();
            foodMenu.setUsername(cursor.getString(0));
            foodMenu.setPassWord(cursor.getString(1));
            String chuoi = foodMenu.getUsername() + " - " + foodMenu.getPassWord();
            people.add(chuoi);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return people;
    }


    public void close() {
        dbHelper.close();
    }

    public int createUserACc(UserAcc userAcc) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_EMAIL, userAcc.getUsername());
        values.put(SQLHelper.COLUMN_NAME_USER, userAcc.getPassWord());
        long insertId = database.insert(SQLHelper.TABLE_USER, null,
                values);
        if (insertId <= 0) {
            return -1;
        }
        return 1;
    }

    public int createUserInfo(UserInfo userInfo) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_UserInfo_UserHeight, userInfo.getUserHeight());
        values.put(SQLHelper.COLUMN_UserInfo_UserWeight, userInfo.getUserWeight());
        values.put(SQLHelper.COLUMN_UserInfo_BirthDay, userInfo.getBirthDay());
        values.put(SQLHelper.COLUMN_UserInfo_Exercise, userInfo.getExercise());
        values.put(SQLHelper.COLUMN_UserInfo_Gender, userInfo.getGender());
        values.put(SQLHelper.COLUMN_UserInfo_Target, userInfo.getTarget());
        int check = checkUserInfo();
        if (check == 1) {
            long insertId = database.insert(SQLHelper.TABLE_UserInfo, null,
                    values);
            if (insertId <= 0) {
                return -1;
            }
            return 1;
        } else {
            long updateId = database.update(SQLHelper.TABLE_UserInfo, values, null, null);
            return 1;
        }
    }

    public int createFood() {
        int[] id ={1,2,3,4};
        String[] namefood={"Phở bò tái","Phở bò viên","Cơm trắng","Cá lóc kho"};
        double[] Calo={431,431,200,131};
        double[] Proteins={18,16,4.6,15.7};
        double[] Fats={12,14,0.6,3.8};
        double[] Carbs={59,59,44.2,8.7};
        String[] sl={"1 tô","1 tô","1 chén","1 lát"};

        ContentValues values = new ContentValues();
        for (int i = 0;i <= id.length-1;i++) {
            values.put(SQLHelper.COLUMN_FoodMenu_idFood, id[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_name, namefood[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Calories, Calo[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Carbs, Carbs[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Fats, Fats[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_soLuong, sl[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Proteins, Proteins[i]);
            Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_FoodMenu + " Where "
                    + SQLHelper.COLUMN_FoodMenu_idFood
                    +" = '"+ id[i] +"'",null);
            if(resultSet.getCount() == 0 ){
                long insertId1 = database.insert(SQLHelper.TABLE_FoodMenu, null,
                        values);
                if (insertId1 <= 0) {
                    return -1;
                }
            }
        }
        return 1;
    }
    public int checkUserInfo(){
        Cursor resultSet = database.rawQuery("Select * from "+SQLHelper.TABLE_UserInfo,null);
        if(resultSet.getCount() == 0 ){
            return 1;
        }
        return 0;
    }
    public List<String> timKiemfood(String nameFood){
        List<String> people = new ArrayList<>();
        Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_FoodMenu + " Where "
                + SQLHelper.COLUMN_FoodMenu_name
                +" like '"+ nameFood +"%'",null);
        if(resultSet.getCount() == 0 ){
            return people;
        }
        resultSet.moveToFirst();
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setFoodName(resultSet.getString(1));
        foodMenu.setsl(resultSet.getString(6));
        String chuoi = foodMenu.getFoodName()+" - "+foodMenu.getsl();
        people.add(chuoi);
        resultSet.close();
        return people;
    }
    public FoodMenu detail_food(int idFood){
           idFood +=1;
            Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_FoodMenu + " Where "
                    + SQLHelper.COLUMN_FoodMenu_idFood
                    + " = '" + idFood + "'", null);
            resultSet.moveToFirst();
            FoodMenu foodMenu = new FoodMenu();
             if(resultSet.getCount() == 0 ){
            return foodMenu;
             }
            String FoodName = resultSet.getString(1);
            String sl = resultSet.getString(6);
            int Calories= resultSet.getInt(2);
            int Fats= resultSet.getInt(3);
            int setProteins= resultSet.getInt(4);
            int setCarbs= resultSet.getInt(5);
            foodMenu.setFoodName(FoodName);
            foodMenu.setCalories(Calories);
            foodMenu.setFats(Fats);
            foodMenu.setProteins(setProteins);
            foodMenu.setCarbs(setCarbs);
            foodMenu.setsl(sl);
            resultSet.close();
        return foodMenu;
    }
    public List<String> getAllfood(){
        List<String> people = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from "+SQLHelper.TABLE_FoodMenu, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FoodMenu foodMenu = new FoodMenu();
            foodMenu.setFoodName(cursor.getString(1));
            foodMenu.setsl(cursor.getString(6));
            String chuoi = foodMenu.getFoodName()+" - "+foodMenu.getsl();
            people.add(chuoi);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return people;
    }
    public UserInfo Bmr(){
        Cursor resultSet = database.rawQuery("Select * from "+SQLHelper.TABLE_UserInfo,null);
        resultSet.moveToFirst();

        UserInfo userInfo = new UserInfo();
        if(resultSet.getCount() == 0 ){
            return userInfo;
        }
        int chieuCao = resultSet.getInt(1);
        int canNang = resultSet.getInt(2);
        int age = resultSet.getInt(3);
        String tapLuyen = resultSet.getString(4);
        String sex = resultSet.getString(5);
        String target = resultSet.getString(6);

        userInfo.setUserHeight(chieuCao);
        userInfo.setUserWeight(canNang);
        userInfo.setGender(sex);
        userInfo.setBirthDay(age);
        userInfo.setExercise(tapLuyen);
        userInfo.setTarget(target);
        return userInfo;
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
