package com.example.technews.network

import com.example.technews.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("/login")
    suspend fun signIn(@Body login : Login): Response<User>

    @POST("/register")
    suspend fun signUp(@Body user : User): Response<User>

    @GET("/news/all")
    suspend fun getAllNews(): Response<MutableList<News>>

    @GET("/events/all")
    suspend fun getAllEvents(): Response<MutableList<Event>>

    @GET("/products/all")
    suspend fun getAllProducts(): Response<MutableList<Product>>

    @GET("/users")
    suspend fun getAllUsers(): Response<MutableList<User>>

    @GET("/messages/all")
    suspend fun getAllMessages(): Response<MutableList<Message>>

    @GET("/communities/all")
    suspend fun getAllCommunities(): Response<MutableList<Community>>


    @GET("/news/{id}/comments")
    suspend fun getAllComments(@Path("id") id:String): Response<MutableList<Comment>>


    @GET("/news/user/{id}")
    suspend fun getNewsByUserId(@Path("id") id:String): Response<MutableList<News>>


    @GET("/notifications/{id}")
    suspend fun getNotifications(@Path("id") id:String): Response<MutableList<Notification>>

    @Headers("Content-Type: application/json")
    @POST("/notifications/addNotification")
    suspend fun createNotification(@Body notification: NotificationRequestBody):Response<Void>


    @POST("/notifications/addNotification")
    suspend fun createNotification1(@Body notification:  JSONObject):Response<Void>



    @Multipart
    @POST("/news/create")
    suspend fun createNews(@Part("description")  description : RequestBody, @Part("creator") creator: RequestBody, @Part file: MultipartBody.Part): Response<Void>


    @Multipart
    @POST("/events/create")
    suspend fun createEvent(@Part("name")  name : RequestBody, @Part("eventDate") date: RequestBody, @Part("address") address: RequestBody, @Part("description") description: RequestBody, @Part("latitude") latitude:RequestBody, @Part("longitude") longitude:RequestBody, @Part file: MultipartBody.Part): Response<News>


    @Multipart
    @POST("/communities/create")
    suspend fun createCommunity(@Part("name")  name : RequestBody, @Part file: MultipartBody.Part): Response<Community>


    @POST("/news/addcomment")
    suspend fun createComment(@Body addCommentJsonBody: AddCommentJsonBody): Response<Comment>




    @Multipart
    @PUT("/updateUser")
    suspend fun updateUser(@Part("_id")id: RequestBody, @Part("name")name: RequestBody, @Part("email")email: RequestBody, @Part("password")password: RequestBody, @Part file: MultipartBody.Part ): Response<User>



}