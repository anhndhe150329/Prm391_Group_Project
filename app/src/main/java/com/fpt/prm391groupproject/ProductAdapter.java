package com.fpt.prm391groupproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.prm391groupproject.DAO.CartDAO;
import com.fpt.prm391groupproject.fragment.ProductFragment;
import com.fpt.prm391groupproject.fragment.ProfileFragment;
import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private List<Product> products;
    private Context context;
    private FragmentActivity fragmentActivity;
    private FirebaseAuth auth;

    public ProductAdapter(List<Product> products, FragmentActivity fragmentActivity) {
        this.products = products;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        auth = FirebaseAuth.getInstance();
        View v = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int imgId = context.getResources().getIdentifier(products.get(position).getImage(), "drawable", context.getPackageName());
        holder.imv.setImageResource(imgId);
        holder.tv_name.setText(products.get(position).getProductName());
        holder.tv_price.setText("$ "+products.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imv;
        TextView tv_name;
        TextView tv_price;
        Button btn_add;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.iv_product_img);
            tv_name = itemView.findViewById(R.id.tv_product_name);
            tv_price = itemView.findViewById(R.id.tv_product_price);
            btn_add =  itemView.findViewById(R.id.btn_add_to_cart);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id =products.get(getAdapterPosition()).getId();
                    CartDAO cd = new CartDAO(view.getContext());
                    Cart c = new Cart();
                    c.setProductId(id);
                    c.setUserId(auth.getCurrentUser().getUid());
                    c.setProductName(products.get(getAdapterPosition()).getProductName());
                    c.setImage(products.get(getAdapterPosition()).getImage());
                    c.setPrice(products.get(getAdapterPosition()).getPrice());
                    c.setQuantity(1);
                    if(cd.GetById(c.getUserId(),c.getProductId())==null){
                        cd.addToCart(c);
                    }else {
                        cd.updateCart(c);
                    }
                    Toast.makeText(view.getContext(),"Add 1 "+c.getProductName()+" to Cart",Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id =products.get(getAdapterPosition()).getId();

                    FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame,new ProductFragment(id));
                    transaction.commit();

//                    Toast.makeText(view.getContext(),"Clicked on: "+(getAdapterPosition()+1),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
