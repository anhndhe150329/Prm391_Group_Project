package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.ProductAdapter;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ProductFragment extends Fragment {
    TextView tv_p_name;
    ImageView iv_img_p;
    ImageView iv_img_fav;
    TextView tv_p_cat;
    TextView tv_p_des;
    TextView tv_p_price;
    Button bt_btn_cart;
    View view;

    private String product_id;
    private ProductDAO dao;

    public ProductFragment(String product_id) {
        this.product_id = product_id;
        dao = new ProductDAO();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product,container,false);
        tv_p_name = view.findViewById(R.id.p_name);
        iv_img_p = view.findViewById(R.id.img_p);
        iv_img_fav = view.findViewById(R.id.img_fav);
        tv_p_cat = view.findViewById(R.id.p_cat);
        tv_p_des = view.findViewById(R.id.p_des);
        tv_p_price = view.findViewById(R.id.p_price);
        bt_btn_cart = view.findViewById(R.id.btn_cart);
        dao.getProductById(new GetProductByIdOnCompleteListener(),product_id);
        return view;
    }

    private class GetProductByIdOnCompleteListener implements OnCompleteListener<DocumentSnapshot> {

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                int imgId = getContext().getResources().getIdentifier(documentSnapshot.getString(Constants.FireBaseProductTable.productImage), "drawable", getContext().getPackageName());
                iv_img_p.setImageResource(imgId);
                tv_p_name.setText(documentSnapshot.getString(Constants.FireBaseProductTable.productName));
                tv_p_cat.setText(documentSnapshot.getString(Constants.FireBaseProductTable.productCategory));
                tv_p_price.setText(documentSnapshot.getLong(Constants.FireBaseProductTable.productPrice)+"");
                tv_p_des.setText(documentSnapshot.getString(Constants.FireBaseProductTable.productDescription));
                iv_img_fav.setImageResource(R.drawable.ic_favorite);
                iv_img_fav.setVisibility(documentSnapshot.getBoolean(Constants.FireBaseProductTable.productFavourite)?View.VISIBLE:View.INVISIBLE);
                Toast.makeText(view.getContext(),"Clicked on: "+tv_p_name.getText(),Toast.LENGTH_SHORT).show();
            }
        }
    }


}
