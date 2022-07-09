package com.fpt.prm391groupproject.DAO;
import android.util.Log;

import androidx.annotation.NonNull;

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
    private final String dbName = "Product";
    private final String productName = "ProductName";
    private final String productPrice = "Price";
    private final String productQuantity = "Quantity";
    private final String productImage = "ImageId";
    private List<Product> products;
    CollectionReference table;

    public ProductDAO() {
        table = FirebaseFirestore.getInstance().collection(dbName);
        products = new ArrayList<>();
    }

    public void addProduct(Product p){
        Map<String, Object> product = new HashMap<>();
        product.put(productName, p.getProductName());
        product.put(productPrice, p.getPrice());
        product.put(productQuantity, p.getQuantity());
        product.put(productImage, p.getId_image());

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


//
//    public Product getProductById(String id){
//        List<Product> result = new ArrayList<>();
//        DocumentReference docRef = table.document(id);
//// Source can be CACHE, SERVER, or DEFAULT.
//        Source source = Source.CACHE;
//
//// Get the document, forcing the SDK to use the offline cache
//        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Document found in the offline cache
//                    DocumentSnapshot document = task.getResult();
//                    Map<String, Object> documentData = document.getData();
//                    Product p = new Product();
//                    p.setId(document.getId());
//                    p.setProductName(documentData.get(productName).toString());
//                    p.setPrice(Integer.parseInt(documentData.get(productPrice).toString()));
//                    p.setQuantity(Integer.parseInt(documentData.get(productQuantity).toString()));
//                    result.add(p);
//                    Log.d("getProduct", "Cached document data: " + document.getData());
//                } else {
//                    Log.d("getProduct", "Cached get failed: ", task.getException());
//                }
//            }
//        });
//
//        return result.size()==1 ? result.get(0):null;
//    }



}
