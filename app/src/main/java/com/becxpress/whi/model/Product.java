package com.becxpress.whi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("quantity")
    private int orderQuantity;
    @SerializedName("product_quantity")
    private int productQuantity;
    @SerializedName("supplier")
    private String productSupplier;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("image")
    private String productImage;
    @SerializedName("isFavourite")
    private int isFavourite;
    @SerializedName("isInCart")
    private int isInCart;
    @SerializedName("description")
    private String description;
    // Include child Parcelable objects
    private Product mInfo;

    public Product(String productName, double productPrice, int productQuantity, String productSupplier, String productCategory,String description) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
        this.description = description;
    }

    public Product(String productName, double productPrice, int productQuantity, String productSupplier, String productCategory,String description,int orderQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
        this.description = description;
        this.orderQuantity = orderQuantity;
    }

    public Product() {

    }

    public int getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductImage() {
        return  "android"+productImage.substring(2);
    }

    public String getDescription() {
        return description;
    }

    public int isFavourite() {
        return isFavourite;
    }

    public int isInCart() {
        return isInCart;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite ? 1 : 0;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart ? 1 : 0;
    }

    // Write the values to be saved to the `Parcel`.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(productId);
        out.writeString(productName);
        out.writeString(description);
        out.writeDouble(productPrice);
        out.writeInt(productQuantity);
        out.writeString(productSupplier);
        out.writeString(productCategory);
        out.writeString(productImage);
        out.writeInt(isFavourite);
        out.writeInt(isInCart);
        out.writeParcelable(mInfo, flags);
    }

    // Retrieve the values written into the `Parcel`.
    private Product(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        description = in.readString();
        productPrice = in.readDouble();
        productQuantity = in.readInt();
        productSupplier = in.readString();
        productCategory = in.readString();
        productImage = in.readString();
        isFavourite = in.readInt();
        isInCart = in.readInt();
        mInfo = in.readParcelable(Product.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Create the Parcelable.Creator<Product> CREATOR` constant for our class;
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {

        // This simply calls our new constructor and
        // passes along `Parcel`, and then returns the new object!
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}