package com.fpt.prm391groupproject.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Product;
import com.fpt.prm391groupproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private UserSQLiteDAO userSQLiteDAO;
    private Context context;

    private List<User> users;
    CollectionReference table;

    public UserDAO(Context context) {
        this.context = context;
        table = FirebaseFirestore.getInstance().collection(Constants.FireBaseUserTable.dbName);
        users = new ArrayList<>();
        userSQLiteDAO = new UserSQLiteDAO(context);
    }

    public void addUser(User u){
        Map<String,Object> user = new HashMap<>();
        user.put(Constants.FireBaseUserTable.userName,u.getName());
        user.put(Constants.FireBaseUserTable.userEmail,u.getEmail());
        user.put(Constants.FireBaseUserTable.userPhone,u.getPhone());
        user.put(Constants.FireBaseUserTable.userAddress,u.getAddress());
        user.put(Constants.FireBaseUserTable.userAge,u.getAge());
        user.put(Constants.FireBaseUserTable.userId,u.getUserId());

        table.add(u)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        //userSQLiteDAO.addUser(u);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void updateUser(User p) {
        String id = p.getUserId();
        table.whereEqualTo(Constants.FireBaseUserTable.userId,id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            String docId="";
                            for (QueryDocumentSnapshot document:task.getResult()){
                                docId=document.getId();
                            }
                           table.document(docId).set(p);
                        }
                    }
                });

    }

    public void getLoginUser( String id){
        table.whereEqualTo(Constants.FireBaseUserTable.userId,id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()){
                                Map<String, Object> documentData = document.getData();
                                User u = new User();
                                u.setUserId(id);
                                u.setName(documentData.get(Constants.FireBaseUserTable.userName).toString());
                                u.setEmail(documentData.get(Constants.FireBaseUserTable.userEmail).toString());
                                u.setPhone(documentData.get(Constants.FireBaseUserTable.userPhone).toString());
                                u.setAddress(documentData.get(Constants.FireBaseUserTable.userAddress).toString());
                                u.setAge(Integer.parseInt(documentData.get(Constants.FireBaseUserTable.userAge).toString()));
                                userSQLiteDAO.addUser(u);
                            }
                        }
                    }
                });
    }


}
