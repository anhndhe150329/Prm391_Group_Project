package com.fpt.prm391groupproject;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.prm391groupproject.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemView> {
private List<Cart> lc;
    private Context context;
    private FragmentActivity fragmentActivity;
    public CartAdapter(List<Cart> lc,FragmentActivity fragmentActivity){
        this.lc=lc;
        this.fragmentActivity=fragmentActivity;
    }
    @NonNull
    @Override
    public CartItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view,parent,false);
        CartItemView civ=new CartItemView(view);
        return civ;
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemView holder, int position) {
        Cart selectedCart=lc.get(position);
        int imgId = context.getResources().getIdentifier(selectedCart.getImage(), "drawable", context.getPackageName());
        holder.ImageView.setImageResource(imgId);
        holder.TextView.setText(selectedCart.getProductName());
        holder.EditText.setText(selectedCart.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return lc.size();
    }
    public class CartItemView extends RecyclerView.ViewHolder {
        public android.widget.ImageView ImageView;
        public android.widget.TextView TextView;
        public android.widget.EditText EditText;
        public android.widget.Button Button;
        public CartItemView(@NonNull View itemView) {
            super(itemView);
            ImageView=itemView.findViewById(R.id.imageView);
            TextView=itemView.findViewById(R.id.textView7);
            EditText=itemView.findViewById(R.id.editTextNumber);
            EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                }
            });
            Button=itemView.findViewById(R.id.button);
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
