package me.prasheelsoni.bookmyspace;

import java.util.List;

import me.prasheelsoni.bookmyspace.pojo.LoginRegisterPojo;
import me.prasheelsoni.bookmyspace.pojo.ParkingSlot;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ps11 on 02/12/16.
 */

public interface NetworkCalls {

    @FormUrlEncoded
    @POST("/register.php/")
    Call<LoginRegisterPojo> register(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("phone") String phone, @Field("aadhar") String aadhar);

    @FormUrlEncoded
    @POST("/login.php/")
    Call<LoginRegisterPojo> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/getAvailableParkings.php/")
    Call<List<ParkingSlot>> getAvailableParkings(@Field("lat")String latitude, @Field("longi") String longitude);

    @FormUrlEncoded
    @POST("/getSelectedSlotDetails.php/")
    Call<ParkingSlot> getSealectedSlotDetails(@Field("name")String name);


}
