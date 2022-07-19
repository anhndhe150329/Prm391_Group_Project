package com.fpt.prm391groupproject.DAO;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {

    private List<Product> products;
    CollectionReference table;

    public ProductDAO() {
        table = FirebaseFirestore.getInstance().collection(Constants.FireBaseProductTable.dbName);
        products = new ArrayList<>();
    }

    public void addProduct(Product p){
        Map<String, Object> product = new HashMap<>();
        product.put(Constants.FireBaseProductTable.productName, p.getProductName());
        product.put(Constants.FireBaseProductTable.productPrice, p.getPrice());
        product.put(Constants.FireBaseProductTable.productQuantity, p.getQuantity());
        product.put(Constants.FireBaseProductTable.productImage, p.getImage());

        table.add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void updateProduct(Product p) {
        String id = p.getId();
        DocumentReference documentReference = table.document(id);
        documentReference.set(p);
    }

    public void deleteProduct(String id) {
        DocumentReference documentReference = table.document(id);
        documentReference.delete();
    }

    public void getListProducts(OnCompleteListener<QuerySnapshot> onCompleteListener){
        table.get()
                .addOnCompleteListener(onCompleteListener );
    }
}
