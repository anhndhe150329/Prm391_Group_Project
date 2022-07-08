package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddBtn(android.view.View v){
        Product p = new Product("Laptop 3",250,30);
        p.setId("MrcCtkVQDReSj1KQk5mh");
        ProductDAO dao = new ProductDAO();
//        dao.updateProduct(p);
//        dao.addProduct(p);
//        List<Product> list = dao.getProducts();
//        for (Product p: list){
//            System.out.println(p);
//        }
//        Product product = dao.getProductById("KXsOYOBf7QmSeq4D4OEa");
//        System.out.println(product!=null ? product.getProductName():"null");
        dao.deleteProduct("MrcCtkVQDReSj1KQk5mh");
    }


}