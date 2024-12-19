package com.example.proyecto.services;


import com.example.proyecto.models.Problema;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ProblemaService {
    @POST("game/problema")
    @Headers("Content-Type: application/json")
    Call<Void> denuncia(@Body Problema problema);
}
