package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.R;

public class ProductFragment extends Fragment {
    TextView tv_p_name;
    ImageView iv_img_p;
    ImageView iv_img_fav;
    TextView tv_p_cat;
    TextView tv_p_des;
    TextView tv_p_price;
    Button bt_btn_cart;

    private String product_id;

    public ProductFragment(String product_id) {
        this.product_id = product_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);
        tv_p_name = view.findViewById(R.id.p_name);
        iv_img_p = view.findViewById(R.id.img_p);
        iv_img_fav = view.findViewById(R.id.img_fav);
        tv_p_cat = view.findViewById(R.id.p_cat);
        tv_p_des = view.findViewById(R.id.p_des);
        tv_p_price = view.findViewById(R.id.p_price);
        bt_btn_cart = view.findViewById(R.id.btn_cart);
//        textView.setText(id);
        return view;
    }
}
