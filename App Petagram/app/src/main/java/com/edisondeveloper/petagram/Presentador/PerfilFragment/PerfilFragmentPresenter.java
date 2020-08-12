package com.edisondeveloper.petagram.Presentador.PerfilFragment;

import android.content.Context;
import android.util.Log;

import com.edisondeveloper.petagram.Modelo.Constantes;
import com.edisondeveloper.petagram.Modelo.EndPointsApi;
import com.edisondeveloper.petagram.Modelo.MediaDeserializador;
import com.edisondeveloper.petagram.Modelo.MediaResponse;
import com.edisondeveloper.petagram.Modelo.MediaUser;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilFragmentPresenter {

    private Context context;
    private IPerfilFragmentPresenter iPerfilFragmentPresenter;

    public PerfilFragmentPresenter(Context context, IPerfilFragmentPresenter iPerfilFragmentPresenter) {
        this.context = context;
        this.iPerfilFragmentPresenter = iPerfilFragmentPresenter;
    }

    public void consumeServiceWeb(){
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(MediaResponse.class, new MediaDeserializador());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .build();
        EndPointsApi endPointsApi = retrofit.create(EndPointsApi.class);
        Call<MediaResponse> response = endPointsApi.getMediaUser();
        response.enqueue(new Callback<MediaResponse>() {
            @Override
            public void onResponse(Call<MediaResponse> call, Response<MediaResponse> response) {
                MediaResponse mediaResponse = response.body();

            }

            @Override
            public void onFailure(Call<MediaResponse> call, Throwable t) {
                 Log.i("Error en la respuesta", t.toString());
            }
        });
    }

}
