package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ProductDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new ProductDAO();
        setContentView(R.layout.activity_main);
    }

    public void onClickAddBtn(android.view.View v){
        Product p = new Product("Laptop 3",250,30);
        dao.addProduct(p);
    }

    public void onClickUpdateBtn(android.view.View v){

    }

    public void onClickDeleteBtn(android.view.View v){
        EditText text = findViewById(R.id.tbId);
        String id = text.getText().toString();
        dao.deleteProduct(id);
    }

    public void onClickViewBtn(android.view.View v){

    }


}