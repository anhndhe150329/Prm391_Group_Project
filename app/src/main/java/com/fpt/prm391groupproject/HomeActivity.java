package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView productRecycleView;
    private ProductAdapter adapter;
    private List<Product> products;

    ProductDAO dao;

    private void bindingView() {
        productRecycleView = findViewById(R.id.recycle_view_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new ProductDAO();
        setContentView(R.layout.activity_home);
        bindingView();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        productRecycleView.setLayoutManager(gridLayoutManager);
        productRecycleView.setHasFixedSize(true);
        getListProduct();
    }

    public void onClickViewBtn(android.view.View v){
        getListProduct();
    }

    private void getListProduct(){
        dao.getListProducts(new GetAllProductsOnCompleteListener());
    }

    private class GetAllProductsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            String dbName = "Product";
            String productName = "ProductName";
            String productPrice = "Price";
            String productQuantity = "Quantity";
            String productImage = "ImageId";
            if (task.isSuccessful()) {
                products = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
//                    add data to list
                    Map<String, Object> documentData = document.getData();
                    Product p = new Product();
                    p.setId(document.getId());
                    p.setProductName(documentData.get(productName).toString());
                    p.setPrice(Integer.parseInt(documentData.get(productPrice).toString()));
                    p.setQuantity(Integer.parseInt(documentData.get(productQuantity).toString()));
                    p.setId_image(Integer.parseInt(documentData.get(productImage).toString()));
                    products.add(p);

                    Log.d("getProduct", document.getId() + " => " + document.getData());
                }
                adapter = new ProductAdapter(products, HomeActivity.this);
                productRecycleView.setAdapter(adapter);
            } else {
                Log.w("getProduct", "Error getting documents.", task.getException());
            }
        }
    }

}