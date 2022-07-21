package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.DAO.UserSQLiteDAO;
import com.fpt.prm391groupproject.DAO.WalletDAO;
import com.fpt.prm391groupproject.ProductAdapter;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Product;
import com.fpt.prm391groupproject.model.User;
import com.fpt.prm391groupproject.model.Wallet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class WalletFragment extends Fragment {
    TextView t_txt_money;
    TextView t_txt_point;
    WalletDAO walletDAO;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet,container,false);
        walletDAO = new WalletDAO(view.getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        t_txt_money = view.findViewById(R.id.txt_money);
        t_txt_point = view.findViewById(R.id.txt_point);
        getUserWallet();
        return view;
    }

    private class GetUserWalletOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                String money = "0";
                String point = "0";
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> documentData = document.getData();
                    money = documentData.get(Constants.FireBaseWalletTable.money).toString();
                    point = documentData.get(Constants.FireBaseWalletTable.point).toString();
                }
                t_txt_money.setText(money);
                t_txt_point.setText(point);
            }
        }
    }
    private void getUserWallet(){
        walletDAO.getUserWallet(new GetUserWalletOnCompleteListener(),firebaseAuth.getCurrentUser().getUid());
    }
}
