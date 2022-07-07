package com.fpt.prm391groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.model.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddBtn(android.view.View v){
        Product p = new Product("Laptop",200,10);
        ProductDAO dao = new ProductDAO();
        dao.addProduct(p);
    }
}