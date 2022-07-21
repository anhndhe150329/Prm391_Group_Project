package com.fpt.prm391groupproject.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Product;
import com.fpt.prm391groupproject.model.User;
import com.fpt.prm391groupproject.model.Wallet;
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

public class WalletDAO {
    private Context context;

    CollectionReference table;
    public WalletDAO(Context context) {
        this.context = context;
        table = FirebaseFirestore.getInstance().collection(Constants.FireBaseWalletTable.dbName);
    }
    public void addWallet(Wallet w){
        Map<String,Object> wallet = new HashMap<>();
        wallet.put(Constants.FireBaseWalletTable.userId,w.getUserId());
        wallet.put(Constants.FireBaseWalletTable.money,w.getMoney());
        wallet.put(Constants.FireBaseWalletTable.point,w.getPoint());

        table.add(w)
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

    public void getUserWallet(OnCompleteListener<QuerySnapshot> onCompleteListener, String userId){
            table
                .whereEqualTo(Constants.FireBaseWalletTable.userId,userId)
                .get()
                .addOnCompleteListener(onCompleteListener );
    }
}
