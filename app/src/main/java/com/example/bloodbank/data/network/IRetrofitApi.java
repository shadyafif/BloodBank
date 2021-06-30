package com.example.bloodbank.data.network;

import com.example.bloodbank.data.model.BloodTypesDetails.BloodType;
import com.example.bloodbank.data.model.CitiesDetails.Cities;
import com.example.bloodbank.data.model.ContactData.ContactUs;
import com.example.bloodbank.data.model.DonationDetails.DonationsDetails;
import com.example.bloodbank.data.model.GovernoratesDetails.Governorates;
import com.example.bloodbank.data.model.Login.login;
import com.example.bloodbank.data.model.NotificationCountData.NotificationCount;
import com.example.bloodbank.data.model.NotificationData.NotificationDetails;
import com.example.bloodbank.data.model.PostsData.Posts;
import com.example.bloodbank.data.model.ProfileData.ProfileDetails;
import com.example.bloodbank.data.model.SettingDetails.SettingData;
import com.example.bloodbank.data.model.SignUpData.CreateUser;
import com.example.bloodbank.data.model.donationRequestCreate.DonationCreate;
import com.example.bloodbank.data.model.resetPassword.ResetPasswordData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitApi {
    //user
    @FormUrlEncoded
    @POST("login")
    Call<login> userLogin(
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("reset-password")
    Call<ResetPasswordData> PasswordReset(@Field("phone") String Phone);

    @FormUrlEncoded
    @POST("signup")
    Call<CreateUser> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("birth_date") String birth_date,
            @Field("city_id") String city_id,
            @Field("phone") String phone,
            @Field("donation_last_date") String donation_date,
            @Field("password") String password,
            @Field("password_confirmation") String confirm_password,
            @Field("blood_type_id") String blood_type_id
    );

    @FormUrlEncoded
    @POST("profile")
    Call<ProfileDetails> getProfileData(@Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("profile")
    Call<ProfileDetails> updateProfileData(@Field("api_token") String api_Token,
                                           @Field("name") String name,
                                           @Field("email") String email,
                                           @Field("birth_date") String birthday,
                                           @Field("phone") String phone,
                                           @Field("city_id") String City_id,
                                           @Field("blood_type_id") String blood_type_id,
                                           @Field("donation_last_date") String donation_date
    );


    //donation
    @GET("donation-requests")
    Call<DonationsDetails> GetDonationRequests(@Query("api_token") String api_token, @Query("page") int page);

    @FormUrlEncoded
    @POST("donation-request/create")
    Call<DonationCreate> createDonation(@Field("api_token") String api_token,
                                        @Field("patient_name") String patient_name,
                                        @Field("patient_age") String patient_age,
                                        @Field("blood_type_id") String blood_type,
                                        @Field("bags_num") String bags_num,
                                        @Field("hospital_name") String hospital_name,
                                        @Field("hospital_address") String hospital_address,
                                        @Field("city_id") String city_id,
                                        @Field("phone") String phone,
                                        @Field("notes") String notes,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude);


    //articles
    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token, @Query("page") int page);

    //General
    @GET("blood-types")
    Call<BloodType> getBloodType();

    @GET("governorates")
    Call<Governorates> getGovernorates();

    @GET("cities")
    Call<Cities> getCitiesById(@Query("governorate_id") int id);

    @GET("cities")
    Call<Cities> getCities();

    @GET("settings")
    Call<SettingData> getSetting(@Query("api_token") String api_token);

    @FormUrlEncoded
    @POST("contact")
    Call<ContactUs> getContact(@Field("api_token") String api_token,
                               @Field("title") String SmsTitle,
                               @Field("message") String SmsContent);

    //Notification

    @GET("notifications")
    Call<NotificationDetails> getNotification(@Query("api_token") String api_token,
                                              @Query("page") int page);

    @GET("notifications-count")
    Call<NotificationCount> getNotificationCount(@Query("api_token") String api_token);

}
