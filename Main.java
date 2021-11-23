package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.MiniMarketApi;
import com.geekbrains.retrofit.model.Product;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMarketApi api = retrofit.create(MiniMarketApi.class);

//        Call<Object> productsCall = api.getProducts();// -> call вызов - может быть синхронным и асинхронным
        // синхронные вызовы блокируют дальнейшее выполнение программы (execute)
        // асинхронные - не блокируют (enqueue)

//        Response<Object> response = productsCall.execute();
//        ArrayList body = (ArrayList) response.body();
//        System.out.println(body.get(0).getClass());
//        System.out.println(body);
//
//        api.getProducts().enqueue(new Callback<Object>() {
//
//            @Override
//            public void onResponse(Call<Object> var1, Response<Object> var2) {
//                System.out.println(var2.body());
//            }
//
//            @Override
//            public void onFailure(Call<Object> var1, Throwable var2) {
//
//            }
//        });
        Response<List<Product>> response = api.getProducts().execute();

        List<Product> products = response.body();

        System.out.println(products);

        Response<Product> response1 = api.getProduct(100L).execute();

        System.out.println(response1);

    }
}
