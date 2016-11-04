package base.rechargeapp.appInterface;

import base.rechargeapp.beans.Login.LoginRequest;
import base.rechargeapp.beans.PostRequest.Example;
import base.rechargeapp.beans.Recharge.RechargeRequest;
import base.rechargeapp.beans.Recharge.RechargeResponse.RechargeResponse;
import base.rechargeapp.beans.Register.RegisterRequest;
import base.rechargeapp.beans.Register.Response.RegisterResponse;
import base.rechargeapp.beans.StackOverflowQuestions;
import base.rechargeapp.beans.changePassword.ChangePasswordRequest;
import base.rechargeapp.beans.changePassword.ChangePasswordResponse;
import base.rechargeapp.beans.commission.RequestCommission;
import base.rechargeapp.beans.commission.Response.ResponseCommission;
import base.rechargeapp.beans.forgot_password.RequestForgotPassword;
import base.rechargeapp.beans.forgot_password.ResponseForgotPassword;
import base.rechargeapp.beans.profile.ProfileRequest;
import base.rechargeapp.beans.serviceProvider.ServiceProviderRequest;
import base.rechargeapp.beans.serviceProvider.ServiceProviderResponse.ResponseServiceProvider;
import base.rechargeapp.beans.transaction_list.RequestTransactionList;
import base.rechargeapp.beans.transaction_list.response.ResponseTransactionList;
import base.rechargeapp.utils.NetworkConstant;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lin on 2/8/16.
 */

public interface RetrofitInterface {

    // Registration API
    @POST(NetworkConstant.REGISTER)
    Call<RegisterResponse> hitRegisterAPI(@Body RegisterRequest mRegisterBean);

    // Login API
    @POST(NetworkConstant.LOGIN)
    Call<RegisterResponse> hitLoginAPI(@Body LoginRequest mLoginBean);


    // changePassword API
    @POST(NetworkConstant.CHANGE_PASSWORD)
    Call<ChangePasswordResponse> hitChangePasswordApi(@Body ChangePasswordRequest mChangePasswordBean);


    // Profile API
    @POST(NetworkConstant.PROFILE)
    Call<RegisterResponse> hitProfileAPI(@Body ProfileRequest mProfileRequestData);


    // Service Provider with their packs API
    @POST(NetworkConstant.SERVICE_PROVIDER)
    Call<ResponseServiceProvider> hitGetServiceProviderWithPacks(@Body ServiceProviderRequest mServiceProviderRequest);

    // Recharge Api
    @POST(NetworkConstant.RECHARGE)
    Call<RechargeResponse> hitRechargeApi(@Body RechargeRequest mRechargeRequest);

    // Get Search History
    @POST(NetworkConstant.TRANSACTION_LIST)
    Call<ResponseTransactionList> hitTransactionList(@Body RequestTransactionList mTransactionList);

    // Get forgot password
    @POST(NetworkConstant.FORGOT_PASSWORD)
    Call<ResponseForgotPassword> hitForgotPassword(@Body RequestForgotPassword mForgotPassword);

    // Get Commission record
    @POST(NetworkConstant.COMMISSION)
    Call<ResponseCommission> hitGetCommission(@Body RequestCommission mRequestCommission);


    // Registration API Form URL ENCODED
    @FormUrlEncoded
    @POST(NetworkConstant.REGISTER)
    Call<RegisterResponse> hitRegisterAPIFields(@Field("first_name") String mFirstName,
                                                @Field("last_name") String last_name,
                                                @Field("email") String email,
                                                @Field("business_type") String business_type,
                                                @Field("rfc") String rfc,
                                                @Field("address") String address,
                                                @Field("password") String password,
                                                @Field("mob_no") String mob_no,
                                                @Field("secret_key") String secret_key,
                                                @Field("api_key") String api_key);


    // Get RegisterRequest
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);

    // Post RegisterRequest
    @FormUrlEncoded
    @POST("/contentaggregate/Services/edition_and_category")
    Call<Example> hitService(@Field("userId") String userId);
}
