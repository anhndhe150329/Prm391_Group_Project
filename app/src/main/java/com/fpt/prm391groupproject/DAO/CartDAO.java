package com.fpt.prm391groupproject.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    SQLiteDatabase db;
    SQLiteHelper helper;
    public CartDAO(Context context) {
        helper = new SQLiteHelper(context,null);
    }
    @SuppressLint("Range")
    public List<Cart> getAllCarts(String sql,String...arg){
        db = helper.getWritableDatabase();
        Cursor c = db.rawQuery(sql,arg);
        List<Cart> result = new ArrayList<>();
        while (c.moveToNext()){
            Cart cart = new Cart();
            cart.setProductId(c.getString(c.getColumnIndex("ProductId")));
            cart.setUserId(c.getString(c.getColumnIndex("UserId")));
            cart.setPrice(c.getInt(c.getColumnIndex("Price")));
            cart.setQuantity(c.getInt(c.getColumnIndex("Quantity")));
            cart.setImage(c.getString(c.getColumnIndex("Image")));
            cart.setProductName(c.getString(c.getColumnIndex("ProductName")));
            result.add(cart);
        }

        return result;
    }
    public void addToCart(Cart c){
            db = helper.getWritableDatabase();
            String sqlInsert = "Insert into Cart (ProductId,UserId,Image,Quantity,Price,ProductName) values (?,?,?,?,?,?)";
            String [] param = new String[]{c.getProductId(),c.getUserId(),c.getImage(),c.getQuantity()+"",c.getPrice()+"",c.getProductName()};
            db.execSQL(sqlInsert,param);
    }
    public int updateCart(Cart c){
        int count;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Quantity",GetById(c.getUserId(),c.getProductId()).getQuantity()+1);
        count = db.update("Cart",values,"UserId = ? and ProductId= ?", new String[]{c.getUserId(),c.getProductId()});

        return count;
    }
    public Cart GetById(String UserId,String ProductId){
        if(getAllCarts("select * from Cart where UserId = ? and ProductId=?",UserId,ProductId).size()==0) return null;
        else return getAllCarts("select * from Cart where UserId = ? and ProductId=?",UserId,ProductId).get(0);
    }
    public void Delete(String UserId){
        db.delete("Cart","UserId=?",new String[]{UserId});
    }
}
