package com.example.trade_game.domain

import com.example.trade_game.domain.models.AuthResponse
import com.example.trade_game.domain.models.CancelOrderResponse
import com.example.trade_game.domain.models.ChatsResponse
import com.example.trade_game.domain.models.CreateOrderResponse
import com.example.trade_game.domain.models.EventsResponse
import com.example.trade_game.domain.models.ExecuteOrderResponse
import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.MarketTransactionRequest
import com.example.trade_game.domain.models.MarketTransactionResponse
import com.example.trade_game.domain.models.OrderDataRequest
import com.example.trade_game.domain.models.OrderIdRequest
import com.example.trade_game.domain.models.OrdersResponse
import com.example.trade_game.domain.models.PriceHistoryResponse
import com.example.trade_game.domain.models.RefreshRequest
import com.example.trade_game.domain.models.RegisterRequest
import com.example.trade_game.domain.models.TradeHistoryResponse
import com.example.trade_game.domain.models.UserAssetsResponse
import com.example.trade_game.domain.models.UserInfoResponse
import com.example.trade_game.domain.models.UserPlaceResponse
import com.example.trade_game.domain.models.UsersTopResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @POST("api/v1/auth/register")
    suspend fun register(@Body registerReq: RegisterRequest): AuthResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginReq: LoginRequest): AuthResponse

    @POST("/api/v1/auth/refresh")
    suspend fun refresh(@Body refreshReq: RefreshRequest): AuthResponse

    @GET("/api/v1/events")
    suspend fun events(@Query("limit") limit: Int, @Query("offset") offset: Int?): EventsResponse

    @GET("/api/v1/users/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserInfoResponse

    @GET("/api/v1/users/top")
    suspend fun getUsersTop(@Query("limit") limit: Int): UsersTopResponse

    @GET("/api/v1/users/place")
    suspend fun getUserPlace(@Query("user_id") userId: Int): UserPlaceResponse

    @GET("/api/v1/user/assets/{user_id}")
    suspend fun getUserAssets(@Path("user_id") userId: Int): UserAssetsResponse

    @GET("/api/v1/trades/history/{user_id}")
    suspend fun getUserTradeHistory(@Path("user_id") userId: Int): TradeHistoryResponse

    @GET("/api/v1/orders/{asset_id}/{user_id}")
    suspend fun getOrders(
        @Path("asset_id") assetId: Int,
        @Path("user_id") userId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int? = null,
        @Query("status") status: String? = null,
        @Query("order_type") orderType: String? = null
    ): OrdersResponse

    @GET("/api/v1/orders/{asset_id}")
    suspend fun getOrders(
        @Path("asset_id") assetId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int? = null,
        @Query("status") status: String? = null,
        @Query("order_type") orderType: String? = null
    ): OrdersResponse

    @POST("/api/v1/order/sell")
    suspend fun sellOrder(
        @Body orderData: OrderIdRequest,
        @Header("Authorization") token: String
    ): ExecuteOrderResponse

    @POST("/api/v1/order/buy")
    suspend fun buyOrder(
        @Body orderData: OrderIdRequest,
        @Header("Authorization") token: String
    ): ExecuteOrderResponse

    @POST("/api/v1/order/create")
    suspend fun createOrder(
        @Body orderData: OrderDataRequest,
        @Header("Authorization") token: String
    ): CreateOrderResponse

    @POST("/api/v1/order/cancel")
    suspend fun cancelOrder(
        @Body orderData: OrderIdRequest,
        @Header("Authorization") token: String
    ): CancelOrderResponse

    @POST("/api/v1/market/buy")
    suspend fun marketBuy(
        @Body marketTransaction: MarketTransactionRequest,
        @Header("Authorization") token: String
    ): MarketTransactionResponse

    @POST("/api/v1/market/sell")
    suspend fun marketSell(
        @Body marketTransaction: MarketTransactionRequest,
        @Header("Authorization") token: String
    ): MarketTransactionResponse

    @GET("/api/v1/price/history/{asset_id}")
    suspend fun priceHistory(@Path("asset_id") assetId: Int): PriceHistoryResponse

    @GET("/api/v1/chats/list")
    suspend fun getChatsList(@Header("Authorization") token: String): ChatsResponse
}