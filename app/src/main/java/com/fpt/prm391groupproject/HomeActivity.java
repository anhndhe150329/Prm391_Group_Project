package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.DAO.UserSQLiteDAO;
import com.fpt.prm391groupproject.fragment.ChangePasswordFragment;
import com.fpt.prm391groupproject.fragment.HomeFragment;
import com.fpt.prm391groupproject.fragment.ProfileFragment;
import com.fpt.prm391groupproject.model.Product;
import com.fpt.prm391groupproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PROFILE = 1;
    private static final int FRAGMENT_CART = 2;
    private static final int FRAGMENT_HISTORY = 3;
    private static final int FRAGMENT_WALLET = 4;
    private static final int FRAGMENT_PRODUCT = 5;
    private static final int FRAGMENT_CHANGEPASS = 6;


    private int currentFragment = FRAGMENT_HOME;

    private FirebaseAuth auth;

    private DrawerLayout drawerLayout;

    private UserSQLiteDAO userSQLiteDAO;

    NavigationView navigationView;
    TextView header_name;
    TextView header_email;
    ImageView header_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        userSQLiteDAO=new UserSQLiteDAO(this);

        auth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navi_drawer_open,R.string.navi_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        header_name = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        header_email = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        header_image = navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);

        if (auth.getCurrentUser()!=null){
            isLogin();
        }else {
            isLogout();
        }

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

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
                checkIdLogin();
                if (currentFragment != FRAGMENT_PROFILE){
                    String currentID = auth.getCurrentUser().getUid();
                    replaceFragment(new ProfileFragment(currentID));
                    currentFragment = FRAGMENT_PROFILE;
                }
                break;
            case R.id.nav_cart:
                checkIdLogin();
                if (currentFragment != FRAGMENT_CART){
                    //replaceFragment(new CartFragment());
                    currentFragment = FRAGMENT_CART;
                }
                break;
            case R.id.nav_history:
                checkIdLogin();
                if (currentFragment != FRAGMENT_HISTORY){
                    //replaceFragment(new HistoryFragment());
                    currentFragment = FRAGMENT_HISTORY;
                }
                break;
            case R.id.nav_wallet:
                checkIdLogin();
                if (currentFragment != FRAGMENT_WALLET){
                    //replaceFragment(new HistoryFragment());
                    currentFragment = FRAGMENT_WALLET;
                }
                break;
            case R.id.nav_change_pass:
                if (currentFragment != FRAGMENT_CHANGEPASS){
                    replaceFragment(new ChangePasswordFragment());
                    currentFragment = FRAGMENT_CHANGEPASS;
                }
                break;
            case  R.id.nav_login:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;
            case R.id.nav_logout:
                logout();
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

    public void logout() {
        auth.signOut();
//        isLogout();
//        replaceFragment(new HomeFragment());
//        currentFragment = FRAGMENT_HOME;
        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void isLogin() {
        User user = userSQLiteDAO.getUserById(auth.getCurrentUser().getUid());
        header_image.setImageResource(R.drawable.avatar);
        header_name.setText(user.getName());
        header_email.setText(user.getEmail());
        navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_change_pass).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
    }

    public void isLogout(){
        header_image.setImageResource(R.mipmap.ic_launcher_round);
        header_name.setText("Laptop Shop");
        header_email.setText("");
        navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_change_pass).setVisible(false);
    }

    public void checkIdLogin(){
        if (auth.getCurrentUser()==null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

}