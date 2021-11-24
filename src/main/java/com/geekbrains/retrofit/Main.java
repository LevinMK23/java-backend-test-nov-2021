package com.geekbrains.retrofit;

import java.io.IOException;

import com.geekbrains.retrofit.api.MiniMarketApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
    public static void main(String[] args) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMarketApi api = retrofit.create(MiniMarketApi.class);
    }
}