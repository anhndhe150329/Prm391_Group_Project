package com.fpt.prm391groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.OrderDetail;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailItemView> {
    private List<OrderDetail> lc;
    private Context context;
    private FragmentActivity fragmentActivity;
    public OrderDetailAdapter(List<OrderDetail> lc,FragmentActivity fragmentActivity){
        this.lc=lc;
        this.fragmentActivity=fragmentActivity;
    }

    @NonNull
    @Override
    public OrderDetailItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail,parent,false);
        return new OrderDetailItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailItemView holder, int position) {
        OrderDetail selectedCart=lc.get(position);
        int imgId = context.getResources().getIdentifier(selectedCart.getImage(), "drawable", context.getPackageName());
        holder.ImageView.setImageResource(imgId);
        holder.TextView.setText(selectedCart.getProductName());
        holder.EditText.setText(selectedCart.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return lc.size();
    }
    public class OrderDetailItemView extends RecyclerView.ViewHolder {
        public android.widget.ImageView ImageView;
        public android.widget.TextView TextView;
        public android.widget.EditText EditText;
        public OrderDetailItemView(@NonNull View itemView) {
            super(itemView);
            ImageView=itemView.findViewById(R.id.imageViewa);
            TextView=itemView.findViewById(R.id.textViewa);
            EditText=itemView.findViewById(R.id.editTextNumbera);
        }
    }
}
