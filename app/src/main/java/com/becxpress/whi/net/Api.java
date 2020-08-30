package com.becxpress.whi.net;

import com.becxpress.whi.model.Cart;
import com.becxpress.whi.model.CartApiResponse;
import com.becxpress.whi.model.CustomerOrderApiResponse;
import com.becxpress.whi.model.Favorite;
import com.becxpress.whi.model.FavoriteApiResponse;
import com.becxpress.whi.model.LoginApiResponse;
import com.becxpress.whi.model.Order;
import com.becxpress.whi.model.Ordering;
import com.becxpress.whi.model.Otp;
import com.becxpress.whi.model.ProductApiResponse;
import com.becxpress.whi.model.RegisterApiResponse;
import com.becxpress.whi.model.Review;
import com.becxpress.whi.model.ReviewApiResponse;
import com.becxpress.whi.model.Shipping;
import com.becxpress.whi.model.StatusApiResponse;
import com.becxpress.whi.model.User;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Api {


    @Headers("Content-Type: application/json")
    @POST("android/api/createUser.php")
    Call<RegisterApiResponse> createUser(@Body User user);

    @Headers("Cache-control:no-cache")
    @GET("android/api/logInUser.php")
    Call<LoginApiResponse> logInUser(@Query("email") String email, @Query("password") String password);


    @FormUrlEncoded
    @POST("android/api/updatePassword.php")
    Call<ResponseBody> updatePassword(@Field("password") String password, @Field("userId") int userId);

    @Multipart
    @POST("android/api/insertProduct.php")
    Call<ResponseBody> insertProduct(@PartMap Map<String, RequestBody> productInfo, @Part MultipartBody.Part image);

    @Headers("Cache-control:no-cache")
    @GET("android/api/getOtp.php")
    Call<Otp> getOtp(@Query("email") String email);

//    @GET("products")
//    Call<ProductApiResponse> getProducts(@Query("page") int page);

    @GET("android/api/getProductsByCategory.php")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category, @Query("userId") int userId,@Query("page") int page);

    @Headers("Cache-control:no-cache")
    @GET("android/api/searchForProduct.php")
    Call<ProductApiResponse> searchForProduct(@Query("q") String keyword, @Query("userId") int userId);

    @Headers("Content-Type: application/json")
    @POST("android/api/addFavorite.php")
    Call<ResponseBody> addFavorite(@Body Favorite favorite);

    @Headers("Content-Type: application/json")
    @POST("android/api/addOrder.php")
    Call<ResponseBody> addOrder(@Body Order order);

    @FormUrlEncoded
    @POST("android/api/removeFavorite.php")
    Call<ResponseBody> removeFavorite(@Field("userId") int userId, @Field("productId") int productId);

    @Headers("Cache-control:no-cache")
    @GET("android/api/getFavorites.php")
    Call<FavoriteApiResponse> getFavorites(@Query("userId") int userId);

    @POST("android/api/addToCart.php")
    Call<ResponseBody> addToCart(@Body Cart cart);

    @FormUrlEncoded
    @POST("android/api/removeFromCart.php")
    Call<ResponseBody> removeFromCart(@Field("userId") int userId, @Field("productId") int productId);


    @Headers("Cache-control:no-cache")
    @GET("android/api/getProductsInCart.php")
    Call<CartApiResponse> getProductsInCart(@Query("userId") int userId);


    @POST("android/api/addReview.php")
    Call<ResponseBody> addReview(@Body Review review);

    @Headers("Cache-control:no-cache")
    @GET("android/api/getAllReviews.php")
    Call<ReviewApiResponse> getAllReviews(@Query("productId") int productId);

    @Headers("Cache-control:no-cache")
    @GET("android/api/getOrders.php")
    Call<CustomerOrderApiResponse> getOrders(@Query("userId") int userId,@Query("track") boolean track);

    @POST("android/api/addShippingAddress.php")
    Call<ResponseBody> addShippingAddress(@Body Shipping shipping);

    @POST("android/api/orderProduct.php")
    Call<ResponseBody> orderProduct(@Body Ordering ordering);

    @Headers("Cache-control:no-cache")
    @GET("android/api/isEmailExist.php")
    Call<Otp> isEmailExist(@Query("email") String email);

    @Headers("Cache-control:no-cache")
    @GET("android/api/isAccountExist.php")
    Call<Otp> isAccountExist(@Query("email") String email);

    @Headers("Cache-control:no-cache")
    @GET("android/api/getProductsOfOrder.php")
    Call<StatusApiResponse> getProductsOfOrder(@Query("orderNumber") String orderNumber);

    @FormUrlEncoded
    @POST("android/api/updatePaymentInfo.php")
    Call<ResponseBody> updatePaymentInfo(@Field("orderId") String orderId, @Field("paymentId") String paymentId, @Field("email") String email, @Field("amount") float amount);
}
