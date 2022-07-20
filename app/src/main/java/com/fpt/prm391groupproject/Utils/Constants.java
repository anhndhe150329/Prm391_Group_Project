package com.fpt.prm391groupproject.Utils;

public class Constants {

    public static class FireBaseProductTable{
        public static final String dbName = "Product";
        public static final String productName = "ProductName";
        public static final String productPrice = "Price";
        public static final String productQuantity = "Quantity";
        public static final String productImage = "Image";
        public static final String productCategory = "CategoryName";
        public static final String productDescription = "Description";
        public static final String productFavourite = "Favourite";
    }

    public static class FireBaseUserTable{
        public static final String dbName = "User";
        public static final String userName = "Name";
        public static final String userEmail = "Email";
        public static final String userId = "UserId";
        public static final String userPhone = "Phone";
        public static final String userAddress = "Address";
        public static final String userAge = "Age";
    }

    public static class SQLiteCategoryTable{
        public static final String tableName = "Category";
        public static final String id = "id";
        public static final String name = "name";
    }
}
