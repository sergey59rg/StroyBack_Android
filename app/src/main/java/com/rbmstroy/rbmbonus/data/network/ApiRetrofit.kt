package com.rbmstroy.rbmbonus.data.network

import com.rbmstroy.rbmbonus.model.*
import io.reactivex.Single
import retrofit2.http.*

interface ApiRetrofit {

    @FormUrlEncoded
    @POST("AuthController.php")
    fun auth(
        @FieldMap params: HashMap<String?, String?>
    ): Single<AuthResponse>

    @FormUrlEncoded
    @POST("RegisterController.php")
    fun register(
        @FieldMap params: HashMap<String?, String?>
    ): Single<RegisterResponse>

    @FormUrlEncoded
    @POST("ConfirmController.php")
    fun confirm(
        @FieldMap params: HashMap<String?, String?>
    ): Single<ConfirmResponse>

    @FormUrlEncoded
    @POST("ForgottenPhoneController.php")
    fun forgotten(
        @FieldMap params: HashMap<String?, String?>
    ): Single<ForgottenResponse>

    @FormUrlEncoded
    @POST("UpdatePassController.php")
    fun update(
        @FieldMap params: HashMap<String?, String?>
    ): Single<UpdateResponse>

    @GET("UserController.php")
    fun user(
        @Query("token") token: String
    ): Single<UserResponse>

    @GET("EventsController.php")
    fun history(
        @Query("token") token: String
    ): Single<HistoryResponse>

    @GET("getCardList.php")
    fun card(
    ): Single<BuyResponse>

    @FormUrlEncoded
    @POST("ShopCardController.php")
    fun shop(
        @FieldMap params: HashMap<String?, String?>
    ): Single<ShopResponse>

    @GET("productPrice.php")
    fun product(
    ): Single<AwardsResponse>

    @GET("getCardRequements.php")
    fun confirmations(
        @Query("token") token: String
    ): Single<ConfirmationsResponse>

    @FormUrlEncoded
    @POST("AcceptCardStatus.php")
    fun accept(
        @FieldMap params: HashMap<String?, String?>
    ): Single<AcceptResponse>

    @FormUrlEncoded
    @POST("ScanQrCode.php")
    fun scan(
        @FieldMap params: HashMap<String?, String?>
    ): Single<ScanResponse>

    @GET("stokGet.php")
    fun promotion(
        @Query("id") id: Int
    ): Single<PromotionResponse>

    @GET("version.php")
    fun version(
    ): Single<VersionResponse>
}