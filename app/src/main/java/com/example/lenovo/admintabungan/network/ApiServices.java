package com.example.lenovo.admintabungan.network;

import com.example.lenovo.admintabungan.response.ReponseRoute;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiServices {
    //https://maps.googleapis.com/maps/api/directions/
    // json?origin=Malang,ID&destination=Jakarta,ID&api_key=YOUR_API_KEY
    @GET("json")
    Call<ReponseRoute> request_route(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("api_key") String api_key
    );
}