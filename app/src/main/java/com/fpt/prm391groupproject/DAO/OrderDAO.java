package com.fpt.prm391groupproject.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.Order;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    private List<Order> orders;
    CollectionReference table;
    public OrderDAO(){
        table = FirebaseFirestore.getInstance().collection("Order");
        orders = new ArrayList<>();
    }
    public void addOrderDetail(String OrderId,List<Cart> lc){
        CollectionReference table1 = FirebaseFirestore.getInstance().collection("OrderDetail");
        for(int i=0;i<lc.size();i++){
            Map<String, Object> product = new HashMap<>();
            product.put("OrderId",OrderId);
            product.put("Image",lc.get(i).getImage());
            product.put("ProductName",lc.get(i).getProductName());
            product.put("ProductId",lc.get(i).getProductId());
            product.put("Quantity",lc.get(i).getQuantity());
            product.put("Price",lc.get(i).getPrice());
            table1.add(product)
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

    }
    public void addOrder(Order p,List<Cart> lc){
        Map<String, Object> product = new HashMap<>();
        product.put("UserId", p.getUserId());
        product.put("OrderDate",p.getOrderDate());
        table.add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        addOrderDetail(documentReference.getId(),lc);
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
    public void getListOrderByUserId(OnCompleteListener<QuerySnapshot> onCompleteListener, String UserId){
            table
//                    .whereGreaterThanOrEqualTo(Constants.FireBaseProductTable.productName,name)
//                    .whereLessThanOrEqualTo(Constants.FireBaseProductTable.productName,name+'\uf8ff')
                    .whereEqualTo("UserId",UserId)
                    .get()
                    .addOnCompleteListener(onCompleteListener );


    }

    public void getListOrders(OnCompleteListener<QuerySnapshot> onCompleteListener){
        table.get()
                .addOnCompleteListener(onCompleteListener );
    }
}
