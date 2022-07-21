package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fpt.prm391groupproject.CartAdapter;
import com.fpt.prm391groupproject.DAO.CartDAO;
import com.fpt.prm391groupproject.DAO.OrderDetailDAO;
import com.fpt.prm391groupproject.OrderAdapter;
import com.fpt.prm391groupproject.OrderDetailAdapter;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.Order;
import com.fpt.prm391groupproject.model.OrderDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {
    View view;
    TextView tv;

    List<OrderDetail> lo;
    RecyclerView rv;
    OrderDetailAdapter odAdapter;
    OrderDetailDAO od;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String OrderId;
    public OrderDetailFragment(String OrderId) {
        // Required empty public constructor
        this.OrderId=OrderId;
    }
    // TODO: Rename and change types and number of parameters
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_order_detail, container, false);
        od = new OrderDetailDAO();
        tv=view.findViewById(R.id.Total);
        rv=view.findViewById(R.id.rv_order_detail);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        getListOrderDetail(OrderId);
        return view;
    }
    private void getListOrderDetail(String OrderId){
        od.getListOrderDetailByOrderId(new GetAllOrdersOnCompleteListener(),OrderId);
    }
    private class GetAllOrdersOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                lo = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
//                    add data to list
                    Map<String, Object> documentData = document.getData();
                    OrderDetail o  = new OrderDetail();
                    o.setOrderId(documentData.get("OrderId").toString());
                    o.setProductId(documentData.get("ProductId").toString());
                    o.setImage(documentData.get("Image").toString());
                    o.setPrice(Integer.parseInt(documentData.get("Price").toString()));
                    o.setProductName(documentData.get("ProductName").toString());
                    o.setQuantity(Integer.parseInt(documentData.get("Quantity").toString()));
                    lo.add(o);
                    Log.d("getOrder", document.getId() + " => " + document.getData());
                }
                int total=0;
                for(int i=0;i<lo.size();i++){
                    total+=lo.get(i).getQuantity()*lo.get(i).getPrice();
                }
                tv.setText(total+"");
                odAdapter = new OrderDetailAdapter(lo, getActivity());
                rv.setAdapter(odAdapter);
            } else {
                Log.w("getOrder", "Error getting documents.", task.getException());
            }
        }
    }
}