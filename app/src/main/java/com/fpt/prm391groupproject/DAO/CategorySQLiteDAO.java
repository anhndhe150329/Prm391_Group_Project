package com.fpt.prm391groupproject.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategorySQLiteDAO {
    SQLiteDatabase db;
    SQLiteHelper helper;
    public CategorySQLiteDAO(Context context) {
        helper = new SQLiteHelper(context,null);
    }

    @SuppressLint("Range")
    public List<Category> getAllCategories(){
        db = helper.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.SQLiteCategoryTable.tableName;
        Cursor c = db.rawQuery(sql,null);
        List<Category> result = new ArrayList<>();
        while (c.moveToNext()){
            Category category = new Category();
            category.setId(c.getInt(c.getColumnIndex(Constants.SQLiteCategoryTable.id)));
            category.setName(c.getString(c.getColumnIndex(Constants.SQLiteCategoryTable.name)));
            result.add(category);
        }
        db.close();
        return result;
    }

    public void addCategory(Category c){
        int count = updateCategory(c);
        if (count==0){
            db = helper.getWritableDatabase();
            String sqlInsert = "Insert into "+ Constants.SQLiteCategoryTable.tableName +"(" +
                    Constants.SQLiteCategoryTable.id+ ","+
                    Constants.SQLiteCategoryTable.name +
                    ") values (?,?,?,?,?)";
            String [] param = new String[]{c.getId()+"",c.getName()};
            db.execSQL(sqlInsert,param);
            db.close();
        }
    }

    public void deleteCountry(int id){
        db = helper.getWritableDatabase();
        db.delete(Constants.SQLiteCategoryTable.tableName,Constants.SQLiteCategoryTable.id+" = ? ",new String[]{id+""});
        db.close();
    }

    public int updateCategory(Category c){
        int count;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.SQLiteCategoryTable.name,c.getName());
        count = db.update(Constants.SQLiteCategoryTable.tableName,values,Constants.SQLiteCategoryTable.id+ " = ? ", new String[]{c.getId()+""});
        db.close();
        return count;
    }


}
