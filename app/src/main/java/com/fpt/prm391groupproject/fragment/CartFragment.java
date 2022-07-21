package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.prm391groupproject.CartAdapter;
import com.fpt.prm391groupproject.DAO.CartDAO;
import com.fpt.prm391groupproject.DAO.OrderDAO;
import com.fpt.prm391groupproject.DAO.WalletDAO;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.Order;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    View view;
    TextView tv;
    Button btn;
    List<Cart> lb;
    private RecyclerView rv;
    private CartAdapter cartAdapter;
    private String UserId;
    private FirebaseAuth auth;
    private int total;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public CartFragment() {
        // Required empty public constructor
        auth = FirebaseAuth.getInstance();
        this.UserId=auth.getCurrentUser().getUid();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart,container,false);
        tv=view.findViewById(R.id.textView3);
        btn=view.findViewById(R.id.button2);

        WalletDAO walletDAO = new WalletDAO(container.getContext());
        CartDAO cd = new CartDAO(container.getContext());
        rv=view.findViewById(R.id.rv_cart);
        lb=cd.getAllCarts("select * from Cart where UserId=?",UserId);
        cartAdapter=new CartAdapter(lb,getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(cartAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        total=0;
        for(int i=0;i<lb.size();i++){
            total+=lb.get(i).getQuantity()*lb.get(i).getPrice();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDAO od = new OrderDAO();
                od.addOrder(new Order(UserId,"", Calendar.getInstance().getTime().toString()),lb);
                cd.Delete(UserId);
                walletDAO.changeMoneyAndPoint(-total,total/10+1, auth.getCurrentUser().getUid());
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,new HomeFragment());
                transaction.commit();
            }
        });
        tv.setText(total+"");
        return view;
    }
}