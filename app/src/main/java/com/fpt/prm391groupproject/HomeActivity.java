package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cartItem:
                Toast.makeText(this,"menuItemCart context Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.searchItem:
                Toast.makeText(this,"menuItemSearch context click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getListProduct(){
        dao.getListProducts(new GetAllProductsOnCompleteListener());
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    private class GetAllProductsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            String productName = "ProductName";
            String productPrice = "Price";
            String productQuantity = "Quantity";
            String productImage = "Image";
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
                    p.setImage(documentData.get(productImage).toString());
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