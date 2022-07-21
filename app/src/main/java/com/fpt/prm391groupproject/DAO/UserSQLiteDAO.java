package com.fpt.prm391groupproject.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Category;
import com.fpt.prm391groupproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserSQLiteDAO {
    SQLiteDatabase db;
    SQLiteHelper helper;
    public UserSQLiteDAO(Context context) {
        helper = new SQLiteHelper(context,null);
    }

    @SuppressLint("Range")
    public List<User> getAllUser(){
        db = helper.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.SQLiteUserTable.tableName;
        Cursor c = db.rawQuery(sql,null);
        List<User> result = new ArrayList<>();
        while (c.moveToNext()){
            User user = new User();
            user.setUserId(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.userId)));
            user.setName(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.name)));
            user.setEmail(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.email)));
            user.setPhone(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.phone)));
            user.setAddress(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.address)));
            user.setAge(c.getInt(c.getColumnIndex(Constants.SQLiteUserTable.age)));
            result.add(user);
        }
        db.close();
        return result;
    }
    @SuppressLint("Range")
    public User getUserById(String userId){
        db = helper.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.SQLiteUserTable.tableName +" WHERE "+Constants.SQLiteUserTable.userId+" = ?" ;
        String [] param = new String[]{userId};
        Cursor c = db.rawQuery(sql,param);
        User user = new User();
        while (c.moveToNext()){
            user.setUserId(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.userId)));
            user.setName(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.name)));
            user.setEmail(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.email)));
            user.setPhone(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.phone)));
            user.setAddress(c.getString(c.getColumnIndex(Constants.SQLiteUserTable.address)));
            user.setAge(c.getInt(c.getColumnIndex(Constants.SQLiteUserTable.age)));
            break;
        }
        db.close();
        return user;
    }

    public void addUser(User user){
        int count = updateUser(user);
        if (count==0){
            db = helper.getWritableDatabase();
            String sqlInsert = "Insert into "+ Constants.SQLiteUserTable.tableName +"(" +
                    Constants.SQLiteUserTable.userId+ ","+
                    Constants.SQLiteUserTable.name+ ","+
                    Constants.SQLiteUserTable.email+ ","+
                    Constants.SQLiteUserTable.phone+ ","+
                    Constants.SQLiteUserTable.address+ ","+
                    Constants.SQLiteUserTable.age+
                    ") values (?,?,?,?,?,?)";
            String [] param = new String[]{user.getUserId() ,user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getAge()+""};
            db.execSQL(sqlInsert,param);
            db.close();
        }
    }

    public void deleteUser(String id){
        db = helper.getWritableDatabase();
        db.delete(Constants.SQLiteUserTable.tableName,Constants.SQLiteUserTable.userId+" = ? ",new String[]{id});
        db.close();
    }

    public int updateUser(User user){
        int count;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.SQLiteUserTable.name,user.getName());
        values.put(Constants.SQLiteUserTable.phone,user.getPhone());
        values.put(Constants.SQLiteUserTable.address,user.getAddress());
        values.put(Constants.SQLiteUserTable.age,user.getAge());
        count = db.update(Constants.SQLiteUserTable.tableName,values,Constants.SQLiteUserTable.userId+ " = ? ", new String[]{user.getUserId()});
        db.close();
        return count;
    }
}
