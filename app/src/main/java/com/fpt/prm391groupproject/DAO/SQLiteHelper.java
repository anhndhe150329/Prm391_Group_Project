package com.fpt.prm391groupproject.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fpt.prm391groupproject.Utils.Constants;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "LaptopShopDB";

    String CREATE_TABLE = "CREATE TABLE "+ Constants.SQLiteCategoryTable.tableName + "("
            + Constants.SQLiteCategoryTable.id +" INTEGER PRIMARY KEY, " +
            Constants.SQLiteCategoryTable.name + " TEXT NOT NULL" +
            ")";

    public static int VERSION = 1;
    public SQLiteHelper(@Nullable Context context,
                        @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE "+ Constants.SQLiteCategoryTable.tableName + "("
                + Constants.SQLiteCategoryTable.id +" INTEGER PRIMARY KEY, " +
                Constants.SQLiteCategoryTable.name + " TEXT NOT NULL" +
                ")";
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String DROP_TABLE = "DROP TABLE if EXISTS category1";
            sqLiteDatabase.execSQL(DROP_TABLE);
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }
    }
}
