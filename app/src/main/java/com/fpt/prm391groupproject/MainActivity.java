package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView productRecycleView;
    private ProductAdapter adapter;
    private List<Product> products;

    ProductDAO dao;

    private void bindingView() {
        productRecycleView = findViewById(R.id.recycle_view_product);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new ProductDAO();
        products = new ArrayList<>();
        setContentView(R.layout.activity_main);
        bindingView();

        Product chap1 = new Product("Laptop 1",15,2,"");
        Product chap2 = new Product("Laptop 2",22,3,"");
        Product chap3 = new Product("Laptop 3",12,4,"");
        Product chap4 = new Product("Laptop 4",34,3,"");
        Product chap5 = new Product("Laptop 5",11,5,"");
        products.add(chap1);
        products.add(chap2);
        products.add(chap3);
        products.add(chap4);
        products.add(chap5);

//        adapter = new ProductAdapter(products, MainActivity.this);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
//        productRecycleView.setLayoutManager(gridLayoutManager);
//        productRecycleView.setHasFixedSize(true);
//
//        productRecycleView.setAdapter(adapter);

    }

    public void onClickAddBtn(android.view.View v){
        Product p = new Product("Laptop x",250,30,"");
        dao.addProduct(p);
        products.add(p);
    }

    public void onClickUpdateBtn(android.view.View v){

    }

    public void onClickDeleteBtn(android.view.View v){
        EditText text = findViewById(R.id.tbId);
        String id = text.getText().toString();
        dao.deleteProduct(id);
    }

    public void onClickViewBtn(android.view.View v){
        dao.getListProducts(new GetAllProductsOnCompleteListener());
    }

    private class GetAllProductsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            String dbName = "Product";
            String productName = "ProductName";
            String productPrice = "Price";
            String productQuantity = "Quantity";
            String productImage = "Image";
            if (task.isSuccessful()) {
                List<Product> listProduct = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
//                    add data to list
                    Map<String, Object> documentData = document.getData();
                    Product p = new Product();
                    p.setId(document.getId());
                    p.setProductName(documentData.get(productName).toString());
                    p.setPrice(Integer.parseInt(documentData.get(productPrice).toString()));
                    p.setQuantity(Integer.parseInt(documentData.get(productQuantity).toString()));
                    p.setImage(documentData.get(productImage).toString());
                    listProduct.add(p);

                    Log.d("getProduct", document.getId() + " => " + document.getData());
                }
//                adapter = new ProductAdapter(listProduct, MainActivity.this);
//                productRecycleView.setAdapter(adapter);
            } else {
                Log.w("getProduct", "Error getting documents.", task.getException());
            }
        }
    }


}