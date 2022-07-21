package com.fpt.prm391groupproject.DAO;

import com.fpt.prm391groupproject.model.Order;
import com.fpt.prm391groupproject.model.OrderDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private List<OrderDetail> orders;
    CollectionReference table;
    public OrderDetailDAO(){
        table = FirebaseFirestore.getInstance().collection("OrderDetail");
        orders = new ArrayList<>();
    }
    public void getListOrderDetailByOrderId(OnCompleteListener<QuerySnapshot> onCompleteListener, String OrderId){
        table
//                    .whereGreaterThanOrEqualTo(Constants.FireBaseProductTable.productName,name)
//                    .whereLessThanOrEqualTo(Constants.FireBaseProductTable.productName,name+'\uf8ff')
                .whereEqualTo("OrderId",OrderId)
                .get()
                .addOnCompleteListener(onCompleteListener );


    }
}
