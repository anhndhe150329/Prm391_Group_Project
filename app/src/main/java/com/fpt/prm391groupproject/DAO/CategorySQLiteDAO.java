package com.fpt.prm391groupproject.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CategorySQLiteDAO {
    SQLiteDatabase db;
    SQLiteHelper helper;
    public CategorySQLiteDAO(Context context) {
        helper = new SQLiteHelper(context,null);
    }


}
