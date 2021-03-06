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

    String CREATE_USER_TABLE = "CREATE TABLE "+ Constants.SQLiteUserTable.tableName + "("
             + Constants.SQLiteUserTable.userId +" TEXT PRIMARY KEY NOT NULL, " +
            Constants.SQLiteUserTable.name +" TEXT, " +
            Constants.SQLiteUserTable.email +" TEXT, " +
            Constants.SQLiteUserTable.phone +" TEXT, " +
            Constants.SQLiteUserTable.address +" TEXT, " +
            Constants.SQLiteUserTable.age +" INTEGER " +
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
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL("CREATE TABLE Cart (\n" +
                "  UserId nvarchar(30),\n" +
                "  ProductId nvarchar(30),\n" +
                "  Image nvarchar(100),\n" +
                "  ProductName nvarchar(30),\n" +
                "  Price int,\n" +
                "  Quantity int\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String DROP_TABLE = "DROP TABLE if EXISTS category1";
            sqLiteDatabase.execSQL(DROP_TABLE);
            sqLiteDatabase.execSQL(CREATE_TABLE);
            sqLiteDatabase.execSQL(CREATE_USER_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Cart");
            sqLiteDatabase.execSQL("CREATE TABLE Cart (\n" +
                    "  UserId nvarchar(30),\n" +
                    "  ProductId nvarchar(30),\n" +
                    "  Image nvarchar(100),\n" +
                    "  ProductName nvarchar(30),\n" +
                    "  Price int,\n" +
                    "  Quantity int\n" +
                    ")");
        }
    }
}
