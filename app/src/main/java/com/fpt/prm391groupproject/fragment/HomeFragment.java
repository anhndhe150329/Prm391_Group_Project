package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.HomeActivity;
import com.fpt.prm391groupproject.ProductAdapter;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RecyclerView productRecycleView;
    private ProductAdapter adapter;
    private List<Product> products;

    ProductDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);

        dao = new ProductDAO();

        bindingView(view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        productRecycleView.setLayoutManager(gridLayoutManager);
        productRecycleView.setHasFixedSize(true);
        getListProduct();

        return view;
    }

    private void getListProduct(){
        dao.getListProducts(new GetAllProductsOnCompleteListener());
    }

    private void bindingView(View view) {
        productRecycleView = view.findViewById(R.id.recycle_view_home);
    }

    private class GetAllProductsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            String productName = "ProductName";
            String productPrice = "Price";
            String productQuantity = "Quantity";
            String productImage = "Image";
            if (task.isSuccessful()) {
                products = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
//                    add data to list
                    Map<String, Object> documentData = document.getData();
                    Product p = new Product();
                    p.setId(document.getId());
                    p.setProductName(documentData.get(productName).toString());
                    p.setPrice(Integer.parseInt(documentData.get(productPrice).toString()));
                    p.setQuantity(Integer.parseInt(documentData.get(productQuantity).toString()));
                    p.setImage(documentData.get(productImage).toString());
                    products.add(p);

                    Log.d("getProduct", document.getId() + " => " + document.getData());
                }
                adapter = new ProductAdapter(products, getActivity());
                productRecycleView.setAdapter(adapter);
            } else {
                Log.w("getProduct", "Error getting documents.", task.getException());
            }
        }
    }
}
