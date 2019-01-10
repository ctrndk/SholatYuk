package com.dolanandev.sholatyuk.api;

import com.dolanandev.sholatyuk.model.ModelJadwal;
import  retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("daily.json?key=7e7857aeb9d8a75666b6e04ec7da7718")
    Call<ModelJadwal> getJadwal();
}
