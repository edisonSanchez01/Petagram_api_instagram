package com.edisondeveloper.petagram.Modelo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndPointsApi {

    @GET(Constantes.URL_GET_MEDIA_USER)
    Call<MediaResponse> getMediaUser();
}
