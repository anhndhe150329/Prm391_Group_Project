package com.fpt.prm391groupproject.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.prm391groupproject.CategoryAdapter;
import com.fpt.prm391groupproject.DAO.CategorySQLiteDAO;
import com.fpt.prm391groupproject.DAO.ProductDAO;
import com.fpt.prm391groupproject.ProductAdapter;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.Utils.Constants;
import com.fpt.prm391groupproject.model.Category;
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

    private Spinner spinnerCategory;
    private CategoryAdapter categoryAdapter;

    private final String DEFAULT_CATEGORY_FILTER = "All";
    private String filterCategory;
    private String filterName;

    Button btnSearch;
    EditText edtSearch;

    ProductDAO productDAO;
    CategorySQLiteDAO categoryDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);

        filterCategory = DEFAULT_CATEGORY_FILTER;

        btnSearch = view.findViewById(R.id.btn_search);

        edtSearch = view.findViewById(R.id.search_text);
        filterName = edtSearch.getText().toString();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListProductFilter(edtSearch.getText().toString(),filterCategory);
            }
        });

        bindingDropDown(view);
        categoryAdapter = new CategoryAdapter(getContext(),R.layout.item_selected,getListCategory());
        spinnerCategory.setAdapter(categoryAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),categoryAdapter.getItem(i).getName(),Toast.LENGTH_SHORT).show();
                filterCategory = categoryAdapter.getItem(i).getName();
                filterName = edtSearch.getText().toString();
                getListProductFilter(filterName,filterCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        productDAO = new ProductDAO();

        bindingView(view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        productRecycleView.setLayoutManager(gridLayoutManager);
        productRecycleView.setHasFixedSize(true);
        //getListProductFilter(filterName,filterCategory);

        categoryDAO = new CategorySQLiteDAO(getContext());
        return view;
    }

    private List<Category> getListCategory(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(DEFAULT_CATEGORY_FILTER));
        categories.add(new Category("Asus"));
        categories.add(new Category("Acer"));
        categories.add(new Category("MSI"));
        //categories = categoryDAO.getAllCategories();
        return categories;
    }

    private void bindingDropDown(View view){
        spinnerCategory = view.findViewById(R.id.spinner_category);
    }

    private void getListProductFilter(String name, String category){
        productDAO.getListProductsFilter(new GetAllProductsOnCompleteListener(),name,category);
    }

    private void bindingView(View view) {
        productRecycleView = view.findViewById(R.id.recycle_view_home);
    }

    private class GetAllProductsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                products = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
//                    add data to list
                    Map<String, Object> documentData = document.getData();
                    Product p = new Product();
                    p.setId(document.getId());
                    p.setProductName(documentData.get(Constants.FireBaseProductTable.productName).toString());
                    p.setPrice(Integer.parseInt(documentData.get(Constants.FireBaseProductTable.productPrice).toString()));
                    p.setQuantity(Integer.parseInt(documentData.get(Constants.FireBaseProductTable.productQuantity).toString()));
                    p.setImage(documentData.get(Constants.FireBaseProductTable.productImage).toString());
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
