package com.example.daraltebassistant.util;

import com.example.daraltebassistant.model.Appointment;
import com.example.daraltebassistant.model.Clinicinfo;
import com.example.daraltebassistant.model.Time;
import com.example.daraltebassistant.model.User;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.retrofit.RetrofitClint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Common {


    //public static String BASE_URL ="http://heyaclinic.brandoctor.net/final/index.php/api/";
    public static String BASE_URL ="http://192.168.56.1/heya/index.php/api/" ;
    public static Clinicinfo currentClinic=null;
    public static User currentUser=null;
    public static Appointment currentAppointment=null;
    static Calendar calender = Calendar.getInstance(TimeZone.getDefault());
    private static int  c_day = calender.get(Calendar.DAY_OF_MONTH);
    private static int c_month = calender.get(Calendar.MONTH);
    private static int c_year = calender.get(Calendar.YEAR);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
    SimpleDateFormat df2 = new SimpleDateFormat("HH:mm aa", Locale.ENGLISH);
    public static String currentDate = c_year + "-" + (c_month + 1) + "-" + c_day;
    public static Time currentTimeSlot=null;


    public static RestFullApi getApi(){
        return RetrofitClint.getClient(BASE_URL).create(RestFullApi.class);
    }
    }
