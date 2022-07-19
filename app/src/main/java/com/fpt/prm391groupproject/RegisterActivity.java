package com.fpt.prm391groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName,etEmail,etPassword,etRePassword,etPhone;
    private Button btRegister;
    private TextView tvLoginhere;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRepassword);
        etPhone = findViewById(R.id.etPhone);
        tvLoginhere = findViewById(R.id.tvLoginhere);

        btRegister = findViewById(R.id.btLogin);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String passs = etPassword.getText().toString().trim();
                String repass = etRePassword.getText().toString().trim();
                String fullname = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(passs)){
                    etPassword.setError("Password required");
                    return;
                }
                if(passs.length() < 6){
                    etPassword.setError("Password must be longer than 6 characters");
                    return;
                }
                if(!repass.equals(passs)){
                    etRePassword.setError("RePassword didn't match");
                    return;
                }
                auth.createUserWithEmailAndPassword(email, passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",fullname);
                            user.put("email",email);
                            user.put("phone",phone);
                            firestore.collection("users").add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.w("TAG", "Success adding user with id: "+documentReference.getId());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error adding document:"+e.toString());
                                        }
                                    });
                            Toast.makeText(RegisterActivity.this, "Success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        tvLoginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}