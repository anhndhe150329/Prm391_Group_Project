package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.fragment.HomeFragment;
import com.fpt.prm391groupproject.fragment.ProfileFragment;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PROFILE = 1;

    private int currentFragment = FRAGMENT_HOME;

    private DrawerLayout drawerLayout;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navi_drawer_open,R.string.navi_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        bindingView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        productRecycleView.setLayoutManager(gridLayoutManager);
        productRecycleView.setHasFixedSize(true);
        getListProduct();
    }

    private void getListProduct(){
        dao.getListProducts(new GetAllProductsOnCompleteListener());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                if (currentFragment != FRAGMENT_HOME){
                    replaceFragment(new HomeFragment());
                    currentFragment = FRAGMENT_HOME;
                }
                break;
            case R.id.nav_profile:
                if (currentFragment != FRAGMENT_PROFILE){
                    replaceFragment(new ProfileFragment());
                    currentFragment = FRAGMENT_PROFILE;
                }
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
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