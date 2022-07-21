package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.prm391groupproject.DAO.OrderDAO;
import com.fpt.prm391groupproject.OrderAdapter;
import com.fpt.prm391groupproject.ProductAdapter;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Order;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import  com.fpt.prm391groupproject.DAO.OrderDAO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    View view;
    List<Order> lo;
    RecyclerView rvOrder;
    OrderAdapter oa;
    TextView tv;
    TextView tv1;
    OrderDAO od=new OrderDAO();
    private String UserId;
    private FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public OrderFragment() {
        // Required empty public constructor
        auth = FirebaseAuth.getInstance();
        this.UserId=auth.getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order,container,false);
        // Inflate the layout for this fragment
        rvOrder=view.findViewById(R.id.rv_order);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        getListOrder(UserId);
        return view;
    }
    private void getListOrder(String UserId){
        od.getListOrderByUserId(new GetAllOrdersOnCompleteListener(),UserId);
    }
    private class GetAllOrdersOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                lo = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
//                    add data to list
                    Map<String, Object> documentData = document.getData();
                    Order o  = new Order();
                    o.setOrderId(document.getId());
                    o.setUserId(documentData.get("UserId").toString());
                    o.setOrderDate(documentData.get("OrderDate").toString());
                    lo.add(o);
                    Log.d("getOrder", document.getId() + " => " + document.getData());
                }
                oa = new OrderAdapter(lo, getActivity());
                rvOrder.setAdapter(oa);
            } else {
                Log.w("getOrder", "Error getting documents.", task.getException());
            }
        }
    }
}