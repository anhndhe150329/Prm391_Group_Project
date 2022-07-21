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
        public static final String userName = "name";
        public static final String userEmail = "email";
        public static final String userId = "userId";
        public static final String userPhone = "phone";
        public static final String userAddress = "address";
        public static final String userAge = "age";
    }

    public static class SQLiteUserTable{
        public static final String tableName = "User";
        public static final String userId = "userId";
        public static final String email = "email";
        public static final String name = "name";
        public static final String phone = "phone";
        public static final String address = "address";
        public static final String age = "age";
    }

    public static class SQLiteCategoryTable{
        public static final String tableName = "Category";
        public static final String id = "id";
        public static final String name = "name";
    }

    public static class FireBaseWalletTable{
        public static final String dbName = "Wallet";
        public static final String userId = "UserId";
        public static final String money = "Money";
        public static final String point = "Point";
    }
}
