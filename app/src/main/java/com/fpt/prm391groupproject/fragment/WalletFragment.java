package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.DAO.WalletDAO;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class WalletFragment extends Fragment {
    TextView t_txt_money;
    TextView t_txt_point;
    Button btn_visible;
    Button btn_add_money;

    String money;
    String point;
    WalletDAO walletDAO;
    FirebaseAuth firebaseAuth;

    private final String DEFAULT_MONEY_DISPLAY = "**********";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet,container,false);
        money = "0";
        point = "0";
        walletDAO = new WalletDAO(view.getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        t_txt_money = view.findViewById(R.id.txt_money);
        t_txt_point = view.findViewById(R.id.txt_point);
        btn_visible = view.findViewById(R.id.btn_hid);
        btn_add_money = view.findViewById(R.id.btn_addMoney);

        btn_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t_txt_money.getText().toString().equals(DEFAULT_MONEY_DISPLAY)){
                    t_txt_money.setText(money);
                } else {
                    t_txt_money.setText(DEFAULT_MONEY_DISPLAY);
                }
            }
        });

        btn_add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletDAO.changeMoneyAndPoint(100,0,firebaseAuth.getCurrentUser().getUid());
                int currentMoney = Integer.parseInt(money);
                int currentPoint = Integer.parseInt(point);
                currentMoney+=100;
                currentPoint+=0;
                money = currentMoney+"";
                point = currentPoint+"";
                if (!t_txt_money.getText().toString().equals(DEFAULT_MONEY_DISPLAY)){
                    t_txt_money.setText(money);
                }
                t_txt_point.setText(point);
            }
        });
        getUserWallet();
        return view;
    }

    private class GetUserWalletOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
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
