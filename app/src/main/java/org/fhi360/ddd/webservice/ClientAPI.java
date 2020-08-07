package org.fhi360.ddd.webservice;


import org.fhi360.ddd.domain.Account;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Response;
import org.fhi360.ddd.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientAPI {

    @Headers("Content-Type: application/json")
    @GET("resources/webservice/mobile/activate/{deviceId}/{pin}")
    Call<User> getUsernamePasswordFromLamis(@Path("deviceId") String deviceId, @Path("pin") String pin);


    @Headers("Content-Type: application/json")
    @GET("resources/webservice/mobile/activate/{userName}/{pin}/{deviceId}/{accountUserName}/{accountPassword}")
    Call<User> activateCPARP(@Path("userName") String userName, @Path("pin") String pin, @Path("deviceId") String deviceId, @Path("accountUserName") String accountUserName, @Path("accountPassword") String accountPassword);

    //
//    @Headers("Content-Type: application/json")
//    @POST("resources/webservice/mobile/sync/monitor")
//    Call<Object> syncMonitor(@Body MonitorList monitorList);
//
//
//    @Headers("Content-Type: application/json")
//    @POST("resources/webservice/mobile/sync/encounter")
//    Call<Object> syncEncounter(@Body EncounterList encounterList);
//
    @Headers("Content-Type: application/json")
    @POST("api/hts/mobile/ddd/account")
    Call<Account> saveAccount(@Body Account account);

    @Headers("Content-Type: application/json")
    @POST("api/hts/mobile/ddd/sync")
    Call<Response> sync(@Body List<Patient> patient);
//
//    @Headers("Content-Type: application/json")
//    @POST("resources/webservice/mobile/sync/drugtherapy")
//    Call<Object> syncDrugtherapy(@Body DrugtherapyList drugtherapyList);
//
//    @Headers("Content-Type: application/json")
//    @POST("resources/webservice/mobile/sync/appointment")
//    Call<Object> syncAppointment(@Body AppointmentList appointmentList);
//
//    @Headers("Content-Type: application/json")
//    @POST("resources/webservice/mobile/sync/mhtc")
//    Call<Object> syncMhtc(@Body MhtcList mhtcList);
}
