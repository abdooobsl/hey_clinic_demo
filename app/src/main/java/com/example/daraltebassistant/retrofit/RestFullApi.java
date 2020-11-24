package com.example.daraltebassistant.retrofit;


import com.example.daraltebassistant.model.AppointmentData;
import com.example.daraltebassistant.model.CancelAppointment;
import com.example.daraltebassistant.model.Clinic;
import com.example.daraltebassistant.model.Confirmation;
import com.example.daraltebassistant.model.Message;
import com.example.daraltebassistant.model.Time;
import com.example.daraltebassistant.model.TimeSlot;
import com.example.daraltebassistant.model.UserData;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestFullApi {
    @GET("get_services")
    Observable<Clinic> get_services();

    @FormUrlEncoded
    @POST("get_appointments")
    Observable<AppointmentData> get_appointments(@Field("b_id") String b_id,
                                                 @Field("from") String from,
                                                 @Field("to") String to);

    @FormUrlEncoded
    @POST("login_assistant")
    Observable<UserData> login(@Field("user_email")String user_email, @Field("user_password") String user_password);




    @FormUrlEncoded
    @POST("update_appointment")
    Observable<Message> updateStatus(@Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("get_time_slot")
    Observable<TimeSlot> get_time_slot(@Field("b_id") String b_id, @Field("date") String date);

    @FormUrlEncoded
    @POST("cancel_appointment")
    Observable<CancelAppointment> cancel_appointment(@Field("user_id") String user_id, @Field("app_id") String app_id);
    @FormUrlEncoded
    @POST("add_appointment")
    Observable<Confirmation> add_appointment(@Field("user_id") String user_id,
                                             @Field("user_fullname") String user_fullname,
                                             @Field("user_email") String user_email,
                                             @Field("user_phone") String user_phone,
                                             @Field("appointment_date") String appointment_date,
                                             @Field("start_time") String start_time,
                                             @Field("time_token") String time_token,
                                             @Field("b_id") String b_id);
}
