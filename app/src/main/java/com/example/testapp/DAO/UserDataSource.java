package com.example.testapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.SQLException;

import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.DTO.Weight;
import com.example.testapp.SQL.SQLHelper;
import com.example.testapp.DTO.UserAcc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserDataSource {
    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] allColumns = {SQLHelper.COLUMN_EMAIL,
            SQLHelper.COLUMN_NAME_USER};
    private String[] colFood = {SQLHelper.COLUMN_FoodMenu_idFood,
            SQLHelper.COLUMN_FoodMenu_name, SQLHelper.COLUMN_FoodMenu_soLuong};

    public UserDataSource(Context context) {
        dbHelper = new SQLHelper(context.getApplicationContext(), "healthyCare", null, 1);
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
    public ArrayList<Double> getDataCaloInMonth(Calendar time) {
        ArrayList<DaulyFood> dataList = new ArrayList<>();
        ArrayList<Double> allData = new ArrayList<>();
        Calendar finTime = time;
        finTime.set(Calendar.DAY_OF_YEAR,2);
        for (int i = 0; i <= 12; i++) {
            dataList.clear();
            Calendar calendarStart = (Calendar) finTime.clone();
            calendarStart.set(Calendar.DAY_OF_MONTH, 1);
            calendarStart.set(Calendar.HOUR, 0);
            calendarStart.set(Calendar.MINUTE, 0);
            calendarStart.set(Calendar.SECOND, 0);
            Date jan1 = new Date(calendarStart.getTimeInMillis());
            Calendar calendarEnd = (Calendar) calendarStart.clone();
            calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
            calendarEnd.set(Calendar.MINUTE, 59);
            calendarEnd.set(Calendar.SECOND, 59);
            Date jan2 = new Date(calendarEnd.getTimeInMillis());
            Log.v("getDataOutComeInMonth", "BETWEEN!! " + String.valueOf(jan1 + " " + jan2));


        }
            return allData;
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
    public int createNewWeight(Weight weight, String email) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_ControlWeight_Date, weight.getControlWeight_Date());
        values.put(SQLHelper.COLUMN_ControlWeight_NewWeight, weight.getWeight());
        values.put(SQLHelper.COLUMN_ControlWeight_EMAIL, email);
        long insertId = database.insert(SQLHelper.TABLE_ControlWeight, null, values);
        if (insertId <= 0) {
            return -1; // Return -1 if the insertion was unsuccessful
        }
        return 1; // Return 1 if the insertion was successful
    }

    public int createUserInfo(UserInfo userInfo, String email) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_UserInfo_UserHeight, userInfo.getUserHeight());
        values.put(SQLHelper.COLUMN_UserInfo_UserWeight, userInfo.getUserWeight());
        values.put(SQLHelper.COLUMN_UserInfo_BirthDay, userInfo.getBirthDay());
        values.put(SQLHelper.COLUMN_UserInfo_Exercise, userInfo.getExercise());
        values.put(SQLHelper.COLUMN_UserInfo_Gender, userInfo.getGender());
        values.put(SQLHelper.COLUMN_UserInfo_Target, userInfo.getTarget());
        values.put(SQLHelper.COLUMN_UserInfo_idEmail, email);
        int check = checkUserInfo(email);
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

    public int createDailyFood(DaulyFood daulyFood, String email) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_CaloDaily_IdDate, daulyFood.getIdDate());
        values.put(SQLHelper.COLUMN_CaloDaily_idFood, daulyFood.getIdFood());
        values.put(SQLHelper.COLUMN_CaloDaily_idEmail, email);
        values.put(SQLHelper.COLUMN_CaloDaily_NameFoodOfday, daulyFood.getNameFoodOfday());
        values.put(SQLHelper.COLUMN_CaloDaily_TimeofDay, daulyFood.getTimeofDay());
        long insertId = database.insert(SQLHelper.TABLE_CaloDaily, null,
                values);
        if (insertId <= 0) {
            return -1;
        }
        return 1;
    }

    public int createFood() {
        String[] namefood = {"Phở bò tái", "Phở bò viên", "Cơm trắng", "Cá lóc kho",
                "Thịt kho trứng","Thịt kho tiêu","Bún thịt nướng","Cơm chiên dương châu"};
        double[] Calo = {431, 431, 200, 131,315,200,451,530};
        double[] Proteins = {18, 16, 4.6, 15.7,19.8,21.2,14.7,14.9};
        double[] Fats = {12, 14, 0.6, 3.8,22.9,7.6,13.7,11.3};
        double[] Carbs = {59, 59, 44.2, 8.7,7.5,11.5,67.3,92.7};
        String[] sl = {"1 tô", "1 tô", "1 chén", "1 lát","1 trứng + 2 miếng thịt","1 đĩa","1 tô","1 đĩa"};
        int[] id = new int[namefood.length];

        for (int i = 0; i < namefood.length; i++) {
            id[i] = i + 1;
        }

        for (int i = 0; i < namefood.length; i++) {
            ContentValues values = new ContentValues();
            values.put(SQLHelper.COLUMN_FoodMenu_idFood, id[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_name, namefood[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Calories, Calo[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Carbs, Carbs[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Fats, Fats[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_soLuong, sl[i]);
            values.put(SQLHelper.COLUMN_FoodMenu_Proteins, Proteins[i]);

            Cursor resultSet = database.rawQuery("SELECT * FROM " + SQLHelper.TABLE_FoodMenu + " WHERE "
                    + SQLHelper.COLUMN_FoodMenu_idFood + " = '" + id[i] + "'", null);

            if (resultSet.getCount() == 0) {
                long insertId1 = database.insert(SQLHelper.TABLE_FoodMenu, null, values);
                if (insertId1 <= 0) {
                    return -1;
                }
            }

            resultSet.close();
        }

        return 1;
    }

    public int checkUserInfo(String email) {
        Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_UserInfo + " Where "
                + SQLHelper.COLUMN_UserInfo_idEmail
                + " = '" + email + "'", null);
        if (resultSet.getCount() == 0) {
            return 1;
        }
        resultSet.close();
        return 0;
    }

    public int checkUserAcc(String email) {
        Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_USER + " Where "
                + SQLHelper.COLUMN_EMAIL
                + " = '" + email + "'", null);

        if (resultSet.getCount() == 0) {
            return 1;
        }
        resultSet.close();
        return 0;
    }
    public Weight checkUpWeight(String email) {
        Cursor resultSet = database.rawQuery("SELECT * FROM " + SQLHelper.TABLE_ControlWeight + " WHERE " +
                SQLHelper.COLUMN_ControlWeight_EMAIL + " = '" + email + "' AND " +
                SQLHelper.COLUMN_ControlWeight_id + " = (SELECT MAX(" + SQLHelper.COLUMN_ControlWeight_id + ") FROM " +
                SQLHelper.TABLE_ControlWeight + " WHERE " + SQLHelper.COLUMN_ControlWeight_EMAIL + " = '" + email + "')", null);
        if (resultSet.getCount() == 0) {
            Log.e("LOI ROI", "No data found");
            resultSet.close();
            return null; // Return null or handle the case when no data is found
        }

        resultSet.moveToFirst();
        Weight weight = new Weight();
        weight.setWeight(resultSet.getInt(resultSet.getColumnIndexOrThrow(SQLHelper.COLUMN_ControlWeight_NewWeight)));
        resultSet.close();
        return weight;
    }

    public int checkFood(String nameFood) {
        Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_FoodMenu + " Where "
                + SQLHelper.COLUMN_FoodMenu_name
                + " like '" + nameFood + "%'", null);
        if (resultSet.getCount() == 0) {
            return -1;
        }
        resultSet.moveToFirst();
        int id = resultSet.getInt(0);
        if (id == 0) {
            return -1;
        }
        resultSet.close();
        return id;
    }

    public int deleteFood(String id, String email) {
        String whereClause = SQLHelper.COLUMN_CaloDaily_Id + " = '" + id + "'";
        String[] whereArgs = new String[]{id};
        String emailClause = SQLHelper.COLUMN_CaloDaily_idEmail + " = '" + email + "'";
        String[] emailArgs = new String[]{email};
        String selection = whereClause + " AND " + emailClause;
        String[] selectionArgs = new String[]{id, email};
        int rowsDeleted = database.delete(SQLHelper.TABLE_CaloDaily, selection, null);
        return rowsDeleted;
    }
    public int Tinhcalo(String email,String date){
        int sum = 0;
        try {
            String sql = "SELECT SUM(" + SQLHelper.COLUMN_FoodMenu_Calories + ") FROM "
                    + SQLHelper.TABLE_FoodMenu + ", "
                    + SQLHelper.TABLE_CaloDaily + " WHERE "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idFood + " = "
                    + SQLHelper.TABLE_FoodMenu + "."
                    + SQLHelper.COLUMN_FoodMenu_idFood + " AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_IdDate + " = '"+date+ "' AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idEmail + " = '"+email+"'";
            Cursor cursor = database.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                sum = cursor.getInt(0);
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e("SQL", "Error executing SQL query: " + e.getMessage());
        }
        return sum;
    }
    public int TinhProtein(String email,String date){
        int sum = 0;
        try {
            String sql = "SELECT SUM(" + SQLHelper.COLUMN_FoodMenu_Proteins + ") FROM "
                    + SQLHelper.TABLE_FoodMenu + ", "
                    + SQLHelper.TABLE_CaloDaily + " WHERE "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idFood + " = "
                    + SQLHelper.TABLE_FoodMenu + "."
                    + SQLHelper.COLUMN_FoodMenu_idFood + " AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_IdDate + " = '"+date+ "' AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idEmail + " = '"+email+"'";
            Cursor cursor = database.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                sum = cursor.getInt(0);
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e("SQL", "Error executing SQL query: " + e.getMessage());
        }
        return sum;
    }
    public int TinhFat(String email,String date){
        int sum = 0;
        try {
            String sql = "SELECT SUM(" + SQLHelper.COLUMN_FoodMenu_Fats + ") FROM "
                    + SQLHelper.TABLE_FoodMenu + ", "
                    + SQLHelper.TABLE_CaloDaily + " WHERE "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idFood + " = "
                    + SQLHelper.TABLE_FoodMenu + "."
                    + SQLHelper.COLUMN_FoodMenu_idFood + " AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_IdDate + " = '"+date+ "' AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idEmail + " = '"+email+"'";
            Cursor cursor = database.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                sum = cursor.getInt(0);
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e("SQL", "Error executing SQL query: " + e.getMessage());
        }
        return sum;
    }
    public int TinhCarb(String email,String date){
        int sum = 0;
        try {
            String sql = "SELECT SUM(" + SQLHelper.COLUMN_FoodMenu_Carbs + ") FROM "
                    + SQLHelper.TABLE_FoodMenu + ", "
                    + SQLHelper.TABLE_CaloDaily + " WHERE "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idFood + " = "
                    + SQLHelper.TABLE_FoodMenu + "."
                    + SQLHelper.COLUMN_FoodMenu_idFood + " AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_IdDate + " = '"+date+ "' AND "
                    + SQLHelper.TABLE_CaloDaily + "."
                    + SQLHelper.COLUMN_CaloDaily_idEmail + " = '"+email+"'";
            Cursor cursor = database.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                sum = cursor.getInt(0);
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e("SQL", "Error executing SQL query: " + e.getMessage());
        }
        return sum;
    }
    public List<String> timKiemfood(String nameFood){
        List<String> food = new ArrayList<>();
        Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_FoodMenu + " Where "
                + SQLHelper.COLUMN_FoodMenu_name
                +" like '"+ nameFood +"%'",null);
        if(resultSet.getCount() == 0 ){
            return food;
        }
        resultSet.moveToFirst();
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setIdFood(resultSet.getInt(0));
        foodMenu.setFoodName(resultSet.getString(1));
        foodMenu.setsl(resultSet.getString(6));
        String chuoi = foodMenu.getFoodName()+" - "+foodMenu.getsl();
        food.add(chuoi);
        resultSet.close();
        return food;
    }
    public FoodMenu detail_food(long idFood){
            Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_FoodMenu + " Where "
                    + SQLHelper.COLUMN_FoodMenu_idFood
                    + " = '" + idFood + "'", null);
            resultSet.moveToFirst();
            FoodMenu foodMenu = new FoodMenu();
             if(resultSet.getCount() == 0 ){
            return foodMenu;
             }
             int id= resultSet.getInt(0);
            String FoodName = resultSet.getString(1);
            String sl = resultSet.getString(6);
            int Calories= resultSet.getInt(2);
            int Fats= resultSet.getInt(3);
            int setProteins= resultSet.getInt(4);
            int setCarbs= resultSet.getInt(5);
            foodMenu.setIdFood(id);
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
    public ArrayList<Weight> getAllWeightUp(String email2){
        ArrayList<Weight> weights = new ArrayList<>();
        List<Double> newwei = new ArrayList<>();
        List<String> IdDate = new ArrayList<>();
        List<String> email = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + SQLHelper.TABLE_ControlWeight + " Where "
                + SQLHelper.COLUMN_ControlWeight_EMAIL
                + " = '" + email2 + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Weight weight = new Weight();
            weight.setControlWeight_Date(cursor.getString(1));
            weight.setWeight(cursor.getDouble(2));
            newwei.add(weight.getWeight());
            email.add(weight.getEmail());
            IdDate.add(weight.getControlWeight_Date());
            cursor.moveToNext();
        }
        weights = Weight.initWeight(newwei,IdDate,email);
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return weights;
    }
    public List<String> getAllWeightToDrawChart(String email2){
        ArrayList<Weight> weights = new ArrayList<>();
        List<Double> newwei = new ArrayList<>();
        List<String> IdDate = new ArrayList<>();
        List<String> cannag = new ArrayList<>();
        List<String> email = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + SQLHelper.TABLE_ControlWeight + " Where "
                + SQLHelper.COLUMN_ControlWeight_EMAIL
                + " = '" + email2 + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Weight weight = new Weight();
            weight.setControlWeight_Date(cursor.getString(1));
            weight.setWeight(cursor.getDouble(2));
            newwei.add(weight.getWeight());
            email.add(weight.getEmail());
            cannag.add(String.valueOf(weight.getWeight()));
            IdDate.add(weight.getControlWeight_Date());
            cursor.moveToNext();
        }
        weights = Weight.initWeight(newwei,IdDate,email);
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return cannag;
    }
    public List<String> getAllDateWeightToDrawChart(String email2){
        ArrayList<Weight> weights = new ArrayList<>();
        List<Double> newwei = new ArrayList<>();
        List<String> IdDate = new ArrayList<>();
        List<String> email = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + SQLHelper.TABLE_ControlWeight + " Where "
                + SQLHelper.COLUMN_ControlWeight_EMAIL
                + " = '" + email2 + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Weight weight = new Weight();
            weight.setControlWeight_Date(cursor.getString(1));
            weight.setWeight(cursor.getDouble(2));
            newwei.add(weight.getWeight());
            email.add(weight.getEmail());
            IdDate.add(weight.getControlWeight_Date());
            cursor.moveToNext();
        }
        weights = Weight.initWeight(newwei,IdDate,email);
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return IdDate;
    }
    String ttluu = "tkmkLog";
    public ArrayList<DaulyFood> getFood(String timeOfDay,String email,String date){
        ArrayList<DaulyFood> foods = new ArrayList<>();
        List<Integer> IdDayly = new ArrayList<>();
        List<String> IdDate = new ArrayList<>();
        List<String> NameFoodOfday = new ArrayList<>();
        List<Integer> IdFood = new ArrayList<>();
        List<String> TimeofDay = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + SQLHelper.TABLE_CaloDaily + " Where "
                + SQLHelper.COLUMN_CaloDaily_TimeofDay +" = '"+ timeOfDay + "' and "+SQLHelper.COLUMN_CaloDaily_idEmail+
                " = '" + email  + "' and "+SQLHelper.COLUMN_CaloDaily_IdDate+
                " = '" + date + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           DaulyFood daulyFood = new DaulyFood();
            daulyFood.setId(cursor.getInt(0));
           daulyFood.setIdDate(cursor.getString(1));
            daulyFood.setNameFoodOfday(cursor.getString(4));
            daulyFood.setIdFood(cursor.getInt(2));
            daulyFood.setTimeofDay(cursor.getString(5));
            IdDayly.add(daulyFood.getId());
            IdDate.add(daulyFood.getIdDate());
            NameFoodOfday.add(daulyFood.getNameFoodOfday());
            IdFood.add(daulyFood.getIdFood());
            TimeofDay.add(daulyFood.getTimeofDay());

            Log.v("getFood", "" + foods);
            cursor.moveToNext();
        }
        foods = DaulyFood.initfood(IdDayly,IdDate,NameFoodOfday,IdFood,TimeofDay);
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        Log.v("readData", ""+foods);
        return foods;
    }
    public ArrayList<DaulyFood> getFoodShowhistoRydata(String email,String date){
        ArrayList<DaulyFood> foods = new ArrayList<>();
        List<Integer> IdDayly = new ArrayList<>();
        List<String> IdDate = new ArrayList<>();
        List<String> NameFoodOfday = new ArrayList<>();
        List<Integer> IdFood = new ArrayList<>();
        List<String> TimeofDay = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + SQLHelper.TABLE_CaloDaily + " Where "
                +SQLHelper.COLUMN_CaloDaily_idEmail+
                " = '" + email  + "' and "+SQLHelper.COLUMN_CaloDaily_IdDate+
                " = '" + date + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DaulyFood daulyFood = new DaulyFood();
            daulyFood.setId(cursor.getInt(0));
            daulyFood.setIdDate(cursor.getString(1));
            daulyFood.setNameFoodOfday(cursor.getString(4));
            daulyFood.setIdFood(cursor.getInt(2));
            daulyFood.setTimeofDay(cursor.getString(5));
            IdDayly.add(daulyFood.getId());
            IdDate.add(daulyFood.getIdDate());
            NameFoodOfday.add(daulyFood.getNameFoodOfday());
            IdFood.add(daulyFood.getIdFood());
            TimeofDay.add(daulyFood.getTimeofDay());

            Log.v("getFood", "" + foods);
            cursor.moveToNext();
        }
        foods = DaulyFood.initfood(IdDayly,IdDate,NameFoodOfday,IdFood,TimeofDay);
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        Log.v("readData", ""+foods);
        return foods;
    }
    public UserInfo Bmr(String email){
        Cursor resultSet = database.rawQuery("Select * from " + SQLHelper.TABLE_UserInfo + " Where "
                + SQLHelper.COLUMN_UserInfo_idEmail
                + " = '" + email + "'",null);
        resultSet.moveToFirst();

        UserInfo userInfo = new UserInfo();
        if(resultSet.getCount() == 0 ){
            return userInfo;
        }
        int chieuCao = resultSet.getInt(1);
        int canNang = resultSet.getInt(2);
        int age = resultSet.getInt(4);
        String tapLuyen = resultSet.getString(5);
        String sex = resultSet.getString(6);
        String target = resultSet.getString(7);

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
