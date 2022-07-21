package com.fpt.prm391groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.prm391groupproject.DAO.UserDAO;
import com.fpt.prm391groupproject.DAO.UserSQLiteDAO;
import com.fpt.prm391groupproject.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {


    EditText txtName,txtAddress,txtAge,txtPhone;
    TextView txtEmail;
    Button btnChange;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DatabaseReference rootDatabaseref;
    private UserSQLiteDAO userSQLiteDAO;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userDAO=new UserDAO(this);
        userSQLiteDAO=new UserSQLiteDAO(this);
        Intent data=getIntent();
        String name=data.getStringExtra("name");
        String email=data.getStringExtra("email");
        String phone=data.getStringExtra("phone");
        String age=data.getStringExtra("age");
        String address=data.getStringExtra("address");
        String uId=data.getStringExtra("userId");
        txtName = findViewById(R.id.editTxtName);
        txtAddress = findViewById(R.id.editTxtAddress);
        txtAge=findViewById(R.id.editTxtAge);
        txtPhone=findViewById(R.id.editTxtPhone);
        txtEmail=findViewById(R.id.EmailTxt);
        btnChange=findViewById(R.id.btnSaveChange);
        txtName.setText(name);
        txtAddress.setText(address);
        txtAge.setText(age);
        txtPhone.setText(phone);
        txtEmail.setText(email);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User(uId,txtEmail.getText().toString(),
                        txtName.getText().toString(),txtPhone.getText().toString(),
                        txtAddress.getText().toString(),Integer.parseInt(txtAge.getText().toString()));
                userDAO.updateUser(user);
                userSQLiteDAO.updateUser(user);
                Intent intent = new Intent(EditProfile.this,HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}