package com.fpt.prm391groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.prm391groupproject.fragment.OrderDetailFragment;
import com.fpt.prm391groupproject.fragment.ProductFragment;
import com.fpt.prm391groupproject.model.Cart;
import com.fpt.prm391groupproject.model.Order;
import com.fpt.prm391groupproject.model.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemView>{
    private List<Order> lo;
    private Context context;
    private FirebaseAuth auth;
    private FragmentActivity fragmentActivity;

    public OrderAdapter(List<Order> products, FragmentActivity fragmentActivity) {
        this.lo= products;
        this.fragmentActivity = fragmentActivity;
    }
    @NonNull
    @Override
    public OrderItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        auth = FirebaseAuth.getInstance();
        View v = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderItemView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemView holder, int position) {
        Order selectedOrder=lo.get(position);

        holder.TextView.setText(selectedOrder.getOrderId()+"");
        holder.TextView1.setText(selectedOrder.getOrderDate()+"");
    }

    @Override
    public int getItemCount() {
        return lo.size();
    }

    public class OrderItemView extends RecyclerView.ViewHolder {
        public android.widget.TextView TextView;
        public android.widget.TextView TextView1;
        public OrderItemView(@NonNull View itemView) {
            super(itemView);
            TextView=itemView.findViewById(R.id.textView4);
            TextView1=itemView.findViewById(R.id.textView5);
            TextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame,new OrderDetailFragment(TextView.getText().toString()));
                    transaction.commit();
                }
            });
        }
    }
}
