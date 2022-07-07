package com.fpt.prm391groupproject.DAO;
import com.fpt.prm391groupproject.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDAO {
    private final String dbName = "Product";
    private final String productName = "ProductName";
    private final String productPrice = "Price";
    private final String productQuantity = "Quantity";
    DatabaseReference myRef;

    public ProductDAO() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(dbName);
    }

    public void addProduct(Product p){
        String id = myRef.push().getKey();
        myRef.child(id).setValue(p);
    }

    public void updateProduct(Product p) {
        myRef.child(p.getId()).child(productName).setValue(p.getProductName());
        myRef.child(p.getId()).child(productPrice).setValue(p.getPrice());
        myRef.child(p.getId()).child(productQuantity).setValue(p.getQuantity());
    }

    public void deleteProduct(String id) {
        myRef.child(id).removeValue();
    }
}
